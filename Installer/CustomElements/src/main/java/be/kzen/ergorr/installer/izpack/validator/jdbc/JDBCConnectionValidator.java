/*
 * Project: Buddata ebXML
 * Class: JDBCConnectionValidator.java
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
package be.kzen.ergorr.installer.izpack.validator.jdbc;

import java.sql.SQLException;

import be.kzen.ergorr.installer.common.Constants;
import be.kzen.ergorr.installer.common.Database;

import com.izforge.izpack.installer.AutomatedInstallData;
import com.izforge.izpack.installer.DataValidator;

/**
 * Attempts to validate the presence of a JDBC driver and the correctness of
 * connexion parameters. The database will be created if it does not exist and
 * the template is present. If already present it may be dropped and recreated
 * from the template if the variable ERGO_DB_DROP is set.
 * 
 * Note that this class makes no attempt to sanitise any of the input.
 * 
 * @author Bram Van Dam - 4C Technologies
 * 
 */
public class JDBCConnectionValidator implements DataValidator {
	private static final String DB_DRIVER = "org.postgresql.Driver";
	private static final String VALIDATION_QUERY = "SELECT 1";
	private final static String MAIN_DB = "template1";

	private String dbUrl;
	private String dbName;
	private String dbUser;
	private String dbPassword;
	private String dbTemplate;
	private String dbDrop;
	private boolean writeDefaults;

	private String errorMsg;

	public Status validateData(AutomatedInstallData aid) {
		mapParams(aid);

		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException ex) {
			errorMsg = "Invalid connection parameters: Could not connect to PostgreSQL database.";
			return Status.ERROR;
		}

		if (!validateDb(MAIN_DB)) {
			errorMsg = "Invalid connection parameters: Could not connect to '"
					+ MAIN_DB + "' database.";
			return Status.ERROR;
		}

		if (!validateDb(dbTemplate)) {
			errorMsg = "Invalid connection parameters: Could not connect to '"
					+ dbTemplate + "' database.";
			return Status.ERROR;
		}

		if (validateDb(dbName)) {
			if (Constants.TRUE.equals(dbDrop)) {
				if (writeDefaults) {
					// Database exists and we want it dropped. Drop it!
					try {
						Database db = new Database(dbUrl, dbTemplate, dbUser,
								dbPassword);
						db.executeSql("DROP DATABASE " + dbName);
					} catch (SQLException se) {
						errorMsg = "Could not drop database '" + dbName + "'";
						return Status.ERROR;
					}
				}
			} else {
				errorMsg = "Invalid connection parameters: Database '" + dbName
						+ "' already exists.";
				return Status.ERROR;
			}
		}

		// Attempt to create the database
		if (writeDefaults) {
			try {
				Database db = new Database(dbUrl, dbTemplate, dbUser,
						dbPassword);
				db.executeSql("CREATE DATABASE " + dbName + " WITH OWNER "
						+ dbUser + " ENCODING = 'UTF8' TEMPLATE = "
						+ dbTemplate);
			} catch (SQLException se) {
				errorMsg = "Could not create database '" + dbName
						+ "' from template '" + dbTemplate + "'";
				return Status.ERROR;
			}
		}
		return Status.OK;
	}

	public boolean getDefaultAnswer() {
		return false;
	}

	public String getErrorMessageId() {
		return errorMsg;
	}

	public String getWarningMessageId() {
		return "";
	}

	private boolean validateDb(String db) {
		try {
			Database database = new Database(dbUrl, db, dbUser, dbPassword);
			database.executeSql(VALIDATION_QUERY);
		} catch (SQLException ex) {
			return false;
		}
		return true;
	}

	/**
	 * Map the ProcessingClient parameters to JDBCConnectionValidator
	 * properties.
	 */
	private void mapParams(AutomatedInstallData aid) {
		dbUrl = aid.getVariable(Constants.URL_PARAM);
		dbName = aid.getVariable(Constants.DB_PARAM);
		dbUser = aid.getVariable(Constants.USER_PARAM);
		dbPassword = aid.getVariable(Constants.PASS_PARAM);
		dbTemplate = aid.getVariable(Constants.TEMPLATE_PARAM);
		dbDrop = aid.getVariable(Constants.DROP_PARAM);
		writeDefaults = Constants.TRUE
				.equals(aid.getVariable(Constants.WRITE_DB_PARAM));
	}
}
