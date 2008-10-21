package be.kzen.ergorr.model.util;

/**
 *
 * @author Yaman Ustuntas
 */
public class OFactory {
    public static be.kzen.ergorr.model.csw.ObjectFactory csw = new be.kzen.ergorr.model.csw.ObjectFactory();
    public static be.kzen.ergorr.model.eo.atm.ObjectFactory eo_atm = new be.kzen.ergorr.model.eo.atm.ObjectFactory();
    public static be.kzen.ergorr.model.eo.hma.ObjectFactory eo_hma = new be.kzen.ergorr.model.eo.hma.ObjectFactory();
    public static be.kzen.ergorr.model.eo.opt.ObjectFactory eo_opt = new be.kzen.ergorr.model.eo.opt.ObjectFactory();
    public static be.kzen.ergorr.model.eo.sar.ObjectFactory eo_sar = new be.kzen.ergorr.model.eo.sar.ObjectFactory();
    public static be.kzen.ergorr.model.gml.ObjectFactory gml = new be.kzen.ergorr.model.gml.ObjectFactory();
    public static be.kzen.ergorr.model.ogc.ObjectFactory ogc = new be.kzen.ergorr.model.ogc.ObjectFactory();
    public static be.kzen.ergorr.model.ows.ObjectFactory ows = new be.kzen.ergorr.model.ows.ObjectFactory();
    public static be.kzen.ergorr.model.purl.elements.ObjectFactory purl_elements = new be.kzen.ergorr.model.purl.elements.ObjectFactory();
    public static be.kzen.ergorr.model.purl.terms.ObjectFactory purl_terms = new be.kzen.ergorr.model.purl.terms.ObjectFactory();
    public static be.kzen.ergorr.model.rim.ObjectFactory rim = new be.kzen.ergorr.model.rim.ObjectFactory();
    public static be.kzen.ergorr.model.wrs.ObjectFactory wrs = new be.kzen.ergorr.model.wrs.ObjectFactory();
    
    public static Class getRimClassByElementName(String elementName) throws ClassNotFoundException {
        return Class.forName("be.kzen.ergorr.model.rim." + elementName + "Type");
    }
}
