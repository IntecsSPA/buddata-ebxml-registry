
package be.kzen.ergorr.model.eo.hma;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import be.kzen.ergorr.model.gml.CodeListType;


/**
 * <p>Java class for ArchivingInformationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArchivingInformationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="archivingCenter" type="{http://www.opengis.net/gml}CodeListType"/>
 *         &lt;element name="archivingDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="archivingIdentifier" type="{http://earth.esa.int/hma}CodeWithAuthorityType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArchivingInformationType", propOrder = {
    "archivingCenter",
    "archivingDate",
    "archivingIdentifier"
})
public class ArchivingInformationType {

    @XmlElement(required = true)
    protected CodeListType archivingCenter;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar archivingDate;
    protected CodeWithAuthorityType archivingIdentifier;

    /**
     * Gets the value of the archivingCenter property.
     * 
     * @return
     *     possible object is
     *     {@link CodeListType }
     *     
     */
    public CodeListType getArchivingCenter() {
        return archivingCenter;
    }

    /**
     * Sets the value of the archivingCenter property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeListType }
     *     
     */
    public void setArchivingCenter(CodeListType value) {
        this.archivingCenter = value;
    }

    public boolean isSetArchivingCenter() {
        return (this.archivingCenter!= null);
    }

    /**
     * Gets the value of the archivingDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getArchivingDate() {
        return archivingDate;
    }

    /**
     * Sets the value of the archivingDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setArchivingDate(XMLGregorianCalendar value) {
        this.archivingDate = value;
    }

    public boolean isSetArchivingDate() {
        return (this.archivingDate!= null);
    }

    /**
     * Gets the value of the archivingIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link CodeWithAuthorityType }
     *     
     */
    public CodeWithAuthorityType getArchivingIdentifier() {
        return archivingIdentifier;
    }

    /**
     * Sets the value of the archivingIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeWithAuthorityType }
     *     
     */
    public void setArchivingIdentifier(CodeWithAuthorityType value) {
        this.archivingIdentifier = value;
    }

    public boolean isSetArchivingIdentifier() {
        return (this.archivingIdentifier!= null);
    }

}
