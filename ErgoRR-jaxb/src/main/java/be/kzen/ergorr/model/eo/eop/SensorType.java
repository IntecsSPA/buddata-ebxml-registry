
package be.kzen.ergorr.model.eo.eop;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import be.kzen.ergorr.model.gml.CodeListType;
import be.kzen.ergorr.model.gml.MeasureType;


/**
 * <p>Java class for SensorType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SensorType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sensorType" type="{http://earth.esa.int/eop}SensorTypePropertyType" minOccurs="0"/>
 *         &lt;element name="operationalMode" type="{http://www.opengis.net/gml}CodeListType" minOccurs="0"/>
 *         &lt;element name="resolution" type="{http://www.opengis.net/gml}MeasureType" minOccurs="0"/>
 *         &lt;element name="swathIdentifier" type="{http://www.opengis.net/gml}CodeListType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SensorType", propOrder = {
    "sensorType",
    "operationalMode",
    "resolution",
    "swathIdentifier"
})
public class SensorType {

    protected SensorTypePropertyType sensorType;
    protected CodeListType operationalMode;
    protected MeasureType resolution;
    protected CodeListType swathIdentifier;

    /**
     * Gets the value of the sensorType property.
     * 
     * @return
     *     possible object is
     *     {@link SensorTypePropertyType }
     *     
     */
    public SensorTypePropertyType getSensorType() {
        return sensorType;
    }

    /**
     * Sets the value of the sensorType property.
     * 
     * @param value
     *     allowed object is
     *     {@link SensorTypePropertyType }
     *     
     */
    public void setSensorType(SensorTypePropertyType value) {
        this.sensorType = value;
    }

    public boolean isSetSensorType() {
        return (this.sensorType!= null);
    }

    /**
     * Gets the value of the operationalMode property.
     * 
     * @return
     *     possible object is
     *     {@link CodeListType }
     *     
     */
    public CodeListType getOperationalMode() {
        return operationalMode;
    }

    /**
     * Sets the value of the operationalMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeListType }
     *     
     */
    public void setOperationalMode(CodeListType value) {
        this.operationalMode = value;
    }

    public boolean isSetOperationalMode() {
        return (this.operationalMode!= null);
    }

    /**
     * Gets the value of the resolution property.
     * 
     * @return
     *     possible object is
     *     {@link MeasureType }
     *     
     */
    public MeasureType getResolution() {
        return resolution;
    }

    /**
     * Sets the value of the resolution property.
     * 
     * @param value
     *     allowed object is
     *     {@link MeasureType }
     *     
     */
    public void setResolution(MeasureType value) {
        this.resolution = value;
    }

    public boolean isSetResolution() {
        return (this.resolution!= null);
    }

    /**
     * Gets the value of the swathIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link CodeListType }
     *     
     */
    public CodeListType getSwathIdentifier() {
        return swathIdentifier;
    }

    /**
     * Sets the value of the swathIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeListType }
     *     
     */
    public void setSwathIdentifier(CodeListType value) {
        this.swathIdentifier = value;
    }

    public boolean isSetSwathIdentifier() {
        return (this.swathIdentifier!= null);
    }

}
