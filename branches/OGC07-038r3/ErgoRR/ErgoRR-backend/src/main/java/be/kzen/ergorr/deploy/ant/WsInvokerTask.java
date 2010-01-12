/*
 * Project: Buddata ebXML RegRep
 * Class: WsInvokerTask.java
 * Copyright (C) 2008 Yaman Ustuntas
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
package be.kzen.ergorr.deploy.ant;

import be.kzen.ergorr.interfaces.soap.csw.CswPortType;
import be.kzen.ergorr.interfaces.soap.csw.CswService;
import be.kzen.ergorr.interfaces.soap.csw.ServiceExceptionReport;
import be.kzen.ergorr.model.csw.InsertType;
import be.kzen.ergorr.model.csw.TransactionResponseType;
import be.kzen.ergorr.model.csw.TransactionType;
import be.kzen.ergorr.model.util.JAXBUtil;
import be.kzen.ergorr.model.util.OFactory;
import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * Ant task to insert RIM or other supported models through
 * the webservice interface.
 * 
 * @author yamanustuntas
 */
public class WsInvokerTask extends Task {

    private String url;
    private String dataSrc;
    private CswPortType service;

    /**
     * Set a folder path containing metadata XML files
     * or the path of a single metadata file.
     *
     * @param dataSrc Data source folder or file.
     */
    public void setDataSrc(String dataSrc) {
        this.dataSrc = dataSrc;
    }

    /**
     * URL of the deployed service.
     *
     * @param url Service URL.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() throws BuildException {
        File[] xmlFiles = null;
        File f = new File(dataSrc);
        XmlFileFilter filter = new XmlFileFilter();

        if (f.isDirectory()) {
            xmlFiles = f.listFiles(filter);
        } else if (f.isFile()) {

            if (filter.accept(f)) {
                xmlFiles = new File[]{f};
            } else {
                throw new BuildException("Not an XML file: " + dataSrc);
            }
        } else {
            throw new BuildException("Path does not exist: " + dataSrc);
        }

        if (xmlFiles.length > 0) {
            insert(xmlFiles);
        }
    }

    /**
     * Insert the list of XML metadata files into the registry.
     *
     * @param xmlFiles List of XML metadata files.
     * @throws BuildException
     */
    private void insert(File[] xmlFiles) throws BuildException {
        QName serviceQName = new QName("http://www.kzen.be/ergorr/interfaces/soap", "webservice");
        Unmarshaller unmarshaller = null;

        try {
            Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
            unmarshaller = JAXBUtil.getInstance().createUnmarshaller();
        } catch (JAXBException ex) {
            throw new BuildException("Could not create unmarshaller", ex);
        }

        service = new CswService(getURL(), serviceQName).getCswPort();

        for (File xmlFile : xmlFiles) {
            TransactionType t = new TransactionType();
            InsertType insert = new InsertType();

            try {
                JAXBElement jaxbEl = (JAXBElement) unmarshaller.unmarshal(xmlFile);
                insert.getAny().add(jaxbEl);

                t.getInsertOrUpdateOrDelete().add(insert);
                TransactionResponseType response = service.cswTransaction(t);

                String responseStr = JAXBUtil.getInstance().marshallToStr(OFactory.csw.createTransactionResponse(response));
                System.out.println(responseStr);
            } catch (ServiceExceptionReport ex) {
                ex.printStackTrace();
            } catch (JAXBException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Create a {@code URL} instance from {@code url} String.
     *
     * @return URL of the service.
     * @throws BuildException
     */
    private URL getURL() throws BuildException {
        URL serviceUrl = null;
        try {
            serviceUrl = new URL(url);
        } catch (MalformedURLException ex) {
            throw new BuildException("invalid URL: " + url);
        }

        return serviceUrl;
    }

    /**
     * File filter for XML files.
     */
    class XmlFileFilter implements FileFilter {

        /**
         * {@inheritDoc}
         */
        public boolean accept(File file) {
            return file.isFile() && file.getName().toLowerCase().endsWith(".xml");
        }
    }
}