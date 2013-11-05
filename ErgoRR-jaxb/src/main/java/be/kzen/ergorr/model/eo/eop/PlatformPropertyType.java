
package be.kzen.ergorr.model.eo.eop;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PlatformPropertyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PlatformPropertyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://earth.esa.int/eop}Platform"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PlatformPropertyType", propOrder = {
    "platform"
})
public class PlatformPropertyType {

    @XmlElement(name = "Platform", required = true)
    protected PlatformType platform;

    /**
     * Gets the value of the platform property.
     * 
     * @return
     *     possible object is
     *     {@link PlatformType }
     *     
     */
    public PlatformType getPlatform() {
        return platform;
    }

    /**
     * Sets the value of the platform property.
     * 
     * @param value
     *     allowed object is
     *     {@link PlatformType }
     *     
     */
    public void setPlatform(PlatformType value) {
        this.platform = value;
    }

    public boolean isSetPlatform() {
        return (this.platform!= null);
    }

}
