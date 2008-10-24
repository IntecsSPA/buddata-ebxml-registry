
package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.persist.dao.ExtrinsicObjectTypeDAO;
import be.kzen.ergorr.model.rim.ExtrinsicObjectType;
import be.kzen.ergorr.model.rim.InternationalStringType;
import be.kzen.ergorr.model.rim.LocalizedStringType;
import be.kzen.ergorr.model.rim.VersionInfoType;
import java.sql.ResultSet;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Yaman Ustuntas
 */
public class ExtrinsicObjectTypeDAOTest {

    public ExtrinsicObjectTypeDAOTest() {
    }

    @Test
    public void testCreateValues() {
        
        ExtrinsicObjectType eo = new ExtrinsicObjectType();
        eo.setId("urn:id");
        eo.setLid("urn:lid");
        eo.setHome("urn:home");
        eo.setMimeType("xml");
        eo.setObjectType("EOProduct");
        eo.setStatus("inserted");
        
        InternationalStringType name = new InternationalStringType();
        LocalizedStringType nameLocal = new LocalizedStringType();
        
        ExtrinsicObjectTypeDAO eoDao = new ExtrinsicObjectTypeDAO(eo);
        
        VersionInfoType version = new VersionInfoType();
        version.setComment("vComment");
        version.setVersionName("1");
        eo.setVersionInfo(version);
        VersionInfoType cversion = new VersionInfoType();
        cversion.setComment("cvComment");
        cversion.setVersionName("1");
        eo.setContentVersionInfo(cversion);
        
        System.out.println(eoDao.createValues());
//        assertEquals(expResult, result);
    }
}