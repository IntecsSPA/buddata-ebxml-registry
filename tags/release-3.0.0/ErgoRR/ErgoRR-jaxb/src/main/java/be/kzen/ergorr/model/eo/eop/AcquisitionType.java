
package be.kzen.ergorr.model.eo.eop;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.datatype.XMLGregorianCalendar;
import be.kzen.ergorr.model.gml.AngleType;
import be.kzen.ergorr.model.gml.MeasureType;


/**
 * <p>Java class for AcquisitionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AcquisitionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="orbitNumber" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="lastOrbitNumber" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="orbitDirection" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="ASCENDING"/>
 *               &lt;enumeration value="DESCENDING"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="wrsLongitudeGrid" minOccurs="0">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>anySimpleType">
 *                 &lt;attribute name="codeSpace" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="wrsLatitudeGrid" minOccurs="0">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>anySimpleType">
 *                 &lt;attribute name="codeSpace" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="ascendingNodeDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ascendingNodeLongitude" type="{http://www.opengis.net/gml}MeasureType" minOccurs="0"/>
 *         &lt;element name="startTimeFromAscendingNode" type="{http://www.opengis.net/gml}MeasureType" minOccurs="0"/>
 *         &lt;element name="completionTimeFromAscendingNode" type="{http://www.opengis.net/gml}MeasureType" minOccurs="0"/>
 *         &lt;element name="orbitDuration" type="{http://www.opengis.net/gml}MeasureType" minOccurs="0"/>
 *         &lt;element name="acrossTrackIncidenceAngle" type="{http://www.opengis.net/gml}AngleType" minOccurs="0"/>
 *         &lt;element name="alongTrackIncidenceAngle" type="{http://www.opengis.net/gml}AngleType" minOccurs="0"/>
 *         &lt;element name="incidenceAngle" type="{http://www.opengis.net/gml}AngleType" minOccurs="0"/>
 *         &lt;element name="pitch" type="{http://www.opengis.net/gml}AngleType" minOccurs="0"/>
 *         &lt;element name="roll" type="{http://www.opengis.net/gml}AngleType" minOccurs="0"/>
 *         &lt;element name="yaw" type="{http://www.opengis.net/gml}AngleType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AcquisitionType", propOrder = {
    "orbitNumber",
    "lastOrbitNumber",
    "orbitDirection",
    "wrsLongitudeGrid",
    "wrsLatitudeGrid",
    "ascendingNodeDate",
    "ascendingNodeLongitude",
    "startTimeFromAscendingNode",
    "completionTimeFromAscendingNode",
    "orbitDuration",
    "acrossTrackIncidenceAngle",
    "alongTrackIncidenceAngle",
    "incidenceAngle",
    "pitch",
    "roll",
    "yaw"
})
@XmlSeeAlso({
    be.kzen.ergorr.model.eo.opt.AcquisitionType.class,
    be.kzen.ergorr.model.eo.sar.AcquisitionType.class
})
public class AcquisitionType {

    protected Integer orbitNumber;
    protected Integer lastOrbitNumber;
    protected String orbitDirection;
    protected AcquisitionType.WrsLongitudeGrid wrsLongitudeGrid;
    protected AcquisitionType.WrsLatitudeGrid wrsLatitudeGrid;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar ascendingNodeDate;
    protected MeasureType ascendingNodeLongitude;
    protected MeasureType startTimeFromAscendingNode;
    protected MeasureType completionTimeFromAscendingNode;
    protected MeasureType orbitDuration;
    protected AngleType acrossTrackIncidenceAngle;
    protected AngleType alongTrackIncidenceAngle;
    protected AngleType incidenceAngle;
    protected AngleType pitch;
    protected AngleType roll;
    protected AngleType yaw;

    /**
     * Gets the value of the orbitNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOrbitNumber() {
        return orbitNumber;
    }

    /**
     * Sets the value of the orbitNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOrbitNumber(Integer value) {
        this.orbitNumber = value;
    }

    public boolean isSetOrbitNumber() {
        return (this.orbitNumber!= null);
    }

    /**
     * Gets the value of the lastOrbitNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLastOrbitNumber() {
        return lastOrbitNumber;
    }

    /**
     * Sets the value of the lastOrbitNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLastOrbitNumber(Integer value) {
        this.lastOrbitNumber = value;
    }

    public boolean isSetLastOrbitNumber() {
        return (this.lastOrbitNumber!= null);
    }

    /**
     * Gets the value of the orbitDirection property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrbitDirection() {
        return orbitDirection;
    }

    /**
     * Sets the value of the orbitDirection property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrbitDirection(String value) {
        this.orbitDirection = value;
    }

    public boolean isSetOrbitDirection() {
        return (this.orbitDirection!= null);
    }

    /**
     * Gets the value of the wrsLongitudeGrid property.
     * 
     * @return
     *     possible object is
     *     {@link AcquisitionType.WrsLongitudeGrid }
     *     
     */
    public AcquisitionType.WrsLongitudeGrid getWrsLongitudeGrid() {
        return wrsLongitudeGrid;
    }

    /**
     * Sets the value of the wrsLongitudeGrid property.
     * 
     * @param value
     *     allowed object is
     *     {@link AcquisitionType.WrsLongitudeGrid }
     *     
     */
    public void setWrsLongitudeGrid(AcquisitionType.WrsLongitudeGrid value) {
        this.wrsLongitudeGrid = value;
    }

    public boolean isSetWrsLongitudeGrid() {
        return (this.wrsLongitudeGrid!= null);
    }

    /**
     * Gets the value of the wrsLatitudeGrid property.
     * 
     * @return
     *     possible object is
     *     {@link AcquisitionType.WrsLatitudeGrid }
     *     
     */
    public AcquisitionType.WrsLatitudeGrid getWrsLatitudeGrid() {
        return wrsLatitudeGrid;
    }

    /**
     * Sets the value of the wrsLatitudeGrid property.
     * 
     * @param value
     *     allowed object is
     *     {@link AcquisitionType.WrsLatitudeGrid }
     *     
     */
    public void setWrsLatitudeGrid(AcquisitionType.WrsLatitudeGrid value) {
        this.wrsLatitudeGrid = value;
    }

    public boolean isSetWrsLatitudeGrid() {
        return (this.wrsLatitudeGrid!= null);
    }

    /**
     * Gets the value of the ascendingNodeDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAscendingNodeDate() {
        return ascendingNodeDate;
    }

    /**
     * Sets the value of the ascendingNodeDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAscendingNodeDate(XMLGregorianCalendar value) {
        this.ascendingNodeDate = value;
    }

    public boolean isSetAscendingNodeDate() {
        return (this.ascendingNodeDate!= null);
    }

    /**
     * Gets the value of the ascendingNodeLongitude property.
     * 
     * @return
     *     possible object is
     *     {@link MeasureType }
     *     
     */
    public MeasureType getAscendingNodeLongitude() {
        return ascendingNodeLongitude;
    }

    /**
     * Sets the value of the ascendingNodeLongitude property.
     * 
     * @param value
     *     allowed object is
     *     {@link MeasureType }
     *     
     */
    public void setAscendingNodeLongitude(MeasureType value) {
        this.ascendingNodeLongitude = value;
    }

    public boolean isSetAscendingNodeLongitude() {
        return (this.ascendingNodeLongitude!= null);
    }

    /**
     * Gets the value of the startTimeFromAscendingNode property.
     * 
     * @return
     *     possible object is
     *     {@link MeasureType }
     *     
     */
    public MeasureType getStartTimeFromAscendingNode() {
        return startTimeFromAscendingNode;
    }

    /**
     * Sets the value of the startTimeFromAscendingNode property.
     * 
     * @param value
     *     allowed object is
     *     {@link MeasureType }
     *     
     */
    public void setStartTimeFromAscendingNode(MeasureType value) {
        this.startTimeFromAscendingNode = value;
    }

    public boolean isSetStartTimeFromAscendingNode() {
        return (this.startTimeFromAscendingNode!= null);
    }

    /**
     * Gets the value of the completionTimeFromAscendingNode property.
     * 
     * @return
     *     possible object is
     *     {@link MeasureType }
     *     
     */
    public MeasureType getCompletionTimeFromAscendingNode() {
        return completionTimeFromAscendingNode;
    }

    /**
     * Sets the value of the completionTimeFromAscendingNode property.
     * 
     * @param value
     *     allowed object is
     *     {@link MeasureType }
     *     
     */
    public void setCompletionTimeFromAscendingNode(MeasureType value) {
        this.completionTimeFromAscendingNode = value;
    }

    public boolean isSetCompletionTimeFromAscendingNode() {
        return (this.completionTimeFromAscendingNode!= null);
    }

    /**
     * Gets the value of the orbitDuration property.
     * 
     * @return
     *     possible object is
     *     {@link MeasureType }
     *     
     */
    public MeasureType getOrbitDuration() {
        return orbitDuration;
    }

    /**
     * Sets the value of the orbitDuration property.
     * 
     * @param value
     *     allowed object is
     *     {@link MeasureType }
     *     
     */
    public void setOrbitDuration(MeasureType value) {
        this.orbitDuration = value;
    }

    public boolean isSetOrbitDuration() {
        return (this.orbitDuration!= null);
    }

    /**
     * Gets the value of the acrossTrackIncidenceAngle property.
     * 
     * @return
     *     possible object is
     *     {@link AngleType }
     *     
     */
    public AngleType getAcrossTrackIncidenceAngle() {
        return acrossTrackIncidenceAngle;
    }

    /**
     * Sets the value of the acrossTrackIncidenceAngle property.
     * 
     * @param value
     *     allowed object is
     *     {@link AngleType }
     *     
     */
    public void setAcrossTrackIncidenceAngle(AngleType value) {
        this.acrossTrackIncidenceAngle = value;
    }

    public boolean isSetAcrossTrackIncidenceAngle() {
        return (this.acrossTrackIncidenceAngle!= null);
    }

    /**
     * Gets the value of the alongTrackIncidenceAngle property.
     * 
     * @return
     *     possible object is
     *     {@link AngleType }
     *     
     */
    public AngleType getAlongTrackIncidenceAngle() {
        return alongTrackIncidenceAngle;
    }

    /**
     * Sets the value of the alongTrackIncidenceAngle property.
     * 
     * @param value
     *     allowed object is
     *     {@link AngleType }
     *     
     */
    public void setAlongTrackIncidenceAngle(AngleType value) {
        this.alongTrackIncidenceAngle = value;
    }

    public boolean isSetAlongTrackIncidenceAngle() {
        return (this.alongTrackIncidenceAngle!= null);
    }

    /**
     * Gets the value of the incidenceAngle property.
     * 
     * @return
     *     possible object is
     *     {@link AngleType }
     *     
     */
    public AngleType getIncidenceAngle() {
        return incidenceAngle;
    }

    /**
     * Sets the value of the incidenceAngle property.
     * 
     * @param value
     *     allowed object is
     *     {@link AngleType }
     *     
     */
    public void setIncidenceAngle(AngleType value) {
        this.incidenceAngle = value;
    }

    public boolean isSetIncidenceAngle() {
        return (this.incidenceAngle!= null);
    }

    /**
     * Gets the value of the pitch property.
     * 
     * @return
     *     possible object is
     *     {@link AngleType }
     *     
     */
    public AngleType getPitch() {
        return pitch;
    }

    /**
     * Sets the value of the pitch property.
     * 
     * @param value
     *     allowed object is
     *     {@link AngleType }
     *     
     */
    public void setPitch(AngleType value) {
        this.pitch = value;
    }

    public boolean isSetPitch() {
        return (this.pitch!= null);
    }

    /**
     * Gets the value of the roll property.
     * 
     * @return
     *     possible object is
     *     {@link AngleType }
     *     
     */
    public AngleType getRoll() {
        return roll;
    }

    /**
     * Sets the value of the roll property.
     * 
     * @param value
     *     allowed object is
     *     {@link AngleType }
     *     
     */
    public void setRoll(AngleType value) {
        this.roll = value;
    }

    public boolean isSetRoll() {
        return (this.roll!= null);
    }

    /**
     * Gets the value of the yaw property.
     * 
     * @return
     *     possible object is
     *     {@link AngleType }
     *     
     */
    public AngleType getYaw() {
        return yaw;
    }

    /**
     * Sets the value of the yaw property.
     * 
     * @param value
     *     allowed object is
     *     {@link AngleType }
     *     
     */
    public void setYaw(AngleType value) {
        this.yaw = value;
    }

    public boolean isSetYaw() {
        return (this.yaw!= null);
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>anySimpleType">
     *       &lt;attribute name="codeSpace" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class WrsLatitudeGrid {

        @XmlValue
        @XmlSchemaType(name = "anySimpleType")
        protected Object value;
        @XmlAttribute
        @XmlSchemaType(name = "anyURI")
        protected String codeSpace;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setValue(Object value) {
            this.value = value;
        }

        public boolean isSetValue() {
            return (this.value!= null);
        }

        /**
         * Gets the value of the codeSpace property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCodeSpace() {
            return codeSpace;
        }

        /**
         * Sets the value of the codeSpace property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCodeSpace(String value) {
            this.codeSpace = value;
        }

        public boolean isSetCodeSpace() {
            return (this.codeSpace!= null);
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>anySimpleType">
     *       &lt;attribute name="codeSpace" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class WrsLongitudeGrid {

        @XmlValue
        @XmlSchemaType(name = "anySimpleType")
        protected Object value;
        @XmlAttribute
        @XmlSchemaType(name = "anyURI")
        protected String codeSpace;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setValue(Object value) {
            this.value = value;
        }

        public boolean isSetValue() {
            return (this.value!= null);
        }

        /**
         * Gets the value of the codeSpace property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCodeSpace() {
            return codeSpace;
        }

        /**
         * Sets the value of the codeSpace property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCodeSpace(String value) {
            this.codeSpace = value;
        }

        public boolean isSetCodeSpace() {
            return (this.codeSpace!= null);
        }

    }

}
