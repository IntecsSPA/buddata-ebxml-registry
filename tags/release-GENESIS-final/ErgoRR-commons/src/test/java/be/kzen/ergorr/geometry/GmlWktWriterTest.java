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
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author yamanustuntas
 */
public class GmlWktWriterTest {

    public GmlWktWriterTest() {
    }

    @Test
    public void testPoint() throws Exception {
        PointType p = new PointType();
        DirectPositionType pos = new DirectPositionType();
        pos.getValue().add(1.0);
        pos.getValue().add(2.0);
        p.setPos(pos);

        GmlWktWriter writer = new GmlWktWriter(p);
        String expectedOutput = "POINT(1.0 2.0)";
        assertTrue(writer.write().equals(expectedOutput));
    }

    @Test
    public void testPolygon() throws Exception {
        PolygonType p = new PolygonType();
        AbstractRingPropertyType extRingProp = new AbstractRingPropertyType();
        p.setExterior(extRingProp);
        LinearRingType exRing = new LinearRingType();
        extRingProp.setRing(OFactory.gml.createLinearRing(exRing));
        DirectPositionListType exPosList = new DirectPositionListType();
        exRing.setPosList(exPosList);
        exPosList.getValue().add(1.0);
        exPosList.getValue().add(1.0);
        exPosList.getValue().add(2.0);
        exPosList.getValue().add(2.0);
        exPosList.getValue().add(4.0);
        exPosList.getValue().add(4.0);
        exPosList.getValue().add(1.0);
        exPosList.getValue().add(1.0);

        for (int i = 0; i < 2; i++) {
            AbstractRingPropertyType inRingProp = new AbstractRingPropertyType();
            inRingProp.setRing(OFactory.gml.createLinearRing(exRing));
            p.getInterior().add(inRingProp);
        }

        GmlWktWriter writer = new GmlWktWriter(p);

        String expectedOutput = "POLYGON((1.0 1.0,2.0 2.0,4.0 4.0,1.0 1.0),(1.0 1.0,2.0 2.0,4.0 4.0,1.0 1.0),(1.0 1.0,2.0 2.0,4.0 4.0,1.0 1.0))";
        assertTrue(writer.write().equals(expectedOutput));
    }

    @Test
    public void testEnvelope() throws Exception {
        EnvelopeType env = new EnvelopeType();
        DirectPositionType pos = new DirectPositionType();
        pos.getValue().add(1.0);
        pos.getValue().add(2.0);
        env.setLowerCorner(pos);
        env.setUpperCorner(pos);

        GmlWktWriter writer = new GmlWktWriter(env);
        String expectedOutput = "ENVELOPE(1.0 2.0,1.0 2.0)";
        assertTrue(writer.write().equals(expectedOutput));
    }

    @Test
    public void testLineString() throws Exception {
        LineStringType line = new LineStringType();
        DirectPositionListType posList = new DirectPositionListType();
        line.setPosList(posList);
        posList.getValue().add(1.0);
        posList.getValue().add(1.0);
        posList.getValue().add(2.0);
        posList.getValue().add(2.0);
        posList.getValue().add(4.0);
        posList.getValue().add(4.0);
        posList.getValue().add(1.0);
        posList.getValue().add(1.0);

        GmlWktWriter writer = new GmlWktWriter(line);
        String expectedOutput = "LINESTRING(1.0 1.0,2.0 2.0,4.0 4.0,1.0 1.0)";
        assertTrue(writer.write().equals(expectedOutput));
    }
}