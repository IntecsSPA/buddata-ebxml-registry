
package be.kzen.ergorr.model.gml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import be.kzen.ergorr.model.eo.hma.EarthObservationEquipmentType;
import be.kzen.ergorr.model.eo.hma.EarthObservationResultType;
import be.kzen.ergorr.model.eo.hma.FootprintType;
import be.kzen.ergorr.model.eo.hma.MaskFeatureType;
import be.kzen.ergorr.model.eo.hma.MaskType;


/**
 * An abstract feature provides a set of common properties, including id, metaDataProperty, name and description inherited from AbstractGMLType, plus boundedBy.    A concrete feature type must derive from this type and specify additional  properties in an application schema. A feature must possess an identifying attribute ('id' - 'fid' has been deprecated).
 * 
 * <p>Java class for AbstractFeatureType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AbstractFeatureType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/gml}AbstractGMLType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.opengis.net/gml}boundedBy" minOccurs="0"/>
 *         &lt;element ref="{http://www.opengis.net/gml}location" minOccurs="0"/>
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
    AbstractFeatureCollectionType.class,
    MaskType.class,
    MaskFeatureType.class,
    FootprintType.class,
    EarthObservationEquipmentType.class,
    EarthObservationResultType.class,
    ObservationType.class
})
public abstract class AbstractFeatureType
    extends AbstractGMLType
{

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
     * deprecated in GML version 3.1
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
