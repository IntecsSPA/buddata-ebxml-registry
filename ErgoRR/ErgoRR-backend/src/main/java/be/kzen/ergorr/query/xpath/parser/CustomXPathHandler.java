/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.kzen.ergorr.query.xpath.parser;

import org.saxpath.Axis;
import org.saxpath.Operator;
import org.saxpath.SAXPathException;
import org.saxpath.XPathHandler;

/**
 * XPath parser implementation.
 *
 * @author yamanustuntas
 */
public class CustomXPathHandler implements XPathHandler {
    private XPathNode root;
    private XPathNode parent;
    boolean inCondition;

    public CustomXPathHandler() {
        inCondition = false;
    }

    public XPathNode getRoot() {
        return root;
    }

    public void startXPath() throws SAXPathException {
        // ignore
    }

    public void endXPath() throws SAXPathException {
        // ignore
    }

    public void startAbsoluteLocationPath() throws SAXPathException {
        // ignore
    }

    public void endAbsoluteLocationPath() throws SAXPathException {
        // ignore
    }

    public void startRelativeLocationPath() throws SAXPathException {
        // ignore
    }

    public void endRelativeLocationPath() throws SAXPathException {
        // ignore
    }

    public void startAdditiveExpr() throws SAXPathException {
        // ignore
    }

    public void endAdditiveExpr(int op) throws SAXPathException {
        if (op != Operator.NO_OP) {
            throw new SAXPathException("Unsupported endAdditiveExpr");
        }
    }

    public void startAllNodeStep(int axis) throws SAXPathException {
        throw new SAXPathException("Unsupported startAllNodeStep");
    }

    public void endAllNodeStep() throws SAXPathException {
        throw new SAXPathException("Unsupported endAllNodeStep");
    }

    public void startCommentNodeStep(int axis) throws SAXPathException {
        throw new SAXPathException("Unsupported startCommentNodeStep");
    }

    public void endCommentNodeStep() throws SAXPathException {
        throw new SAXPathException("Unsupported endCommentNodeStep");
    }

    public void startEqualityExpr() throws SAXPathException {
//        System.out.println("START EQUAL");
    }

    public void endEqualityExpr(int op) throws SAXPathException {
        if (op == Operator.EQUALS) {
//            System.out.println("EQUAL");
        } else if (op == Operator.NOT_EQUALS) {
//            System.out.println("NOT EQUAL");
        }
    }

    public void startFilterExpr() throws SAXPathException {
        // called before comparison literal
    }

    public void endFilterExpr() throws SAXPathException {
        // called after comparison literal
    }

    public void startFunction(String prefix, String name) throws SAXPathException {
        throw new SAXPathException("Unsupported startFunction");
    }

    public void endFunction() throws SAXPathException {
        throw new SAXPathException("Unsupported endFunction");
    }

    public void startMultiplicativeExpr() throws SAXPathException {
        // ingore *, DIV, MOD
    }

    public void endMultiplicativeExpr(int op) throws SAXPathException {
        if (op != Operator.NO_OP) {
            throw new SAXPathException("Unsupported endMultiplicativeExpr");
        }
    }

    public void startNameStep(int axis, String prefix, String name) throws SAXPathException {
        String type = "";
        switch (axis) {
            case Axis.CHILD:
                type = "CHILD";
                XPathNode node = new XPathNode();
                node.setName(name);

                if (parent != null) {
                    parent.setChild(node);
                    node.setParent(parent);
                } else {
                    root = node;
                }

                parent = node;
                
                break;
            case Axis.ATTRIBUTE:
                type = "ATTRIBUTE";
                
                if (inCondition) {
                    parent.setSubSelectName(name);
                } else {
                    parent.setAttributeName(name);
                }

                break;
            case Axis.ANCESTOR:
                throw new SAXPathException("Unsupported ANCESTOR");
            case Axis.ANCESTOR_OR_SELF:
                throw new SAXPathException("Unsupported ANCESTOR_OR_SELF");
            case Axis.DESCENDANT:
                throw new SAXPathException("Unsupported DESCENDANT");
            case Axis.DESCENDANT_OR_SELF:
                throw new SAXPathException("Unsupported DESCENDANT_OR_SELF");
            case Axis.FOLLOWING:
                throw new SAXPathException("Unsupported FOLLOWING");
            case Axis.FOLLOWING_SIBLING:
                throw new SAXPathException("Unsupported FOLLOWING_SIBLING");
            case Axis.INVALID_AXIS:
                throw new SAXPathException("Unsupported INVALID_AXIS");
            case Axis.NAMESPACE:
                throw new SAXPathException("Unsupported NAMESPACE");
            case Axis.PARENT:
                throw new SAXPathException("Unsupported PARENT");
            case Axis.PRECEDING:
                throw new SAXPathException("Unsupported PRECEDING");
            case Axis.PRECEDING_SIBLING:
                throw new SAXPathException("Unsupported PRECEDING_SIBLING");
            case Axis.SELF:
                throw new SAXPathException("Unsupported SELF");
        }

//        System.out.println("namestep: " + type + "    prefix:" + prefix + "    name:" + name);
    }

