
package be.kzen.ergorr.model.eo.opt;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import be.kzen.ergorr.model.gml.AngleType;


/**
 * <p>Java class for AcquisitionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AcquisitionType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://earth.esa.int/hma}AcquisitionType">
 *       &lt;sequence>
 *         &lt;element name="illuminationAzimuthAngle" type="{http://www.opengis.net/gml}AngleType" minOccurs="0"/>
 *         &lt;element name="illuminationElevationAngle" type="{http://www.opengis.net/gml}AngleType" minOccurs="0"/>
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
    "illuminationAzimuthAngle",
    "illuminationElevationAngle"
})
public class AcquisitionType
    extends be.kzen.ergorr.model.eo.hma.AcquisitionType
{

    protected AngleType illuminationAzimuthAngle;
    protected AngleType illuminationElevationAngle;

    /**
     * Gets the value of the illuminationAzimuthAngle property.
     * 
     * @return
     *     possible object is
     *     {@link AngleType }
     *     
     */
    public AngleType getIlluminationAzimuthAngle() {
        return illuminationAzimuthAngle;
    }

    /**
     * Sets the value of the illuminationAzimuthAngle property.
     * 
     * @param value
     *     allowed object is
     *     {@link AngleType }
     *     
     */
    public void setIlluminationAzimuthAngle(AngleType value) {
        this.illuminationAzimuthAngle = value;
    }

    public boolean isSetIlluminationAzimuthAngle() {
        return (this.illuminationAzimuthAngle!= null);
    }

    /**
     * Gets the value of the illuminationElevationAngle property.
     * 
     * @return
     *     possible object is
     *     {@link AngleType }
     *     
     */
    public AngleType getIlluminationElevationAngle() {
        return illuminationElevationAngle;
    }

    /**
     * Sets the value of the illuminationElevationAngle property.
     * 
     * @param value
     *     allowed object is
     *     {@link AngleType }
     *     
     */
    public void setIlluminationElevationAngle(AngleType value) {
        this.illuminationElevationAngle = value;
    }

    public boolean isSetIlluminationElevationAngle() {
        return (this.illuminationElevationAngle!= null);
    }

}
