
package be.kzen.ergorr.model.eo.eop;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import be.kzen.ergorr.model.gml.CodeListType;


/**
 * <p>Java class for ProcessingInformationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProcessingInformationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="processingCenter" type="{http://www.opengis.net/gml}CodeListType" minOccurs="0"/>
 *         &lt;element name="processingDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="compositeType" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="DAILY"/>
 *               &lt;enumeration value="WEEKLY"/>
 *               &lt;enumeration value="MONTHLY"/>
 *               &lt;enumeration value=""/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="method" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="methodVersion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="processorName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="processorVersion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="processingLevel" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="1A"/>
 *               &lt;enumeration value="1B"/>
 *               &lt;enumeration value="2"/>
 *               &lt;enumeration value="3"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="nativeProductFormat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProcessingInformationType", propOrder = {
    "processingCenter",
    "processingDate",
    "compositeType",
    "method",
    "methodVersion",
    "processorName",
    "processorVersion",
    "processingLevel",
    "nativeProductFormat"
})
public class ProcessingInformationType {

    protected CodeListType processingCenter;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar processingDate;
    protected String compositeType;
    protected String method;
    protected String methodVersion;
    protected String processorName;
    protected String processorVersion;
    protected String processingLevel;
    protected String nativeProductFormat;

    /**
     * Gets the value of the processingCenter property.
     * 
     * @return
     *     possible object is
     *     {@link CodeListType }
     *     
     */
    public CodeListType getProcessingCenter() {
        return processingCenter;
    }

    /**
     * Sets the value of the processingCenter property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeListType }
     *     
     */
    public void setProcessingCenter(CodeListType value) {
        this.processingCenter = value;
    }

    public boolean isSetProcessingCenter() {
        return (this.processingCenter!= null);
    }

    /**
     * Gets the value of the processingDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getProcessingDate() {
        return processingDate;
    }

    /**
     * Sets the value of the processingDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setProcessingDate(XMLGregorianCalendar value) {
        this.processingDate = value;
    }

    public boolean isSetProcessingDate() {
        return (this.processingDate!= null);
    }

    /**
     * Gets the value of the compositeType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompositeType() {
        return compositeType;
    }

    /**
     * Sets the value of the compositeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompositeType(String value) {
        this.compositeType = value;
    }

    public boolean isSetCompositeType() {
        return (this.compositeType!= null);
    }

    /**
     * Gets the value of the method property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMethod() {
        return method;
    }

    /**
     * Sets the value of the method property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMethod(String value) {
        this.method = value;
    }

    public boolean isSetMethod() {
        return (this.method!= null);
    }

    /**
     * Gets the value of the methodVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMethodVersion() {
        return methodVersion;
    }

    /**
     * Sets the value of the methodVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMethodVersion(String value) {
        this.methodVersion = value;
    }

    public boolean isSetMethodVersion() {
        return (this.methodVersion!= null);
    }

    /**
     * Gets the value of the processorName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcessorName() {
        return processorName;
    }

    /**
     * Sets the value of the processorName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcessorName(String value) {
        this.processorName = value;
    }

    public boolean isSetProcessorName() {
        return (this.processorName!= null);
    }

    /**
     * Gets the value of the processorVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcessorVersion() {
        return processorVersion;
    }

    /**
     * Sets the value of the processorVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcessorVersion(String value) {
        this.processorVersion = value;
    }

    public boolean isSetProcessorVersion() {
        return (this.processorVersion!= null);
    }

    /**
     * Gets the value of the processingLevel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcessingLevel() {
        return processingLevel;
    }

    /**
     * Sets the value of the processingLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcessingLevel(String value) {
        this.processingLevel = value;
    }

    public boolean isSetProcessingLevel() {
        return (this.processingLevel!= null);
    }

    /**
     * Gets the value of the nativeProductFormat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNativeProductFormat() {
        return nativeProductFormat;
    }

    /**
     * Sets the value of the nativeProductFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNativeProductFormat(String value) {
        this.nativeProductFormat = value;
    }

    public boolean isSetNativeProductFormat() {
        return (this.nativeProductFormat!= null);
    }

}
