
package be.kzen.ergorr.commons;

import be.kzen.ergorr.model.gml.AbstractGeometryType;
import be.kzen.ergorr.model.gml.EnvelopeType;
import be.kzen.ergorr.model.gml.LineStringType;
import be.kzen.ergorr.model.gml.LinearRingType;
import be.kzen.ergorr.model.gml.PointType;
import be.kzen.ergorr.model.gml.PolygonType;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Yaman Ustuntas
 */
public class TransformerTest {

    public TransformerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
//
//    @Test
//    public void testGeometryFromAbstractGeometry() throws Exception {
//        System.out.println("geometryFromAbstractGeometry");
//        AbstractGeometryType geometryObj = null;
//        Geometry expResult = null;
//        Geometry result = Transformer.geometryFromAbstractGeometry(geometryObj);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testWktFromGmlPolygonType() throws Exception {
//        System.out.println("wktFromGmlPolygonType");
//        PolygonType gmlPolygon = null;
//        String expResult = "";
//        String result = Transformer.wktFromGmlPolygonType(gmlPolygon);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testPolygonFromGmlPolygonType() throws Exception {
//        System.out.println("polygonFromGmlPolygonType");
//        PolygonType gmlPolygon = null;
//        Polygon expResult = null;
//        Polygon result = Transformer.polygonFromGmlPolygonType(gmlPolygon);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testGetLinearRingPoints() {
//        System.out.println("getLinearRingPoints");
//        LinearRingType exRingType = null;
//        List<Double> expResult = null;
//        List<Double> result = Transformer.getLinearRingPoints(exRingType);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testPointFromGmlPointType() throws Exception {
//        System.out.println("pointFromGmlPointType");
//        PointType gmlPoint = null;
//        Point expResult = null;
//        Point result = Transformer.pointFromGmlPointType(gmlPoint);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testLineStringFromGmlLineString() throws Exception {
//        System.out.println("lineStringFromGmlLineString");
//        LineStringType gmlLineString = null;
//        LineString expResult = null;
//        LineString result = Transformer.lineStringFromGmlLineString(gmlLineString);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testPolygonFromEnvelope() throws Exception {
//        System.out.println("polygonFromEnvelope");
//        EnvelopeType env = null;
//        Polygon expResult = null;
//        Polygon result = Transformer.polygonFromGmlEnvelope(env);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }

}