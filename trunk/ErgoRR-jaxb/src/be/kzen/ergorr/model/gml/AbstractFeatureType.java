
package be.kzen.ergorr.model.gml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import be.kzen.ergorr.model.eo.hma.EarthObservationEquipmentType;
import be.kzen.ergorr.model.eo.hma.EarthObservationResultType;
import be.kzen.ergorr.model.eo.hma.FootprintType;
import be.kzen.ergorr.model.eo.hma.MaskFeatureType;
import be.kzen.ergorr.model.eo.hma.MaskType;


/**
 * The basic feature model is given by the gml:AbstractFeatureType.
 * The content model for gml:AbstractFeatureType adds two specific properties suitable for geographic features to the content model defined in gml:AbstractGMLType. 
 * The value of the gml:boundedBy property describes an envelope that encloses the entire feature instance, and is primarily useful for supporting rapid searching for features that occur in a particular location. 
 * The value of the gml:location property describes the extent, position or relative location of the feature.
 * 
 * <p>Java class for AbstractFeatureType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AbstractFeatureType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/gml/3.2}AbstractGMLType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.opengis.net/gml/3.2}boundedBy" minOccurs="0"/>
 *         &lt;element ref="{http://www.opengis.net/gml/3.2}location" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractFeatureType", propOrder = {
    "boundedBy",
    "location"
})
@XmlSeeAlso({
    DynamicFeatureType.class,
    AbstractCoverageType.class,
    MaskFeatureType.class,
    MaskType.class,
    EarthObservationEquipmentType.class,
    FootprintType.class,
    EarthObservationResultType.class,
    ObservationType.class
})
public abstract class AbstractFeatureType
    extends AbstractGMLType
{

    @XmlElement(nillable = true)
    protected BoundingShapeType boundedBy;
    protected LocationPropertyType location;

    /**
     * Gets the value of the boundedBy property.
     * 
     * @return
     *     possible object is
     *     {@link BoundingShapeType }
     *     
     */
    public BoundingShapeType getBoundedBy() {
        return boundedBy;
    }

    /**
     * Sets the value of the boundedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link BoundingShapeType }
     *     
     */
    public void setBoundedBy(BoundingShapeType value) {
        this.boundedBy = value;
    }

    public boolean isSetBoundedBy() {
        return (this.boundedBy!= null);
    }

    /**
     * Gets the value of the location property.
     * 
     * @return
     *     possible object is
     *     {@link LocationPropertyType }
     *     
     */
    public LocationPropertyType getLocation() {
        return location;
    }

    /**
     * Sets the value of the location property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationPropertyType }
     *     
     */
    public void setLocation(LocationPropertyType value) {
        this.location = value;
    }

    public boolean isSetLocation() {
        return (this.location!= null);
    }

}
