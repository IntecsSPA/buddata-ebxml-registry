package be.kzen.ergorr.query.xpath;

import be.kzen.ergorr.commons.CommonFunctions;
import be.kzen.ergorr.model.util.OFactory;
import be.kzen.ergorr.query.QueryObject;
import be.kzen.ergorr.query.xpath.parser.XPathNode;
import be.kzen.ergorr.query.xpath.parser.SimpleXPathParser;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xpath.XPathException;

/**
 *
 * @author yaman
 */
public class XPathToSqlConverter {

    private Map<String, QueryObject> queryObjs;
    private String xpath;
    private StringBuilder sqlCriteria;

    public XPathToSqlConverter(Map<String, QueryObject> queryObjs, String xpath, StringBuilder sqlCriteria) {
        this.queryObjs = queryObjs;
        this.xpath = xpath;
        this.sqlCriteria = sqlCriteria;
    }

    public void convert() throws XPathException, ClassNotFoundException {
        List<XPathNode> nodes = new ArrayList<XPathNode>();
        SimpleXPathParser parser = new SimpleXPathParser(xpath);

        while (parser.hasNext()) {
            nodes.add(parser.getNextNode());
        }

        if (nodes.size() > 0) {
            XPathNode n = nodes.get(0);
            QueryObject qo  = queryObjs.get(n.getName());

            if (qo == null) {

            }

            Class c = OFactory.getXmlClassByElementName(qo.getObjectName());
            
        }

        if (xpath.startsWith("/")) {
            xpath = xpath.substring(1);
        }

        int idx = xpath.indexOf("/");

//        if (idx >= 0) {
//            String objName = xpath.substring(0, idx);
//            objName = CommonFunctions.removePrefix(objName);
//
//            QueryObject qo = queryObjs.get(objName);
//
//            if (qo != null) {
//
//                try {
//                    Class clazz = Class.forName("be.kzen.ergorr.query.xpath." + qo.getObjectName());
//                    Constructor cons = clazz.getConstructor(String.class);
//                    XPathObject xpo = (XPathObject) cons.newInstance("");
//
//
//                } catch (InstantiationException ex) {
//                    Logger.getLogger(XPathToSqlConverter.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (IllegalAccessException ex) {
//                    Logger.getLogger(XPathToSqlConverter.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (IllegalArgumentException ex) {
//                    Logger.getLogger(XPathToSqlConverter.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (InvocationTargetException ex) {
//                    Logger.getLogger(XPathToSqlConverter.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (NoSuchMethodException ex) {
//                    Logger.getLogger(XPathToSqlConverter.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (SecurityException ex) {
//                    Logger.getLogger(XPathToSqlConverter.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (ClassNotFoundException ex) {
//                    Logger.getLogger(XPathToSqlConverter.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            } else {
//                //TODO throw exception, not a query object
//            }
//
//        }
    }
}
