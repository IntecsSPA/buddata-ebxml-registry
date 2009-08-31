package be.kzen.ergorr.query.xpath;

import be.kzen.ergorr.commons.InternalConstants;
import be.kzen.ergorr.persist.InternalSlotTypes;
import be.kzen.ergorr.query.SqlQuery;
import be.kzen.ergorr.query.xpath.parser.CustomXPathHandler;
import be.kzen.ergorr.query.xpath.parser.XPathNode;
import com.werken.saxpath.XPathReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xpath.XPathException;
import org.saxpath.SAXPathException;

/**
 * Processes the XPath provided in an OGC filter.
 *
 * @author yaman
 */
public class XPathToSqlConverter {

    private static Logger logger = Logger.getLogger(XPathToSqlConverter.class.getName());
    private SqlQuery sqlQuery;
    private String xpath;

    /**
     * Constructor
     *
     * @param sqlQuery SQL query constructor.
     * @param xpath XPath string.
     */
    public XPathToSqlConverter(SqlQuery sqlQuery, String xpath) {
        this.sqlQuery = sqlQuery;
        this.xpath = xpath;
    }

    /**
     * Processes the <code>xpath</code> and appends the query information to the SqlQuery.
     *
     * @return Root XPath node.
     * @throws javax.xml.xpath.XPathException
     */
    public XPathNode process() throws XPathException {

        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "eval xpath: " + xpath);
        }

        XPathNode rootNode = parseXPath();

        if (rootNode != null) {

            if (rootNode.getChild() == null) {
                rootNode.setQueryAttrType(InternalConstants.TYPE_STRING);
                return sqlQuery.addXPath(rootNode);
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
                } else {
                    throw new XPathException("Not a valid child node: " + childNode.getName());
                }
                
                return sqlQuery.addXPath(rootNode).getChild();
            }

        } else {
            String err = "Could not evaluate XPath: " + xpath;
            logger.log(Level.SEVERE, err);
            throw new XPathException(err);
        }
    }

    /**
     * Parses the <code>xpath</code> string to <code>XPathNode</code> chain.
     *
     * @return Root XPath node.
     * @throws javax.xml.xpath.XPathException
     */
    private XPathNode parseXPath() throws XPathException {
        // XPath doesn't like $ character which are used in GML filter XPaths, so remove them before processing.
        xpath = xpath.replaceAll("\\$", "");

        XPathReader reader = new XPathReader();
        CustomXPathHandler handler = new CustomXPathHandler();
        reader.setXPathHandler(handler);

        try {
            reader.parse(xpath);
        } catch (SAXPathException ex) {
            throw new XPathException(ex);
        }

        return handler.getRoot();
    }
}
