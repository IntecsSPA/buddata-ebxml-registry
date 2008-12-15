package be.kzen.ergorr.query.xpath.parser;

/**
 *
 * @author yaman
 */
public class ParsedNode {
    private String name;
    private String attributeName;
    private String subSelectName;
    private String subSelectValue;

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
}
