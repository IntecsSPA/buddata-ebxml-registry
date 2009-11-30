/*
 * Project: Buddata ebXML RegRep
 * Class: XPathNode.java
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
