
package be.kzen.ergorr.model.eo.eop;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import be.kzen.ergorr.model.gml.AbstractMetaDataType;
import be.kzen.ergorr.model.gml.CodeListType;
import be.kzen.ergorr.model.gml.MeasureType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;


/**
 * <p>Java class for EarthObservationMetaDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EarthObservationMetaDataType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/gml}AbstractMetaDataType">
 *       &lt;sequence>
 *         &lt;element ref="{http://earth.esa.int/eop}identifier"/>
 *         &lt;element ref="{http://earth.esa.int/eop}doi" minOccurs="0"/>
 *         &lt;element ref="{http://earth.esa.int/eop}parentIdentifier" minOccurs="0"/>
 *         &lt;element ref="{http://earth.esa.int/eop}acquisitionType"/>
 *         &lt;element ref="{http://earth.esa.int/eop}acquisitionSubType" minOccurs="0"/>
 *         &lt;element ref="{http://earth.esa.int/eop}productType"/>
 *         &lt;element ref="{http://earth.esa.int/eop}status"/>
 *         &lt;element name="downlinkedTo" type="{http://earth.esa.int/eop}DownlinkInformationArrayPropertyType" minOccurs="0"/>
 *         &lt;element name="archivedIn" type="{http://earth.esa.int/eop}ArchivingInformationArrayPropertyType" minOccurs="0"/>
 *         &lt;element ref="{http://earth.esa.int/eop}imageQualityDegradation" minOccurs="0"/>
 *         &lt;element ref="{http://earth.esa.int/eop}imageQualityDegradationQuotationMode" minOccurs="0"/>
 *         &lt;element ref="{http://earth.esa.int/eop}histograms" minOccurs="0"/>
 *         &lt;element ref="{http://earth.esa.int/eop}composedOf" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://earth.esa.int/eop}subsetOf" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://earth.esa.int/eop}linkedWith" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="processing" type="{http://earth.esa.int/eop}ProcessingInformationPropertyType" minOccurs="0"/>
 *         &lt;element name="vendorSpecific" type="{http://earth.esa.int/eop}SpecificInformationArrayPropertyType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EarthObservationMetaDataType")
public class EarthObservationMetaDataType
    extends AbstractMetaDataType
{
    @XmlElement(required = true)
    protected String identifier;
    protected String doi;
    protected String parentIdentifier;
    @XmlElement(required = true)
    protected String acquisitionType;
    protected CodeListType acquisitionSubType;
    @XmlElement(required = true)
    protected String productType;
    @XmlElement(required = true)
    protected String status;
    protected DownlinkInformationArrayPropertyType downlinkedTo;
    protected ArchivingInformationArrayPropertyType archivedIn;
    protected MeasureType imageQualityDegradation;
    protected String imageQualityDegradationQuotationMode;
    protected HistogramArrayPropertyType histograms;
    protected List<EarthObservationPropertyType> composedOf;
    protected List<EarthObservationPropertyType> subsetOf;
    protected List<EarthObservationPropertyType> linkedWith;
    protected ProcessingInformationPropertyType processing;
    protected SpecificInformationArrayPropertyType vendorSpecific;

    /**
     * Gets the value of the identifier property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Sets the value of the identifier property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setIdentifier(String value) {
        this.identifier = value;
    }

    public boolean isSetIdentifier() {
        return (this.identifier!= null);
    }

    /**
     * Gets the value of the doi property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDoi() {
        return doi;
    }

    /**
     * Sets the value of the doi property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDoi(String value) {
        this.doi = value;
    }

    public boolean isSetDoi() {
        return (this.doi!= null);
    }

    /**
     * Gets the value of the parentIdentifier property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getParentIdentifier() {
        return parentIdentifier;
    }

    /**
     * Sets the value of the parentIdentifier property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setParentIdentifier(String value) {
        this.parentIdentifier = value;
    }

    public boolean isSetParentIdentifier() {
        return (this.parentIdentifier!= null);
    }

    /**
     * Gets the value of the acquisitionType property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getAcquisitionType() {
        return acquisitionType;
    }

    /**
     * Sets the value of the acquisitionType property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setAcquisitionType(String value) {
        this.acquisitionType = value;
    }

    public boolean isSetAcquisitionType() {
        return (this.acquisitionType!= null);
    }

    /**
     * Gets the value of the acquisitionSubType property.
     *
     * @return
     *     possible object is
     *     {@link CodeListType }
     *
     */
    public CodeListType getAcquisitionSubType() {
        return acquisitionSubType;
    }

    /**
     * Sets the value of the acquisitionSubType property.
     *
     * @param value
     *     allowed object is
     *     {@link CodeListType }
     *
     */
    public void setAcquisitionSubType(CodeListType value) {
        this.acquisitionSubType = value;
    }

    public boolean isSetAcquisitionSubType() {
        return (this.acquisitionSubType!= null);
    }

    /**
     * Gets the value of the productType property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getProductType() {
        return productType;
    }

    /**
     * Sets the value of the productType property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setProductType(String value) {
        this.productType = value;
    }

    public boolean isSetProductType() {
        return (this.productType!= null);
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

    /**
     * Gets the value of the downlinkedTo property.
     *
     * @return
     *     possible object is
     *     {@link DownlinkInformationArrayPropertyType }
     *
     */
    public DownlinkInformationArrayPropertyType getDownlinkedTo() {
        return downlinkedTo;
    }

    /**
     * Sets the value of the downlinkedTo property.
     *
     * @param value
     *     allowed object is
     *     {@link DownlinkInformationArrayPropertyType }
     *
     */
    public void setDownlinkedTo(DownlinkInformationArrayPropertyType value) {
        this.downlinkedTo = value;
    }

    public boolean isSetDownlinkedTo() {
        return (this.downlinkedTo!= null);
    }

    /**
     * Gets the value of the archivedIn property.
     *
     * @return
     *     possible object is
     *     {@link ArchivingInformationArrayPropertyType }
     *
     */
    public ArchivingInformationArrayPropertyType getArchivedIn() {
        return archivedIn;
    }

    /**
     * Sets the value of the archivedIn property.
     *
     * @param value
     *     allowed object is
     *     {@link ArchivingInformationArrayPropertyType }
     *
     */
    public void setArchivedIn(ArchivingInformationArrayPropertyType value) {
        this.archivedIn = value;
    }

    public boolean isSetArchivedIn() {
        return (this.archivedIn!= null);
    }

    /**
     * Gets the value of the imageQualityDegradation property.
     *
     * @return
     *     possible object is
     *     {@link MeasureType }
     *
     */
    public MeasureType getImageQualityDegradation() {
        return imageQualityDegradation;
    }

    /**
     * Sets the value of the imageQualityDegradation property.
     *
     * @param value
     *     allowed object is
     *     {@link MeasureType }
     *
     */
    public void setImageQualityDegradation(MeasureType value) {
        this.imageQualityDegradation = value;
    }

    public boolean isSetImageQualityDegradation() {
        return (this.imageQualityDegradation!= null);
    }

    /**
     * Gets the value of the imageQualityDegradationQuotationMode property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getImageQualityDegradationQuotationMode() {
        return imageQualityDegradationQuotationMode;
    }

    /**
     * Sets the value of the imageQualityDegradationQuotationMode property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setImageQualityDegradationQuotationMode(String value) {
        this.imageQualityDegradationQuotationMode = value;
    }

    public boolean isSetImageQualityDegradationQuotationMode() {
        return (this.imageQualityDegradationQuotationMode!= null);
    }

    /**
     * Gets the value of the histograms property.
     *
     * @return
     *     possible object is
     *     {@link HistogramArrayPropertyType }
     *
     */
    public HistogramArrayPropertyType getHistograms() {
        return histograms;
    }

    /**
     * Sets the value of the histograms property.
     *
     * @param value
     *     allowed object is
     *     {@link HistogramArrayPropertyType }
     *
     */
    public void setHistograms(HistogramArrayPropertyType value) {
        this.histograms = value;
    }

    public boolean isSetHistograms() {
        return (this.histograms!= null);
    }

    /**
     * Gets the value of the composedOf property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the composedOf property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComposedOf().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EarthObservationPropertyType }
     *
     *
     */
    public List<EarthObservationPropertyType> getComposedOf() {
        if (composedOf == null) {
            composedOf = new ArrayList<EarthObservationPropertyType>();
        }
        return this.composedOf;
    }

    public boolean isSetComposedOf() {
        return ((this.composedOf!= null)&&(!this.composedOf.isEmpty()));
    }

    public void unsetComposedOf() {
        this.composedOf = null;
    }

    /**
     * Gets the value of the subsetOf property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subsetOf property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubsetOf().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EarthObservationPropertyType }
     *
     *
     */
    public List<EarthObservationPropertyType> getSubsetOf() {
        if (subsetOf == null) {
            subsetOf = new ArrayList<EarthObservationPropertyType>();
        }
        return this.subsetOf;
    }

    public boolean isSetSubsetOf() {
        return ((this.subsetOf!= null)&&(!this.subsetOf.isEmpty()));
    }

    public void unsetSubsetOf() {
        this.subsetOf = null;
    }

    /**
     * Gets the value of the linkedWith property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the linkedWith property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLinkedWith().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EarthObservationPropertyType }
     *
     *
     */
    public List<EarthObservationPropertyType> getLinkedWith() {
        if (linkedWith == null) {
            linkedWith = new ArrayList<EarthObservationPropertyType>();
        }
        return this.linkedWith;
    }

    public boolean isSetLinkedWith() {
        return ((this.linkedWith!= null)&&(!this.linkedWith.isEmpty()));
    }

    public void unsetLinkedWith() {
        this.linkedWith = null;
    }

    /**
     * Gets the value of the processing property.
     *
     * @return
     *     possible object is
     *     {@link ProcessingInformationPropertyType }
     *
     */
    public ProcessingInformationPropertyType getProcessing() {
        return processing;
    }

    /**
     * Sets the value of the processing property.
     *
     * @param value
     *     allowed object is
     *     {@link ProcessingInformationPropertyType }
     *
     */
    public void setProcessing(ProcessingInformationPropertyType value) {
        this.processing = value;
    }

    public boolean isSetProcessing() {
        return (this.processing!= null);
    }

    /**
     * Gets the value of the vendorSpecific property.
     *
     * @return
     *     possible object is
     *     {@link SpecificInformationArrayPropertyType }
     *
     */
    public SpecificInformationArrayPropertyType getVendorSpecific() {
        return vendorSpecific;
    }

    /**
     * Sets the value of the vendorSpecific property.
     *
     * @param value
     *     allowed object is
     *     {@link SpecificInformationArrayPropertyType }
     *
     */
    public void setVendorSpecific(SpecificInformationArrayPropertyType value) {
        this.vendorSpecific = value;
    }

    public boolean isSetVendorSpecific() {
        return (this.vendorSpecific!= null);
    }
}
