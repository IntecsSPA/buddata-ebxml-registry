
package be.kzen.ergorr.query;

import org.junit.Test;

/**
 *
 * @author Yaman Ustuntas
 */
public class XPathObjectTest {

    public XPathObjectTest() {
    }

    @Test
    public void testProcessObjectType() throws Exception {
        XPathObject xpo = new XPathObject("/rim:ExtrinsicObject/@objectType");
        xpo.process();
    }
    
    @Test
    public void testProcessSlotValue() throws Exception {
        XPathObject xpo = new XPathObject("/rim:ExtrinsicObject/rim:Slot[@name=\"urn:ogc:def:ebRIM-Slot:OGC-06-131:multiExtentOf\"]/rim:ValueList/rim:Value[1]");
        xpo.process();
    }
    
    @Test
    public void testProcessSlotType() throws Exception {
        XPathObject xpo = new XPathObject("");
        xpo.process();
    }

    @Test
    public void testProcessNameLang() throws Exception {
        XPathObject xpo = new XPathObject("");
        xpo.process();
    }
}