
package be.kzen.ergorr.model.ogc;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UnaryLogicOpType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UnaryLogicOpType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/ogc}LogicOpsType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element ref="{http://www.opengis.net/ogc}comparisonOps"/>
 *           &lt;element ref="{http://www.opengis.net/ogc}spatialOps"/>
 *           &lt;element ref="{http://www.opengis.net/ogc}logicOps"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UnaryLogicOpType", propOrder = {
    "comparisonOps",
    "spatialOps",
    "logicOps"
})
public class UnaryLogicOpType
    extends LogicOpsType
{

    @XmlElementRef(name = "comparisonOps", namespace = "http://www.opengis.net/ogc", type = JAXBElement.class)
    protected JAXBElement<? extends ComparisonOpsType> comparisonOps;
    @XmlElementRef(name = "spatialOps", namespace = "http://www.opengis.net/ogc", type = JAXBElement.class)
    protected JAXBElement<? extends SpatialOpsType> spatialOps;
    @XmlElementRef(name = "logicOps", namespace = "http://www.opengis.net/ogc", type = JAXBElement.class)
    protected JAXBElement<? extends LogicOpsType> logicOps;

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

}
