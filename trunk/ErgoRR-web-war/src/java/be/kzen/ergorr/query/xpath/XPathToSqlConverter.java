package be.kzen.ergorr.query.xpath;

import be.kzen.ergorr.commons.CommonFunctions;
import be.kzen.ergorr.commons.InternalConstants;
import be.kzen.ergorr.model.csw.QueryType;
import be.kzen.ergorr.model.util.OFactory;
import be.kzen.ergorr.persist.InternalSlotTypes;
import be.kzen.ergorr.query.QueryObject;
import be.kzen.ergorr.query.SqlQuery;
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

    private static Logger logger = Logger.getLogger(XPathToSqlConverter.class.getName());
    private SqlQuery sqlQuery;
    private String xpath;

    public XPathToSqlConverter(SqlQuery sqlQuery, String xpath) {
        this.sqlQuery = sqlQuery;
        this.xpath = xpath;
    }

    public XPathNode process() throws XPathException {

        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "eval xpath: " + xpath);
        }

        SimpleXPathParser parser = new SimpleXPathParser(xpath);
        XPathNode rootNode = parser.parse();

        if (rootNode != null) {
            sqlQuery.addXPath(rootNode);

            if (rootNode.getChild() == null) {
                rootNode.setQueryAttrType(InternalConstants.TYPE_STRING);
                return rootNode;
            } else {
                String childNodeName = rootNode.getChild().getName().toLowerCase();
                if (childNodeName.equals("slot")) {
                    String queryType = InternalSlotTypes.getInstance().getSlotType(rootNode.getChild().getSubSelectValue());
                    rootNode.getChild().setAttributeName(queryType + "value");
                    rootNode.getChild().setQueryAttrType(queryType);
                } else if (childNodeName.equals("name") || childNodeName.equals("description")) {
                    rootNode.getChild().setAttributeName("value_");
                    rootNode.getChild().setQueryAttrType(InternalConstants.TYPE_STRING);
                }
                return rootNode.getChild();
            }

        } else {
            String err = "Could not evaluate XPath: " + xpath;
            logger.log(Level.SEVERE, err);
            throw new XPathException(err);
        }
    }
}
