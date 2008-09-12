
package be.kzen.ergorr.model.csw;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import be.kzen.ergorr.model.ows.BoundingBoxType;
import be.kzen.ergorr.model.ows.WGS84BoundingBoxType;
import be.kzen.ergorr.model.purl.elements.SimpleLiteral;


/**
 * 
 *             This type defines a summary representation of the common record
 *             format.  It extends AbstractRecordType to include the core
 *             properties.
 *          
 * 
 * <p>Java class for SummaryRecordType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SummaryRecordType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/cat/csw/2.0.2}AbstractRecordType">
 *       &lt;sequence>
 *         &lt;element ref="{http://purl.org/dc/elements/1.1/}identifier" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://purl.org/dc/elements/1.1/}title" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://purl.org/dc/elements/1.1/}type" minOccurs="0"/>
 *         &lt;element ref="{http://purl.org/dc/elements/1.1/}subject" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://purl.org/dc/elements/1.1/}format" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://purl.org/dc/elements/1.1/}relation" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://purl.org/dc/terms/}modified" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://purl.org/dc/terms/}abstract" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://purl.org/dc/terms/}spatial" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.opengis.net/ows/1.1}BoundingBox" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SummaryRecordType", propOrder = {
    "identifier",
    "title",
    "type",
    "subject",
    "format",
    "relation",
    "modified",
    "_abstract",
    "spatial",
    "boundingBox"
})
public class SummaryRecordType
    extends AbstractRecordType
{

    @XmlElementRef(name = "identifier", namespace = "http://purl.org/dc/elements/1.1/", type = JAXBElement.class)
    protected List<JAXBElement<SimpleLiteral>> identifier;
    @XmlElementRef(name = "title", namespace = "http://purl.org/dc/elements/1.1/", type = JAXBElement.class)
    protected List<JAXBElement<SimpleLiteral>> title;
    @XmlElement(namespace = "http://purl.org/dc/elements/1.1/")
    protected SimpleLiteral type;
    @XmlElement(namespace = "http://purl.org/dc/elements/1.1/")
    protected List<SimpleLiteral> subject;
    @XmlElementRef(name = "format", namespace = "http://purl.org/dc/elements/1.1/", type = JAXBElement.class)
    protected List<JAXBElement<SimpleLiteral>> format;
    @XmlElementRef(name = "relation", namespace = "http://purl.org/dc/elements/1.1/", type = JAXBElement.class)
    protected List<JAXBElement<SimpleLiteral>> relation;
    @XmlElement(namespace = "http://purl.org/dc/terms/")
    protected List<SimpleLiteral> modified;
    @XmlElement(name = "abstract", namespace = "http://purl.org/dc/terms/")
    protected List<SimpleLiteral> _abstract;
    @XmlElement(namespace = "http://purl.org/dc/terms/")
    protected List<SimpleLiteral> spatial;
    @XmlElementRef(name = "BoundingBox", namespace = "http://www.opengis.net/ows/1.1", type = JAXBElement.class)
    protected List<JAXBElement<? extends BoundingBoxType>> boundingBox;

    /**
     * Gets the value of the identifier property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the identifier property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIdentifier().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link SimpleLiteral }{@code >}
     * {@link JAXBElement }{@code <}{@link SimpleLiteral }{@code >}
     * 
     * 
     */
    public List<JAXBElement<SimpleLiteral>> getIdentifier() {
        if (identifier == null) {
            identifier = new ArrayList<JAXBElement<SimpleLiteral>>();
        }
        return this.identifier;
    }

    public boolean isSetIdentifier() {
        return ((this.identifier!= null)&&(!this.identifier.isEmpty()));
    }

    public void unsetIdentifier() {
        this.identifier = null;
    }

    /**
     * Gets the value of the title property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the title property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTitle().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link SimpleLiteral }{@code >}
     * {@link JAXBElement }{@code <}{@link SimpleLiteral }{@code >}
     * 
     * 
     */
    public List<JAXBElement<SimpleLiteral>> getTitle() {
        if (title == null) {
            title = new ArrayList<JAXBElement<SimpleLiteral>>();
        }
        return this.title;
    }

    public boolean isSetTitle() {
        return ((this.title!= null)&&(!this.title.isEmpty()));
    }

    public void unsetTitle() {
        this.title = null;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link SimpleLiteral }
     *     
     */
    public SimpleLiteral getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link SimpleLiteral }
     *     
     */
    public void setType(SimpleLiteral value) {
        this.type = value;
    }

    public boolean isSetType() {
        return (this.type!= null);
    }

    /**
     * Gets the value of the subject property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subject property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubject().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SimpleLiteral }
     * 
     * 
     */
    public List<SimpleLiteral> getSubject() {
        if (subject == null) {
            subject = new ArrayList<SimpleLiteral>();
        }
        return this.subject;
    }

    public boolean isSetSubject() {
        return ((this.subject!= null)&&(!this.subject.isEmpty()));
    }

    public void unsetSubject() {
        this.subject = null;
    }

    /**
     * Gets the value of the format property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the format property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFormat().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link SimpleLiteral }{@code >}
     * {@link JAXBElement }{@code <}{@link SimpleLiteral }{@code >}
     * {@link JAXBElement }{@code <}{@link SimpleLiteral }{@code >}
     * 
     * 
     */
    public List<JAXBElement<SimpleLiteral>> getFormat() {
        if (format == null) {
            format = new ArrayList<JAXBElement<SimpleLiteral>>();
        }
        return this.format;
    }

    public boolean isSetFormat() {
        return ((this.format!= null)&&(!this.format.isEmpty()));
    }

    public void unsetFormat() {
        this.format = null;
    }

    /**
     * Gets the value of the relation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the relation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRelation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link SimpleLiteral }{@code >}
     * {@link JAXBElement }{@code <}{@link SimpleLiteral }{@code >}
     * {@link JAXBElement }{@code <}{@link SimpleLiteral }{@code >}
     * {@link JAXBElement }{@code <}{@link SimpleLiteral }{@code >}
     * {@link JAXBElement }{@code <}{@link SimpleLiteral }{@code >}
     * {@link JAXBElement }{@code <}{@link SimpleLiteral }{@code >}
     * {@link JAXBElement }{@code <}{@link SimpleLiteral }{@code >}
     * {@link JAXBElement }{@code <}{@link SimpleLiteral }{@code >}
     * {@link JAXBElement }{@code <}{@link SimpleLiteral }{@code >}
     * {@link JAXBElement }{@code <}{@link SimpleLiteral }{@code >}
     * {@link JAXBElement }{@code <}{@link SimpleLiteral }{@code >}
     * {@link JAXBElement }{@code <}{@link SimpleLiteral }{@code >}
     * {@link JAXBElement }{@code <}{@link SimpleLiteral }{@code >}
     * {@link JAXBElement }{@code <}{@link SimpleLiteral }{@code >}
     * 
     * 
     */
    public List<JAXBElement<SimpleLiteral>> getRelation() {
        if (relation == null) {
            relation = new ArrayList<JAXBElement<SimpleLiteral>>();
        }
        return this.relation;
    }

    public boolean isSetRelation() {
        return ((this.relation!= null)&&(!this.relation.isEmpty()));
    }

    public void unsetRelation() {
        this.relation = null;
    }

    /**
     * Gets the value of the modified property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the modified property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getModified().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SimpleLiteral }
     * 
     * 
     */
    public List<SimpleLiteral> getModified() {
        if (modified == null) {
            modified = new ArrayList<SimpleLiteral>();
        }
        return this.modified;
    }

    public boolean isSetModified() {
        return ((this.modified!= null)&&(!this.modified.isEmpty()));
    }

    public void unsetModified() {
        this.modified = null;
    }

    /**
     * Gets the value of the abstract property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the abstract property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAbstract().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SimpleLiteral }
     * 
     * 
     */
    public List<SimpleLiteral> getAbstract() {
        if (_abstract == null) {
            _abstract = new ArrayList<SimpleLiteral>();
        }
        return this._abstract;
    }

    public boolean isSetAbstract() {
        return ((this._abstract!= null)&&(!this._abstract.isEmpty()));
    }

    public void unsetAbstract() {
        this._abstract = null;
    }

    /**
     * Gets the value of the spatial property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the spatial property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSpatial().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SimpleLiteral }
     * 
     * 
     */
    public List<SimpleLiteral> getSpatial() {
        if (spatial == null) {
            spatial = new ArrayList<SimpleLiteral>();
        }
        return this.spatial;
    }

    public boolean isSetSpatial() {
        return ((this.spatial!= null)&&(!this.spatial.isEmpty()));
    }

    public void unsetSpatial() {
        this.spatial = null;
    }

    /**
     * Gets the value of the boundingBox property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the boundingBox property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBoundingBox().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link WGS84BoundingBoxType }{@code >}
     * {@link JAXBElement }{@code <}{@link BoundingBoxType }{@code >}
     * 
     * 
     */
    public List<JAXBElement<? extends BoundingBoxType>> getBoundingBox() {
        if (boundingBox == null) {
            boundingBox = new ArrayList<JAXBElement<? extends BoundingBoxType>>();
        }
        return this.boundingBox;
    }

    public boolean isSetBoundingBox() {
        return ((this.boundingBox!= null)&&(!this.boundingBox.isEmpty()));
    }

    public void unsetBoundingBox() {
        this.boundingBox = null;
    }

}
