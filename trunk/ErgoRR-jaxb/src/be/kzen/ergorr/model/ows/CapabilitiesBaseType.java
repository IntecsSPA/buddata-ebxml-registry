
package be.kzen.ergorr.model.ows;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import be.kzen.ergorr.model.csw.CapabilitiesType;


/**
 * XML encoded GetCapabilities operation response. This document provides clients with service metadata about a specific service instance, usually including metadata about the tightly-coupled data served. If the server does not implement the updateSequence parameter, the server shall always return the complete Capabilities document, without the updateSequence parameter. When the server implements the updateSequence parameter and the GetCapabilities operation request included the updateSequence parameter with the current value, the server shall return this element with only the "version" and "updateSequence" attributes. Otherwise, all optional elements shall be included or not depending on the actual value of the Contents parameter in the GetCapabilities operation request. This base type shall be extended by each specific OWS to include the additional contents needed. 
 * 
 * <p>Java class for CapabilitiesBaseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CapabilitiesBaseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.opengis.net/ows/1.1}ServiceIdentification" minOccurs="0"/>
 *         &lt;element ref="{http://www.opengis.net/ows/1.1}ServiceProvider" minOccurs="0"/>
 *         &lt;element ref="{http://www.opengis.net/ows/1.1}OperationsMetadata" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="version" use="required" type="{http://www.opengis.net/ows/1.1}VersionType" />
 *       &lt;attribute name="updateSequence" type="{http://www.opengis.net/ows/1.1}UpdateSequenceType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CapabilitiesBaseType", propOrder = {
    "serviceIdentification",
    "serviceProvider",
    "operationsMetadata"
})
@XmlSeeAlso({
    CapabilitiesType.class
})
public class CapabilitiesBaseType {

    @XmlElement(name = "ServiceIdentification")
    protected ServiceIdentification serviceIdentification;
    @XmlElement(name = "ServiceProvider")
    protected ServiceProvider serviceProvider;
    @XmlElement(name = "OperationsMetadata")
    protected OperationsMetadata operationsMetadata;
    @XmlAttribute(required = true)
    protected String version;
    @XmlAttribute
    protected String updateSequence;

    /**
     * Gets the value of the serviceIdentification property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceIdentification }
     *     
     */
    public ServiceIdentification getServiceIdentification() {
        return serviceIdentification;
    }

    /**
     * Sets the value of the serviceIdentification property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceIdentification }
     *     
     */
    public void setServiceIdentification(ServiceIdentification value) {
        this.serviceIdentification = value;
    }

    public boolean isSetServiceIdentification() {
        return (this.serviceIdentification!= null);
    }

    /**
     * Gets the value of the serviceProvider property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceProvider }
     *     
     */
    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    /**
     * Sets the value of the serviceProvider property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceProvider }
     *     
     */
    public void setServiceProvider(ServiceProvider value) {
        this.serviceProvider = value;
    }

    public boolean isSetServiceProvider() {
        return (this.serviceProvider!= null);
    }

    /**
     * Gets the value of the operationsMetadata property.
     * 
     * @return
     *     possible object is
     *     {@link OperationsMetadata }
     *     
     */
    public OperationsMetadata getOperationsMetadata() {
        return operationsMetadata;
    }

    /**
     * Sets the value of the operationsMetadata property.
     * 
     * @param value
     *     allowed object is
     *     {@link OperationsMetadata }
     *     
     */
    public void setOperationsMetadata(OperationsMetadata value) {
        this.operationsMetadata = value;
    }

    public boolean isSetOperationsMetadata() {
        return (this.operationsMetadata!= null);
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    public boolean isSetVersion() {
        return (this.version!= null);
    }

    /**
     * Gets the value of the updateSequence property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpdateSequence() {
        return updateSequence;
    }

    /**
     * Sets the value of the updateSequence property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpdateSequence(String value) {
        this.updateSequence = value;
    }

    public boolean isSetUpdateSequence() {
        return (this.updateSequence!= null);
    }

}
