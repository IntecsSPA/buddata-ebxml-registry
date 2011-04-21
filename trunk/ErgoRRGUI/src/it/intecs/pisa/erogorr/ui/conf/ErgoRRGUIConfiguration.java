

package it.intecs.pisa.erogorr.ui.conf;

import it.intecs.pisa.util.DOMUtil;
import java.io.File;
import java.util.Hashtable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Andrea Marongiu
 */
public class ErgoRRGUIConfiguration {
   
    private static final String PROPERTY_ELEMENT = "property";
    private static final String PROPERTY_NAME_ATTRIBUTE = "name";
    private static final String PROPERTY_VALUE_ATTRIBUTE = "value";
    
    public static final String ERGORR_URL_PROPERTY = "ergoRRURL";
    public static final String ERGORR_LOCAL_URL_PROPERTY = "ergoRRLocalURL";
    public static final String HOST_LOCAL_URL = "hostLocalURL";
    public static final String LOCALHOST_PROPERTY = "checkLocaHost";
    
    private Hashtable configValues;
    
    private File configurationFile=null;
    
    public ErgoRRGUIConfiguration (File configurationFile)throws Exception{
     
        this.configurationFile=configurationFile;
        this.loadProperties();
        
    }
    
    private void loadProperties() throws Exception{
        NodeList propNodes=null;
        int i;
        String propName,propValue;
        DOMUtil domUtil=new DOMUtil();
        this.configValues=new Hashtable();
        Document confDocument=domUtil.fileToDocument(this.configurationFile);
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
    
    
    public void addLocalAddressProperty(String ip, String port) throws Exception{
        String externalErgoRRURL=(String) this.configValues.get(ERGORR_URL_PROPERTY);
        String [] urlSplit=null;
        Element newProp=null;
        DOMUtil domUtil=new DOMUtil();
        Document confDocument=domUtil.fileToDocument(this.configurationFile);
        Element root=confDocument.getDocumentElement();
        String temp=externalErgoRRURL.replaceAll("http://", "");
        urlSplit=temp.split("/");
        String internalErgoRRURL=externalErgoRRURL.replaceAll(urlSplit[0], ip+":"+port);
        
        newProp=confDocument.createElement(PROPERTY_ELEMENT);
        newProp.setAttribute(PROPERTY_NAME_ATTRIBUTE, HOST_LOCAL_URL);
        newProp.setAttribute(PROPERTY_VALUE_ATTRIBUTE, "http://"+ip+":"+port);
        root.appendChild(newProp);
        
        newProp=confDocument.createElement(PROPERTY_ELEMENT);
        newProp.setAttribute(PROPERTY_NAME_ATTRIBUTE, ERGORR_LOCAL_URL_PROPERTY);
        newProp.setAttribute(PROPERTY_VALUE_ATTRIBUTE, internalErgoRRURL);
        root.appendChild(newProp);
        
        this.loadProperties();
        
        DOMUtil.dumpXML(confDocument, configurationFile);
        
    }


}
