
package be.kzen.ergorr.model.csw;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *             The response message for a GetRecords request. Some or all of the 
 *             matching records may be included as children of the SearchResults 
 *             element. The RequestId is only included if the client specified it.
 *          
 * 
 * <p>Java class for GetRecordsResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetRecordsResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RequestId" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="SearchStatus" type="{http://www.opengis.net/cat/csw/2.0.2}RequestStatusType"/>
 *         &lt;element name="SearchResults" type="{http://www.opengis.net/cat/csw/2.0.2}SearchResultsType"/>
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
@XmlType(name = "GetRecordsResponseType", propOrder = {
    "requestId",
    "searchStatus",
    "searchResults"
})
@XmlRootElement
public class GetRecordsResponseType {

    @XmlElement(name = "RequestId")
    @XmlSchemaType(name = "anyURI")
    protected String requestId;
    @XmlElement(name = "SearchStatus", required = true)
    protected RequestStatusType searchStatus;
    @XmlElement(name = "SearchResults", required = true)
    protected SearchResultsType searchResults;
    @XmlAttribute
    protected String version;

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

    /**
     * Gets the value of the searchStatus property.
     * 
     * @return
     *     possible object is
     *     {@link RequestStatusType }
     *     
     */
    public RequestStatusType getSearchStatus() {
        return searchStatus;
    }

    /**
     * Sets the value of the searchStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestStatusType }
     *     
     */
    public void setSearchStatus(RequestStatusType value) {
        this.searchStatus = value;
    }

    public boolean isSetSearchStatus() {
        return (this.searchStatus!= null);
    }

    /**
     * Gets the value of the searchResults property.
     * 
     * @return
     *     possible object is
     *     {@link SearchResultsType }
     *     
     */
    public SearchResultsType getSearchResults() {
        return searchResults;
    }

    /**
     * Sets the value of the searchResults property.
     * 
     * @param value
     *     allowed object is
     *     {@link SearchResultsType }
     *     
     */
    public void setSearchResults(SearchResultsType value) {
        this.searchResults = value;
    }

    public boolean isSetSearchResults() {
        return (this.searchResults!= null);
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
