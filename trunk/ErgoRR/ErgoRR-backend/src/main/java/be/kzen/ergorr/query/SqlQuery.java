/*
 * Project: Buddata ebXML RegRep
 * Class: SqlQuery.java
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
package be.kzen.ergorr.query;

import be.kzen.ergorr.commons.CommonProperties;
import be.kzen.ergorr.persist.SyntaxElements;
import be.kzen.ergorr.query.xpath.parser.XPathNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xpath.XPathException;

/**
 * Constructs and stores pieces of the SQL query data
 * which are put together in the end to form the SQL query string.
 * 
 * @author yamanustuntas
 */
public class SqlQuery {

    private static Logger logger = Logger.getLogger(SqlQuery.class.getName());
    private StringBuilder whereClause;
    private String sortBy;
    private List<XPathNode> joinNodes;
    private StringBuilder criteria;
    private Map<String, QueryObject> selectObjs;
    private QueryObject returnObj;
    private int aliasIdx;
    private int startPosition;
    private int maxResults;
    private static final String OBJECT_ALIAS = "o";
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final int ALLOWED_MAX_RESULTS = CommonProperties.getInstance().getInt("db.maxResponse");

    public SqlQuery() {
        whereClause = new StringBuilder();
        criteria = new StringBuilder();
        joinNodes = new ArrayList<XPathNode>();
        selectObjs = new HashMap<String, QueryObject>();
        aliasIdx = 0;
        startPosition = 1; // Postgres starts at 0, OGC filter starts at 1
        maxResults = ALLOWED_MAX_RESULTS;
    }

    /**
     * Get the max results to be returned by the query.
     *
     * @return Maximum number of results.
     */
    public int getMaxResults() {
        return maxResults;
    }

    /**
     * Set the max results to be returned by the query.
     *
     * @param maxResults Maximum number of results.
     */
    public void setMaxResults(int maxResults) {
        if (maxResults < 0 || maxResults > ALLOWED_MAX_RESULTS) {
            maxResults = ALLOWED_MAX_RESULTS;
        }
        this.maxResults = maxResults;
    }

    /**
     * Get the start position of the results to be returned by the query.
     *
     * @return Start position of results.
     */
    public int getStartPosition() {
        return startPosition;
    }

    /**
     * Set the start position of the results to be returned by the query.
     * 
     * @param startPosition Start position of results.
     */
    public void setStartPosition(int startPosition) {
        if (startPosition <= 0) {
            startPosition = 1; // OGC filter start position is 1
        }
        
        this.startPosition = startPosition;
    }

    /**
     * Set the 'sort by' SQL query clause.
     *
     * @param sortBy SQL sort by.
     */
    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    /**
     * Build the SQL query from the collected input.
     *
     * @return SQL query string.
     */
    public String buildQuery() {
        if (whereClause.length() == 0) {
            buildWhereClause();
        }

        StringBuilder selectSql = new StringBuilder();
        selectSql.append(SyntaxElements.SELECT_DISTINCT).append(returnObj.getSqlAlias()).append(SyntaxElements.ALL_COLUMNS)
                .append(whereClause.toString());

        if (sortBy != null && sortBy.length() > 0) {
            selectSql.append(SyntaxElements.ORDER_BY).append(sortBy);
        }

        selectSql.append(SyntaxElements.LIMIT).append(maxResults);

        // Postgres starts at 0, OGC filter starts at 1
        int postgreStartPosition = startPosition - 1;

        if (postgreStartPosition > 0) {
            selectSql.append(SyntaxElements.OFFSET).append(postgreStartPosition);
        }

        return selectSql.toString();
    }

    /**
     * Build the SQL WHERE clause of the query.
     */
    private void buildWhereClause() {
        whereClause.append(buildFromClause());

        if (!joinNodes.isEmpty() || criteria.length() > 0) {
            whereClause.append(SyntaxElements.WHERE).append(buildJoinCriteria()).append(criteria);
        }
    }

    /**
     * Build the SQL count query to be able to
     * get the count of matched objects.
     *
     * @return SQL count query.
     */
    public String buildCountQuery() {
        if (whereClause.length() == 0) {
            buildWhereClause();
        }

        return SyntaxElements.SELECT + "count(DISTINCT " + returnObj.getSqlAlias() + SyntaxElements.CLOSE_BR + whereClause.toString();
    }

    /**
     * Build the SQL FROM clause of the query.
     *
     * @return SQL FROM clause.
     */
    private String buildFromClause() {
        Iterator<String> it = selectObjs.keySet().iterator();

        StringBuilder fromClause = new StringBuilder(SyntaxElements.FROM);

        while (it.hasNext()) {
            String key = it.next();
            QueryObject qo = selectObjs.get(key);

            if (qo.isUsedInQuery() || qo.getSqlAlias().equals(returnObj.getSqlAlias())) {
                fromClause.append(qo.getTableName()).append(SyntaxElements.SPACE).append(qo.getSqlAlias()).append(SyntaxElements.COMMA);
            } else {
                if (logger.isLoggable(Level.INFO)) {
                    logger.info("typeName '" + qo.getObjectName() + "' with alias '" + qo.getSqlAlias() + " is not used in the query");
                }
            }
        }

        if (!joinNodes.isEmpty()) {
            for (XPathNode node : joinNodes) {
                fromClause.append(node.getQueryObject().getTableName()).append(SyntaxElements.SPACE).append(node.getQueryObject().getSqlAlias()).append(SyntaxElements.COMMA);
            }
        }
        
        fromClause.delete(fromClause.length() - 1, fromClause.length());

        return fromClause.toString() + LINE_SEPARATOR;
    }

