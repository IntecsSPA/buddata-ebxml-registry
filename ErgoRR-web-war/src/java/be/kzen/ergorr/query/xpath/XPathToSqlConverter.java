package be.kzen.ergorr.query.xpath;

import be.kzen.ergorr.commons.InternalConstants;
import be.kzen.ergorr.persist.InternalSlotTypes;
import be.kzen.ergorr.query.SqlQuery;
import be.kzen.ergorr.query.xpath.parser.XPathNode;
import be.kzen.ergorr.query.xpath.parser.SimpleXPathParser;
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
                XPathNode childNode = rootNode.getChild();
                String childNodeName = childNode.getName().toLowerCase();
                
                if (childNodeName.equals("slot")) {
                    String internalSlotType = null;
                    
                    if (childNode.getSubSelectName().equals("name")) {
                        internalSlotType = InternalSlotTypes.getInstance().getSlotType(childNode.getSubSelectValue());

                        if (internalSlotType == null) {
                            throw new XPathException("Not a registered slot name: " + childNode.getSubSelectValue());
                        }

                    } else {
                        throw new XPathException("Not a queriable slot attribute: " + childNode.getSubSelectName());
                    }

                    childNode.setAttributeName(internalSlotType + "value");
                    childNode.setQueryAttrType(internalSlotType);
                } else if (childNodeName.equals("name") || childNodeName.equals("description")) {
                    childNode.setAttributeName("value_");
                    childNode.setQueryAttrType(InternalConstants.TYPE_STRING);
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
