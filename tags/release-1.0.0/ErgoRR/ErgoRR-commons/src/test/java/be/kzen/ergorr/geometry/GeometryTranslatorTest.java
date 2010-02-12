/*
 * Project: Buddata ebXML RegRep
 * Class: GeometryTranslatorTest.java
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

import be.kzen.ergorr.model.gml.AbstractGeometryType;
import be.kzen.ergorr.model.gml.AbstractRingPropertyType;
import be.kzen.ergorr.model.gml.EnvelopeType;
import be.kzen.ergorr.model.gml.LineStringType;
import be.kzen.ergorr.model.gml.LinearRingType;
import be.kzen.ergorr.model.gml.PointType;
import be.kzen.ergorr.model.gml.PolygonType;
import java.util.List;
import javax.xml.bind.JAXBElement;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.postgis.Geometry;
import org.postgis.LineString;
import org.postgis.LinearRing;
import org.postgis.Point;
import org.postgis.Polygon;

/**
 *
 * @author yamanustuntas
 */
public class GeometryTranslatorTest {

    public GeometryTranslatorTest() {
    }

    @Test
    public void testWkbFromGmlGeometry() throws Exception {
    }

    @Test
    public void testWkbFromGmlEnvelope() throws Exception {
    }

    @Test
    public void testGeometryFromGmlGeometry() throws Exception {
    }

    @Test
    public void testPolygonFromGmlPolygonType() throws Exception {
    }

    @Test
    public void testLinearRingFromGmlLinearRing() throws Exception {
    }

    @Test
    public void testGetLinearRingPoints() {
    }

    @Test
    public void testPointFromGmlPointType() throws Exception {
    }

    @Test
    public void testPolygonFromEnvelope() throws Exception {
    }

    @Test
    public void testLineStringFromGmlLineString() throws Exception {
    }

    @Test
    public void testGmlLinearRingFromLinearRing() {
    }

    @Test
    public void testGmlGeometryFromGeometry() {
    }
}