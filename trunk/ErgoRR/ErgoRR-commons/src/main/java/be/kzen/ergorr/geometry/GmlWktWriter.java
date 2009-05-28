/*
 * Project: Buddata ebXML RegRep
 * Class: GmlWktWriter.java
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

import be.kzen.ergorr.exceptions.TransformException;
import be.kzen.ergorr.model.gml.AbstractRingPropertyType;
import be.kzen.ergorr.model.gml.DirectPositionListType;
import be.kzen.ergorr.model.gml.DirectPositionType;
import be.kzen.ergorr.model.gml.EnvelopeType;
import be.kzen.ergorr.model.gml.LineStringType;
import be.kzen.ergorr.model.gml.LinearRingType;
import be.kzen.ergorr.model.gml.PointPropertyType;
import be.kzen.ergorr.model.gml.PointType;
import be.kzen.ergorr.model.gml.PolygonType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;

/**
 *
 * @author yamanustuntas
 */
public class GmlWktWriter {

    private Object geometry;
    private StringBuilder wkt;

    public GmlWktWriter(Object geometry) {
        this.geometry = geometry;
        wkt = new StringBuilder(64);
    }

    public String write() throws TransformException {
        if (geometry instanceof PolygonType) {
            writePolygon((PolygonType) geometry);
        } else if (geometry instanceof PointType) {
            writePoint((PointType) geometry);
        } else if (geometry instanceof LineStringType) {
            writeLineString((LineStringType) geometry);
        } else if (geometry instanceof EnvelopeType) {
            writeEnvelope((EnvelopeType) geometry);
        } else {
            throw new TransformException("Unsupported geometry type: " + geometry.getClass().getName());
        }

        return wkt.toString();
    }

    private void writeLineString(LineStringType lineString) throws TransformException {
        DirectPositionListType posList = lineString.getPosList();

        if (posList != null) {
            wkt.append("LINESTRING(");
            appendDoubleListAsPoints(posList.getValue());
            wkt.append(")");

            if (lineString.isSetSrsName()) {
                wkt.append(lineString.getSrsName());
            }
        } else {
            throw new TransformException("Invalid LineString");
        }
    }

    private void writeEnvelope(EnvelopeType envelope) throws TransformException {
        DirectPositionType upPos = envelope.getUpperCorner();
        DirectPositionType lowPos = envelope.getLowerCorner();

        if (upPos != null && lowPos != null &&
                upPos.getValue().size() > 1 && (upPos.getValue().size() % 2 == 0) &&
                lowPos.getValue().size() > 1 && (lowPos.getValue().size() % 2 == 0)) {
            wkt.append("ENVELOPE(");
            wkt.append(lowPos.getValue().get(0)).append(" ").append(lowPos.getValue().get(1)).append(",");
            wkt.append(upPos.getValue().get(0)).append(" ").append(upPos.getValue().get(1));
            wkt.append(")");

            if (envelope.isSetSrsName()) {
                wkt.append(envelope.getSrsName());
            }
        } else {
            throw new TransformException("Invalid Envelope");
        }
    }

    private void writePoint(PointType point) throws TransformException {
        if (point.isSetPos() && point.getPos().isSetValue() &&
                point.getPos().getValue().size() > 1 && (point.getPos().getValue().size() % 2 == 0)) {
            wkt.append("POINT(");
            wkt.append(point.getPos().getValue().get(0)).append(" ").append(point.getPos().getValue().get(1));
            wkt.append(")");

            if (point.isSetSrsName()) {
                wkt.append(point.getSrsName());
            }
        } else {
            throw new TransformException("Point does not have X and Y values");
        }
    }

    private void writePolygon(PolygonType polygon) throws TransformException {
        wkt.append("POLYGON((");

        AbstractRingPropertyType extAbsRingProp = polygon.getExterior();
        LinearRingType extRing = (LinearRingType) extAbsRingProp.getRing().getValue();
        appendLinearRing(extRing);
        wkt.append(")");
        List<AbstractRingPropertyType> inAbsRingProps = polygon.getInterior();


        for (AbstractRingPropertyType inAbsRingProp : inAbsRingProps) {
            wkt.append(",(");
            LinearRingType inRing = (LinearRingType) inAbsRingProp.getRing().getValue();
            appendLinearRing(inRing);
            wkt.append(")");
        }

        wkt.append(")");

        if (polygon.isSetSrsName()) {
            wkt.append(polygon.getSrsName());
        }
    }

    private void appendLinearRing(LinearRingType gmlRing) throws TransformException {
        List<Double> pointsXY = getLinearRingPoints(gmlRing);
        appendDoubleListAsPoints(pointsXY);
    }

    private void appendDoubleListAsPoints(List<Double> xy) throws TransformException {

        if (xy.size() > 1 && (xy.size() % 2 == 0)) {
            int exCoordSize = (xy.size() > 1) ? (xy.size() / 2) : 1;

            for (int i = 0; i < exCoordSize; i++) {
                wkt.append(xy.get(i * 2)).append(" ").append(xy.get(i * 2 + 1)).append(",");
            }
            wkt.deleteCharAt(wkt.length() - 1);
        } else {
            throw new TransformException("Wrong number of XY coordinates");
        }
    }

    private List<Double> getLinearRingPoints(LinearRingType linearRing) {

        if (linearRing.isSetPosList()) {
            return linearRing.getPosList().getValue();
        } else if (linearRing.isSetPosOrPointPropertyOrPointRep()) {
            List<Double> list = new ArrayList<Double>();

            for (JAXBElement posObjEl : linearRing.getPosOrPointPropertyOrPointRep()) {
                Object posObj = posObjEl.getValue();

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
}
