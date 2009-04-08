package be.kzen.ergorr.xpath;

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * XPath utility class
 *
 * @author <a href="mailto:yaman@cryptosense.com">Yaman Ustuntas</a>
 * Created on 12 November 2006
 */
public class XPathUtil {
    private XPathFactory xpathFactory;
    private XPath xpath;

    public XPathUtil() {
        init();
    }
    
    public XPathUtil(NamespaceContext ns) {
        init();
        xpath.setNamespaceContext(ns);
    }
    
    /**
     * Initialize XPath & XPath factory.
     */
    private void init() {
        xpathFactory = XPathFactory.newInstance();
        xpath = xpathFactory.newXPath();
    }
    
    /**
     * Set the namespaces to be used during XPath evaluations.
     *
     * @param namespaces Namespaces
     */
    public void setNamespaces(NamespaceContext namespaces) {
        xpath.setNamespaceContext(namespaces);
    }
    
    /**
     * Get an <code>Element</code> instance from an XML document with a XPath expression.
     *
     * @param expression XPath expression.
     * @param obj Node, NodeList or Document.
     * @throws javax.xml.xpath.XPathExpressionException
     * @return Selected element.
     */
    public Element getElement(String expression, Object obj) throws XPathExpressionException {
        return (Element)xpath.evaluate(expression, obj, XPathConstants.NODE);
    }
    
    /**
     * Get a <code>Node</code> instance from an XML document with a XPath expression.
     *
     * @param expression XPath expression.
     * @param obj Node, NodeList or Document.
     * @throws javax.xml.xpath.XPathExpressionException
     * @return Selected node.
     */
    public Node getNode(String expression, Object obj) throws XPathExpressionException {
        return (Node)xpath.evaluate(expression, obj, XPathConstants.NODE);
    }
    
    /**
     * Get a <code>NodeList</code> instance from an XML document with a XPath expression.
     *
     * @param expression XPath expression.
     * @param obj Node, NodeList or Document.
     * @throws javax.xml.xpath.XPathExpressionException
     * @return Selected nodes.
     */
    public NodeList getNodes(String expression, Object obj) throws XPathExpressionException {
        return (NodeList)xpath.evaluate(expression, obj, XPathConstants.NODESET);
    }
    
    /**
     * Get a <code>String</code> from an XML document with a XPath expression.
     *
     * @param expression XPath expression.
     * @param obj Node, NodeList or Document.
     * @throws javax.xml.xpath.XPathExpressionException
     * @return Result as string.
     */
    public String getString(String expression, Object obj) throws XPathExpressionException {
        return (String)xpath.evaluate(expression, obj, XPathConstants.STRING);
    }
    
    /**
     * Get a <code>double</code> from an XML document with a XPath expression.
     *
     * @param expression XPath expression.
     * @param obj Node, NodeList or Document.
     * @throws javax.xml.xpath.XPathExpressionException
     * @return Result as double.
     */
    public double getNumber(String expression, Object obj) throws XPathExpressionException {
        return (Double)xpath.evaluate(expression, obj, XPathConstants.NUMBER);
    }
    
    /**
     * Delete the first matching node from a XML document.
     *
     * @param expression XPath of the node to delete.
     * @param doc XML document to delete the node from.
     * @throws javax.xml.xpath.XPathExpressionException
     */
    public void deleteNode(String expression, Document doc) throws XPathExpressionException {
        Node n = (Node)xpath.evaluate(expression, doc, XPathConstants.NODE);
        if (n != null && n.getParentNode() != null) {
            n.getParentNode().removeChild(n);
        }
    }
    
    public void insertNode(String expression, Document doc, Node newNode) throws XPathExpressionException {
        Node node = (Node)xpath.evaluate(expression, doc, XPathConstants.NODE);
        newNode = doc.importNode(node, true);
        node.appendChild(newNode);
    }
    
    public void insertNodeBefore(String expression, Document doc, Node newNode) throws XPathExpressionException {
        Node node = (Node)xpath.evaluate(expression, doc, XPathConstants.NODE);
        newNode = doc.importNode(node, true);
        node.insertBefore(newNode, node);
    }
}
