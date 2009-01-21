/*
 * Project: Buddata ebXML RegRep
 * Class: GeometryTransformer.java
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
package be.kzen.ergorr.geometry;

import be.kzen.ergorr.commons.*;
import be.kzen.ergorr.exceptions.TransformException;
import be.kzen.ergorr.model.gml.AbstractGeometryType;
import be.kzen.ergorr.model.gml.AbstractRingPropertyType;
import be.kzen.ergorr.model.gml.DirectPositionListType;
import be.kzen.ergorr.model.gml.DirectPositionType;
import be.kzen.ergorr.model.gml.EnvelopeType;
import be.kzen.ergorr.model.gml.LineStringType;
import be.kzen.ergorr.model.gml.LinearRingType;
import be.kzen.ergorr.model.gml.PointPropertyType;
import be.kzen.ergorr.model.gml.PointType;
import be.kzen.ergorr.model.gml.PolygonType;
import be.kzen.ergorr.model.util.OFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;
import org.postgis.Geometry;
import org.postgis.LineString;
import org.postgis.LinearRing;
import org.postgis.Point;
import org.postgis.Polygon;
import org.postgis.binary.BinaryWriter;

/**
 *
 * @author Yaman Ustuntas
 */
public class GeometryTranslator {

    private static Logger logger = Logger.getLogger(GeometryTranslator.class.getName());

    public GeometryTranslator() {
    }

    public static byte[] wkbFromGmlGeometry(AbstractGeometryType gmlGeometry) throws TransformException {
        Geometry g = geometryFromGmlGeometry(gmlGeometry);
        BinaryWriter bw = new BinaryWriter();
        return bw.writeBinary(g);
    }

    public static byte[] wkbFromGmlEnvelope(EnvelopeType env) throws TransformException {
        Geometry g = polygonFromEnvelope(env);
        BinaryWriter bw = new BinaryWriter();
        return bw.writeBinary(g);
    }

    public static Geometry geometryFromGmlGeometry(AbstractGeometryType gmlGeometry) throws TransformException {

        if (gmlGeometry instanceof PolygonType) {
            return polygonFromGmlPolygonType((PolygonType) gmlGeometry);
        } else if (gmlGeometry instanceof PointType) {
            return pointFromGmlPointType((PointType) gmlGeometry);
        } else if (gmlGeometry instanceof LineStringType) {
            return lineStringFromGmlLineString((LineStringType) gmlGeometry);
        } else {
            throw new TransformException("Unsupporeted geometry type: " + gmlGeometry.getClass().toString());
        }
    }

