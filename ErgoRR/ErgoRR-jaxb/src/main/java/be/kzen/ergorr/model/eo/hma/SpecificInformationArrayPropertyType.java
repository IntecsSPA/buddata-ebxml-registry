
package be.kzen.ergorr.model.eo.hma;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SpecificInformationArrayPropertyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SpecificInformationArrayPropertyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://earth.esa.int/hma}SpecificInformation" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SpecificInformationArrayPropertyType", propOrder = {
    "specificInformation"
})
public class SpecificInformationArrayPropertyType {

    @XmlElement(name = "SpecificInformation", required = true)
    protected List<SpecificInformationType> specificInformation;

    /**
     * Gets the value of the specificInformation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the specificInformation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSpecificInformation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SpecificInformationType }
     * 
     * 
     */
    public List<SpecificInformationType> getSpecificInformation() {
        if (specificInformation == null) {
            specificInformation = new ArrayList<SpecificInformationType>();
        }
        return this.specificInformation;
    }

    public boolean isSetSpecificInformation() {
        return ((this.specificInformation!= null)&&(!this.specificInformation.isEmpty()));
    }

    public void unsetSpecificInformation() {
        this.specificInformation = null;
    }

}
