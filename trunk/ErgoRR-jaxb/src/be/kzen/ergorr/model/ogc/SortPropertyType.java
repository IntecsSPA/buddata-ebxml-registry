
package be.kzen.ergorr.model.ogc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;


/**
 * <p>Java class for SortPropertyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SortPropertyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.opengis.net/ogc}PropertyName"/>
 *         &lt;element name="SortOrder" type="{http://www.opengis.net/ogc}SortOrderType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SortPropertyType", propOrder = {
    "propertyName",
    "sortOrder"
})
public class SortPropertyType {

    @XmlAnyElement
    protected Element propertyName;
    @XmlElement(name = "SortOrder")
    protected SortOrderType sortOrder;

    /**
     * Gets the value of the propertyName property.
     * 
     * @return
     *     possible object is
     *     {@link Element }
     *     
     */
    public Element getPropertyName() {
        return propertyName;
    }

    /**
     * Sets the value of the propertyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link Element }
     *     
     */
    public void setPropertyName(Element value) {
        this.propertyName = value;
    }

    public boolean isSetPropertyName() {
        return (this.propertyName!= null);
    }

    /**
     * Gets the value of the sortOrder property.
     * 
     * @return
     *     possible object is
     *     {@link SortOrderType }
     *     
     */
    public SortOrderType getSortOrder() {
        return sortOrder;
    }

    /**
     * Sets the value of the sortOrder property.
     * 
     * @param value
     *     allowed object is
     *     {@link SortOrderType }
     *     
     */
    public void setSortOrder(SortOrderType value) {
        this.sortOrder = value;
    }

    public boolean isSetSortOrder() {
        return (this.sortOrder!= null);
    }

}
