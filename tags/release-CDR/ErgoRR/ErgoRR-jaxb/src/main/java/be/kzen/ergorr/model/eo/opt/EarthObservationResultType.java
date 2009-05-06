
package be.kzen.ergorr.model.eo.opt;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import be.kzen.ergorr.model.gml.MeasureType;


/**
 * <p>Java class for EarthObservationResultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EarthObservationResultType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://earth.esa.int/eop}EarthObservationResultType">
 *       &lt;sequence>
 *         &lt;element name="cloudCoverPercentage" type="{http://www.opengis.net/gml}MeasureType" minOccurs="0"/>
 *         &lt;element name="cloudCoverPercentageAssessmentConfidence" type="{http://www.opengis.net/gml}MeasureType" minOccurs="0"/>
 *         &lt;element name="cloudCoverPercentageQuotationMode" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="AUTOMATIC"/>
 *               &lt;enumeration value="MANUAL"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="snowCoverPercentage" type="{http://www.opengis.net/gml}MeasureType" minOccurs="0"/>
 *         &lt;element name="snowCoverPercentageAssessmentConfidence" type="{http://www.opengis.net/gml}MeasureType" minOccurs="0"/>
 *         &lt;element name="snowCoverPercentageQuotationMode" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="AUTOMATIC"/>
 *               &lt;enumeration value="MANUAL"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EarthObservationResultType", propOrder = {
    "cloudCoverPercentage",
    "cloudCoverPercentageAssessmentConfidence",
    "cloudCoverPercentageQuotationMode",
    "snowCoverPercentage",
    "snowCoverPercentageAssessmentConfidence",
    "snowCoverPercentageQuotationMode"
})
public class EarthObservationResultType
    extends be.kzen.ergorr.model.eo.eop.EarthObservationResultType
{

    protected MeasureType cloudCoverPercentage;
    protected MeasureType cloudCoverPercentageAssessmentConfidence;
    protected String cloudCoverPercentageQuotationMode;
    protected MeasureType snowCoverPercentage;
    protected MeasureType snowCoverPercentageAssessmentConfidence;
    protected String snowCoverPercentageQuotationMode;

    /**
     * Gets the value of the cloudCoverPercentage property.
     * 
     * @return
     *     possible object is
     *     {@link MeasureType }
     *     
     */
    public MeasureType getCloudCoverPercentage() {
        return cloudCoverPercentage;
    }

    /**
     * Sets the value of the cloudCoverPercentage property.
     * 
     * @param value
     *     allowed object is
     *     {@link MeasureType }
     *     
     */
    public void setCloudCoverPercentage(MeasureType value) {
        this.cloudCoverPercentage = value;
    }

    public boolean isSetCloudCoverPercentage() {
        return (this.cloudCoverPercentage!= null);
    }

    /**
     * Gets the value of the cloudCoverPercentageAssessmentConfidence property.
     * 
     * @return
     *     possible object is
     *     {@link MeasureType }
     *     
     */
    public MeasureType getCloudCoverPercentageAssessmentConfidence() {
        return cloudCoverPercentageAssessmentConfidence;
    }

    /**
     * Sets the value of the cloudCoverPercentageAssessmentConfidence property.
     * 
     * @param value
     *     allowed object is
     *     {@link MeasureType }
     *     
     */
    public void setCloudCoverPercentageAssessmentConfidence(MeasureType value) {
        this.cloudCoverPercentageAssessmentConfidence = value;
    }

    public boolean isSetCloudCoverPercentageAssessmentConfidence() {
        return (this.cloudCoverPercentageAssessmentConfidence!= null);
    }

    /**
     * Gets the value of the cloudCoverPercentageQuotationMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCloudCoverPercentageQuotationMode() {
        return cloudCoverPercentageQuotationMode;
    }

    /**
     * Sets the value of the cloudCoverPercentageQuotationMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCloudCoverPercentageQuotationMode(String value) {
        this.cloudCoverPercentageQuotationMode = value;
    }

    public boolean isSetCloudCoverPercentageQuotationMode() {
        return (this.cloudCoverPercentageQuotationMode!= null);
    }

    /**
     * Gets the value of the snowCoverPercentage property.
     * 
     * @return
     *     possible object is
     *     {@link MeasureType }
     *     
     */
    public MeasureType getSnowCoverPercentage() {
        return snowCoverPercentage;
    }

    /**
     * Sets the value of the snowCoverPercentage property.
     * 
     * @param value
     *     allowed object is
     *     {@link MeasureType }
     *     
     */
    public void setSnowCoverPercentage(MeasureType value) {
        this.snowCoverPercentage = value;
    }

    public boolean isSetSnowCoverPercentage() {
        return (this.snowCoverPercentage!= null);
    }

    /**
     * Gets the value of the snowCoverPercentageAssessmentConfidence property.
     * 
     * @return
     *     possible object is
     *     {@link MeasureType }
     *     
     */
    public MeasureType getSnowCoverPercentageAssessmentConfidence() {
        return snowCoverPercentageAssessmentConfidence;
    }

    /**
     * Sets the value of the snowCoverPercentageAssessmentConfidence property.
     * 
     * @param value
     *     allowed object is
     *     {@link MeasureType }
     *     
     */
    public void setSnowCoverPercentageAssessmentConfidence(MeasureType value) {
        this.snowCoverPercentageAssessmentConfidence = value;
    }

    public boolean isSetSnowCoverPercentageAssessmentConfidence() {
        return (this.snowCoverPercentageAssessmentConfidence!= null);
    }

    /**
     * Gets the value of the snowCoverPercentageQuotationMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSnowCoverPercentageQuotationMode() {
        return snowCoverPercentageQuotationMode;
    }

    /**
     * Sets the value of the snowCoverPercentageQuotationMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSnowCoverPercentageQuotationMode(String value) {
        this.snowCoverPercentageQuotationMode = value;
    }

    public boolean isSetSnowCoverPercentageQuotationMode() {
        return (this.snowCoverPercentageQuotationMode!= null);
    }

}
