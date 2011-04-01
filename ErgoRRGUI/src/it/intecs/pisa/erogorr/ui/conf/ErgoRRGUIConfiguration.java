

package it.intecs.pisa.erogorr.ui.conf;

import it.intecs.pisa.util.DOMUtil;
import java.io.File;
import java.util.Hashtable;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 *
 * @author Andrea Marongiu
 */
public class ErgoRRGUIConfiguration {
   
    private static final String PROPERTY_ELEMENT = "property";
    private static final String PROPERTY_NAME_ATTRIBUTE = "name";
    private static final String PROPERTY_VALUE_ATTRIBUTE = "value";
    
    private Hashtable configValues;
    
    public ErgoRRGUIConfiguration (File configurationFile) throws Exception{
        NodeList propNodes=null;
        int i;
        String propName,propValue;
        this.configValues=new Hashtable();
        DOMUtil domUtil=new DOMUtil();
        Document confDocument=domUtil.fileToDocument(configurationFile);
        propNodes=confDocument.getDocumentElement().getElementsByTagName(PROPERTY_ELEMENT);
        for(i=0;i<propNodes.getLength();i++){
            propName=propNodes.item(i).getAttributes().getNamedItem(PROPERTY_NAME_ATTRIBUTE).getNodeValue();  
            propValue=propNodes.item(i).getAttributes().getNamedItem(PROPERTY_VALUE_ATTRIBUTE).getNodeValue();
            this.configValues.put(propName, propValue);
        }
    }
    
    
    public String getProperty(String propertyName){
        return (String) this.configValues.get(propertyName);
    }


}
