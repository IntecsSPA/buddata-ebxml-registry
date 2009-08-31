package be.kzen.ergorr.query.xpath.parser;

import be.kzen.ergorr.query.QueryObject;

/**
 * Represents an XPath node.
 * 
 * @author yaman
 */
public class XPathNode {

    private XPathNode parent;
    private XPathNode child;
    private String name;
    private String attributeName;
    private String subSelectName;
    private String subSelectValue;
    private String queryAttrType;
    private QueryObject queryObject;

    public XPathNode getParent() {
        return parent;
    }

    public void setParent(XPathNode parent) {
        this.parent = parent;
    }

    public XPathNode getChild() {
        return child;
    }

    public void setChild(XPathNode child) {
        this.child = child;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public boolean isSetAttributeName() {
        return attributeName != null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubSelectName() {
        return subSelectName;
    }

    public void setSubSelectName(String subSelectName) {
        this.subSelectName = subSelectName;
    }

    public String getSubSelectValue() {
        return subSelectValue;
    }

    public void setSubSelectValue(String subSelectValue) {
        this.subSelectValue = subSelectValue;
    }

    public boolean isSetSubSelectName() {
        return subSelectName != null;
    }

    public boolean isSetSubSelectValue() {
        return subSelectValue != null;
    }

    public QueryObject getQueryObject() {
        return queryObject;
    }

    public void setQueryObject(QueryObject queryObject) {
        this.queryObject = queryObject;
    }

    public String getQueryAttrType() {
        return queryAttrType;
    }

    public void setQueryAttrType(String queryType) {
        this.queryAttrType = queryType;
    }

    public boolean equalsNode(XPathNode otherNode) {
        boolean isEqual = false;

        if (getName().equals(otherNode.getName()) &&
                ((!isSetSubSelectName() && !otherNode.isSetSubSelectName()) ||
                        (isSetSubSelectName() && otherNode.isSetSubSelectName() && getSubSelectName().equals(otherNode.getSubSelectName()))) &&
                ((!isSetSubSelectValue() && !otherNode.isSetSubSelectValue()) ||
                        (isSetSubSelectValue() && otherNode.isSetSubSelectValue() && getSubSelectValue().equals(otherNode.getSubSelectValue())))) {

            if (otherNode.getChild() != null && getChild() != null) {
                if (getChild().equalsNode(otherNode.getChild())) {
                    isEqual = true;
                }
            } else if (getChild() == null && otherNode.getChild() == null) {
                isEqual = true;
            }
        }

        return isEqual;
    }
}
