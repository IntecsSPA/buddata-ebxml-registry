
package be.kzen.ergorr.model.csw;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *             Users may insert, update, or delete catalogue entries. If the 
 *             verboseResponse attribute has the value "true", then one or more 
 *             csw:InsertResult elements must be included in the response.
 *          
 * 
 * <p>Java class for TransactionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransactionType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/cat/csw/2.0.2}RequestBaseType">
 *       &lt;sequence>
 *         &lt;choice maxOccurs="unbounded">
 *           &lt;element name="Insert" type="{http://www.opengis.net/cat/csw/2.0.2}InsertType"/>
 *           &lt;element name="Update" type="{http://www.opengis.net/cat/csw/2.0.2}UpdateType"/>
 *           &lt;element name="Delete" type="{http://www.opengis.net/cat/csw/2.0.2}DeleteType"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="verboseResponse" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="requestId" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransactionType", propOrder = {
    "insertOrUpdateOrDelete"
})
public class TransactionType
    extends RequestBaseType
{

    @XmlElements({
        @XmlElement(name = "Update", type = UpdateType.class),
        @XmlElement(name = "Insert", type = InsertType.class),
        @XmlElement(name = "Delete", type = DeleteType.class)
    })
    protected List<Object> insertOrUpdateOrDelete;
    @XmlAttribute
    protected Boolean verboseResponse;
    @XmlAttribute
    @XmlSchemaType(name = "anyURI")
    protected String requestId;

    /**
     * Gets the value of the insertOrUpdateOrDelete property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the insertOrUpdateOrDelete property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInsertOrUpdateOrDelete().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UpdateType }
     * {@link InsertType }
     * {@link DeleteType }
     * 
     * 
     */
    public List<Object> getInsertOrUpdateOrDelete() {
        if (insertOrUpdateOrDelete == null) {
            insertOrUpdateOrDelete = new ArrayList<Object>();
        }
        return this.insertOrUpdateOrDelete;
    }

    public boolean isSetInsertOrUpdateOrDelete() {
        return ((this.insertOrUpdateOrDelete!= null)&&(!this.insertOrUpdateOrDelete.isEmpty()));
    }

    public void unsetInsertOrUpdateOrDelete() {
        this.insertOrUpdateOrDelete = null;
    }

    /**
     * Gets the value of the verboseResponse property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isVerboseResponse() {
        if (verboseResponse == null) {
            return false;
        } else {
            return verboseResponse;
        }
    }

    /**
     * Sets the value of the verboseResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setVerboseResponse(boolean value) {
        this.verboseResponse = value;
    }

    public boolean isSetVerboseResponse() {
        return (this.verboseResponse!= null);
    }

    public void unsetVerboseResponse() {
        this.verboseResponse = null;
    }

    /**
     * Gets the value of the requestId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Sets the value of the requestId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestId(String value) {
        this.requestId = value;
    }

    public boolean isSetRequestId() {
        return (this.requestId!= null);
    }

}
