
package be.kzen.ergorr.model.eo.hma;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import be.kzen.ergorr.model.gml.AbstractFeatureType;


/**
 * <p>Java class for EarthObservationResultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EarthObservationResultType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/gml}AbstractFeatureType">
 *       &lt;sequence>
 *         &lt;element name="browse" type="{http://earth.esa.int/hma}BrowseInformationArrayPropertyType" minOccurs="0"/>
 *         &lt;element name="product" type="{http://earth.esa.int/hma}ProductInformationArrayPropertyType" minOccurs="0"/>
 *         &lt;element name="mask" type="{http://earth.esa.int/hma}MaskInformationArrayPropertyType" minOccurs="0"/>
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
    "browse",
    "product",
    "mask"
})
@XmlSeeAlso({
    be.kzen.ergorr.model.eo.atm.EarthObservationResultType.class,
    be.kzen.ergorr.model.eo.opt.EarthObservationResultType.class
})
public class EarthObservationResultType
    extends AbstractFeatureType
{

    protected BrowseInformationArrayPropertyType browse;
    protected ProductInformationArrayPropertyType product;
    protected MaskInformationArrayPropertyType mask;

    /**
     * Gets the value of the browse property.
     * 
     * @return
     *     possible object is
     *     {@link BrowseInformationArrayPropertyType }
     *     
     */
    public BrowseInformationArrayPropertyType getBrowse() {
        return browse;
    }

    /**
     * Sets the value of the browse property.
     * 
     * @param value
     *     allowed object is
     *     {@link BrowseInformationArrayPropertyType }
     *     
     */
    public void setBrowse(BrowseInformationArrayPropertyType value) {
        this.browse = value;
    }

    public boolean isSetBrowse() {
        return (this.browse!= null);
    }

    /**
     * Gets the value of the product property.
     * 
     * @return
     *     possible object is
     *     {@link ProductInformationArrayPropertyType }
     *     
     */
    public ProductInformationArrayPropertyType getProduct() {
        return product;
    }

    /**
     * Sets the value of the product property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductInformationArrayPropertyType }
     *     
     */
    public void setProduct(ProductInformationArrayPropertyType value) {
        this.product = value;
    }

    public boolean isSetProduct() {
        return (this.product!= null);
    }

    /**
     * Gets the value of the mask property.
     * 
     * @return
     *     possible object is
     *     {@link MaskInformationArrayPropertyType }
     *     
     */
    public MaskInformationArrayPropertyType getMask() {
        return mask;
    }

    /**
     * Sets the value of the mask property.
     * 
     * @param value
     *     allowed object is
     *     {@link MaskInformationArrayPropertyType }
     *     
     */
    public void setMask(MaskInformationArrayPropertyType value) {
        this.mask = value;
    }

    public boolean isSetMask() {
        return (this.mask!= null);
    }

}
