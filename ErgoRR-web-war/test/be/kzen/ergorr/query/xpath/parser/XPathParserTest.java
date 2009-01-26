/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package be.kzen.ergorr.query.xpath.parser;

import be.kzen.ergorr.query.xpath.parser.XPathNode;
import be.kzen.ergorr.query.xpath.parser.SimpleXPathParser;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author yaman
 */
public class XPathParserTest {

    public XPathParserTest() {
    }

    @Test
    public void testGetNextElement() throws Exception {
//        String xpath = "$AI/rim:Slot[@name=\"urn:ogc:def:ebRIM-Slot:OGC-06-131:archivingDate\"]/rim:ValueList/rim:Value[1]";
//        String xpath = "$AI[@objectType=\"urn:blabla\"]/rim:Slot[@name=\"urn:ogc:def:ebRIM-Slot:OGC-06-131:archivingDate\"]/rim:ValueList/rim:Value[1]";
        String xpath = "$ACQPLAT/rim:Name/rim:LocalizedString[@xml:lang=\"en-US\"]/@value";
//        String xpath = "$ACQPLAT/rim:Slot[@name=\"urn:ogc:def:ebRIM-Slot:OGC-06-131:sensorType\"]/rim:ValueList/rim:Value[1]";
//        String xpath = "rim:Association/@sourceObject";
//        String xpath = "$asso/@sourceObject";
        SimpleXPathParser parser = new SimpleXPathParser(xpath);

        int count = 0;
        while (parser.hasNext()) {
            count++;
            if (count > 100) {
                break;
            }

            XPathNode n = parser.getNextNode();
            System.out.print("Node: " + n.getName());

            if (n.isSetAttributeName()) {
                System.out.println(" attr: " + n.getAttributeName());
            } else if (n.isSetSubSelectName()) {
                System.out.println(" sub: " + n.getSubSelectName() + " __ " + n.getSubSelectValue());
            } else if (n.isSetSubSelectValue()) {
                System.out.println(" subv: " + n.getSubSelectValue());
            }
            System.out.println("");
            System.out.println("-------------");
        }
    }
}