    public void endNameStep() throws SAXPathException {
        // ignore
    }

    public void startOrExpr() throws SAXPathException {
        // ignore
    }

    public void endOrExpr(boolean create) throws SAXPathException {
        if (create) {
            throw new SAXPathException("Unsupported endOrExpr");
        }
    }

    public void startAndExpr() throws SAXPathException {
        // ignore
    }

    public void endAndExpr(boolean create) throws SAXPathException {
        if (create) {
            throw new SAXPathException("Unsupported endAndExpr");
        }
    }

    public void startPathExpr() throws SAXPathException {
        // ignore
    }

    public void endPathExpr() throws SAXPathException {
        // ignore
    }

    public void startPredicate() throws SAXPathException {
//        System.out.println("startPredicate");
        inCondition = true;
    }

    public void endPredicate() throws SAXPathException {
//        System.out.println("endPredicate");
        inCondition = false;
    }

    public void startProcessingInstructionNodeStep(int axis, String name) throws SAXPathException {
        throw new SAXPathException("Unsupported startProcessingInstructionNodeStep");
    }

    public void endProcessingInstructionNodeStep() throws SAXPathException {
        throw new SAXPathException("Unsupported endProcessingInstructionNodeStep");
    }

    public void startRelationalExpr() throws SAXPathException {
        // ignore
    }

    public void endRelationalExpr(int op) throws SAXPathException {
        if (op != Operator.NO_OP) {
            throw new SAXPathException("Unsupported endRelationalExpr");
        }

//        switch (op) {
//            case Operator.LESS_THAN:
//                System.out.println("LESS THAN");
//                break;
//            case Operator.LESS_THAN_EQUALS:
//                System.out.println("LESS THAN EQUALS");
//                break;
//            case Operator.GREATER_THAN:
//                System.out.println("GREATER THAN");
//                break;
//            case Operator.GREATER_THAN_EQUALS:
//                System.out.println("GREATER THAN EQUALS");
//                break;
//        }
    }

    public void startTextNodeStep(int axis) throws SAXPathException {
        throw new SAXPathException("Unsupported startTextNodeStep");
    }

    public void endTextNodeStep() throws SAXPathException {
        throw new SAXPathException("Unsupported endTextNodeStep");
    }

    public void startUnaryExpr() throws SAXPathException {
        // ignore
    }

    public void endUnaryExpr(int op) throws SAXPathException {
        if (op != Operator.NO_OP) {
            throw new SAXPathException("Unsupported endUnaryExpr");
        }
    }

    public void startUnionExpr() throws SAXPathException {
        // ignore
    }

    public void endUnionExpr(boolean create) throws SAXPathException {
        if (create) {
            throw new SAXPathException("Unsupported endUnionExpr");
        }
    }

    public void literal(String literal) throws SAXPathException {
//        System.out.println("lit: " + literal);
        parent.setSubSelectValue(literal);
    }

    public void number(double num) throws SAXPathException {
//        System.out.println("number: " + num);
        parent.setSubSelectValue(String.valueOf(num));
    }

    public void number(int num) throws SAXPathException {
//        System.out.println("number:" + num);
        parent.setSubSelectValue(String.valueOf(num));
    }

    public void variableReference(String prefix, String name) throws SAXPathException {
        throw new SAXPathException("Unsupported variableReference");
    }
}
