
package be.kzen.ergorr.model.eo.atm;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DataLayerPropertyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DataLayerPropertyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://earth.esa.int/atm}DataLayer" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DataLayerPropertyType", propOrder = {
    "dataLayer"
})
public class DataLayerPropertyType {

    @XmlElement(name = "DataLayer", required = true)
    protected List<DataLayerType> dataLayer;

    /**
     * Gets the value of the dataLayer property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dataLayer property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataLayer().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataLayerType }
     * 
     * 
     */
    public List<DataLayerType> getDataLayer() {
        if (dataLayer == null) {
            dataLayer = new ArrayList<DataLayerType>();
        }
        return this.dataLayer;
    }

    public boolean isSetDataLayer() {
        return ((this.dataLayer!= null)&&(!this.dataLayer.isEmpty()));
    }

    public void unsetDataLayer() {
        this.dataLayer = null;
    }

}
