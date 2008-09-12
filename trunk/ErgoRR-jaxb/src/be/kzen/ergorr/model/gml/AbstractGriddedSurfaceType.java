
package be.kzen.ergorr.model.gml;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AbstractGriddedSurfaceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AbstractGriddedSurfaceType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/gml/3.2}AbstractParametricCurveSurfaceType">
 *       &lt;sequence>
 *         &lt;group ref="{http://www.opengis.net/gml/3.2}PointGrid"/>
 *       &lt;/sequence>
 *       &lt;attribute name="rows" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="columns" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractGriddedSurfaceType", propOrder = {
    "rows"
})
@XmlSeeAlso({
    ConeType.class,
    CylinderType.class,
    SphereType.class
})
public abstract class AbstractGriddedSurfaceType
    extends AbstractParametricCurveSurfaceType
{

    @XmlElement(required = true)
    protected AbstractGriddedSurfaceType.Rows rows;
    @XmlAttribute(name = "rows")
    protected BigInteger gridRows;
    @XmlAttribute
    protected BigInteger columns;

    /**
     * Gets the value of the rows property.
     * 
     * @return
     *     possible object is
     *     {@link AbstractGriddedSurfaceType.Rows }
     *     
     */
    public AbstractGriddedSurfaceType.Rows getRows() {
        return rows;
    }

    /**
     * Sets the value of the rows property.
     * 
     * @param value
     *     allowed object is
     *     {@link AbstractGriddedSurfaceType.Rows }
     *     
     */
    public void setRows(AbstractGriddedSurfaceType.Rows value) {
        this.rows = value;
    }

    public boolean isSetRows() {
        return (this.rows!= null);
    }

    /**
     * Gets the value of the gridRows property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getGridRows() {
        return gridRows;
    }

    /**
     * Sets the value of the gridRows property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setGridRows(BigInteger value) {
        this.gridRows = value;
    }

    public boolean isSetGridRows() {
        return (this.gridRows!= null);
    }

    /**
     * Gets the value of the columns property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getColumns() {
        return columns;
    }

    /**
     * Sets the value of the columns property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setColumns(BigInteger value) {
        this.columns = value;
    }

    public boolean isSetColumns() {
        return (this.columns!= null);
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Row" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;group ref="{http://www.opengis.net/gml/3.2}geometricPositionListGroup"/>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "row"
    })
    public static class Rows {

        @XmlElement(name = "Row", required = true)
        protected List<AbstractGriddedSurfaceType.Rows.Row> row;

        /**
         * Gets the value of the row property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the row property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRow().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AbstractGriddedSurfaceType.Rows.Row }
         * 
         * 
         */
        public List<AbstractGriddedSurfaceType.Rows.Row> getRow() {
            if (row == null) {
                row = new ArrayList<AbstractGriddedSurfaceType.Rows.Row>();
            }
            return this.row;
        }

        public boolean isSetRow() {
            return ((this.row!= null)&&(!this.row.isEmpty()));
        }

        public void unsetRow() {
            this.row = null;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;group ref="{http://www.opengis.net/gml/3.2}geometricPositionListGroup"/>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "posList",
            "geometricPositionGroup"
        })
        public static class Row {

            protected DirectPositionListType posList;
            @XmlElements({
                @XmlElement(name = "pos", type = DirectPositionType.class),
                @XmlElement(name = "pointProperty", type = PointPropertyType.class)
            })
            protected List<Object> geometricPositionGroup;

            /**
             * Gets the value of the posList property.
             * 
             * @return
             *     possible object is
             *     {@link DirectPositionListType }
             *     
             */
            public DirectPositionListType getPosList() {
                return posList;
            }

            /**
             * Sets the value of the posList property.
             * 
             * @param value
             *     allowed object is
             *     {@link DirectPositionListType }
             *     
             */
            public void setPosList(DirectPositionListType value) {
                this.posList = value;
            }

            public boolean isSetPosList() {
                return (this.posList!= null);
            }

            /**
             * Gets the value of the geometricPositionGroup property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the geometricPositionGroup property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getGeometricPositionGroup().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link DirectPositionType }
             * {@link PointPropertyType }
             * 
             * 
             */
            public List<Object> getGeometricPositionGroup() {
                if (geometricPositionGroup == null) {
                    geometricPositionGroup = new ArrayList<Object>();
                }
                return this.geometricPositionGroup;
            }

            public boolean isSetGeometricPositionGroup() {
                return ((this.geometricPositionGroup!= null)&&(!this.geometricPositionGroup.isEmpty()));
            }

            public void unsetGeometricPositionGroup() {
                this.geometricPositionGroup = null;
            }

        }

    }

}