    /**
     * Build the JOIN between RegistryObjects and [Slot, Name, Description]
     *
     * @return SQL JOIN criteria.
     */
    private String buildJoinCriteria() {
        if (!joinNodes.isEmpty()) {
            StringBuilder joinCriteria = new StringBuilder();

            for (XPathNode node : joinNodes) {
                joinCriteria.append(node.getParent().getQueryObject().getSqlAlias()).append(SyntaxElements.DOT).append("id").append(SyntaxElements.EQUAL_SIGN);
                joinCriteria.append(node.getQueryObject().getSqlAlias()).append(SyntaxElements.DOT).append("parent").append(SyntaxElements.AND);

                if (node.isSetSubSelectName() && node.isSetSubSelectValue()) {
                    joinCriteria.append(node.getQueryObject().getSqlAlias()).append(SyntaxElements.DOT).append(getColumnName(node.getSubSelectName()));
                    joinCriteria.append(SyntaxElements.EQUAL_SIGN).append(SyntaxElements.SINGLE_QUOTE).append(node.getSubSelectValue()).append(SyntaxElements.SINGLE_QUOTE).append(SyntaxElements.AND);
                }
            }

//            joinCriteria.delete(joinCriteria.length() - 5, joinCriteria.length());

            return joinCriteria.toString() + LINE_SEPARATOR;
        } else {
            return "";
        }
    }

    /**
     * Add a new JOIN criteria.
     * If JOIN doesn't exist in the request, set the joined table
     * as a {@code QueryObject} to the join node of the XPath.
     *
     * @param root XPath root element of the JOIN.
     * @return
     */
    private XPathNode addJoin(XPathNode root) {
        XPathNode node = null;

        for (XPathNode joinNode : joinNodes) {
            if (joinNode.getParent().equalsNode(root)) {
                node = joinNode.getParent();
                break;
            }
        }

        if (node == null) {
            XPathNode join = root.getChild();
            QueryObject qo = new QueryObject(join.getName(), getNextObjectAlias());
            join.setQueryObject(qo);
            joinNodes.add(join);
            node = join.getParent();
        }

        return node;
    }

    /**
     * Add a new QueryObject to the request.
     *
     * @param fullType type name.
     * @return QueryObject constructed by the {@code fullName}
     */
    public QueryObject addNewQueryObject(String fullType) {
        int idx = fullType.indexOf("_");

        QueryObject qo = null;
        if (idx > 0) {
            String[] split = fullType.split("_");
            qo = new QueryObject(split[0], getNextObjectAlias());
            selectObjs.put(split[1], qo);
        } else {
            qo = new QueryObject(fullType, getNextObjectAlias());
            selectObjs.put(fullType, qo);
        }

        return qo;
    }

    /**
     * Add a XPath to the query.
     * Extracts the QueryObject from the {@code rootNode}.
     * A QueryObject previously constructed by the csw:Query/@typeNames
     * has to match the QueryObject extracted from {@code rootNode}.
     *
     * If {@code rootNode} contains a joined child node, it will
     * be added as a JOIN criteria.
     *
     * @param rootNode XPath root node.
     * @return Root node itself or its child node if exists.
     * @throws XPathException If QueryObject constructed from {@code rootNode} is not already in the request.
     */
    public XPathNode addXPath(XPathNode rootNode) throws XPathException {
        QueryObject qo = getQueryObject(rootNode.getName());
        XPathNode node = null;

        if (qo == null) {
            String err = "Object: " + rootNode.getName() + " not defined in csw:Query/@typeNames attribute";

            if (logger.isLoggable(Level.INFO)) {
                logger.info(err);
            }
            throw new XPathException(err);
        }

        qo.setUsedInQuery(true);
        rootNode.setQueryObject(qo);

        if (rootNode.getChild() != null) {
            node = addJoin(rootNode);
        } else {
            node = rootNode;
        }

        return node;
    }

    /**
     * Get the QueryObject by alias from {@code selectObjs}.
     *
     * @param queryAlias Query alias.
     * @return QueryObject of alias or {@code null} if not found.
     */
    public QueryObject getQueryObject(String queryAlias) {
        int idx = queryAlias.indexOf("_");
        String alias = "";

        if (idx > 0) {
            String[] split = queryAlias.split("_");
            alias = split[1];
        } else {
            alias = queryAlias;
        }

        return selectObjs.get(alias);
    }

    /**
     * Append to the SQL WHERE criteria.
     *
     * @param str String to append.
     * @return StringBuilder of criteria.
     */
    public StringBuilder append(String str) {
        return criteria.append(str);
    }

    /**
     * Append to the SQL WHERE criteria.
     *
     * @param obj Object to append.
     * @return StringBuilder of criteria.
     */
    public StringBuilder append(Object obj) {
        return criteria.append(obj);
    }

    /**
     * Set the object type to be returned by this query.
     * @param returnObj Object type to return.
     */
    public void setReturnType(QueryObject returnObj) {
        this.returnObj = returnObj;
    }

    /**
     * Get the object type to be returned by this query.
     * @return Object type to return.
     */
    public QueryObject getReturnType() {
        return returnObj;
    }

    /**
     * Some column names are keywords in some database.
     * E.g: 'name'
     * This method follows the convention to append
     * a '_' at the end of those column names.
     *
     * @param name Unescaped column name.
     * @return Escapsed column name.
     */
    public static String getColumnName(String name) {
        String colName = name;

        if (name.equals("name") || name.equals("value")) {
            colName += "_";
        }
        return colName;
    }

    /**
     * Get the next, unused object alias.
     *
     * @return new alias.
     */
    private String getNextObjectAlias() {
        return OBJECT_ALIAS + aliasIdx++;
    }
}
