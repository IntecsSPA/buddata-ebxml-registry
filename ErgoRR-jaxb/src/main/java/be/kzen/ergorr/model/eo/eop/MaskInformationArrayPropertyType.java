
package be.kzen.ergorr.model.eo.eop;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MaskInformationArrayPropertyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MaskInformationArrayPropertyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://earth.esa.int/eop}MaskInformation" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MaskInformationArrayPropertyType", propOrder = {
    "maskInformation"
})
public class MaskInformationArrayPropertyType {

    @XmlElement(name = "MaskInformation", required = true)
    protected List<MaskInformationType> maskInformation;

    /**
     * Gets the value of the maskInformation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the maskInformation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMaskInformation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MaskInformationType }
     * 
     * 
     */
    public List<MaskInformationType> getMaskInformation() {
        if (maskInformation == null) {
            maskInformation = new ArrayList<MaskInformationType>();
        }
        return this.maskInformation;
    }

    public boolean isSetMaskInformation() {
        return ((this.maskInformation!= null)&&(!this.maskInformation.isEmpty()));
    }

    public void unsetMaskInformation() {
        this.maskInformation = null;
    }

}
