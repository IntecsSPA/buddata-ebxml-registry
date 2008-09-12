
package be.kzen.ergorr.model.eo.hma;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SensorPropertyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SensorPropertyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://earth.esa.int/hma}Sensor"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SensorPropertyType", propOrder = {
    "sensor"
})
public class SensorPropertyType {

    @XmlElement(name = "Sensor", required = true)
    protected SensorType sensor;

    /**
     * Gets the value of the sensor property.
     * 
     * @return
     *     possible object is
     *     {@link SensorType }
     *     
     */
    public SensorType getSensor() {
        return sensor;
    }

    /**
     * Sets the value of the sensor property.
     * 
     * @param value
     *     allowed object is
     *     {@link SensorType }
     *     
     */
    public void setSensor(SensorType value) {
        this.sensor = value;
    }

    public boolean isSetSensor() {
        return (this.sensor!= null);
    }

}
