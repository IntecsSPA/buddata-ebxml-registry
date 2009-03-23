
package be.kzen.ergorr.model.eo.hma;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import be.kzen.ergorr.model.gml.AbstractFeatureType;
import be.kzen.ergorr.model.gml.SurfacePropertyType;


/**
 * <p>Java class for MaskFeatureType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MaskFeatureType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/gml}AbstractFeatureType">
 *       &lt;sequence>
 *         &lt;element name="maskType" type="{http://earth.esa.int/hma}CodeWithAuthorityType"/>
 *         &lt;element ref="{http://www.opengis.net/gml}extentOf"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MaskFeatureType", propOrder = {
    "maskType",
    "extentOf"
})
public class MaskFeatureType
    extends AbstractFeatureType
{

    @XmlElement(required = true)
    protected CodeWithAuthorityType maskType;
    @XmlElement(namespace = "http://www.opengis.net/gml", required = true)
    protected SurfacePropertyType extentOf;

    /**
     * Gets the value of the maskType property.
     * 
     * @return
     *     possible object is
     *     {@link CodeWithAuthorityType }
     *     
     */
    public CodeWithAuthorityType getMaskType() {
        return maskType;
    }

    /**
     * Sets the value of the maskType property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeWithAuthorityType }
     *     
     */
    public void setMaskType(CodeWithAuthorityType value) {
        this.maskType = value;
    }

    public boolean isSetMaskType() {
        return (this.maskType!= null);
    }

    /**
     * Mask member extent. Expected structure is gml:Polygon/gml:exterior/gml:LinearRing/gml:posList with 0 to n gml:Polygon/gml:interior/gml:LinearRing/gml:posList elements representing the holes.
     * 
     * @return
     *     possible object is
     *     {@link SurfacePropertyType }
     *     
     */
    public SurfacePropertyType getExtentOf() {
        return extentOf;
    }

    /**
     * Sets the value of the extentOf property.
     * 
     * @param value
     *     allowed object is
     *     {@link SurfacePropertyType }
     *     
     */
    public void setExtentOf(SurfacePropertyType value) {
        this.extentOf = value;
    }

    public boolean isSetExtentOf() {
        return (this.extentOf!= null);
    }

}
