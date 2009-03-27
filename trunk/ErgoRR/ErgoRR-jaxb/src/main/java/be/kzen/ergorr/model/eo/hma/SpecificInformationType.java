
package be.kzen.ergorr.model.eo.hma;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SpecificInformationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SpecificInformationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="localAttribute" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="localValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SpecificInformationType", propOrder = {
    "localAttribute",
    "localValue"
})
public class SpecificInformationType {

    @XmlElement(required = true)
    protected String localAttribute;
    @XmlElement(required = true)
    protected String localValue;

    /**
     * Gets the value of the localAttribute property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalAttribute() {
        return localAttribute;
    }

    /**
     * Sets the value of the localAttribute property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalAttribute(String value) {
        this.localAttribute = value;
    }

    public boolean isSetLocalAttribute() {
        return (this.localAttribute!= null);
    }

    /**
     * Gets the value of the localValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalValue() {
        return localValue;
    }

    /**
     * Sets the value of the localValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalValue(String value) {
        this.localValue = value;
    }

    public boolean isSetLocalValue() {
        return (this.localValue!= null);
    }

}
