
package be.kzen.ergorr.model.eo.eop;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import be.kzen.ergorr.model.gml.CodeListType;


/**
 * <p>Java class for DownlinkInformationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DownlinkInformationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="acquisitionStation" type="{http://www.opengis.net/gml}CodeListType"/>
 *         &lt;element name="acquisitionDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DownlinkInformationType", propOrder = {
    "acquisitionStation",
    "acquisitionDate"
})
public class DownlinkInformationType {

    @XmlElement(required = true)
    protected CodeListType acquisitionStation;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar acquisitionDate;

    /**
     * Gets the value of the acquisitionStation property.
     * 
     * @return
     *     possible object is
     *     {@link CodeListType }
     *     
     */
    public CodeListType getAcquisitionStation() {
        return acquisitionStation;
    }

    /**
     * Sets the value of the acquisitionStation property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeListType }
     *     
     */
    public void setAcquisitionStation(CodeListType value) {
        this.acquisitionStation = value;
    }

    public boolean isSetAcquisitionStation() {
        return (this.acquisitionStation!= null);
    }

    /**
     * Gets the value of the acquisitionDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAcquisitionDate() {
        return acquisitionDate;
    }

    /**
     * Sets the value of the acquisitionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAcquisitionDate(XMLGregorianCalendar value) {
        this.acquisitionDate = value;
    }

    public boolean isSetAcquisitionDate() {
        return (this.acquisitionDate!= null);
    }

}
