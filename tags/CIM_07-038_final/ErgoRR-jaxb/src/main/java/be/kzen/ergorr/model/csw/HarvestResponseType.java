
package be.kzen.ergorr.model.csw;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HarvestResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HarvestResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element ref="{http://www.opengis.net/cat/csw/2.0.2}Acknowledgement"/>
 *         &lt;element ref="{http://www.opengis.net/cat/csw/2.0.2}TransactionResponse"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HarvestResponseType", propOrder = {
    "acknowledgement",
    "transactionResponse"
})
public class HarvestResponseType {

    @XmlElement(name = "Acknowledgement")
    protected AcknowledgementType acknowledgement;
    @XmlElement(name = "TransactionResponse")
    protected TransactionResponseType transactionResponse;

    /**
     * Gets the value of the acknowledgement property.
     * 
     * @return
     *     possible object is
     *     {@link AcknowledgementType }
     *     
     */
    public AcknowledgementType getAcknowledgement() {
        return acknowledgement;
    }

    /**
     * Sets the value of the acknowledgement property.
     * 
     * @param value
     *     allowed object is
     *     {@link AcknowledgementType }
     *     
     */
    public void setAcknowledgement(AcknowledgementType value) {
        this.acknowledgement = value;
    }

    public boolean isSetAcknowledgement() {
        return (this.acknowledgement!= null);
    }

    /**
     * Gets the value of the transactionResponse property.
     * 
     * @return
     *     possible object is
     *     {@link TransactionResponseType }
     *     
     */
    public TransactionResponseType getTransactionResponse() {
        return transactionResponse;
    }

    /**
     * Sets the value of the transactionResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransactionResponseType }
     *     
     */
    public void setTransactionResponse(TransactionResponseType value) {
        this.transactionResponse = value;
    }

    public boolean isSetTransactionResponse() {
        return (this.transactionResponse!= null);
    }

}
