
package be.kzen.ergorr.model.gml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for AbstractGMLType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AbstractGMLType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{http://www.opengis.net/gml/3.2}StandardObjectProperties"/>
 *       &lt;/sequence>
 *       &lt;attribute ref="{http://www.opengis.net/gml/3.2}id use="required""/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractGMLType", propOrder = {
    "metaDataProperty",
    "descriptionReference",
    "identifier",
    "name"
})
@XmlSeeAlso({
    AbstractTimeSliceType.class,
    CompositeValueType.class,
    AbstractTimeObjectType.class,
    AbstractGeometryType.class,
    AbstractTopologyType.class,
    DefinitionBaseType.class,
    AbstractFeatureType.class
})
public abstract class AbstractGMLType {

    protected List<MetaDataPropertyType> metaDataProperty;
    protected ReferenceType descriptionReference;
    protected CodeWithAuthorityType identifier;
    protected List<CodeType> name;
    @XmlAttribute(namespace = "http://www.opengis.net/gml/3.2", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Gets the value of the metaDataProperty property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the metaDataProperty property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMetaDataProperty().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MetaDataPropertyType }
     * 
     * 
     */
    public List<MetaDataPropertyType> getMetaDataProperty() {
        if (metaDataProperty == null) {
            metaDataProperty = new ArrayList<MetaDataPropertyType>();
        }
        return this.metaDataProperty;
    }

    public boolean isSetMetaDataProperty() {
        return ((this.metaDataProperty!= null)&&(!this.metaDataProperty.isEmpty()));
    }

    public void unsetMetaDataProperty() {
        this.metaDataProperty = null;
    }

    /**
     * Gets the value of the descriptionReference property.
     * 
     * @return
     *     possible object is
     *     {@link ReferenceType }
     *     
     */
    public ReferenceType getDescriptionReference() {
        return descriptionReference;
    }

    /**
     * Sets the value of the descriptionReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferenceType }
     *     
     */
    public void setDescriptionReference(ReferenceType value) {
        this.descriptionReference = value;
    }

    public boolean isSetDescriptionReference() {
        return (this.descriptionReference!= null);
    }

    /**
     * Gets the value of the identifier property.
     * 
     * @return
     *     possible object is
     *     {@link CodeWithAuthorityType }
     *     
     */
    public CodeWithAuthorityType getIdentifier() {
        return identifier;
    }

    /**
     * Sets the value of the identifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeWithAuthorityType }
     *     
     */
    public void setIdentifier(CodeWithAuthorityType value) {
        this.identifier = value;
    }

    public boolean isSetIdentifier() {
        return (this.identifier!= null);
    }

    /**
     * Gets the value of the name property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the name property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getName().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CodeType }
     * 
     * 
     */
    public List<CodeType> getName() {
        if (name == null) {
            name = new ArrayList<CodeType>();
        }
        return this.name;
    }

    public boolean isSetName() {
        return ((this.name!= null)&&(!this.name.isEmpty()));
    }

    public void unsetName() {
        this.name = null;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    public boolean isSetId() {
        return (this.id!= null);
    }

}
