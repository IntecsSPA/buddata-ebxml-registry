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

    public SqlQuery() {
        whereClause = new StringBuilder();
        criteria = new StringBuilder();
        joinNodes = new ArrayList<XPathNode>();
        selectObjs = new HashMap<String, QueryObject>();
        aliasIdx = 0;
        startPosition = 0;
        maxResults = 0;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String buildQuery() {
        if (whereClause.length() == 0) {
            buildWhereClause();
        }

        Integer allowedMaxResults = CommonProperties.getInstance().getInt("db.maxResponse");

        StringBuilder selectSql = new StringBuilder();
        selectSql.append(SyntaxElements.SELECT).append(returnObj.getSqlAlias()).append(SyntaxElements.ALL_COLUMNS)
                .append(whereClause.toString());

        if (sortBy != null && sortBy.length() > 0) {
            selectSql.append(SyntaxElements.ORDER_BY).append(sortBy);
        }

        int limit = maxResults;
        if (maxResults <= 0 || maxResults > allowedMaxResults) {
            limit = allowedMaxResults;
        }

        selectSql.append(SyntaxElements.LIMIT).append(limit);

        if (startPosition > 0) {
            selectSql.append(SyntaxElements.OFFSET).append(startPosition);
        }

        return selectSql.toString();
    }

    private void buildWhereClause() {
        whereClause.append(buildFromClause());

        if (!joinNodes.isEmpty() || criteria.length() > 0) {
            whereClause.append(SyntaxElements.WHERE).append(buildJoinCriteria()).append(criteria);
        }
    }

    public String buildCountQuery() {
        if (whereClause.length() == 0) {
            buildWhereClause();
        }

        return SyntaxElements.SELECT + "count(" + returnObj.getSqlAlias() + SyntaxElements.CLOSE_BR + whereClause.toString();
    }

    private String buildFromClause() {
        Iterator<String> it = selectObjs.keySet().iterator();

        StringBuilder fromClause = new StringBuilder(SyntaxElements.FROM);

        while (it.hasNext()) {
            String key = it.next();
            QueryObject qo = selectObjs.get(key);

            if (qo.isUsedInQuery() || qo.getSqlAlias().equals(returnObj.getSqlAlias())) {
                fromClause.append(qo.getTableName()).append(SyntaxElements.SPACE).append(qo.getSqlAlias()).append(SyntaxElements.COMMA);
            } else {
                if (logger.isLoggable(Level.WARNING)) {
                    logger.warning("typeName '" + qo.getObjectName() + "' with alias '" + qo.getSqlAlias() + " is not used in the query");
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

    public XPathNode addXPath(XPathNode rootNode) throws XPathException {
        QueryObject qo = getQueryObject(rootNode.getName());
        XPathNode node = null;

        if (qo == null) {
            throw new XPathException("Object: " + rootNode.getName() + " not defined in typeNames attribute");
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

    public StringBuilder append(String str) {
        return criteria.append(str);
    }

    public StringBuilder append(Object obj) {
        return criteria.append(obj);
    }

    public void setReturnType(QueryObject returnObj) {
        this.returnObj = returnObj;
    }

    public QueryObject getReturnType() {
        return returnObj;
    }

    public static String getColumnName(String name) {
        String colName = name;

        if (name.equals("name") || name.equals("value")) {
            colName += "_";
        }
        return colName;
    }

    private String getNextObjectAlias() {
        return OBJECT_ALIAS + aliasIdx++;
    }
}
