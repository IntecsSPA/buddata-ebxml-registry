/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.kzen.ergorr.commons;

import be.kzen.ergorr.geometry.SrsTools;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Yaman Ustuntas
 */
public class SrsToolsTest {

    public SrsToolsTest() {
    }

//    @Test
//    public void testTranslateGeometry() throws Exception {
////        String plg_str = "POLYGON((100 100 0,101 100 0,101 101 0,100 101 0,100 100 0))";
//        String plg_str = "POLYGON((1.5 44.5 0, 1.5 43.5 0, 0.5 43.5 0, 0.5 44.5 0, 1.5 44.5 0))";
//        
//        PrecisionModel pm = new PrecisionModel(PrecisionModel.FLOATING); 
//        GeometryFactory factory = new GeometryFactory(pm, 27700);
//        
//        Polygon polygon = (Polygon) new WKTReader(factory).read(plg_str);
//        System.out.println("1: " + polygon.getSRID());
//        polygon.setSRID(27700);
//        
//        Polygon newPolygon = (Polygon) SrsTools.getInstance().transformGeometry(polygon);
//        System.out.println(newPolygon.getSRID());
//        
//        System.out.println("out: " + new WKTWriter().write(polygon));
//        
//        System.out.println("out: " + new WKTWriter().write(newPolygon));
//        
//        WKBWriter w = new WKBWriter(2);
//        byte[] wkb = w.write(polygon); 
//        
//        System.out.println(w.bytesToHex(wkb));
//        
//        WKBReader r = new WKBReader(factory);
//        Geometry newGeo = r.read(wkb);
//        System.out.println("out: " + new WKTWriter().write(newGeo));
//        System.out.println(newGeo.getSRID());
//        
//        String geoStr = "01030000206A6900000100000005000000D88CF1D0F8306141D67BFCC29DE939417841CCC13977624161E7DB7FC5254541B44D5D66756860418032EEFC919F49417841CCC13977624161E7DB7FC5254541D88CF1D0F8306141D67BFCC29DE93941";
//        byte[] a = r.hexToBytes(geoStr);
//        System.out.println(new String(a));
//        Geometry newGeo2 = r.read(a);
//        
//        System.out.println("out: " + new WKTWriter().write(newGeo2));
//        System.out.println(newGeo2.getSRID());
//        
//        GeometryFactory factory2 = new GeometryFactory(pm, newGeo2.getSRID());
//        WKBWriter w2 = new WKBWriter(2, 2);
//        a = w2.write(newGeo2);
//
//        System.out.println(w2.bytesToHex(a));
//    }
    
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