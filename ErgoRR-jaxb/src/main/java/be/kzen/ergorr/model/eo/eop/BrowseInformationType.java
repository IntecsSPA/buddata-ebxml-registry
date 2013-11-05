
package be.kzen.ergorr.model.eo.eop;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import be.kzen.ergorr.model.gml.CodeListType;


/**
 * <p>Java class for BrowseInformationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BrowseInformationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="type">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="THUMBNAIL"/>
 *               &lt;enumeration value="QUICKLOOK"/>
 *               &lt;enumeration value="ALBUM"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="subType" type="{http://www.opengis.net/gml}CodeListType" minOccurs="0"/>
 *         &lt;element name="referenceSystemIdentifier" type="{http://earth.esa.int/eop}CodeWithAuthorityType"/>
 *         &lt;element name="fileName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BrowseInformationType", propOrder = {
    "type",
    "subType",
    "referenceSystemIdentifier",
    "fileName"
})
public class BrowseInformationType {

    @XmlElement(required = true)
    protected String type;
    protected CodeListType subType;
    @XmlElement(required = true)
    protected CodeWithAuthorityType referenceSystemIdentifier;
    @XmlElement(required = true)
    protected String fileName;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    public boolean isSetType() {
        return (this.type!= null);
    }

    /**
     * Gets the value of the subType property.
     * 
     * @return
     *     possible object is
     *     {@link CodeListType }
     *     
     */
    public CodeListType getSubType() {
        return subType;
    }

    /**
     * Sets the value of the subType property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeListType }
     *     
     */
    public void setSubType(CodeListType value) {
        this.subType = value;
    }

    public boolean isSetSubType() {
        return (this.subType!= null);
    }

    /**
     * Gets the value of the referenceSystemIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link CodeWithAuthorityType }
     *     
     */
    public CodeWithAuthorityType getReferenceSystemIdentifier() {
        return referenceSystemIdentifier;
    }

    /**
     * Sets the value of the referenceSystemIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeWithAuthorityType }
     *     
     */
    public void setReferenceSystemIdentifier(CodeWithAuthorityType value) {
        this.referenceSystemIdentifier = value;
    }

    public boolean isSetReferenceSystemIdentifier() {
        return (this.referenceSystemIdentifier!= null);
    }

    /**
     * Gets the value of the fileName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the value of the fileName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileName(String value) {
        this.fileName = value;
    }

    public boolean isSetFileName() {
        return (this.fileName!= null);
    }

}
