/*
 * Project: Buddata ebXML
 * Class: LoadDBInstallListener.java
 * Copyright (C) 2009 Bram Van Dam
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package be.kzen.ergorr.installer.izpack.event;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import be.kzen.ergorr.commons.InternalConstants;
import be.kzen.ergorr.commons.RequestContext;
import be.kzen.ergorr.exceptions.ServiceException;
import be.kzen.ergorr.installer.common.Constants;
import be.kzen.ergorr.installer.common.Database;
import be.kzen.ergorr.model.csw.InsertType;
import be.kzen.ergorr.model.csw.TransactionResponseType;
import be.kzen.ergorr.model.csw.TransactionType;
import be.kzen.ergorr.model.util.JAXBUtil;
import be.kzen.ergorr.model.util.OFactory;
import be.kzen.ergorr.persist.service.DbConnectionParams;
import be.kzen.ergorr.service.TransactionService;

import com.izforge.izpack.event.SimpleInstallerListener;
import com.izforge.izpack.installer.AutomatedInstallData;
import com.izforge.izpack.util.AbstractUIProgressHandler;

/**
 * 
 * Database install listener: these methods are executed towards the end of the
 * installation. They load the default data in the database insert any data the
 * user may have specified.
 * 
 * @author Bram Van Dam - 4C Technologies
 * 
 *         TODO: Refactor.
 * 
 *         TODO: Remove magic numbers
 * 
 */
public class LoadDBInstallListener extends SimpleInstallerListener {

	private static final String[] DEFAULT_SQL_FILES = { "database.sql" };

	private static final String[] DEFAULT_XML_FILES = {
			"rim-objecttype-scheme.xml", "rim-datatype-scheme.xml",
			"rim-associationtype-scheme.xml", "rim-querylanguage-scheme.xml",
			"ergo.xml", "OGC-root-package.xml", "eo-rim-model.xml",
			"eo-slot-init.xml", "ISO19119-Services-Scheme.xml",
			"UNSD-Regions-Scheme.xml", "Basic-Package.xml",
			"basic-package-slot-init.xml", "CIM-CharacterSetScheme.xml",
			"CIM-CitedResponsiblePartyScheme.xml",
			"CIM-ClassificationCodeScheme.xml", "CIM-CouplingTypeScheme.xml",
			"CIM-DCPListScheme.xml", "CIM-FormatNameAndVersionScheme.xml",
			"CIM-KeywordScheme.xml", "CIM-KeywordTypeScheme.xml",
			"CIM-MetadataStandardNameAndVersionScheme.xml",
			"CIM-RestrictionCodeScheme.xml", "CIM-RestrictionTypeScheme.xml",
			"CIM-SpatialRepresentationScheme.xml",
			"CIM-TopicCategoryScheme.xml", "CIM-Package.xml" };

	private String dbUrl;
	private String dbName;
	private String dbUser;
	private String dbPassword;
	private String dataFileString;
	private boolean writeDefaults;

	private String[] dataFiles;

	public void afterPacks(AutomatedInstallData data,
			AbstractUIProgressHandler handler) throws Exception {
		super.afterPacks(data, handler);

		install(data);
	}

	private void install(AutomatedInstallData data) throws SQLException,
			IOException {
		mapParams(data);
		fixErgorrProperties(data);

		if (writeDefaults) {
			File defaultRoot = getDefaultModelDirectory(data);
			loadBaseModel(defaultRoot);
		}

		if (dataFiles != null && dataFiles.length > 0) {
			// Insert user specified XMl files
			File[] files = new File[dataFiles.length];
			for (int i = 0; i < dataFiles.length; i++) {
				files[i] = new File(dataFiles[i]);
			}
			invoke(files);
		}
	}

	/**
	 * Create the database tables and insert the basic model.
	 */
	private void loadBaseModel(File defaultRoot) throws SQLException,
			IOException {
		insertSQLData(defaultRoot);
		File[] files = getModelFiles(defaultRoot, DEFAULT_XML_FILES);
		invoke(files);
	}

	private void insertSQLData(File defaultRoot) throws SQLException,
			IOException {
		Database db = new Database(dbUrl, dbName, dbUser, dbPassword);
		for (File f : getModelFiles(defaultRoot, DEFAULT_SQL_FILES)) {
			db.executeSql(readFileAsString(f));
		}
	}

