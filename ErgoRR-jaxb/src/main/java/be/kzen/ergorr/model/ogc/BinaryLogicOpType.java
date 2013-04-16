
package be.kzen.ergorr.model.ogc;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BinaryLogicOpType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BinaryLogicOpType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/ogc}LogicOpsType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="2">
 *         &lt;element ref="{http://www.opengis.net/ogc}comparisonOps"/>
 *         &lt;element ref="{http://www.opengis.net/ogc}spatialOps"/>
 *         &lt;element ref="{http://www.opengis.net/ogc}logicOps"/>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BinaryLogicOpType", propOrder = {
    "comparisonOpsOrSpatialOpsOrLogicOps"
})
public class BinaryLogicOpType
    extends LogicOpsType
{

    @XmlElementRefs({
        @XmlElementRef(name = "comparisonOps", namespace = "http://www.opengis.net/ogc", type = JAXBElement.class),
        @XmlElementRef(name = "logicOps", namespace = "http://www.opengis.net/ogc", type = JAXBElement.class),
        @XmlElementRef(name = "spatialOps", namespace = "http://www.opengis.net/ogc", type = JAXBElement.class)
    })
    protected List<JAXBElement<?>> comparisonOpsOrSpatialOpsOrLogicOps;

    /**
     * Gets the value of the comparisonOpsOrSpatialOpsOrLogicOps property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the comparisonOpsOrSpatialOpsOrLogicOps property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComparisonOpsOrSpatialOpsOrLogicOps().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DWithin }
     * {@link PropertyIsLike }
     * {@link Intersects }
     * {@link Touches }
     * {@link PropertyIsLessThanOrEqualTo }
     * {@link PropertyIsGreaterThanOrEqualTo }
     * {@link Beyond }
     * {@link PropertyIsEqualTo }
     * {@link JAXBElement }{@code <}{@link ComparisonOpsType }{@code >}
     * {@link Disjoint }
     * {@link Not }
     * {@link JAXBElement }{@code <}{@link LogicOpsType }{@code >}
     * {@link PropertyIsNull }
     * {@link Or }
     * {@link PropertyIsLessThan }
     * {@link PropertyIsNotEqualTo }
     * {@link And }
     * {@link JAXBElement }{@code <}{@link SpatialOpsType }{@code >}
     * {@link PropertyIsBetween }
     * {@link Overlaps }
     * {@link Equals }
     * {@link Contains }
     * {@link BBOX }
     * {@link PropertyIsGreaterThan }
     * {@link Crosses }
     * {@link Within }
     * 
     * 
     */
    public List<JAXBElement<?>> getComparisonOpsOrSpatialOpsOrLogicOps() {
        if (comparisonOpsOrSpatialOpsOrLogicOps == null) {
            comparisonOpsOrSpatialOpsOrLogicOps = new ArrayList<JAXBElement<?>>();
        }
        return this.comparisonOpsOrSpatialOpsOrLogicOps;
    }

    public boolean isSetComparisonOpsOrSpatialOpsOrLogicOps() {
        return ((this.comparisonOpsOrSpatialOpsOrLogicOps!= null)&&(!this.comparisonOpsOrSpatialOpsOrLogicOps.isEmpty()));
    }

    public void unsetComparisonOpsOrSpatialOpsOrLogicOps() {
        this.comparisonOpsOrSpatialOpsOrLogicOps = null;
    }

}
