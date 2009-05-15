/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.kzen.ergorr.service;

import be.kzen.ergorr.commons.CommonProperties;
import be.kzen.ergorr.commons.IDGenerator;
import be.kzen.ergorr.commons.InternalConstants;
import be.kzen.ergorr.commons.RequestContext;
import be.kzen.ergorr.model.csw.InsertType;
import be.kzen.ergorr.model.csw.TransactionType;
import be.kzen.ergorr.model.rim.AssociationType;
import be.kzen.ergorr.model.rim.ClassificationType;
import be.kzen.ergorr.model.rim.ExternalIdentifierType;
import be.kzen.ergorr.model.rim.ExtrinsicObjectType;
import be.kzen.ergorr.model.rim.IdentifiableType;
import be.kzen.ergorr.model.rim.RegistryObjectListType;
import be.kzen.ergorr.model.util.JAXBUtil;
import be.kzen.ergorr.persist.service.DbConnectionParams;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author yamanustuntas
 */
public class TransactionServiceTest {

    public TransactionServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    @Ignore
    public void testProcess() throws Exception {
        DbConnectionParams dbConn = new DbConnectionParams();
        dbConn.setDbName(CommonProperties.getInstance().get("deployName"));
        dbConn.setDbPassword(CommonProperties.getInstance().get("db.password"));
        dbConn.setDbUrl(CommonProperties.getInstance().get("db.url"));
        dbConn.setDbUser(CommonProperties.getInstance().get("db.user"));
        RequestContext rc = new RequestContext();
        rc.putParam(InternalConstants.DB_CONNECTION_PARAMS, dbConn);
        
        Unmarshaller unmarshaller = JAXBUtil.getInstance().createUnmarshaller();
        long startTime = System.currentTimeMillis();
        File dir = new File("/Users/yamanustuntas/workspace/projects/ERGO/2008-final");
        File[] files = dir.listFiles();

        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            System.out.println(i + " " + file.getName());

            if (!file.getName().toLowerCase().endsWith(".xml")) {
                continue;
            }

            if (file.isFile()) {
                TransactionType t = new TransactionType();
                InsertType insert = new InsertType();

                JAXBElement jaxbEl = (JAXBElement) unmarshaller.unmarshal(file);
                RegistryObjectListType regObjList = (RegistryObjectListType) jaxbEl.getValue();

                Map<String, String> ids = new HashMap<String, String>();

                for (JAXBElement<? extends IdentifiableType> identEl : regObjList.getIdentifiable()) {
                    IdentifiableType ident = identEl.getValue();

                    String id = ids.get(ident.getId());

                    if (id == null) {
                        id = "" + IDGenerator.generateUuid() + IDGenerator.generateInt();
                        ids.put(ident.getId(), id);
                    }

                    ident.setId(id);

                    if (ident instanceof AssociationType) {
                        AssociationType a = (AssociationType) ident;
                        id = ids.get(a.getSourceObject());

                        if (id == null) {
                            id = "" + IDGenerator.generateUuid() + IDGenerator.generateInt();
                            ids.put(a.getSourceObject(), id);
                        }

                        a.setSourceObject(id);

                        id = ids.get(a.getTargetObject());

                        if (id == null) {
                            id = "" + IDGenerator.generateUuid() + IDGenerator.generateInt();
                            ids.put(a.getTargetObject(), id);
                        }

                        a.setTargetObject(id);

                    } else if (ident instanceof ClassificationType) {
                        ClassificationType c = (ClassificationType) ident;
                        id = ids.get(c.getClassifiedObject());

                        if (id == null) {
                            id = "" + IDGenerator.generateUuid() + IDGenerator.generateInt();
                            ids.put(c.getClassifiedObject(), id);
                        }

                        c.setClassifiedObject(id);
                    } else if (ident instanceof ExternalIdentifierType) {
                        ExternalIdentifierType e = (ExternalIdentifierType) ident;

                        id = ids.get(e.getRegistryObject());

                        if (id == null) {
                            id = "" + IDGenerator.generateUuid() + IDGenerator.generateInt();
                            ids.put(e.getRegistryObject(), id);
                        }

                        e.setRegistryObject(id);
                    } else if (ident instanceof ExtrinsicObjectType) {
                        ExtrinsicObjectType e = (ExtrinsicObjectType) ident;

                        e.setLid(id);
                    }
                }

                insert.getAny().add(jaxbEl);

//                System.out.println(JAXBUtil.getInstance().marshall(jaxbEl));

                t.getInsertOrUpdateOrDelete().add(insert);
                rc.setRequest(t);
                TransactionService tService = new TransactionService(rc);
                tService.process();
            }
        }

        System.out.println(System.currentTimeMillis() - startTime);
    }
}