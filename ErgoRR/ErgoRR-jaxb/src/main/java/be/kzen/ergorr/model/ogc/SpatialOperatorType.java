
package be.kzen.ergorr.model.ogc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SpatialOperatorType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SpatialOperatorType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GeometryOperands" type="{http://www.opengis.net/ogc}GeometryOperandsType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.opengis.net/ogc}SpatialOperatorNameType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SpatialOperatorType", propOrder = {
    "geometryOperands"
})
public class SpatialOperatorType {

    @XmlElement(name = "GeometryOperands")
    protected GeometryOperandsType geometryOperands;
    @XmlAttribute
    protected SpatialOperatorNameType name;

    /**
     * Gets the value of the geometryOperands property.
     * 
     * @return
     *     possible object is
     *     {@link GeometryOperandsType }
     *     
     */
    public GeometryOperandsType getGeometryOperands() {
        return geometryOperands;
    }

    /**
     * Sets the value of the geometryOperands property.
     * 
     * @param value
     *     allowed object is
     *     {@link GeometryOperandsType }
     *     
     */
    public void setGeometryOperands(GeometryOperandsType value) {
        this.geometryOperands = value;
    }

    public boolean isSetGeometryOperands() {
        return (this.geometryOperands!= null);
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link SpatialOperatorNameType }
     *     
     */
    public SpatialOperatorNameType getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link SpatialOperatorNameType }
     *     
     */
    public void setName(SpatialOperatorNameType value) {
        this.name = value;
    }

    public boolean isSetName() {
        return (this.name!= null);
    }

}
