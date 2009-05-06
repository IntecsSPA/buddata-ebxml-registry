
package be.kzen.ergorr.model.csw;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *             The response for a transaction request that was successfully
 *             completed. If the transaction failed for any reason, a service 
 *             exception report indicating a TransactionFailure is returned
 *             instead.
 *          
 * 
 * <p>Java class for TransactionResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransactionResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TransactionSummary" type="{http://www.opengis.net/cat/csw/2.0.2}TransactionSummaryType"/>
 *         &lt;element name="InsertResult" type="{http://www.opengis.net/cat/csw/2.0.2}InsertResultType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="version" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransactionResponseType", propOrder = {
    "transactionSummary",
    "insertResult"
})
public class TransactionResponseType {

    @XmlElement(name = "TransactionSummary", required = true)
    protected TransactionSummaryType transactionSummary;
    @XmlElement(name = "InsertResult")
    protected List<InsertResultType> insertResult;
    @XmlAttribute
    protected String version;

    /**
     * Gets the value of the transactionSummary property.
     * 
     * @return
     *     possible object is
     *     {@link TransactionSummaryType }
     *     
     */
    public TransactionSummaryType getTransactionSummary() {
        return transactionSummary;
    }

    /**
     * Sets the value of the transactionSummary property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransactionSummaryType }
     *     
     */
    public void setTransactionSummary(TransactionSummaryType value) {
        this.transactionSummary = value;
    }

    public boolean isSetTransactionSummary() {
        return (this.transactionSummary!= null);
    }

    /**
     * Gets the value of the insertResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the insertResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInsertResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InsertResultType }
     * 
     * 
     */
    public List<InsertResultType> getInsertResult() {
        if (insertResult == null) {
            insertResult = new ArrayList<InsertResultType>();
        }
        return this.insertResult;
    }

    public boolean isSetInsertResult() {
        return ((this.insertResult!= null)&&(!this.insertResult.isEmpty()));
    }

    public void unsetInsertResult() {
        this.insertResult = null;
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

}
