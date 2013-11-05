/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package be.kzen.ergorr.service.translator;

import be.kzen.ergorr.commons.EOPConstants;
import be.kzen.ergorr.commons.NamespaceConstants;
import be.kzen.ergorr.model.rim.RegistryObjectListType;
import be.kzen.ergorr.model.util.JAXBUtil;
import be.kzen.ergorr.model.util.OFactory;
import be.kzen.ergorr.xpath.XMLNamespaces;
import be.kzen.ergorr.xpath.XPathUtil;
import java.net.URL;
import javax.xml.bind.JAXBElement;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;
import org.w3c.dom.Node;

/**
 *
 * @author yamanustuntas
 */
public class TranslationFactoryTest {

    @Test
    @Ignore
    public void testTranslate() throws Exception {
        URL sarUrl = this.getClass().getResource("sar-full1.xml");
        JAXBElement jaxbEl = (JAXBElement) JAXBUtil.getInstance().unmarshall(sarUrl);
        Translator instance = TranslatorFactory.getInstance(jaxbEl);
        RegistryObjectListType regObjList = instance.translate();

        JAXBElement<RegistryObjectListType> regObjsEl = OFactory.rim.createRegistryObjectList(regObjList);
//        System.out.println(JAXBUtil.getInstance().marshallToStr(regObjsEl));
        
        Node regObjsNode = JAXBUtil.getInstance().marshall(regObjsEl);
        
        XMLNamespaces ns = new XMLNamespaces();
        ns.addNamespaceURI("g", NamespaceConstants.GML);
        ns.addNamespaceURI("r", NamespaceConstants.RIM);
        ns.addNamespaceURI("w", NamespaceConstants.WRS);
        XPathUtil xpath = new XPathUtil(ns);

        String eoProductXPath = "/r:RegistryObjectList/w:ExtrinsicObject[@objectType='" + EOPConstants.E_PRODUCT + "']";
        double eoPRoductCount = xpath.getNumber("count(" + eoProductXPath + ")", regObjsNode);
        assertEquals(1.0, eoPRoductCount);

        Node eoProductN = xpath.getNode(eoProductXPath, regObjsNode);
    }
}
