
package be.kzen.ergorr.model.csw;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *             Returns a "brief" view of any newly created catalogue records.
 *             The handle attribute may reference a particular statement in
 *             the corresponding transaction request.
 *          
 * 
 * <p>Java class for InsertResultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InsertResultType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.opengis.net/cat/csw/2.0.2}BriefRecord" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="handleRef" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InsertResultType", propOrder = {
    "briefRecord"
})
public class InsertResultType {

    @XmlElement(name = "BriefRecord", required = true)
    protected List<BriefRecordType> briefRecord;
    @XmlAttribute
    @XmlSchemaType(name = "anyURI")
    protected String handleRef;

    /**
     * Gets the value of the briefRecord property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the briefRecord property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBriefRecord().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BriefRecordType }
     * 
     * 
     */
    public List<BriefRecordType> getBriefRecord() {
        if (briefRecord == null) {
            briefRecord = new ArrayList<BriefRecordType>();
        }
        return this.briefRecord;
    }

    public boolean isSetBriefRecord() {
        return ((this.briefRecord!= null)&&(!this.briefRecord.isEmpty()));
    }

    public void unsetBriefRecord() {
        this.briefRecord = null;
    }

    /**
     * Gets the value of the handleRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHandleRef() {
        return handleRef;
    }

    /**
     * Sets the value of the handleRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHandleRef(String value) {
        this.handleRef = value;
    }

    public boolean isSetHandleRef() {
        return (this.handleRef!= null);
    }

}
