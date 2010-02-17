
package be.kzen.ergorr.model.eo.sar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import be.kzen.ergorr.model.gml.AngleType;
import be.kzen.ergorr.model.gml.MeasureType;


/**
 * <p>Java class for AcquisitionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AcquisitionType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://earth.esa.int/eop}AcquisitionType">
 *       &lt;sequence>
 *         &lt;element ref="{http://earth.esa.int/sar}polarisationMode" minOccurs="0"/>
 *         &lt;element ref="{http://earth.esa.int/sar}polarisationChannels" minOccurs="0"/>
 *         &lt;element ref="{http://earth.esa.int/sar}antennaLookDirection" minOccurs="0"/>
 *         &lt;element ref="{http://earth.esa.int/sar}minimumIncidenceAngle" minOccurs="0"/>
 *         &lt;element ref="{http://earth.esa.int/sar}maximumIncidenceAngle" minOccurs="0"/>
 *         &lt;element ref="{http://earth.esa.int/sar}incidenceAngleVariation" minOccurs="0"/>
 *         &lt;element ref="{http://earth.esa.int/sar}dopplerFrequency" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AcquisitionType", propOrder = {
    "polarisationMode",
    "polarisationChannels",
    "antennaLookDirection",
    "minimumIncidenceAngle",
    "maximumIncidenceAngle",
    "incidenceAngleVariation",
    "dopplerFrequency"
})
public class AcquisitionType
    extends be.kzen.ergorr.model.eo.eop.AcquisitionType
{

    protected PolarisationModePropertyType polarisationMode;
    protected PolarisationChannelsPropertyType polarisationChannels;
    protected String antennaLookDirection;
    protected AngleType minimumIncidenceAngle;
    protected AngleType maximumIncidenceAngle;
    protected AngleType incidenceAngleVariation;
    protected MeasureType dopplerFrequency;

    /**
     * Gets the value of the polarisationMode property.
     * 
     * @return
     *     possible object is
     *     {@link PolarisationModePropertyType }
     *     
     */
    public PolarisationModePropertyType getPolarisationMode() {
        return polarisationMode;
    }

    /**
     * Sets the value of the polarisationMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link PolarisationModePropertyType }
     *     
     */
    public void setPolarisationMode(PolarisationModePropertyType value) {
        this.polarisationMode = value;
    }

    public boolean isSetPolarisationMode() {
        return (this.polarisationMode!= null);
    }

    /**
     * Gets the value of the polarisationChannels property.
     * 
     * @return
     *     possible object is
     *     {@link PolarisationChannelsPropertyType }
     *     
     */
    public PolarisationChannelsPropertyType getPolarisationChannels() {
        return polarisationChannels;
    }

    /**
     * Sets the value of the polarisationChannels property.
     * 
     * @param value
     *     allowed object is
     *     {@link PolarisationChannelsPropertyType }
     *     
     */
    public void setPolarisationChannels(PolarisationChannelsPropertyType value) {
        this.polarisationChannels = value;
    }

    public boolean isSetPolarisationChannels() {
        return (this.polarisationChannels!= null);
    }

    /**
     * Gets the value of the antennaLookDirection property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAntennaLookDirection() {
        return antennaLookDirection;
    }

    /**
     * Sets the value of the antennaLookDirection property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAntennaLookDirection(String value) {
        this.antennaLookDirection = value;
    }

    public boolean isSetAntennaLookDirection() {
        return (this.antennaLookDirection!= null);
    }

    /**
     * Gets the value of the minimumIncidenceAngle property.
     * 
     * @return
     *     possible object is
     *     {@link AngleType }
     *     
     */
    public AngleType getMinimumIncidenceAngle() {
        return minimumIncidenceAngle;
    }

    /**
     * Sets the value of the minimumIncidenceAngle property.
     * 
     * @param value
     *     allowed object is
     *     {@link AngleType }
     *     
     */
    public void setMinimumIncidenceAngle(AngleType value) {
        this.minimumIncidenceAngle = value;
    }

    public boolean isSetMinimumIncidenceAngle() {
        return (this.minimumIncidenceAngle!= null);
    }

    /**
     * Gets the value of the maximumIncidenceAngle property.
     * 
     * @return
     *     possible object is
     *     {@link AngleType }
     *     
     */
    public AngleType getMaximumIncidenceAngle() {
        return maximumIncidenceAngle;
    }

    /**
     * Sets the value of the maximumIncidenceAngle property.
     * 
     * @param value
     *     allowed object is
     *     {@link AngleType }
     *     
     */
    public void setMaximumIncidenceAngle(AngleType value) {
        this.maximumIncidenceAngle = value;
    }

    public boolean isSetMaximumIncidenceAngle() {
        return (this.maximumIncidenceAngle!= null);
    }

    /**
     * Gets the value of the incidenceAngleVariation property.
     * 
     * @return
     *     possible object is
     *     {@link AngleType }
     *     
     */
    public AngleType getIncidenceAngleVariation() {
        return incidenceAngleVariation;
    }

    /**
     * Sets the value of the incidenceAngleVariation property.
     * 
     * @param value
     *     allowed object is
     *     {@link AngleType }
     *     
     */
    public void setIncidenceAngleVariation(AngleType value) {
        this.incidenceAngleVariation = value;
    }

    public boolean isSetIncidenceAngleVariation() {
        return (this.incidenceAngleVariation!= null);
    }

    /**
     * Gets the value of the dopplerFrequency property.
     * 
     * @return
     *     possible object is
     *     {@link MeasureType }
     *     
     */
    public MeasureType getDopplerFrequency() {
        return dopplerFrequency;
    }

    /**
     * Sets the value of the dopplerFrequency property.
     * 
     * @param value
     *     allowed object is
     *     {@link MeasureType }
     *     
     */
    public void setDopplerFrequency(MeasureType value) {
        this.dopplerFrequency = value;
    }

    public boolean isSetDopplerFrequency() {
        return (this.dopplerFrequency!= null);
    }

}
