
package be.kzen.ergorr.model.query;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RegistryObjectQueryType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RegistryObjectQueryType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0}FilterQueryType">
 *       &lt;sequence>
 *         &lt;element name="SlotBranch" type="{urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0}SlotBranchType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="NameBranch" type="{urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0}InternationalStringBranchType" minOccurs="0"/>
 *         &lt;element name="DescriptionBranch" type="{urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0}InternationalStringBranchType" minOccurs="0"/>
 *         &lt;element name="VersionInfoFilter" type="{urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0}FilterType" minOccurs="0"/>
 *         &lt;element ref="{urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0}ClassificationQuery" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0}ExternalIdentifierQuery" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ObjectTypeQuery" type="{urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0}ClassificationNodeQueryType" minOccurs="0"/>
 *         &lt;element name="StatusQuery" type="{urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0}ClassificationNodeQueryType" minOccurs="0"/>
 *         &lt;element name="SourceAssociationQuery" type="{urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0}AssociationQueryType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="TargetAssociationQuery" type="{urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0}AssociationQueryType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegistryObjectQueryType", propOrder = {
    "slotBranch",
    "nameBranch",
    "descriptionBranch",
    "versionInfoFilter",
    "classificationQuery",
    "externalIdentifierQuery",
    "objectTypeQuery",
    "statusQuery",
    "sourceAssociationQuery",
    "targetAssociationQuery"
})
@XmlSeeAlso({
    ClassificationSchemeQueryType.class,
    SpecificationLinkQueryType.class,
    AssociationQueryType.class,
    ExternalIdentifierQueryType.class,
    AdhocQueryQueryType.class,
    ClassificationNodeQueryType.class,
    AuditableEventQueryType.class,
    SubscriptionQueryType.class,
    ExtrinsicObjectQueryType.class,
    ExternalLinkQueryType.class,
    ServiceBindingQueryType.class,
    RegistryQueryType.class,
    RegistryPackageQueryType.class,
    PersonQueryType.class,
    OrganizationQueryType.class,
    FederationQueryType.class,
    ServiceQueryType.class,
    ClassificationQueryType.class,
    NotificationQueryType.class
})
public class RegistryObjectQueryType
    extends FilterQueryType
{

    @XmlElement(name = "SlotBranch")
    protected List<SlotBranchType> slotBranch;
    @XmlElement(name = "NameBranch")
    protected InternationalStringBranchType nameBranch;
    @XmlElement(name = "DescriptionBranch")
    protected InternationalStringBranchType descriptionBranch;
    @XmlElement(name = "VersionInfoFilter")
    protected FilterType versionInfoFilter;
    @XmlElement(name = "ClassificationQuery")
    protected List<ClassificationQueryType> classificationQuery;
    @XmlElement(name = "ExternalIdentifierQuery")
    protected List<ExternalIdentifierQueryType> externalIdentifierQuery;
    @XmlElement(name = "ObjectTypeQuery")
    protected ClassificationNodeQueryType objectTypeQuery;
    @XmlElement(name = "StatusQuery")
    protected ClassificationNodeQueryType statusQuery;
    @XmlElement(name = "SourceAssociationQuery")
    protected List<AssociationQueryType> sourceAssociationQuery;
    @XmlElement(name = "TargetAssociationQuery")
    protected List<AssociationQueryType> targetAssociationQuery;

    /**
     * Gets the value of the slotBranch property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the slotBranch property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSlotBranch().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SlotBranchType }
     * 
     * 
     */
    public List<SlotBranchType> getSlotBranch() {
        if (slotBranch == null) {
            slotBranch = new ArrayList<SlotBranchType>();
        }
        return this.slotBranch;
    }

    public boolean isSetSlotBranch() {
        return ((this.slotBranch!= null)&&(!this.slotBranch.isEmpty()));
    }

    public void unsetSlotBranch() {
        this.slotBranch = null;
    }

    /**
     * Gets the value of the nameBranch property.
     * 
     * @return
     *     possible object is
     *     {@link InternationalStringBranchType }
     *     
     */
    public InternationalStringBranchType getNameBranch() {
        return nameBranch;
    }

    /**
     * Sets the value of the nameBranch property.
     * 
     * @param value
     *     allowed object is
     *     {@link InternationalStringBranchType }
     *     
     */
    public void setNameBranch(InternationalStringBranchType value) {
        this.nameBranch = value;
    }

    public boolean isSetNameBranch() {
        return (this.nameBranch!= null);
    }

    /**
     * Gets the value of the descriptionBranch property.
     * 
     * @return
     *     possible object is
     *     {@link InternationalStringBranchType }
     *     
     */
    public InternationalStringBranchType getDescriptionBranch() {
        return descriptionBranch;
    }

    /**
     * Sets the value of the descriptionBranch property.
     * 
     * @param value
     *     allowed object is
     *     {@link InternationalStringBranchType }
     *     
     */
    public void setDescriptionBranch(InternationalStringBranchType value) {
        this.descriptionBranch = value;
    }

    public boolean isSetDescriptionBranch() {
        return (this.descriptionBranch!= null);
    }

    /**
     * Gets the value of the versionInfoFilter property.
     * 
     * @return
     *     possible object is
     *     {@link FilterType }
     *     
     */
    public FilterType getVersionInfoFilter() {
        return versionInfoFilter;
    }

    /**
     * Sets the value of the versionInfoFilter property.
     * 
     * @param value
     *     allowed object is
     *     {@link FilterType }
     *     
     */
    public void setVersionInfoFilter(FilterType value) {
        this.versionInfoFilter = value;
    }

    public boolean isSetVersionInfoFilter() {
        return (this.versionInfoFilter!= null);
    }

    /**
     * Gets the value of the classificationQuery property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the classificationQuery property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClassificationQuery().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ClassificationQueryType }
     * 
     * 
     */
    public List<ClassificationQueryType> getClassificationQuery() {
        if (classificationQuery == null) {
            classificationQuery = new ArrayList<ClassificationQueryType>();
        }
        return this.classificationQuery;
    }

    public boolean isSetClassificationQuery() {
        return ((this.classificationQuery!= null)&&(!this.classificationQuery.isEmpty()));
    }

    public void unsetClassificationQuery() {
        this.classificationQuery = null;
    }

    /**
     * Gets the value of the externalIdentifierQuery property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the externalIdentifierQuery property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExternalIdentifierQuery().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExternalIdentifierQueryType }
     * 
     * 
     */
    public List<ExternalIdentifierQueryType> getExternalIdentifierQuery() {
        if (externalIdentifierQuery == null) {
            externalIdentifierQuery = new ArrayList<ExternalIdentifierQueryType>();
        }
        return this.externalIdentifierQuery;
    }

    public boolean isSetExternalIdentifierQuery() {
        return ((this.externalIdentifierQuery!= null)&&(!this.externalIdentifierQuery.isEmpty()));
    }

    public void unsetExternalIdentifierQuery() {
        this.externalIdentifierQuery = null;
    }

    /**
     * Gets the value of the objectTypeQuery property.
     * 
     * @return
     *     possible object is
     *     {@link ClassificationNodeQueryType }
     *     
     */
    public ClassificationNodeQueryType getObjectTypeQuery() {
        return objectTypeQuery;
    }

    /**
     * Sets the value of the objectTypeQuery property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClassificationNodeQueryType }
     *     
     */
    public void setObjectTypeQuery(ClassificationNodeQueryType value) {
        this.objectTypeQuery = value;
    }

    public boolean isSetObjectTypeQuery() {
        return (this.objectTypeQuery!= null);
    }

    /**
     * Gets the value of the statusQuery property.
     * 
     * @return
     *     possible object is
     *     {@link ClassificationNodeQueryType }
     *     
     */
    public ClassificationNodeQueryType getStatusQuery() {
        return statusQuery;
    }

    /**
     * Sets the value of the statusQuery property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClassificationNodeQueryType }
     *     
     */
    public void setStatusQuery(ClassificationNodeQueryType value) {
        this.statusQuery = value;
    }

    public boolean isSetStatusQuery() {
        return (this.statusQuery!= null);
    }

    /**
     * Gets the value of the sourceAssociationQuery property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sourceAssociationQuery property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSourceAssociationQuery().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AssociationQueryType }
     * 
     * 
     */
    public List<AssociationQueryType> getSourceAssociationQuery() {
        if (sourceAssociationQuery == null) {
            sourceAssociationQuery = new ArrayList<AssociationQueryType>();
        }
        return this.sourceAssociationQuery;
    }

    public boolean isSetSourceAssociationQuery() {
        return ((this.sourceAssociationQuery!= null)&&(!this.sourceAssociationQuery.isEmpty()));
    }

    public void unsetSourceAssociationQuery() {
        this.sourceAssociationQuery = null;
    }

    /**
     * Gets the value of the targetAssociationQuery property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the targetAssociationQuery property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTargetAssociationQuery().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AssociationQueryType }
     * 
     * 
     */
    public List<AssociationQueryType> getTargetAssociationQuery() {
        if (targetAssociationQuery == null) {
            targetAssociationQuery = new ArrayList<AssociationQueryType>();
        }
        return this.targetAssociationQuery;
    }

    public boolean isSetTargetAssociationQuery() {
        return ((this.targetAssociationQuery!= null)&&(!this.targetAssociationQuery.isEmpty()));
    }

    public void unsetTargetAssociationQuery() {
        this.targetAssociationQuery = null;
    }

}
