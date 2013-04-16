
package it.intecs.pisa.ergorr.service.translator.rim;

import be.kzen.ergorr.exceptions.ServiceException;
import be.kzen.ergorr.model.rim.IdentifiableType;
import be.kzen.ergorr.model.rim.RegistryObjectListType;
import be.kzen.ergorr.model.util.JAXBUtil;
import be.kzen.ergorr.service.translator.Translator;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Andrea Marongiu
 */
public class RIMTranslator <T extends Object> implements Translator<T> {

   protected T rim;
   protected JAXBElement remoteXmlEl;

    public RegistryObjectListType translate() {
       RegistryObjectListType regObjList = null;
       if (this.rim instanceof IdentifiableType) {
             regObjList = new RegistryObjectListType();
             regObjList.getIdentifiable().add(this.remoteXmlEl);
                } else if (this.rim instanceof RegistryObjectListType) {
                    regObjList = (RegistryObjectListType) this.rim;
                }
       return(regObjList);
    }

    public void setObject(URL url) throws JAXBException {
        
    }

    public void setObject(Object obj){
       if(obj instanceof String){
            try {
                this.remoteXmlEl = (JAXBElement) JAXBUtil.getInstance().unmarshall((String) obj);
            } catch (JAXBException ex) {
                Logger.getLogger(RIMTranslator.class.getName()).log(Level.SEVERE, null, ex);
            }
           this.rim = (T) remoteXmlEl.getValue();
        }
    }

}
