
package be.kzen.ergorr.model.ogc;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import be.kzen.ergorr.model.wrs.RecordIdType;


/**
 * <p>Java class for FilterType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FilterType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element ref="{http://www.opengis.net/ogc}spatialOps"/>
 *         &lt;element ref="{http://www.opengis.net/ogc}comparisonOps"/>
 *         &lt;element ref="{http://www.opengis.net/ogc}logicOps"/>
 *         &lt;element ref="{http://www.opengis.net/ogc}_Id" maxOccurs="unbounded"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FilterType", propOrder = {
    "spatialOps",
    "comparisonOps",
    "logicOps",
    "id"
})
public class FilterType {

    @XmlElementRef(name = "spatialOps", namespace = "http://www.opengis.net/ogc", type = JAXBElement.class)
    protected JAXBElement<? extends SpatialOpsType> spatialOps;
    @XmlElementRef(name = "comparisonOps", namespace = "http://www.opengis.net/ogc", type = JAXBElement.class)
    protected JAXBElement<? extends ComparisonOpsType> comparisonOps;
    @XmlElementRef(name = "logicOps", namespace = "http://www.opengis.net/ogc", type = JAXBElement.class)
    protected JAXBElement<? extends LogicOpsType> logicOps;
    @XmlElementRef(name = "_Id", namespace = "http://www.opengis.net/ogc", type = JAXBElement.class)
    protected List<JAXBElement<? extends AbstractIdType>> id;

    /**
     * Gets the value of the spatialOps property.
     * 
     * @return
     *     possible object is
     *     {@link Contains }
     *     {@link DWithin }
     *     {@link Intersects }
     *     {@link Disjoint }
     *     {@link BBOX }
     *     {@link Touches }
     *     {@link JAXBElement }{@code <}{@link SpatialOpsType }{@code >}
     *     {@link Overlaps }
     *     {@link Within }
     *     {@link Crosses }
     *     {@link Equals }
     *     {@link Beyond }
     *     
     */
    public JAXBElement<? extends SpatialOpsType> getSpatialOps() {
        return spatialOps;
    }

    /**
     * Sets the value of the spatialOps property.
     * 
     * @param value
     *     allowed object is
     *     {@link Contains }
     *     {@link DWithin }
     *     {@link Intersects }
     *     {@link Disjoint }
     *     {@link BBOX }
     *     {@link Touches }
     *     {@link JAXBElement }{@code <}{@link SpatialOpsType }{@code >}
     *     {@link Overlaps }
     *     {@link Within }
     *     {@link Crosses }
     *     {@link Equals }
     *     {@link Beyond }
     *     
     */
    public void setSpatialOps(JAXBElement<? extends SpatialOpsType> value) {
        this.spatialOps = ((JAXBElement<? extends SpatialOpsType> ) value);
    }

    public boolean isSetSpatialOps() {
        return (this.spatialOps!= null);
    }

    /**
     * Gets the value of the comparisonOps property.
     * 
     * @return
     *     possible object is
     *     {@link PropertyIsLike }
     *     {@link PropertyIsEqualTo }
     *     {@link JAXBElement }{@code <}{@link ComparisonOpsType }{@code >}
     *     {@link PropertyIsNull }
     *     {@link PropertyIsGreaterThan }
     *     {@link PropertyIsNotEqualTo }
     *     {@link PropertyIsBetween }
     *     {@link PropertyIsLessThan }
     *     {@link PropertyIsLessThanOrEqualTo }
     *     {@link PropertyIsGreaterThanOrEqualTo }
     *     
     */
    public JAXBElement<? extends ComparisonOpsType> getComparisonOps() {
        return comparisonOps;
    }

    /**
     * Sets the value of the comparisonOps property.
     * 
     * @param value
     *     allowed object is
     *     {@link PropertyIsLike }
     *     {@link PropertyIsEqualTo }
     *     {@link JAXBElement }{@code <}{@link ComparisonOpsType }{@code >}
     *     {@link PropertyIsNull }
     *     {@link PropertyIsGreaterThan }
     *     {@link PropertyIsNotEqualTo }
     *     {@link PropertyIsBetween }
     *     {@link PropertyIsLessThan }
     *     {@link PropertyIsLessThanOrEqualTo }
     *     {@link PropertyIsGreaterThanOrEqualTo }
     *     
     */
    public void setComparisonOps(JAXBElement<? extends ComparisonOpsType> value) {
        this.comparisonOps = ((JAXBElement<? extends ComparisonOpsType> ) value);
    }

    public boolean isSetComparisonOps() {
        return (this.comparisonOps!= null);
    }

    /**
     * Gets the value of the logicOps property.
     * 
     * @return
     *     possible object is
     *     {@link And }
     *     {@link Not }
     *     {@link Or }
     *     {@link JAXBElement }{@code <}{@link LogicOpsType }{@code >}
     *     
     */
    public JAXBElement<? extends LogicOpsType> getLogicOps() {
        return logicOps;
    }

    /**
     * Sets the value of the logicOps property.
     * 
     * @param value
     *     allowed object is
     *     {@link And }
     *     {@link Not }
     *     {@link Or }
     *     {@link JAXBElement }{@code <}{@link LogicOpsType }{@code >}
     *     
     */
    public void setLogicOps(JAXBElement<? extends LogicOpsType> value) {
        this.logicOps = ((JAXBElement<? extends LogicOpsType> ) value);
    }

    public boolean isSetLogicOps() {
        return (this.logicOps!= null);
    }

    /**
     * Gets the value of the id property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the id property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link RecordIdType }{@code >}
     * {@link JAXBElement }{@code <}{@link FeatureIdType }{@code >}
     * {@link JAXBElement }{@code <}{@link GmlObjectIdType }{@code >}
     * {@link JAXBElement }{@code <}{@link AbstractIdType }{@code >}
     * 
     * 
     */
    public List<JAXBElement<? extends AbstractIdType>> getId() {
        if (id == null) {
            id = new ArrayList<JAXBElement<? extends AbstractIdType>>();
        }
        return this.id;
    }

    public boolean isSetId() {
        return ((this.id!= null)&&(!this.id.isEmpty()));
    }

    public void unsetId() {
        this.id = null;
    }

}
