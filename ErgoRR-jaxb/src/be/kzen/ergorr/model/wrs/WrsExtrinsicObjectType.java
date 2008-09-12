
package be.kzen.ergorr.model.wrs;

import be.kzen.ergorr.model.rim.ExtrinsicObjectType;
import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *       Extends rim:ExtrinsicObjectType to add the following:
 *       1. MTOM/XOP based attachment support.
 *       2. XLink based reference to a part in a multipart/related
 *          message structure.
 *       NOTE: This is planned for RegRep 4.0.
 *             
 * 
 * <p>Java class for ExtrinsicObjectType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ExtrinsicObjectType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0}ExtrinsicObjectType">
 *       &lt;choice minOccurs="0">
 *         &lt;element name="repositoryItemRef" type="{http://www.opengis.net/cat/wrs/1.0}SimpleLinkType"/>
 *         &lt;element name="repositoryItem" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExtrinsicObjectType", propOrder = {
    "repositoryItemRef",
    "repositoryItem"
})
@XmlRootElement
public class WrsExtrinsicObjectType
    extends ExtrinsicObjectType
{

    protected SimpleLinkType repositoryItemRef;
    @XmlMimeType("*/*")
    protected DataHandler repositoryItem;

    /**
     * Gets the value of the repositoryItemRef property.
     * 
     * @return
     *     possible object is
     *     {@link SimpleLinkType }
     *     
     */
    public SimpleLinkType getRepositoryItemRef() {
        return repositoryItemRef;
    }

    /**
     * Sets the value of the repositoryItemRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link SimpleLinkType }
     *     
     */
    public void setRepositoryItemRef(SimpleLinkType value) {
        this.repositoryItemRef = value;
    }

    public boolean isSetRepositoryItemRef() {
        return (this.repositoryItemRef!= null);
    }

    /**
     * Gets the value of the repositoryItem property.
     * 
     * @return
     *     possible object is
     *     {@link DataHandler }
     *     
     */
    public DataHandler getRepositoryItem() {
        return repositoryItem;
    }

    /**
     * Sets the value of the repositoryItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataHandler }
     *     
     */
    public void setRepositoryItem(DataHandler value) {
        this.repositoryItem = value;
    }

    public boolean isSetRepositoryItem() {
        return (this.repositoryItem!= null);
    }

}
