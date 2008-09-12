/*
 * Project: Buddata ebXML RegRep
 * Class: SlotDM.java
 * Copyright (C) 2008 Yaman Ustuntas
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package be.kzen.ergorr.persist.model;

import be.kzen.ergorr.commons.SlotTypes;
import be.kzen.ergorr.exceptions.MappingException;
import be.kzen.ergorr.model.gml.AbstractRingPropertyType;
import be.kzen.ergorr.model.gml.DirectPositionListType;
import be.kzen.ergorr.model.gml.DirectPositionType;
import be.kzen.ergorr.model.gml.LinearRingType;
import be.kzen.ergorr.model.gml.PointType;
import be.kzen.ergorr.model.gml.PolygonType;
import be.kzen.ergorr.model.util.OFactory;
import be.kzen.ergorr.model.wrs.AnyValueType;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.log4j.Logger;
import org.hibernate.annotations.Type;

/**
 *
 * @author Yaman Ustuntas
 */
@Entity(name = "Slot")
public class SlotDM implements Serializable {

    private static Logger log = Logger.getLogger(SlotDM.class);
    @Id
    @GeneratedValue
    protected long id;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "parent")
    protected IdentifiableDM parent;
    protected String slotName;
    protected String slotType;
    protected String stringValue;
    protected Boolean boolValue;
    protected Integer intValue;
    protected Double doubleValue;
    protected String specType;
    @Temporal(TemporalType.TIMESTAMP)
    protected Date dateTimeValue;
    @Basic
    @Column(name = "geometryValue", columnDefinition = "GEOMETRY")
    @Type(type = "org.hibernatespatial.GeometryUserType")
    protected Geometry geometryValue;
    @Basic
    @Column(name = "queryGeometryValue", columnDefinition = "GEOMETRY")
    @Type(type = "org.hibernatespatial.GeometryUserType")
    protected Geometry queryGeometryValue;

    public SlotDM() {
        specType = "rim";
    }

    public boolean isBoolValue() {
        return boolValue;
    }

    public void setBoolValue(boolean boolValue) {
        this.boolValue = boolValue;
    }

    public Geometry getGeometryValue() {
        return geometryValue;
    }

    public void setGeometryValue(Geometry geometryValue) {
        this.geometryValue = geometryValue;
    }

    public Geometry getQueryGeometryValue() {
        return queryGeometryValue;
    }

    public void setQueryGeometryValue(Geometry queryGeometryValue) {
        this.queryGeometryValue = queryGeometryValue;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public IdentifiableDM getParent() {
        return parent;
    }

    public void setParent(IdentifiableDM parent) {
        this.parent = parent;
    }

    public String getSlotName() {
        return slotName;
    }

    public void setSlotName(String slotName) {
        this.slotName = slotName;
    }

    public String getSlotType() {
        return slotType;
    }

    public void setSlotType(String slotType) {
        this.slotType = slotType;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getSpecType() {
        return specType;
    }

    public void setSpecType(String specType) {
        this.specType = specType;
    }

    public Date getDateTimeValue() {
        return dateTimeValue;
    }

    public void setDateTimeValue(Date dateTimeValue) {
        this.dateTimeValue = dateTimeValue;
    }

    public Boolean getBoolValue() {
        return boolValue;
    }

    public void setBoolValue(Boolean boolValue) {
        this.boolValue = boolValue;
    }

    public Double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(Double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public Integer getIntValue() {
        return intValue;
    }

    public void setIntValue(Integer intValue) {
        this.intValue = intValue;
    }

    public boolean geometryValueIsSet() {
        return geometryValue != null;
    }

    public void setAnyValue(Object obj) throws MappingException {
        if (obj instanceof Geometry) {
            setGeometryValue((Geometry) obj);
        } else if (obj instanceof String) {
            setStringValue((String) obj);
        } else if (obj instanceof Double) {
            setDoubleValue((Double) obj);
        } else if (obj instanceof Integer) {
            setIntValue((Integer) obj);
        } else if (obj instanceof XMLGregorianCalendar) {
            setDateTimeValue(((XMLGregorianCalendar) obj).toGregorianCalendar().getTime());
        } else {
            throw new MappingException("Not a valid slot value type: " + obj.getClass().toString());
        }
    }

    /**
     * This method covers the problem that Double, Int, DateTime can be
     * submitted as rim:Value although rim:Value is a string.
     * 
     * @param value String representation of slot value.
     * @param slotType Slot type.
     * @throws be.kzen.ergorr.exceptions.MappingException
     */
    public void setAnyValueByType(String value, String slotType) throws MappingException {
        slotType = SlotTypes.getInternalSlotType(slotType);
        value = value.trim();
        if (slotType.equals("string")) {
            setSpecType("rim");
            setStringValue(value);
        } else {
            setSpecType("wrs");
            if (slotType.equals("int")) {
                setIntValue(Integer.valueOf(value));
            } else if (slotType.equals("datetime")) {
                try {
                    setDateTimeValue(DatatypeFactory.newInstance().newXMLGregorianCalendar(value).toGregorianCalendar().getTime());
                } catch (DatatypeConfigurationException ex) {
                    throw new MappingException(ex.getMessage());
                }
            } else if (slotType.equals("double")) {
                setDoubleValue(Double.valueOf(value));
            } else {
                throw new MappingException("Not a valid any value slot type: " + slotType);
            }
        }
    }

    public JAXBElement createJaxb() {
        if (specType.equals("rim")) {
            return OFactory.rim.createValue(stringValue);
        } else {
            AnyValueType anyVal = new AnyValueType();
            anyVal.getContent().add(getWrsValue());

            return OFactory.wrs.createAnyValue(anyVal);
        }
    }

    public Object getWrsValue() {
        if (boolValue != null) {
            return boolValue.toString();
        } else if (intValue != null) {
            return intValue.toString();
        } else if (doubleValue != null) {
            return doubleValue.toString();
        } else if (dateTimeValue != null) {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(dateTimeValue);
            try {
//                return new JAXBElement(new QName("http://schemas.microsoft.com/2003/10/Serialization/", "dateTime"), 
//                        XMLGregorianCalendar.class, DatatypeFactory.newInstance().newXMLGregorianCalendar(gc));
                return DatatypeFactory.newInstance().newXMLGregorianCalendar(gc).toXMLFormat();
            } catch (Exception ex) {
                log.debug("Error converting date", ex);
                return dateTimeValue.toString();
            }
        } else if (geometryValue != null) {
            return getGeometryJaxb();
        } else {
            return "";
        }
    }

    private JAXBElement getGeometryJaxb() {
        if (geometryValue != null) {
            if (geometryValue instanceof Polygon) {
                Polygon p = (Polygon) geometryValue;
                PolygonType polygonJaxb = new PolygonType();

                if (p.getSRID() > 0) {
                    polygonJaxb.setSrsName("EPSG:" + p.getSRID());
                }

                DirectPositionListType exPosListJaxb = new DirectPositionListType();
                LinearRingType exRingJaxb = new LinearRingType();
                exRingJaxb.setPosList(exPosListJaxb);
                AbstractRingPropertyType exAbsRingJaxb = new AbstractRingPropertyType();
                exAbsRingJaxb.setAbstractRing(OFactory.gml.createLinearRing(exRingJaxb));
                polygonJaxb.setExterior(exAbsRingJaxb);

                LineString exRing = p.getExteriorRing();

                for (int i = 0; i < exRing.getNumPoints(); i++) {
                    Point exPoint = exRing.getPointN(i);
                    exPosListJaxb.getValue().add(exPoint.getX());
                    exPosListJaxb.getValue().add(exPoint.getY());
                }

                int interiorRingsCount = p.getNumInteriorRing();

                for (int i = 0; i < interiorRingsCount; i++) {
                    LineString inRing = p.getInteriorRingN(i);
                    DirectPositionListType inPosListJaxb = new DirectPositionListType();
                    LinearRingType inRingJaxb = new LinearRingType();
                    inRingJaxb.setPosList(inPosListJaxb);
                    AbstractRingPropertyType inAbsRingJaxb = new AbstractRingPropertyType();
                    inAbsRingJaxb.setAbstractRing(OFactory.gml.createAbstractRing(inRingJaxb));

                    for (int j = 0; j < inRing.getNumPoints(); j++) {
                        Point inPoint = inRing.getPointN(j);
                        inPosListJaxb.getValue().add(inPoint.getX());
                        inPosListJaxb.getValue().add(inPoint.getY());
                    }

                    polygonJaxb.getInterior().add(inAbsRingJaxb);
                }

                return OFactory.gml.createPolygon(polygonJaxb);

            } else if (geometryValue instanceof Point) {
                Point p = (Point) geometryValue;
                PointType pointJaxb = new PointType();

                if (p.getSRID() > 0) {
                    pointJaxb.setSrsName("EPSG:" + p.getSRID());
                }

                DirectPositionType posJaxb = new DirectPositionType();
                posJaxb.getValue().add(p.getX());
                posJaxb.getValue().add(p.getY());
                pointJaxb.setPos(posJaxb);

                return OFactory.gml.createPoint(pointJaxb);
            }
        }
        return null;
    }
    
    public void printSlot() {
        System.out.println("Slot: " + slotName);
        System.out.println("  type: " + slotType);
        
        String val = "";
        
        if (intValue != null) {
            val = intValue.toString();
        } else if (doubleValue != null) {
            val = doubleValue.toString();
        } else if (dateTimeValue != null) {
            val = dateTimeValue.toString();
        } else if (geometryValue != null) {
            val = geometryValue.toString();
        } else {
            val = stringValue;
        }
        
        System.out.println("  val: " + val);
    }
}
