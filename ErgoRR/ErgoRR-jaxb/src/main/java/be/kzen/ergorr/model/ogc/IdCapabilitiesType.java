
package be.kzen.ergorr.model.ogc;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Id_CapabilitiesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Id_CapabilitiesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded">
 *         &lt;element ref="{http://www.opengis.net/ogc}EID"/>
 *         &lt;element ref="{http://www.opengis.net/ogc}FID"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Id_CapabilitiesType", propOrder = {
    "eidOrFID"
})
public class IdCapabilitiesType {

    @XmlElements({
        @XmlElement(name = "FID", type = FID.class),
        @XmlElement(name = "EID", type = EID.class)
    })
    protected List<Object> eidOrFID;

    /**
     * Gets the value of the eidOrFID property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the eidOrFID property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEIDOrFID().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FID }
     * {@link EID }
     * 
     * 
     */
    public List<Object> getEIDOrFID() {
        if (eidOrFID == null) {
            eidOrFID = new ArrayList<Object>();
        }
        return this.eidOrFID;
    }

    public boolean isSetEIDOrFID() {
        return ((this.eidOrFID!= null)&&(!this.eidOrFID.isEmpty()));
    }

    public void unsetEIDOrFID() {
        this.eidOrFID = null;
    }

}
