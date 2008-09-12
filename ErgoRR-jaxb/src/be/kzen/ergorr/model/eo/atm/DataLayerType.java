
package be.kzen.ergorr.model.eo.atm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import be.kzen.ergorr.model.gml.MeasureType;


/**
 * <p>Java class for DataLayerType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DataLayerType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="specy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="unit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="highestLocation" type="{http://www.opengis.net/gml/3.2}MeasureType" minOccurs="0"/>
 *         &lt;element name="lowestLocation" type="{http://www.opengis.net/gml/3.2}MeasureType" minOccurs="0"/>
 *         &lt;element name="algorithmName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="algorithmVersion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DataLayerType", propOrder = {
    "specy",
    "unit",
    "highestLocation",
    "lowestLocation",
    "algorithmName",
    "algorithmVersion"
})
public class DataLayerType {

    protected String specy;
    protected String unit;
    protected MeasureType highestLocation;
    protected MeasureType lowestLocation;
    protected String algorithmName;
    protected String algorithmVersion;

    /**
     * Gets the value of the specy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecy() {
        return specy;
    }

    /**
     * Sets the value of the specy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecy(String value) {
        this.specy = value;
    }

    public boolean isSetSpecy() {
        return (this.specy!= null);
    }

    /**
     * Gets the value of the unit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Sets the value of the unit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnit(String value) {
        this.unit = value;
    }

    public boolean isSetUnit() {
        return (this.unit!= null);
    }

    /**
     * Gets the value of the highestLocation property.
     * 
     * @return
     *     possible object is
     *     {@link MeasureType }
     *     
     */
    public MeasureType getHighestLocation() {
        return highestLocation;
    }

    /**
     * Sets the value of the highestLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link MeasureType }
     *     
     */
    public void setHighestLocation(MeasureType value) {
        this.highestLocation = value;
    }

    public boolean isSetHighestLocation() {
        return (this.highestLocation!= null);
    }

    /**
     * Gets the value of the lowestLocation property.
     * 
     * @return
     *     possible object is
     *     {@link MeasureType }
     *     
     */
    public MeasureType getLowestLocation() {
        return lowestLocation;
    }

    /**
     * Sets the value of the lowestLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link MeasureType }
     *     
     */
    public void setLowestLocation(MeasureType value) {
        this.lowestLocation = value;
    }

    public boolean isSetLowestLocation() {
        return (this.lowestLocation!= null);
    }

    /**
     * Gets the value of the algorithmName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlgorithmName() {
        return algorithmName;
    }

    /**
     * Sets the value of the algorithmName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlgorithmName(String value) {
        this.algorithmName = value;
    }

    public boolean isSetAlgorithmName() {
        return (this.algorithmName!= null);
    }

    /**
     * Gets the value of the algorithmVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlgorithmVersion() {
        return algorithmVersion;
    }

    /**
     * Sets the value of the algorithmVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlgorithmVersion(String value) {
        this.algorithmVersion = value;
    }

    public boolean isSetAlgorithmVersion() {
        return (this.algorithmVersion!= null);
    }

}
