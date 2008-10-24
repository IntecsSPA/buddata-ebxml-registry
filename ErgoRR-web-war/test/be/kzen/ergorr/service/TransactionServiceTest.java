package be.kzen.ergorr.service;

import be.kzen.ergorr.commons.RequestContext;
import be.kzen.ergorr.model.csw.TransactionType;
import be.kzen.ergorr.commons.FileUtil;
import be.kzen.ergorr.commons.IDGenerator;
import be.kzen.ergorr.model.csw.InsertType;
import be.kzen.ergorr.model.rim.AssociationType;
import be.kzen.ergorr.model.rim.ClassificationType;
import be.kzen.ergorr.model.rim.ExternalIdentifierType;
import be.kzen.ergorr.model.rim.ExtrinsicObjectType;
import be.kzen.ergorr.model.rim.IdentifiableType;
import be.kzen.ergorr.model.rim.RegistryObjectListType;
import be.kzen.ergorr.model.util.JAXBUtil;
import be.kzen.ergorr.persist.service.SqlPersistence;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Yaman Ustuntas
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
//        RimService service = new RimService();
//        RequestContext rc = new RequestContext();
//        rc.setRimDAO(service);
//        InputStream inStream = this.getClass().getClassLoader().getResourceAsStream("resources/transactionRequest1.xml");
//        String mock = FileUtil.streamToString(inStream);
//        Unmarshaller unmarshaller = JAXBUtil.getInstance().createUnmarshaller();
//
//        GregorianCalendar cal = new GregorianCalendar(2006, 1, 1);
//        double c1 = 1.0;
//        double c2 = 2.0;
//
//        for (int i = 0; i < 20000; i++) {
//            System.out.println(i);
//            XMLGregorianCalendar xmlCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
//            String s = mock.replaceFirst("!archivingDate!", xmlCal.toXMLFormat());
//            s = s.replaceFirst("!beginPosition!", xmlCal.toXMLFormat());
//            s = s.replaceFirst("!endPosition!", xmlCal.toXMLFormat());
//            String posList = "" + c1 + " " + c1 + " " + c2 + " " + c1 + " " + c2 + " " + c2 + " " + c1 + " " + c2 + " " + c1 + " " + c1;
//            String pos = "" + c1 + " " + c2;
//            s = s.replaceFirst("!posList!", posList);
//            s = s.replaceFirst("!pos!", pos);
//
//            JAXBElement jaxbEl = (JAXBElement) unmarshaller.unmarshal(new ByteArrayInputStream(s.getBytes()));
//            TransactionType transactionRequest = (TransactionType) jaxbEl.getValue();
//            rc.setRequest(transactionRequest);
//
//            TransactionService t = new TransactionService(rc);
//            t.process();
//
//            cal.add(GregorianCalendar.HOUR, 24);
//            c1 += 1.0;
//            c2 += 1.0;
//        }
//
//        service.closeEntityManagerFactory();
    }

    @Test
    @Ignore
    public void testProcess2() throws Exception {
        Unmarshaller unmarshaller = JAXBUtil.getInstance().createUnmarshaller();
        RequestContext rc = new RequestContext();
        long startTime = System.currentTimeMillis();
        File dir = new File("/home/yaman/workspace/projects/ERGO/2008-final");
        File[] files = dir.listFiles();

        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            System.out.println(i + " " + file.getName());

            if (file.isFile()) {
                TransactionType t = new TransactionType();
                InsertType insert = new InsertType();

                JAXBElement jaxbEl = (JAXBElement) unmarshaller.unmarshal(file);
                insert.getAny().add(jaxbEl);

                t.getInsertOrUpdateOrDelete().add(insert);
                rc.setRequest(t);
                TransactionService tService = new TransactionService(rc);
                tService.process();
            }
        }

        System.out.println(System.currentTimeMillis() - startTime);
    }

    @Test
    public void testProcess3() throws Exception {
        Unmarshaller unmarshaller = JAXBUtil.getInstance().createUnmarshaller();
        RequestContext rc = new RequestContext();
        long startTime = System.currentTimeMillis();
        File dir = new File("/home/yaman/workspace/projects/ERGO/2008-final");
        File[] files = dir.listFiles();

        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            System.out.println(i + " " + file.getName());

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