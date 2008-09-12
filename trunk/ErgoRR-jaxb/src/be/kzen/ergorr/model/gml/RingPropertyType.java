
package be.kzen.ergorr.model.gml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * A property with the content model of gml:RingPropertyType encapsulates a ring to represent a component of a surface boundary.
 * 
 * <p>Java class for RingPropertyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RingPropertyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.opengis.net/gml/3.2}Ring"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RingPropertyType", propOrder = {
    "ring"
})
public class RingPropertyType {

    @XmlElement(name = "Ring", required = true)
    protected RingType ring;

    /**
     * Gets the value of the ring property.
     * 
     * @return
     *     possible object is
     *     {@link RingType }
     *     
     */
    public RingType getRing() {
        return ring;
    }

    /**
     * Sets the value of the ring property.
     * 
     * @param value
     *     allowed object is
     *     {@link RingType }
     *     
     */
    public void setRing(RingType value) {
        this.ring = value;
    }

    public boolean isSetRing() {
        return (this.ring!= null);
    }

}