    /**
     * Creates a <code>Polygon</code> object from a GML <code>PolygonType</code>.
     * 
     * @param gmlPolygon GML PolygonType to convert.
     * @return New <code>Polygon</code> with <code>PolygonType</code> values.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    public static Polygon polygonFromGmlPolygonType(PolygonType gmlPolygon) throws TransformException {
        LinearRingType exRingType = (LinearRingType) gmlPolygon.getExterior().getAbstractRing().getValue();
        List<LinearRing> rings = new ArrayList<LinearRing>();

        rings.add(linearRingFromGmlLinearRing(exRingType));

        List<AbstractRingPropertyType> absRingPropTypes = gmlPolygon.getInterior();

        for (int j = 0; j < absRingPropTypes.size(); j++) {
            AbstractRingPropertyType absRingPropType = absRingPropTypes.get(j);
            LinearRingType inRingType = (LinearRingType) absRingPropType.getAbstractRing().getValue();
            rings.add(linearRingFromGmlLinearRing(inRingType));
        }

        Polygon polygon = new Polygon(rings.toArray(new LinearRing[rings.size()]));
        polygon.setSrid(SrsTools.getSrsId(gmlPolygon.getSrsName()));

        if (logger.isLoggable(Level.FINE)) {
            logger.fine("Created polygon: " + polygon.getValue() + " : " + polygon.getSrid());
        }

        return polygon;
    }

    public static LinearRing linearRingFromGmlLinearRing(LinearRingType gmlRing) throws TransformException {
        List<Double> exPointsXY = getLinearRingPoints(gmlRing);
        int exCoordSize = (exPointsXY.size() > 1) ? (exPointsXY.size() / 2) : 1;

        if (exPointsXY.size() > 1) {
            Point[] points = new Point[exCoordSize];

            for (int i = 0; i < exCoordSize; i++) {
                Point p = new Point(exPointsXY.get(i * 2), exPointsXY.get(i * 2 + 1));
                points[i] = p;
            }

            LinearRing ring = new LinearRing(points);

            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Created linearring: " + ring.getValue() + " : " + ring.getSrid());
            }

            return ring;
        } else {
            throw new TransformException("Invalid exterior Polygon ring");
        }
    }

    /**
     * Get the list of points from a LinearRing.
     * 
     * @param gmlPolygon Polygon.
     * @return List of points.
     */
    public static List<Double> getLinearRingPoints(LinearRingType exRingType) {

        if (exRingType.isSetPosList()) {
            return exRingType.getPosList().getValue();
        } else if (exRingType.isSetPosOrPointProperty()) {
            List<Double> list = new ArrayList<Double>();

            for (Object posObj : exRingType.getPosOrPointProperty()) {
                if (posObj instanceof DirectPositionType) {
                    DirectPositionType pos = (DirectPositionType) posObj;
                    list.addAll(pos.getValue());
                } else if (posObj instanceof PointPropertyType) {
                    PointPropertyType point = (PointPropertyType) posObj;

                    if (point.isSetPoint() && point.getPoint().isSetPos()) {
                        list.addAll(point.getPoint().getPos().getValue());
                    }
                }
            }

            return list;
        } else {
            return new ArrayList<Double>();
        }
    }

