/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.kzen.ergorr.interfaces.soap;

import be.kzen.ergorr.commons.CommonProperties;
import be.kzen.ergorr.model.csw.ElementSetNameType;
import be.kzen.ergorr.model.csw.ElementSetType;
import be.kzen.ergorr.model.csw.GetRecordByIdResponseType;
import be.kzen.ergorr.model.csw.GetRecordByIdType;
import be.kzen.ergorr.model.csw.GetRecordsResponseType;
import be.kzen.ergorr.model.csw.GetRecordsType;
import be.kzen.ergorr.model.csw.InsertType;
import be.kzen.ergorr.model.csw.QueryConstraintType;
import be.kzen.ergorr.model.csw.QueryType;
import be.kzen.ergorr.model.csw.TransactionType;
import be.kzen.ergorr.model.ogc.BinaryComparisonOpType;
import be.kzen.ergorr.model.ogc.BinaryLogicOpType;
import be.kzen.ergorr.model.ogc.FilterType;
import be.kzen.ergorr.model.ogc.LiteralType;
import be.kzen.ergorr.model.ogc.PropertyIsEqualTo;
import be.kzen.ergorr.model.ogc.PropertyNameType;
import be.kzen.ergorr.model.util.JAXBUtil;
import be.kzen.ergorr.model.util.OFactory;
import java.io.File;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Yaman Ustuntas
 */
public class CswClientTest {

    CswSoapClient client;

    public CswClientTest() throws MalformedURLException {
        JAXBUtil.getInstance();
        client = new CswSoapClient(new URL("http://localhost:8080/esa1/webservice?wsdl"));
    }

    @Test
    @Ignore
    public void testTransaction() throws Exception {
        Unmarshaller unmarshaller = JAXBUtil.getInstance().createUnmarshaller();

        File dir = new File(CommonProperties.getInstance().get("sampleDataFolder"));
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
                
                client.transact(t);
            }
        }
    }

    @Test(timeout = 5000)
    public void testGetRecords() throws Exception {
        System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
        GetRecordsType request = new GetRecordsType();
        request.setMaxRecords(BigInteger.valueOf(30));

        QueryType query = new QueryType();
        request.setAbstractQuery(OFactory.csw.createQuery(query));

        // set the query object to return
        QName rimQName = new QName("urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0", "ExtrinsicObject");
        query.setElementSetName(new ElementSetNameType());
        query.getElementSetName().getTypeNames().add(rimQName);

        // set the objects to query
        query.getTypeNames().add(rimQName);

        QueryConstraintType constraint = new QueryConstraintType();
        query.setConstraint(constraint);

        FilterType filter = new FilterType();
        constraint.setFilter(filter);

        BinaryLogicOpType andType = new BinaryLogicOpType();
        filter.setLogicOps(OFactory.ogc.createAnd(andType));

        BinaryComparisonOpType propEqualType = new BinaryComparisonOpType();
        PropertyIsEqualTo propEqual = new PropertyIsEqualTo(propEqualType);
        andType.getComparisonOpsOrSpatialOpsOrLogicOps().add(propEqual);

        PropertyNameType propName = new PropertyNameType();
        propName.getContent().add("/rim:ExtrinsicObject/@objectType");
        propEqualType.getExpression().add(OFactory.ogc.createPropertyName(propName));

        LiteralType literal = new LiteralType();
        literal.getContent().add("urn:x-ogc:specification:csw-ebrim:ObjectType:EO:EOProduct");
        propEqualType.getExpression().add(OFactory.ogc.createLiteral(literal));

        try {
            GetRecordsResponseType response = client.getRecords(request);
        } catch (ServiceExceptionReport ex) {
            ex.printStackTrace();
        }
    }

    @Test(timeout = 5000)
    @Ignore
    public void testGetRecordById() throws Exception {
        GetRecordByIdType request = new GetRecordByIdType();
        request.getId().add("urn:uuid:2eb09d2f-38ea-4fd6-84a6-b78730e40f74");
        ElementSetNameType setName = new ElementSetNameType();
        setName.setValue(ElementSetType.FULL);
        request.setElementSetName(setName);

        GetRecordByIdResponseType response = client.getRecordById(request);

        System.out.println(JAXBUtil.getInstance().marshallToStr(OFactory.csw.createGetRecordByIdResponse(response)));
    }

    @Test
    public void testTransact() throws Exception {
        assertEquals("", "");
    }

    @Test
    public void testHarvest() throws Exception {
        assertEquals("", "");
    }

    @Test
    public void testGetRecordDescription() throws Exception {
        assertEquals("", "");
    }

    @Test
    public void testGetDomain() throws Exception {
        assertEquals("", "");
    }

    @Test
    public void testGetCapabilities() throws Exception {
        assertEquals("", "");
    }
}