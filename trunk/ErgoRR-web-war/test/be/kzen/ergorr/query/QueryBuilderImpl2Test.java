package be.kzen.ergorr.query;

import be.kzen.ergorr.model.csw.GetRecordsResponseType;
import be.kzen.ergorr.model.csw.GetRecordsType;
import be.kzen.ergorr.model.util.JAXBUtil;
import be.kzen.ergorr.persist.InternalSlotTypes;
import java.io.File;
import javax.xml.bind.JAXBElement;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author yamanustuntas
 */
public class QueryBuilderImpl2Test {

    public QueryBuilderImpl2Test() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        InternalSlotTypes.getInstance().putTestSlot("urn:ogc:def:ebRIM-Slot:OGC-06-131:beginPosition", "datetime");
        InternalSlotTypes.getInstance().putTestSlot("urn:ogc:def:ebRIM-Slot:OGC-06-131:sensorType", "string");
        InternalSlotTypes.getInstance().putTestSlot("urn:ogc:def:ebRIM-Slot:OGC-06-131:multiExtentOf", "geometry");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of build method, of class QueryBuilderImpl2.
     */
    @Test
    public void testBuild() throws Exception {
        JAXBElement<GetRecordsType> getRecordsEl =
                (JAXBElement<GetRecordsType>) JAXBUtil.getInstance()
                .unmarshall(new File("/Users/yamanustuntas/workspace/projects/ERGO/buddata-ebxml-registry/ErgoRR-web-war/test/resources/GetRecords3.xml"));


        QueryBuilderImpl2 qb = new QueryBuilderImpl2(getRecordsEl.getValue());

        System.out.println(qb.build());
    }
}