    /**
     * Creates a <code>Point</code> object from a GML <code>PointType</code>.
     * 
     * @param gmlPoint GML PointType to convert.
     * @return New <code>Point</code> with <code>PointType</code> values.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    public static Point pointFromGmlPointType(PointType gmlPoint) throws TransformException {

        if (gmlPoint.isSetPos() && gmlPoint.getPos().isSetValue() && gmlPoint.getPos().getValue().size() > 1) {
            Point point = new Point(gmlPoint.getPos().getValue().get(0), gmlPoint.getPos().getValue().get(1));
            point.setSrid(SrsTools.getSrsId(gmlPoint.getSrsName()));

            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Created point: " + point.getValue() + " : " + point.getSrid());
            }

            return point;
        } else {
            throw new TransformException("Point does not have X and Y values");
        }
    }

    /**
     * Creates a <code>Polygon</code> object from a GML <code>EnvelopeType</code>.
     * 
     * @param env GML EnvelopeType to convert.
     * @return New <code>Polygon</code> with <code>EnvelopeType</code> values.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    public static Polygon polygonFromEnvelope(EnvelopeType env) throws TransformException {
        if (env.getLowerCorner().getValue().size() == 2 && env.getUpperCorner().getValue().size() == 2) {
            double x1 = env.getLowerCorner().getValue().get(0);
            double y1 = env.getLowerCorner().getValue().get(1);
            double x2 = env.getUpperCorner().getValue().get(0);
            double y2 = env.getUpperCorner().getValue().get(1);

            Point[] points = new Point[]{
                new Point(x1, y1),
                new Point(x2, y1),
                new Point(x2, y2),
                new Point(x1, y2),
                new Point(x1, y1)
            };

            LinearRing exLinearRing = new LinearRing(points);
            Polygon polygon = new Polygon(new LinearRing[]{exLinearRing});
            polygon.setSrid(SrsTools.getSrsId(env.getSrsName()));

            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Created polygon from envelope: " + polygon.getValue() + " : " + polygon.getSrid());
            }

            return polygon;
        } else {
            throw new TransformException("Invalid BBOX values");
        }
    }

    /**
     * Creates a <code>com.vividsolutions.jts.geom.LineString</code> from
     * a <code>be.kzen.ergorr.model.gml.LineStringType</code>.
     * 
     * @param gmlLineString LineString JAXB object.
     * @return Transformed LineString.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    public static LineString lineStringFromGmlLineString(LineStringType gmlLineString) throws TransformException {
        if (gmlLineString.isSetPosList() && !gmlLineString.getPosList().getValue().isEmpty()) {
            List<Double> exPointsXY = gmlLineString.getPosList().getValue();
            int pointsCount = (exPointsXY.size() > 1) ? (exPointsXY.size() / 2) : 1;

            Point[] points = new Point[pointsCount];

            for (int i = 0; i < pointsCount; i++) {
                points[i] = new Point(exPointsXY.get(i * 2), exPointsXY.get(i * 2 + 1));
            }

            LineString lineString = new LineString(points);
            lineString.setSrid(SrsTools.getSrsId(gmlLineString.getSrsName()));

            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Created linestring: " + lineString.getValue() + " : " + lineString.getSrid());
            }

            return lineString;
        } else {
            throw new TransformException("gml:LineString value not found");
        }
    }

    public static JAXBElement<LinearRingType> gmlLinearRingFromLinearRing(LinearRing ring) {
        LinearRingType gmlRing = new LinearRingType();
        DirectPositionListType gmlPosList = new DirectPositionListType();
        gmlRing.setPosList(gmlPosList);

        Point[] exPoints = ring.getPoints();

        for (int i = 0; i < exPoints.length; i++) {
            Point exPoint = exPoints[i];
            gmlPosList.getValue().add(exPoint.getX());
            gmlPosList.getValue().add(exPoint.getY());
        }

        return OFactory.gml.createLinearRing(gmlRing);
    }

    public static JAXBElement<? extends AbstractGeometryType> gmlGeometryFromGeometry(Geometry geometryValue) {
        if (geometryValue != null) {
            if (geometryValue instanceof Polygon) {
                Polygon polygon = (Polygon) geometryValue;
                PolygonType gmlPolygon = new PolygonType();
                gmlPolygon.setId("ID_" + IDGenerator.generateIntString());

                if (polygon.getSrid() > 0) {
                    gmlPolygon.setSrsName("EPSG:" + polygon.getSrid());
                }

                AbstractRingPropertyType gmlExAbsRing = new AbstractRingPropertyType();
                LinearRing exRing = polygon.getRing(0);
                gmlExAbsRing.setAbstractRing(gmlLinearRingFromLinearRing(exRing));
                gmlPolygon.setExterior(gmlExAbsRing);



                if (polygon.numRings() > 1) {
                    int interiorRingsCount = polygon.numRings();

                    for (int i = 1; i < interiorRingsCount; i++) {
                        AbstractRingPropertyType gmlInAbsRing = new AbstractRingPropertyType();
                        LinearRing inRing = polygon.getRing(i);
                        gmlInAbsRing.setAbstractRing(gmlLinearRingFromLinearRing(inRing));
                        gmlPolygon.getInterior().add(gmlInAbsRing);
                    }
                }



                return OFactory.gml.createPolygon(gmlPolygon);

            } else if (geometryValue instanceof Point) {
                Point p = (Point) geometryValue;
                PointType gmlPoint = new PointType();
                gmlPoint.setId("ID_" + IDGenerator.generateIntString());
                
                if (p.getSrid() > 0) {
                    gmlPoint.setSrsName("EPSG:" + p.getSrid());
                }

                DirectPositionType posJaxb = new DirectPositionType();
                posJaxb.getValue().add(p.getX());
                posJaxb.getValue().add(p.getY());
                gmlPoint.setPos(posJaxb);

                return OFactory.gml.createPoint(gmlPoint);
            }
        }
        return null;
    }
}
