/*
 * Project: Buddata ebXML RegRep
 * Class: GmlWktParser.java
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

import be.kzen.ergorr.model.gml.AbstractRingPropertyType;
import be.kzen.ergorr.model.gml.DirectPositionListType;
import be.kzen.ergorr.model.gml.DirectPositionType;
import be.kzen.ergorr.model.gml.EnvelopeType;
import be.kzen.ergorr.model.gml.LineStringType;
import be.kzen.ergorr.model.gml.LinearRingType;
import be.kzen.ergorr.model.gml.PointType;
import be.kzen.ergorr.model.gml.PolygonType;
import be.kzen.ergorr.model.util.OFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.bind.JAXBElement;

/**
 *
 * @author yamanustuntas
 */
public class GmlWktParser {

    private char[] wkt;
    private String srs;
    private static final char[] POLYGON = "POLYGON".toCharArray();
    private static final char[] LINESTRING = "LINESTRING".toCharArray();
    private static final char[] ENVELOPE = "ENVELOPE".toCharArray();
    private static final char[] POINT = "POINT".toCharArray();
    private static final char SP = ' ';
    private static final char CM = ',';
    private static final char BR_OPEN = '(';
    private static final char BR_CLOSE = ')';

    public GmlWktParser(String wkt) {
        this.wkt = wkt.toCharArray();
        srs = null;
    }

    public JAXBElement parse() {
        JAXBElement geometry = null;
        int openBrIdx = openBrIdx();
        int closeBrIdx = closeBrIdx();
        int coordinatesLen = wkt.length - ((wkt.length - closeBrIdx) + openBrIdx) - 1;

        char[] type = new char[openBrIdx];
        System.arraycopy(wkt, 0, type, 0, openBrIdx);

        char[] coordinates = new char[coordinatesLen];
        System.arraycopy(wkt, openBrIdx + 1, coordinates, 0, coordinatesLen);

        int srsLen = wkt.length - closeBrIdx - 1;

        if (srsLen > 0) {
            srs = new String(wkt, closeBrIdx + 1, srsLen);
        }

        if (Arrays.equals(type, POLYGON)) {
            geometry = OFactory.gml.createPolygon(parsePolygon(coordinates));
        } else if (Arrays.equals(type, POINT)) {
            geometry = OFactory.gml.createPoint(parsePoint(coordinates));
        } else if (Arrays.equals(type, ENVELOPE)) {
            geometry = OFactory.gml.createEnvelope(parseEnvelope(coordinates));
        } else if (Arrays.equals(type, LINESTRING)) {
            geometry = OFactory.gml.createLineString(parseLineString(coordinates));
        }

        return geometry;
    }

    private int openBrIdx() {
        for (int i = 0; i < wkt.length; i++) {
            if (wkt[i] == BR_OPEN) {
                return i;
            }
        }
        return -1;
    }

    private int closeBrIdx() {
        for (int i = wkt.length - 1; i >= 0; i--) {
            char c = wkt[i];
            if (wkt[i] == BR_CLOSE) {
                return i;
            }
        }

        return -1;
    }

    private int findNext(char[] chars, char c, int start) {

        for (int i = start; i < chars.length; i++) {
            if (c == chars[i]) {
                return i;
            }
        }

        return -1;
    }

    private List<char[]> getCoordinates(char[] coordinateList) {
        List<char[]> cList = new ArrayList<char[]>();

        int lastIdx = 0;

        while ((lastIdx + 1) < coordinateList.length) {
            int start = findNext(coordinateList, BR_OPEN, lastIdx);
            lastIdx = start;
            int end = findNext(coordinateList, BR_CLOSE, lastIdx);
            int coordLen = end - start - 1;
            char[] coord = new char[coordLen];
            System.arraycopy(coordinateList, start + 1, coord, 0, coordLen);
            lastIdx = end;
            cList.add(coord);
        }

        return cList;
    }

    private PointType parsePoint(char[] coordinates) {
        PointType p = new PointType();
        DirectPositionType pos = new DirectPositionType();
        List<Double> d = getXYs(coordinates);

        pos.getValue().addAll(d);
        p.setPos(pos);
        p.setSrsName(srs);
        return p;
    }

    public EnvelopeType parseEnvelope(char[] coordinates) {
        EnvelopeType env = new EnvelopeType();

        List<Double> xys = getXYs(coordinates);

        DirectPositionType low = new DirectPositionType();
        low.getValue().add(xys.get(0));
        low.getValue().add(xys.get(1));
        env.setLowerCorner(low);

        DirectPositionType up = new DirectPositionType();
        up.getValue().add(xys.get(2));
        up.getValue().add(xys.get(3));
        env.setUpperCorner(up);

        env.setSrsName(srs);
        
        return env;
    }

    public LineStringType parseLineString(char[] coordinates) {
        LineStringType line = new LineStringType();
        line.setPosList(coordToDirectPosList(coordinates));
        line.setSrsName(srs);

        return line;
    }

    public PolygonType parsePolygon(char[] coordinateList) {
        PolygonType p = new PolygonType();
        AbstractRingPropertyType exRingProp = new AbstractRingPropertyType();
        LinearRingType exRing = new LinearRingType();
        
        List<char[]> coordinates = getCoordinates(coordinateList);
        exRing.setPosList(coordToDirectPosList(coordinates.get(0)));

        exRingProp.setRing(OFactory.gml.createLinearRing(exRing));
        p.setExterior(exRingProp);


        for (int i = 1; i < coordinates.size(); i++) {
            AbstractRingPropertyType inRingProp = new AbstractRingPropertyType();
            LinearRingType inRing = new LinearRingType();
            inRing.setPosList(coordToDirectPosList(coordinates.get(i)));
            inRingProp.setRing(OFactory.gml.createLinearRing(inRing));
            p.getInterior().add(inRingProp);
        }

        p.setSrsName(srs);

        return p;
    }

    private DirectPositionListType coordToDirectPosList(char[] coord) {
        DirectPositionListType pos = new DirectPositionListType();
        pos.getValue().addAll(getXYs(coord));
        return pos;
    }

    private List<Double> getXYs(char[] coordinates) {
        List<Double> xys = new ArrayList<Double>();

        int lastIdx = 0;

        for (int i = 0; i < coordinates.length; i++) {
            char c = coordinates[i];

            if (c == SP || c == CM) {
                xys.add(Double.valueOf(String.valueOf(coordinates, lastIdx, i - lastIdx)));
                lastIdx = i + 1;
            } else if ((i+1) >= coordinates.length) {
                xys.add(Double.valueOf(String.valueOf(coordinates, lastIdx, i - lastIdx + 1)));
            }
        }

        return xys;
    }
}
