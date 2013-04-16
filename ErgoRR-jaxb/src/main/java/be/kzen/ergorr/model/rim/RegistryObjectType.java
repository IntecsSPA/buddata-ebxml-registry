
package be.kzen.ergorr.model.rim;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RegistryObjectType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RegistryObjectType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0}IdentifiableType">
 *       &lt;sequence minOccurs="0">
 *         &lt;element ref="{urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0}Name" minOccurs="0"/>
 *         &lt;element ref="{urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0}Description" minOccurs="0"/>
 *         &lt;element name="VersionInfo" type="{urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0}VersionInfoType" minOccurs="0"/>
 *         &lt;element ref="{urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0}Classification" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0}ExternalIdentifier" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="lid" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       &lt;attribute name="objectType" type="{urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0}referenceURI" />
 *       &lt;attribute name="status" type="{urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0}referenceURI" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegistryObjectType", propOrder = {
    "name",
    "description",
    "versionInfo",
    "classification",
    "externalIdentifier"
})
@XmlSeeAlso({
    ExtrinsicObjectType.class,
    ClassificationSchemeType.class,
    ServiceType.class,
    ClassificationNodeType.class,
    AssociationType .class,
    OrganizationType.class,
    AdhocQueryType.class,
    RegistryType.class,
    ClassificationType.class,
    FederationType.class,
    ServiceBindingType.class,
    RegistryPackageType.class,
    NotificationType.class,
    SpecificationLinkType.class,
    ExternalLinkType.class,
    AuditableEventType.class,
    SubscriptionType.class,
    ExternalIdentifierType.class,
    PersonType.class
})
public class RegistryObjectType
    extends IdentifiableType
{

    @XmlElement(name = "Name")
    protected InternationalStringType name;
    @XmlElement(name = "Description")
    protected InternationalStringType description;
    @XmlElement(name = "VersionInfo")
    protected VersionInfoType versionInfo;
    @XmlElement(name = "Classification")
    protected List<ClassificationType> classification;
    @XmlElement(name = "ExternalIdentifier")
    protected List<ExternalIdentifierType> externalIdentifier;
    @XmlAttribute
    @XmlSchemaType(name = "anyURI")
    protected String lid;
    @XmlAttribute
    protected String objectType;
    @XmlAttribute
    protected String status;

    @XmlTransient
    protected String objectTypeUrn = "";
    @XmlTransient
    protected boolean hasStaticObjectTypeUrn = false;

    public boolean hasStaticObjectTypeUrn() {
        return hasStaticObjectTypeUrn;
    }

    public String getObjectTypeUrn() {
        return objectTypeUrn;
    }

    public void setObjectTypeUrn(String objectTypeUrn) {
        this.objectTypeUrn = objectTypeUrn;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link InternationalStringType }
     *     
     */
    public InternationalStringType getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link InternationalStringType }
     *     
     */
    public void setName(InternationalStringType value) {
        this.name = value;
    }

    public boolean isSetName() {
        return (this.name!= null);
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link InternationalStringType }
     *     
     */
    public InternationalStringType getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link InternationalStringType }
     *     
     */
    public void setDescription(InternationalStringType value) {
        this.description = value;
    }

    public boolean isSetDescription() {
        return (this.description!= null);
    }

    /**
     * Gets the value of the versionInfo property.
     * 
     * @return
     *     possible object is
     *     {@link VersionInfoType }
     *     
     */
    public VersionInfoType getVersionInfo() {
        return versionInfo;
    }

    /**
     * Sets the value of the versionInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link VersionInfoType }
     *     
     */
    public void setVersionInfo(VersionInfoType value) {
        this.versionInfo = value;
    }

    public boolean isSetVersionInfo() {
        return (this.versionInfo!= null);
    }

    /**
     * Gets the value of the classification property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the classification property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClassification().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ClassificationType }
     * 
     * 
     */
    public List<ClassificationType> getClassification() {
        if (classification == null) {
            classification = new ArrayList<ClassificationType>();
        }
        return this.classification;
    }

    public boolean isSetClassification() {
        return ((this.classification!= null)&&(!this.classification.isEmpty()));
    }

    public void unsetClassification() {
        this.classification = null;
    }

    /**
     * Gets the value of the externalIdentifier property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the externalIdentifier property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExternalIdentifier().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExternalIdentifierType }
     * 
     * 
     */
    public List<ExternalIdentifierType> getExternalIdentifier() {
        if (externalIdentifier == null) {
            externalIdentifier = new ArrayList<ExternalIdentifierType>();
        }
        return this.externalIdentifier;
    }

    public boolean isSetExternalIdentifier() {
        return ((this.externalIdentifier!= null)&&(!this.externalIdentifier.isEmpty()));
    }

    public void unsetExternalIdentifier() {
        this.externalIdentifier = null;
    }

    /**
     * Gets the value of the lid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLid() {
        return lid;
    }

    /**
     * Sets the value of the lid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLid(String value) {
        this.lid = value;
    }

    public boolean isSetLid() {
        return (this.lid!= null);
    }

    /**
     * Gets the value of the objectType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObjectType() {
        return objectType;
    }

    /**
     * Sets the value of the objectType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObjectType(String value) {
        this.objectType = value;
    }

    public boolean isSetObjectType() {
        return (this.objectType!= null);
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    public boolean isSetStatus() {
        return (this.status!= null);
    }
}
