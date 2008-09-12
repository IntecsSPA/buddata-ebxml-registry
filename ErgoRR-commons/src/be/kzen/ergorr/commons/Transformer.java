/*
 * Project: Buddata ebXML RegRep
 * Class: Transformer.java
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
package be.kzen.ergorr.commons;

import be.kzen.ergorr.exceptions.QueryException;
import be.kzen.ergorr.exceptions.TransformException;
import be.kzen.ergorr.model.gml.AbstractGeometryType;
import be.kzen.ergorr.model.gml.AbstractRingPropertyType;
import be.kzen.ergorr.model.gml.DirectPositionType;
import be.kzen.ergorr.model.gml.EnvelopeType;
import be.kzen.ergorr.model.gml.LineStringType;
import be.kzen.ergorr.model.gml.LinearRingType;
import be.kzen.ergorr.model.gml.PointPropertyType;
import be.kzen.ergorr.model.gml.PointType;
import be.kzen.ergorr.model.gml.PolygonType;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.impl.PackedCoordinateSequence;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Yaman Ustuntas
 */
public class Transformer {

    private static Logger log = Logger.getLogger(Transformer.class);

    /**
     * Creates a <code>Geometry</code> object from a GML <code>AbstractGeometryType</code>.
     * 
     * @param absGeometry GML AbstractGeometryType to convert.
     * @return New <code>Geometry</code> with <code>AbstractGeometryType</code> values.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    public static Geometry geometryFromAbstractGeometry(AbstractGeometryType geometryObj) throws TransformException {
        if (geometryObj instanceof PolygonType) {
            return polygonFromGmlPolygonType((PolygonType) geometryObj);
        } else if (geometryObj instanceof PointType) {
            return pointFromGmlPointType((PointType) geometryObj);
        } else if (geometryObj instanceof LineStringType) {
            return lineStringFromGmlLineString((LineStringType) geometryObj);
        } else {
            throw new TransformException("Unsupporeted geometry type: " + geometryObj.getClass().getSimpleName());
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
        GeometryFactory geoFactory = new GeometryFactory();
        List<Double> exPointsXY = getLinearRingPoints(exRingType);

        int exCoordSize = (exPointsXY.size() > 1) ? (exPointsXY.size() / 2) : 1;
        PackedCoordinateSequence exCoordSeq = new PackedCoordinateSequence.Double(exCoordSize, 2);

        if (exPointsXY.size() > 1) {
            for (int i = 0; i < exCoordSize; i++) {
                if (log.isDebugEnabled()) {
                    log.debug("External ring point: " + exPointsXY.get(i * 2) + " " + exPointsXY.get(i * 2 + 1));
                }
                exCoordSeq.setX(i, exPointsXY.get(i * 2));
                exCoordSeq.setY(i, exPointsXY.get(i * 2 + 1));
            }
        }
        LinearRing exLR = new LinearRing(exCoordSeq, geoFactory);

        List<AbstractRingPropertyType> absRingPropTypes = gmlPolygon.getInterior();
        LinearRing[] inRings = new LinearRing[absRingPropTypes.size()];

        for (int j = 0; j < absRingPropTypes.size(); j++) {
            AbstractRingPropertyType absRingPropType = absRingPropTypes.get(j);
            LinearRingType inRingType = (LinearRingType) absRingPropType.getAbstractRing().getValue();
            List<Double> inPointsXY = getLinearRingPoints(inRingType);

            int inCoordSize = (inPointsXY.size() > 1) ? (inPointsXY.size() / 2) : 1;
            PackedCoordinateSequence inCoordSeq = new PackedCoordinateSequence.Double(inCoordSize, 2);

            if (exPointsXY.size() > 0) {
                for (int i = 0; i < inCoordSize; i++) {
                    inCoordSeq.setX(i, inPointsXY.get(i * 2));
                    inCoordSeq.setY(i, inPointsXY.get(i * 2 + 1));

                }
            }

            inRings[j] = new LinearRing(inCoordSeq, geoFactory);
        }

        Polygon polygon = new Polygon(exLR, inRings, geoFactory);
        polygon.setSRID(SrsTools.getSrsId(gmlPolygon.getSrsName()));
        return polygon;
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
        GeometryFactory geoFactory = new GeometryFactory();
        PackedCoordinateSequence coordSeq = new PackedCoordinateSequence.Double(1, 2);

        if (gmlPoint.getPos().getValue().size() > 1) {
            coordSeq.setX(0, gmlPoint.getPos().getValue().get(0));
            coordSeq.setY(0, gmlPoint.getPos().getValue().get(1));

            Point point = new Point(coordSeq, geoFactory);
            point.setSRID(SrsTools.getSrsId(gmlPoint.getSrsName()));
            return point;
        } else {
            throw new TransformException("Point does not have X and Y values");
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
        GeometryFactory geoFactory = new GeometryFactory();

        if (gmlLineString.isSetPosList() && !gmlLineString.getPosList().getValue().isEmpty()) {
            List<Double> exPointsXY = gmlLineString.getPosList().getValue();
            int exCoordSize = (exPointsXY.size() > 1) ? (exPointsXY.size() / 2) : 1;

            PackedCoordinateSequence exCoordSeq = new PackedCoordinateSequence.Double(exCoordSize, 2);

            for (int i = 0; i < exCoordSize; i++) {
                if (log.isDebugEnabled()) {
                    log.debug("External ring point: " + exPointsXY.get(i * 2) + " " + exPointsXY.get(i * 2 + 1));
                }
                exCoordSeq.setX(i, exPointsXY.get(i * 2));
                exCoordSeq.setY(i, exPointsXY.get(i * 2 + 1));
            }

            LineString lineString = new LineString(exCoordSeq, geoFactory);
            lineString.setSRID(SrsTools.getSrsId(gmlLineString.getSrsName()));
            return lineString;
        } else {
            throw new TransformException("gml:LineString value not found");
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

            GeometryFactory geoFactory = new GeometryFactory();
            PackedCoordinateSequence exCoordSeq = new PackedCoordinateSequence.Double(5, 2);

            exCoordSeq.setX(0, x1);
            exCoordSeq.setY(0, y1);

            exCoordSeq.setX(1, x2);
            exCoordSeq.setY(1, y1);

            exCoordSeq.setX(2, x2);
            exCoordSeq.setY(2, y2);

            exCoordSeq.setX(3, x1);
            exCoordSeq.setY(3, y2);

            exCoordSeq.setX(4, x1);
            exCoordSeq.setY(4, y1);


            LinearRing exLR = new LinearRing(exCoordSeq, geoFactory);
            Polygon polygon = new Polygon(exLR, new LinearRing[0], geoFactory);
            polygon.setSRID(SrsTools.getSrsId(env.getSrsName()));
            return polygon;
        } else {
            throw new TransformException("Invalid BBOX values");
        }
    }
}
