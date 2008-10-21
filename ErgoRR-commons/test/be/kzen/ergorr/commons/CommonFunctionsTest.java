package be.kzen.ergorr.commons;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Yaman Ustuntas
 */
public class CommonFunctionsTest {

    public CommonFunctionsTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testRemovePrefix1() {
        String obj = "rim:ExtrinsicObject";
        String expResult = "ExtrinsicObject";
        String result = CommonFunctions.removePrefix(obj);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testRemovePrefix2() {
        String obj = "ExtrinsicObject";
        String expResult = "ExtrinsicObject";
        String result = CommonFunctions.removePrefix(obj);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetElementName() {
        String expResult = "ExtrinsicObject";
        String result = CommonFunctions.getElementName("ExtrinsicObjectType");
        assertEquals(expResult, result);
    }
}