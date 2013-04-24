package be.kzen.ergorr.persist;

import be.kzen.ergorr.commons.CommonFunctions;

/**
 * Helper class to provide SQL syntax constants and helpers.
 *
 * @author yamanustuntas
 */
public class SyntaxElements {

    private SyntaxElements() {
    }

    public static final String EQUAL_SIGN = " = ";
    public static final String NOT_EQUAL_SIGN = " != ";
    public static final String GREATER_SIGN = " > ";
    public static final String GREATER_OR_EQUAL_SIGN = " >= ";
    public static final String LESS_SIGN = " < ";
    public static final String LESS_OR_EQUAL_SIGN = " <= ";
    public static final char SINGLE_CHAR = '_';
    public static final char ESCAPE_CHAR = '!';
    public static final char WILDCARD_CHAR = '%';
    public static final String SPACE = " ";
    public static final String DOT = ".";
    public static final String COMMA = ",";
    // public static final String SELECT = " SELECT ";
    public static final String SELECT = " SELECT DISTINCT ";
    public static final String WHERE = " WHERE ";
    public static final String FROM = " FROM ";
    public static final String LIKE = " LIKE ";
    public static final String LIMIT = " LIMIT ";
    public static final String OFFSET = " OFFSET ";
    public static final String BETWEEN = " BETWEEN ";
    public static final String ORDER_BY = " ORDER BY ";
    public static final String AND = " AND ";
    public static final String OR = " OR ";
    public static final String NULL = " null ";
    public static final String IS_NULL = " is null ";
    public static final String ALL_COLUMNS = ".*";
    public static final String OPEN_BR = "(";
    public static final String CLOSE_BR = ")";
    public static final String DOUBLE_QUOTE = "\"";
    public static final String SINGLE_QUOTE = "'";
    private static final String ESCAPE = "\\\\";

    /**
     * Replace SQL LIKE characters from user provided characters to database supported characters.
     * Database supported characters are:
     * {@code SINGLE_CHAR}
     * {@code WILDCARD_CHAR}
     * {@code ESCAPE_CHAR}
     *
     * @param str String to replace.
     * @param single Single character token.
     * @param wildcard Wildcard character token.
     * @param escape Escape character token.
     * @return Replaced SQL LIKE value.
     */
    public static String replaceLike(String str, String single, String wildcard, String escape) {

        char[] chars = str.toCharArray();

        char singleChar = (CommonFunctions.stringHasData(single)) ? single.charAt(0) : SINGLE_CHAR;
        char wildcardChar = (CommonFunctions.stringHasData(wildcard)) ? wildcard.charAt(0) : WILDCARD_CHAR;
        char escapeChar = (CommonFunctions.stringHasData(escape)) ? escape.charAt(0) : ESCAPE_CHAR;
        boolean singleEq = SINGLE_CHAR == singleChar;
        boolean wildcardEq = WILDCARD_CHAR == wildcardChar;
        boolean escapeEq = ESCAPE_CHAR == escapeChar;

        StringBuilder sb = new StringBuilder();

        for (char c : chars) {
            if (c == SINGLE_CHAR) {
                if (!singleEq) {
                    sb.append(ESCAPE);
                }
                sb.append(c);
            } else if (c == WILDCARD_CHAR) {
                if (!wildcardEq) {
                    sb.append(ESCAPE);
                }
                sb.append(c);
            } else if (c == ESCAPE_CHAR) {
                if (!escapeEq) {
                    sb.append(ESCAPE);
                }
            } else if (c == singleChar) { // can't be equal to SINGLE_CHAR, 1rst IF
                sb.append(SINGLE_CHAR);
            } else if (c == wildcardChar) {
                sb.append(WILDCARD_CHAR);
            } else if (c == escapeChar) {
                sb.append(ESCAPE_CHAR);
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }
}
