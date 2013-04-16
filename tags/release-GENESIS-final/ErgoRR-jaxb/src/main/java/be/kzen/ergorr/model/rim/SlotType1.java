
package be.kzen.ergorr.model.rim;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SlotType1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SlotType1">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0}ValueList"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0}LongName" />
 *       &lt;attribute name="slotType" type="{urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0}referenceURI" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SlotType1", propOrder = {
    "valueList"
})
public class SlotType1 {

    @XmlElementRef(name = "ValueList", namespace = "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0", type = JAXBElement.class)
    protected JAXBElement<? extends be.kzen.ergorr.model.rim.ValueListType> valueList;
    @XmlAttribute(required = true)
    protected String name;
    @XmlAttribute
    protected String slotType;

    /**
     * Gets the value of the valueList property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link be.kzen.ergorr.model.rim.ValueListType }{@code >}
     *     {@link JAXBElement }{@code <}{@link be.kzen.ergorr.model.wrs.WrsValueListType }{@code >}
     *     
     */
    public JAXBElement<? extends be.kzen.ergorr.model.rim.ValueListType> getValueList() {
        return valueList;
    }

    /**
     * Sets the value of the valueList property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link be.kzen.ergorr.model.rim.ValueListType }{@code >}
     *     {@link JAXBElement }{@code <}{@link be.kzen.ergorr.model.wrs.WrsValueListType }{@code >}
     *     
     */
    public void setValueList(JAXBElement<? extends be.kzen.ergorr.model.rim.ValueListType> value) {
        this.valueList = ((JAXBElement<? extends be.kzen.ergorr.model.rim.ValueListType> ) value);
    }

    public boolean isSetValueList() {
        return (this.valueList!= null);
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    public boolean isSetName() {
        return (this.name!= null);
    }

    /**
     * Gets the value of the slotType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSlotType() {
        return slotType;
    }

    /**
     * Sets the value of the slotType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSlotType(String value) {
        this.slotType = value;
    }

    public boolean isSetSlotType() {
        return (this.slotType!= null);
    }
}
