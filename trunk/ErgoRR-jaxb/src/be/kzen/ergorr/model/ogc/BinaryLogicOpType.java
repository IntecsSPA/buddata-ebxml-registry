
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
     * {@link PropertyIsEqualTo }
     * {@link Or }
     * {@link Intersects }
     * {@link PropertyIsGreaterThanOrEqualTo }
     * {@link PropertyIsNotEqualTo }
     * {@link Contains }
     * {@link DWithin }
     * {@link PropertyIsNull }
     * {@link PropertyIsBetween }
     * {@link Crosses }
     * {@link And }
     * {@link JAXBElement }{@code <}{@link LogicOpsType }{@code >}
     * {@link BBOX }
     * {@link Overlaps }
     * {@link PropertyIsLessThan }
     * {@link Within }
     * {@link Touches }
     * {@link Not }
     * {@link PropertyIsLessThanOrEqualTo }
     * {@link Beyond }
     * {@link PropertyIsGreaterThan }
     * {@link JAXBElement }{@code <}{@link ComparisonOpsType }{@code >}
     * {@link Disjoint }
     * {@link JAXBElement }{@code <}{@link SpatialOpsType }{@code >}
     * {@link PropertyIsLike }
     * {@link Equals }
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
