
package be.kzen.ergorr.model.eo.eop;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EarthObservationEquipmentPropertyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EarthObservationEquipmentPropertyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://earth.esa.int/eop}EarthObservationEquipment"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EarthObservationEquipmentPropertyType", propOrder = {
    "earthObservationEquipment"
})
public class EarthObservationEquipmentPropertyType {

    @XmlElement(name = "EarthObservationEquipment", required = true)
    protected EarthObservationEquipmentType earthObservationEquipment;

    /**
     * Gets the value of the earthObservationEquipment property.
     * 
     * @return
     *     possible object is
     *     {@link EarthObservationEquipmentType }
     *     
     */
    public EarthObservationEquipmentType getEarthObservationEquipment() {
        return earthObservationEquipment;
    }

    /**
     * Sets the value of the earthObservationEquipment property.
     * 
     * @param value
     *     allowed object is
     *     {@link EarthObservationEquipmentType }
     *     
     */
    public void setEarthObservationEquipment(EarthObservationEquipmentType value) {
        this.earthObservationEquipment = value;
    }

    public boolean isSetEarthObservationEquipment() {
        return (this.earthObservationEquipment!= null);
    }

}
