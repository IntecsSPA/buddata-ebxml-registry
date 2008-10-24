package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.model.rim.ExtrinsicObjectType;
import java.sql.Timestamp;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Yaman Ustuntas
 */
public class ExtrinsicObjectDAOTest {

    public ExtrinsicObjectDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void use() {
        try {
            XMLGregorianCalendar c1 = DatatypeFactory.newInstance().newXMLGregorianCalendar("2008-01-01T10:20:10.76Z");
            System.out.println(">>>" + c1.toString());
            System.out.println(">>>" + c1.toGregorianCalendar().getTime().toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}