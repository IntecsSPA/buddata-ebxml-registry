/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.kzen.ergorr.commons;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.io.WKTWriter;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Yaman Ustuntas
 */
public class SrsToolsTest {

    public SrsToolsTest() {
    }

    @Test
    public void testTranslateGeometry() throws Exception {
//        String plg_str = "POLYGON((100 100 0,101 100 0,101 101 0,100 101 0,100 100 0))";
        String plg_str = "POLYGON((1.5 44.5 0, 1.5 43.5 0, 0.5 43.5 0, 0.5 44.5 0, 1.5 44.5 0))";
        Polygon polygon = (Polygon) new WKTReader().read(plg_str);
        polygon.setSRID(4326);
        
        Polygon newPolygon = (Polygon) SrsTools.getInstance().transformGeometry(polygon);
        
        System.out.println("out: " + new WKTWriter().write(newPolygon));
        
        newPolygon = (Polygon) SrsTools.getInstance().transformGeometry(newPolygon);
        System.out.println("out: " + new WKTWriter().write(newPolygon));
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
    }
    
    @Test
    public void testGetSrsId1() throws Exception {
        String srsName = "http://www.opengis.net/gml/srs/epsg.xml#4326";
        int result = SrsTools.getSrsId(srsName);
        assertEquals(4326, result);
    }
    
    @Test
    public void testGetSrsId2() throws Exception {
        String srsName = "EPSG:4326";
        int result = SrsTools.getSrsId(srsName);
        assertEquals(4326, result);
    }

    @Test
    public void testGetSrsId3() throws Exception {
        String srsName = "urn:ogc:def:crs:EPSG:4326";
        int result = SrsTools.getSrsId(srsName);
        assertEquals(4326, result);
    }
}