	private void invoke(File[] xmlFiles) {
		if (xmlFiles == null || xmlFiles.length == 0) {
			return;
		}

		Unmarshaller unmarshaller = null;

		try {
			Thread.currentThread().setContextClassLoader(
					getClass().getClassLoader());
			unmarshaller = JAXBUtil.getInstance().createUnmarshaller();
		} catch (JAXBException ex) {
			throw new RuntimeException("Could not create unmarshaller", ex);
		}

		for (File xmlFile : xmlFiles) {
			TransactionType request = new TransactionType();
			InsertType insert = new InsertType();

			try {
				JAXBElement jaxbEl = (JAXBElement) unmarshaller
						.unmarshal(xmlFile);
				insert.getAny().add(jaxbEl);
				request.getInsertOrUpdateOrDelete().add(insert);

				RequestContext context = getRequestContext();
				context.setRequest(request);
				TransactionService service = new TransactionService(context);
				TransactionResponseType response = service.process();

				String responseStr = JAXBUtil.getInstance().marshallToStr(
						OFactory.csw.createTransactionResponse(response));
			} catch (ServiceException ex) {
				ex.printStackTrace();
			} catch (JAXBException ex) {
				ex.printStackTrace();
			}
		}
	}

	private void mapParams(AutomatedInstallData aid) {
		dbUrl = aid.getVariable(Constants.URL_PARAM);
		dbName = aid.getVariable(Constants.DB_PARAM);
		dbUser = aid.getVariable(Constants.USER_PARAM);
		dbPassword = aid.getVariable(Constants.PASS_PARAM);
		dataFileString = aid.getVariable(Constants.DATAFILES_PARAM);
		writeDefaults = Constants.TRUE.equals(aid
				.getVariable(Constants.WRITE_DB_PARAM));

		if (dataFileString != null) {
			dataFiles = dataFileString.split(";");
		}
	}

	private File getDefaultModelDirectory(AutomatedInstallData aid) {
		File root = getInstallRoot(aid);
		return new File(root, Constants.MODEL_DIR);
	}

	private boolean isStandalone(AutomatedInstallData aid) {
		if (Constants.STANDALONE_TYPE.equals(aid
				.getVariable(Constants.INSTALLTYPE_PARAM))) {
			return true;
		}
		return false;
	}

	private File getInstallRoot(AutomatedInstallData aid) {
		String path;
		if (isStandalone(aid)) {
			path = aid.getVariable(Constants.BACKEND_INSTALL_PATH_PARAM);
		} else {
			path = String.format(Constants.DEPLOY_PATH, aid
					.getVariable(Constants.TOMCAT_INSTALL_PATH_PARAM), dbName);
		}
		return new File(path);
	}

	private File[] getModelFiles(File root, String[] filenames) {
		File[] files = new File[filenames.length];

		int i = 0;
		for (String f : filenames) {
			File file = new File(root, f);
			files[i++] = file;
		}
		return files;
	}

	private RequestContext getRequestContext() {
		RequestContext context = new RequestContext();
		DbConnectionParams connexion = new DbConnectionParams(dbUrl, dbName,
				dbUser, dbPassword);
		context.putParam(InternalConstants.DB_CONNECTION_PARAMS, connexion);
		return context;
	}

	private void fixErgorrProperties(AutomatedInstallData aid) {
		File root = getInstallRoot(aid);
		File ergorr;
		if (isStandalone(aid)) {
			ergorr = new File(root, Constants.ERGORR_PROPERTIES_FILE);
		} else {
			ergorr = new File(root, Constants.WEBAPP_CLASSPATH
					+ Constants.ERGORR_PROPERTIES_FILE);
		}
		System.setProperty(Constants.ERGORR_PROPERTY, ergorr.getAbsolutePath());
	}

	/**
	 * Read a file into a String.
	 * 
	 * @param file
	 *            File to read.
	 * @throws java.io.IOException
	 * @return File content as String.
	 */
	private String readFileAsString(File file) throws IOException {
		StringBuffer fileData = new StringBuffer();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		char[] buf = new char[2048];
		int count = 0;

		while ((count = reader.read(buf)) > 0) {
			fileData.append(String.valueOf(buf, 0, count));
			buf = new char[1024];
		}
		reader.close();
		return fileData.toString();
	}
}
