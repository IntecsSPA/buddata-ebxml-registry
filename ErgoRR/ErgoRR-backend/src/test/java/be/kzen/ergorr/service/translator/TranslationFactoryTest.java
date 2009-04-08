/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package be.kzen.ergorr.service.translator;

import be.kzen.ergorr.model.rim.RegistryObjectListType;
import be.kzen.ergorr.model.util.JAXBUtil;
import be.kzen.ergorr.model.util.OFactory;
import java.net.URL;
import javax.xml.bind.JAXBElement;
import junit.framework.TestCase;

/**
 *
 * @author yamanustuntas
 */
public class TranslationFactoryTest extends TestCase {
    
    public TranslationFactoryTest(String testName) {
        super(testName);
    }

    public void testTranslate() throws Exception {
        URL sarUrl = this.getClass().getResource("sar-full1.xml");
        JAXBElement jaxbEl = (JAXBElement) JAXBUtil.getInstance().unmarshall(sarUrl);
        TranslationFactory instance = new TranslationFactory();
        RegistryObjectListType regObjs = instance.translate(jaxbEl);
//        System.out.println(JAXBUtil.getInstance().marshallToStr(jaxbEl));
        JAXBElement<RegistryObjectListType> regObjsEl = OFactory.rim.createRegistryObjectList(regObjs);

        System.out.println(JAXBUtil.getInstance().marshallToStr(regObjsEl));
    }
}
