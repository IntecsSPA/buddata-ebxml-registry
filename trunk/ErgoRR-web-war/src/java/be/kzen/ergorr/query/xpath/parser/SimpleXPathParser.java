 package be.kzen.ergorr.query.xpath.parser;

import be.kzen.ergorr.commons.CommonFunctions;
import java.util.ArrayList;
import java.util.List;
import javax.xml.xpath.XPathException;

/**
 *
 * @author yaman
 */
public class SimpleXPathParser {

    private String xpath;
    private char[] chars;
    private static final char AT = '@';
    private static final char QUOTE = '"';
    private static final char SQ_B_OPEN = '[';
    private static final char SQ_B_CLOSE = ']';
    private static final char SLASH = '/';
    private static final char EQL = '=';
    private boolean inQuotes;
    private boolean inSqBrackets;
    private int bracketStartPos;
    private int pointer;
    private int lastPos;
    private boolean hasNext;

    public SimpleXPathParser(String xpath) {
        this.xpath = (xpath.startsWith("/")) ? xpath.substring(1) : xpath;
        chars = this.xpath.toCharArray();
        inQuotes = false;
        inSqBrackets = false;
        pointer = 0;
        lastPos = 0;
        hasNext = true;
        bracketStartPos = -1;
    }

    public XPathNode parse() throws XPathException {
        XPathNode root = null;
        XPathNode parent = null;

        while (hasNext()) {
            XPathNode xpNode = getNextNode();

            if (parent != null) {
                parent.setChild(xpNode);
                xpNode.setParent(parent);
            } else {
                root = xpNode;
            }

            parent = xpNode;
        }

        return root;
    }

    public XPathNode getNextNode() throws XPathException {
        pointer = lastPos;
        XPathNode nextNode = new XPathNode();

        while (true) {
            if (pointer >= chars.length) {
                if (inQuotes) {
                    throw new XPathException("Invalid usage of quotes");
                }
                String nodeName = CommonFunctions.removePrefix(xpath.substring(lastPos, pointer));
                nextNode.setName(stripName(nodeName));
                hasNext = false;
                break;
            }

            char c = chars[pointer];
            if (c == QUOTE) {
                inQuotes = inQuotes ? false : true;
            } else if (c == SLASH && !inQuotes) {
                if (chars[pointer + 1] == AT) {
                    String nodeName = CommonFunctions.removePrefix(xpath.substring(lastPos, pointer));
                    String attrName = xpath.substring(pointer + 2, xpath.length());
                    nextNode.setName(stripName(nodeName));
                    nextNode.setAttributeName(attrName);
                    hasNext = false;
                    break;
                } else {
                    String nodeName = xpath.substring(lastPos, pointer);
                    nextNode.setName(stripName(nodeName));
                    lastPos = pointer + 1;
                    break;
                }
            } else if (c == SQ_B_OPEN && !inQuotes) {
                inSqBrackets = true;
                String nodeName = xpath.substring(lastPos, pointer);
                nextNode.setName(stripName(nodeName));
                bracketStartPos = pointer;
            } else if (c == SQ_B_CLOSE && !inQuotes) {
                if (!inSqBrackets) {
                    throw new XPathException("Closing square backet without opening at column" + pointer);
                }

                String subSelect = xpath.substring(bracketStartPos + 1, pointer);
                if (subSelect.startsWith("@")) {
                    int eqlIdx = subSelect.indexOf(EQL);

                    if (eqlIdx >= 0) {
                        String attr = subSelect.substring(1, eqlIdx);
                        String attrVal = subSelect.substring(eqlIdx + 2, subSelect.length() - 1);
                        nextNode.setSubSelectName(attr);
                        nextNode.setSubSelectValue(attrVal);
                    }
                } else {
                    nextNode.setSubSelectValue(subSelect);
                }

                inSqBrackets = false;
                bracketStartPos = -1;
                lastPos = pointer + 2;

                if (chars.length > lastPos && chars[lastPos] == AT) {
                    nextNode.setAttributeName(xpath.substring(lastPos + 1, xpath.length()));
                    hasNext = false;
                } else {
                    hasNext = (lastPos < chars.length);
                }

                break;
            }

            pointer++;
        }

        return nextNode;
    }

    public String stripName(String nodeName) {
        if (nodeName.startsWith("$")) {
            nodeName = nodeName.substring(1);
        } else {
            nodeName = CommonFunctions.removePrefix(nodeName);
        }
        return nodeName;
    }

    public boolean hasNext() {
        return hasNext;
    }
}
