
package be.kzen.ergorr.model.eo.hma;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DownlinkInformationArrayPropertyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DownlinkInformationArrayPropertyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://earth.esa.int/hma}DownlinkInformation" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DownlinkInformationArrayPropertyType", propOrder = {
    "downlinkInformation"
})
public class DownlinkInformationArrayPropertyType {

    @XmlElement(name = "DownlinkInformation", required = true)
    protected List<DownlinkInformationType> downlinkInformation;

    /**
     * Gets the value of the downlinkInformation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the downlinkInformation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDownlinkInformation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DownlinkInformationType }
     * 
     * 
     */
    public List<DownlinkInformationType> getDownlinkInformation() {
        if (downlinkInformation == null) {
            downlinkInformation = new ArrayList<DownlinkInformationType>();
        }
        return this.downlinkInformation;
    }

    public boolean isSetDownlinkInformation() {
        return ((this.downlinkInformation!= null)&&(!this.downlinkInformation.isEmpty()));
    }

    public void unsetDownlinkInformation() {
        this.downlinkInformation = null;
    }

}
