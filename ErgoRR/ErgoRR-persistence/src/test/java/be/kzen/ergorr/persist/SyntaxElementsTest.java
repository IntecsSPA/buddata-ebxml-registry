/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package be.kzen.ergorr.persist;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author yamanustuntas
 */
public class SyntaxElementsTest {

    public SyntaxElementsTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testReplaceLike() {
        String str = "%Hello*";
        String single = "_";
        String wildcard = "*";
        String escape = "!";
        String expResult = "\\\\%Hello%";
        String result = SyntaxElements.replaceLike(str, single, wildcard, escape);
        assertEquals(expResult, result);
    }

    @Test
    public void testReplaceLike2() {
        String str = "Hello%";
        String single = null;
        String wildcard = null;
        String escape = null;
        String expResult = "Hello%";
        String result = SyntaxElements.replaceLike(str, single, wildcard, escape);
        assertEquals(expResult, result);
    }

}