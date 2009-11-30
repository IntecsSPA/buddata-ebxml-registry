/*
 * Project: Buddata ebXML RegRep
 * Class: XPathToSqlConverter.java
 * Copyright (C) 2008 Yaman Ustuntas
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
                            String err = "Not a registered slot name: " + childNode.getSubSelectValue();
                            
                            if (logger.isLoggable(Level.INFO)) {
                                logger.info(err);
                            }
                            throw new XPathException(err);
                        }

                    } else {
                        String err = "Not a queriable slot attribute: " + childNode.getSubSelectName();

                        if (logger.isLoggable(Level.INFO)) {
                            logger.info(err);
                        }
                        throw new XPathException(err);
                    }

                    childNode.setAttributeName(internalSlotType + "value");
                    childNode.setQueryAttrType(internalSlotType);
                } else if (childNodeName.equals("name") || childNodeName.equals("description")) {
                    childNode.setAttributeName("value_");
                    childNode.setQueryAttrType(InternalConstants.TYPE_STRING);
                } else {
                    String err = "Not a valid child node: " + childNode.getName();

                    if (logger.isLoggable(Level.INFO)) {
                        logger.info(err);
                    }
                    throw new XPathException(err);
                }
                
                return sqlQuery.addXPath(rootNode).getChild();
            }

        } else {
            String err = "Could not evaluate XPath: " + xpath;

            if (logger.isLoggable(Level.INFO)) {
                logger.info(err);
            }
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
            if (logger.isLoggable(Level.INFO)) {
                logger.log(Level.INFO, "Could not parse XPath: " + xpath, ex);
            }
            throw new XPathException(ex);
        }

        return handler.getRoot();
    }
}
