
package be.kzen.ergorr.model.gml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import be.kzen.ergorr.model.eo.hma.EarthObservationType;


/**
 * <p>Java class for ObservationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ObservationType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/gml}AbstractFeatureType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.opengis.net/gml}validTime"/>
 *         &lt;element ref="{http://www.opengis.net/gml}using" minOccurs="0"/>
 *         &lt;element ref="{http://www.opengis.net/gml}target" minOccurs="0"/>
 *         &lt;element ref="{http://www.opengis.net/gml}resultOf"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ObservationType", propOrder = {
    "validTime",
    "using",
    "target",
    "resultOf"
})
@XmlSeeAlso({
    EarthObservationType.class
})
public class ObservationType
    extends AbstractFeatureType
{

    @XmlElement(required = true)
    protected TimePrimitivePropertyType validTime;
    protected FeaturePropertyType using;
    protected TargetPropertyType target;
    @XmlElement(required = true)
    protected AssociationType resultOf;

    /**
     * Gets the value of the validTime property.
     * 
     * @return
     *     possible object is
     *     {@link TimePrimitivePropertyType }
     *     
     */
    public TimePrimitivePropertyType getValidTime() {
        return validTime;
    }

    /**
     * Sets the value of the validTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimePrimitivePropertyType }
     *     
     */
    public void setValidTime(TimePrimitivePropertyType value) {
        this.validTime = value;
    }

    public boolean isSetValidTime() {
        return (this.validTime!= null);
    }

    /**
     * Gets the value of the using property.
     * 
     * @return
     *     possible object is
     *     {@link FeaturePropertyType }
     *     
     */
    public FeaturePropertyType getUsing() {
        return using;
    }

    /**
     * Sets the value of the using property.
     * 
     * @param value
     *     allowed object is
     *     {@link FeaturePropertyType }
     *     
     */
    public void setUsing(FeaturePropertyType value) {
        this.using = value;
    }

    public boolean isSetUsing() {
        return (this.using!= null);
    }

    /**
     * Gets the value of the target property.
     * 
     * @return
     *     possible object is
     *     {@link TargetPropertyType }
     *     
     */
    public TargetPropertyType getTarget() {
        return target;
    }

    /**
     * Sets the value of the target property.
     * 
     * @param value
     *     allowed object is
     *     {@link TargetPropertyType }
     *     
     */
    public void setTarget(TargetPropertyType value) {
        this.target = value;
    }

    public boolean isSetTarget() {
        return (this.target!= null);
    }

    /**
     * Gets the value of the resultOf property.
     * 
     * @return
     *     possible object is
     *     {@link AssociationType }
     *     
     */
    public AssociationType getResultOf() {
        return resultOf;
    }

    /**
     * Sets the value of the resultOf property.
     * 
     * @param value
     *     allowed object is
     *     {@link AssociationType }
     *     
     */
    public void setResultOf(AssociationType value) {
        this.resultOf = value;
    }

    public boolean isSetResultOf() {
        return (this.resultOf!= null);
    }

}
