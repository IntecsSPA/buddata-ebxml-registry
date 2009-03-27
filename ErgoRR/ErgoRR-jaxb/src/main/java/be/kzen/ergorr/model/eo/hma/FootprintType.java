
package be.kzen.ergorr.model.eo.hma;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import be.kzen.ergorr.model.gml.AbstractFeatureType;
import be.kzen.ergorr.model.gml.MultiSurfacePropertyType;
import be.kzen.ergorr.model.gml.PointPropertyType;


/**
 * <p>Java class for FootprintType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FootprintType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/gml}AbstractFeatureType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.opengis.net/gml}multiExtentOf"/>
 *         &lt;element ref="{http://www.opengis.net/gml}centerOf" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FootprintType", propOrder = {
    "multiExtentOf",
    "centerOf"
})
public class FootprintType
    extends AbstractFeatureType
{

    @XmlElement(namespace = "http://www.opengis.net/gml", required = true)
    protected MultiSurfacePropertyType multiExtentOf;
    @XmlElement(namespace = "http://www.opengis.net/gml")
    protected PointPropertyType centerOf;

    /**
     * Acquisition footprint coordinates, described by a closed polygon (last point=first point), using CRS:WGS84, Latitude,Longitude pairs (per-WGS84 definition of point ordering, not necessarily per all WFS implementations). Expected structure is gml:Polygon/gml:exterior/gml:LinearRing/gml:posList.
     * 
     * HMA/EOLI : polygon/coordinates (F B b s)
     * 							
     * 
     * @return
     *     possible object is
     *     {@link MultiSurfacePropertyType }
     *     
     */
    public MultiSurfacePropertyType getMultiExtentOf() {
        return multiExtentOf;
    }

    /**
     * Sets the value of the multiExtentOf property.
     * 
     * @param value
     *     allowed object is
     *     {@link MultiSurfacePropertyType }
     *     
     */
    public void setMultiExtentOf(MultiSurfacePropertyType value) {
        this.multiExtentOf = value;
    }

    public boolean isSetMultiExtentOf() {
        return (this.multiExtentOf!= null);
    }

    /**
     * Acquisition center coordinates.  Expected structure is gml:Point/gml:pos.
     * 
     * HMA/EOLI : scCenter/coordinates (F B b s)
     * 
     * @return
     *     possible object is
     *     {@link PointPropertyType }
     *     
     */
    public PointPropertyType getCenterOf() {
        return centerOf;
    }

    /**
     * Sets the value of the centerOf property.
     * 
     * @param value
     *     allowed object is
     *     {@link PointPropertyType }
     *     
     */
    public void setCenterOf(PointPropertyType value) {
        this.centerOf = value;
    }

    public boolean isSetCenterOf() {
        return (this.centerOf!= null);
    }

}
