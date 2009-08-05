/*
 * Project: Buddata ebXML RegRep
 * Class: BackendInvokeTask.java
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

import be.kzen.ergorr.commons.InternalConstants;
import be.kzen.ergorr.commons.RequestContext;
import be.kzen.ergorr.exceptions.ServiceException;
import be.kzen.ergorr.model.csw.InsertType;
import be.kzen.ergorr.model.csw.TransactionResponseType;
import be.kzen.ergorr.model.csw.TransactionType;
import be.kzen.ergorr.model.util.JAXBUtil;
import be.kzen.ergorr.model.util.OFactory;
import be.kzen.ergorr.persist.InternalSlotTypes;
import be.kzen.ergorr.persist.service.SqlPersistence;
import be.kzen.ergorr.service.TransactionService;
import java.io.File;
import java.io.FileFilter;
import java.util.logging.Level;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 *
 * @author yamanustuntas
 */
public class BackendInvokerTask extends Task {

    private String dataSrc;
    private InternalSlotTypes slotTypes;

    public BackendInvokerTask() {
        initSlots();
    }

    public void setDataSrc(String dataSrc) {
        this.dataSrc = dataSrc;
    }

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
            invoke(xmlFiles);
        }
    }

    public void invoke(File[] xmlFiles) throws BuildException {
        Unmarshaller unmarshaller = null;

        try {
            Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
            unmarshaller = JAXBUtil.getInstance().createUnmarshaller();
        } catch (JAXBException ex) {
            throw new BuildException("Could not create unmarshaller", ex);
        }

        for (File xmlFile : xmlFiles) {
            TransactionType request = new TransactionType();
            InsertType insert = new InsertType();

            try {
                JAXBElement jaxbEl = (JAXBElement) unmarshaller.unmarshal(xmlFile);
                insert.getAny().add(jaxbEl);
                request.getInsertOrUpdateOrDelete().add(insert);

                RequestContext context = new RequestContext();
                context.setRequest(request);
                TransactionService service = new TransactionService(context);
                TransactionResponseType response = service.process();

                String responseStr = JAXBUtil.getInstance().marshallToStr(OFactory.csw.createTransactionResponse(response));
                System.out.println(responseStr);
            } catch (ServiceException ex) {
                ex.printStackTrace();
            } catch (JAXBException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void initSlots() {
        slotTypes = InternalSlotTypes.getInstance();

        if (slotTypes.getSlotTypeSize() == 0) {
            RequestContext requestContext = new RequestContext();
            SqlPersistence persistence = new SqlPersistence(requestContext);

            try {
                slotTypes.loadSlots(persistence);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    class XmlFileFilter implements FileFilter {

        public boolean accept(File file) {
            return file.isFile() && file.getName().toLowerCase().endsWith(".xml");
        }
    }
}
