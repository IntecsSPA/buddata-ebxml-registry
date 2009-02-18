/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package be.kzen.ergorr.commons;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author yamanustuntas
 */
public class CommonPropertiesTest {

    public CommonPropertiesTest() {
    }

    /**
     * Test of getInstance method, of class CommonProperties.
     */
    @Test
    public void testGetInstance() {
        CommonProperties prop = CommonProperties.getInstance();
        
        System.out.println(prop.get("db.datasource"));
    }
}