
package be.kzen.ergorr.model.eo.hma;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import be.kzen.ergorr.model.gml.AbstractFeatureType;


/**
 * <p>Java class for EarthObservationEquipmentType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EarthObservationEquipmentType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/gml}AbstractFeatureType">
 *       &lt;sequence>
 *         &lt;element name="platform" type="{http://earth.esa.int/hma}PlatformPropertyType" minOccurs="0"/>
 *         &lt;element name="instrument" type="{http://earth.esa.int/hma}InstrumentPropertyType" minOccurs="0"/>
 *         &lt;element name="sensor" type="{http://earth.esa.int/hma}SensorPropertyType" minOccurs="0"/>
 *         &lt;element name="acquisitionParameters" type="{http://earth.esa.int/hma}AcquisitionPropertyType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EarthObservationEquipmentType", propOrder = {
    "platform",
    "instrument",
    "sensor",
    "acquisitionParameters"
})
public class EarthObservationEquipmentType
    extends AbstractFeatureType
{

    protected PlatformPropertyType platform;
    protected InstrumentPropertyType instrument;
    protected SensorPropertyType sensor;
    protected AcquisitionPropertyType acquisitionParameters;

    /**
     * Gets the value of the platform property.
     * 
     * @return
     *     possible object is
     *     {@link PlatformPropertyType }
     *     
     */
    public PlatformPropertyType getPlatform() {
        return platform;
    }

    /**
     * Sets the value of the platform property.
     * 
     * @param value
     *     allowed object is
     *     {@link PlatformPropertyType }
     *     
     */
    public void setPlatform(PlatformPropertyType value) {
        this.platform = value;
    }

    public boolean isSetPlatform() {
        return (this.platform!= null);
    }

    /**
     * Gets the value of the instrument property.
     * 
     * @return
     *     possible object is
     *     {@link InstrumentPropertyType }
     *     
     */
    public InstrumentPropertyType getInstrument() {
        return instrument;
    }

    /**
     * Sets the value of the instrument property.
     * 
     * @param value
     *     allowed object is
     *     {@link InstrumentPropertyType }
     *     
     */
    public void setInstrument(InstrumentPropertyType value) {
        this.instrument = value;
    }

    public boolean isSetInstrument() {
        return (this.instrument!= null);
    }

    /**
     * Gets the value of the sensor property.
     * 
     * @return
     *     possible object is
     *     {@link SensorPropertyType }
     *     
     */
    public SensorPropertyType getSensor() {
        return sensor;
    }

    /**
     * Sets the value of the sensor property.
     * 
     * @param value
     *     allowed object is
     *     {@link SensorPropertyType }
     *     
     */
    public void setSensor(SensorPropertyType value) {
        this.sensor = value;
    }

    public boolean isSetSensor() {
        return (this.sensor!= null);
    }

    /**
     * Gets the value of the acquisitionParameters property.
     * 
     * @return
     *     possible object is
     *     {@link AcquisitionPropertyType }
     *     
     */
    public AcquisitionPropertyType getAcquisitionParameters() {
        return acquisitionParameters;
    }

    /**
     * Sets the value of the acquisitionParameters property.
     * 
     * @param value
     *     allowed object is
     *     {@link AcquisitionPropertyType }
     *     
     */
    public void setAcquisitionParameters(AcquisitionPropertyType value) {
        this.acquisitionParameters = value;
    }

    public boolean isSetAcquisitionParameters() {
        return (this.acquisitionParameters!= null);
    }

}
