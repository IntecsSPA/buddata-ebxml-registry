package be.kzen.ergorr.geometry;

import be.kzen.ergorr.model.gml.DirectPositionListType;
import be.kzen.ergorr.model.gml.DirectPositionType;
import be.kzen.ergorr.model.gml.EnvelopeType;
import be.kzen.ergorr.model.gml.LineStringType;
import be.kzen.ergorr.model.gml.LinearRingType;
import be.kzen.ergorr.model.gml.PointType;
import be.kzen.ergorr.model.gml.PolygonType;
import javax.xml.bind.JAXBElement;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author yamanustuntas
 */
public class GmlWktParserTest {

    public GmlWktParserTest() {
    }

    @Test
    public void testPoint() throws Exception {
        GmlWktParser parser = new GmlWktParser("POINT(1.0 2.0)");
        PointType p = ((JAXBElement<PointType>) parser.parse()).getValue();

        assertTrue(p.getPos().getValue().get(0) == 1.0);
        assertTrue(p.getPos().getValue().get(1) == 2.0);
    }

    @Test
    public void testPointScientific() throws Exception {
        GmlWktParser parser = new GmlWktParser("POINT(6.1E5 6.1E5)");
        PointType p = ((JAXBElement<PointType>) parser.parse()).getValue();

        assertTrue(p.getPos().getValue().get(0) == 6.1E5);
        assertTrue(p.getPos().getValue().get(1) == 6.1E5);
    }

    @Test
    public void testPointWithSrs() throws Exception {
        GmlWktParser parser = new GmlWktParser("POINT(1.0 2.0)TEST:123");
        PointType p = ((JAXBElement<PointType>) parser.parse()).getValue();

        assertTrue(p.getPos().getValue().get(0) == 1.0);
        assertTrue(p.getPos().getValue().get(1) == 2.0);
        assertTrue(p.getSrsName().equals("TEST:123"));
    }

    @Test
    public void testPolygon() throws Exception {
        GmlWktParser parser = new GmlWktParser("POLYGON((1.0 1.0,2.0 2.0,4.0 4.0,1.0 1.0),(1.0 1.0,2.0 2.0,4.0 4.0,1.0 1.0),(1.0 1.0,2.0 2.0,4.0 4.0,1.0 1.0))");
        PolygonType pol = ((JAXBElement<PolygonType>) parser.parse()).getValue();

        LinearRingType exRing = (LinearRingType) pol.getExterior().getRing().getValue();
        DirectPositionListType exPos = exRing.getPosList();

        assertTrue(exPos.getValue().get(0) == 1.0);
        assertTrue(exPos.getValue().get(1) == 1.0);
        assertTrue(exPos.getValue().get(2) == 2.0);
        assertTrue(exPos.getValue().get(3) == 2.0);
        assertTrue(exPos.getValue().get(4) == 4.0);
        assertTrue(exPos.getValue().get(5) == 4.0);
        assertTrue(exPos.getValue().get(6) == 1.0);
        assertTrue(exPos.getValue().get(7) == 1.0);
    }

    @Test
    public void testEnvelope() throws Exception {
        GmlWktParser parser = new GmlWktParser("ENVELOPE(1.0 2.0,4.0 5.0)");
        EnvelopeType env = ((JAXBElement<EnvelopeType>) parser.parse()).getValue();

        DirectPositionType up = env.getUpperCorner();
        DirectPositionType low = env.getLowerCorner();

        assertTrue(low.getValue().get(0) == 1.0);
        assertTrue(low.getValue().get(1) == 2.0);
        assertTrue(up.getValue().get(0) == 4.0);
        assertTrue(up.getValue().get(1) == 5.0);
    }

    @Test
    public void testLineString() throws Exception {
        GmlWktParser parser = new GmlWktParser("LINESTRING(1.0 1.0,2.0 2.0,4.0 4.0)");
        LineStringType line = ((JAXBElement<LineStringType>) parser.parse()).getValue();

        DirectPositionListType pos = line.getPosList();

        assertTrue(pos.getValue().get(0) == 1.0);
        assertTrue(pos.getValue().get(1) == 1.0);
        assertTrue(pos.getValue().get(2) == 2.0);
        assertTrue(pos.getValue().get(3) == 2.0);
        assertTrue(pos.getValue().get(4) == 4.0);
        assertTrue(pos.getValue().get(5) == 4.0);
    }
}
