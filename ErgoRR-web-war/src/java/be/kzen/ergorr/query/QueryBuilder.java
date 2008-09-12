/*
 * Project: Buddata ebXML RegRep
 * Class: QueryBuilder.java
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

import be.kzen.ergorr.commons.SrsTools;
import be.kzen.ergorr.commons.Transformer;
import be.kzen.ergorr.exceptions.QueryException;
import be.kzen.ergorr.exceptions.TransformException;
import be.kzen.ergorr.interfaces.soap.RequestContext;
import be.kzen.ergorr.model.csw.GetRecordsType;
import be.kzen.ergorr.model.csw.QueryType;
import be.kzen.ergorr.model.gml.EnvelopeType;
import be.kzen.ergorr.model.ogc.BBOXType;
import be.kzen.ergorr.model.ogc.BinaryComparisonOpType;
import be.kzen.ergorr.model.ogc.BinaryLogicOpType;
import be.kzen.ergorr.model.ogc.BinarySpatialOpType;
import be.kzen.ergorr.model.ogc.DistanceBufferType;
import be.kzen.ergorr.model.ogc.FilterType;
import be.kzen.ergorr.model.ogc.LiteralType;
import be.kzen.ergorr.model.ogc.PropertyIsBetweenType;
import be.kzen.ergorr.model.ogc.PropertyIsLikeType;
import be.kzen.ergorr.model.ogc.PropertyIsNullType;
import be.kzen.ergorr.model.ogc.PropertyNameType;
import be.kzen.ergorr.commons.SlotTypes;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Polygon;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import org.apache.log4j.Logger;

/**
 * Build an SQL/HQL query string from a given OGC Query XML element.
 * 
 * @author Yaman Ustuntas
 */
public class QueryBuilder {

    private static Logger log = Logger.getLogger(QueryBuilder.class);
    private StringBuilder sql;
    private GetRecordsType request;
    private QueryType queryType;
    private Map<String, QueryObject> queryTypes;
    // stores Geometry and Date objects to be passed as parameters to the SQL query.
    private Map<String, Object> queryParams;
    private int queryObjectIdx;
    private int paramIdx;
    private int maxResults;
    private int startPosition;
    private static final String EQUAL_SIGN = " = ";
    private static final String NOT_EQUAL_SIGN = " != ";
    private static final String GREATER_SIGN = " > ";
    private static final String GREATER_OR_EQUAL_SIGN = " >= ";
    private static final String LESS_SIGN = " < ";
    private static final String LESS_OR_EQUAL_SIGN = " <= ";
    private static final String SINGLE_CHAR = "_";
    private static final String ESCAPE_CHAR = "!";
    private static final String WILDCARD_CHAR = "%";
    private static final String OBJECT_ALIAS = "o";
    private static final String PARAM_ALIAS = "p";

    /**
     * Constructor.
     * 
     * @param requestContext Request context.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    public QueryBuilder(RequestContext requestContext) throws QueryException {
        request = (GetRecordsType) requestContext.getRequest();
        queryType = (QueryType) request.getAbstractQuery().getValue();
        sql = new StringBuilder(128);
        queryTypes = new HashMap<String, QueryObject>();
        queryParams = new HashMap<String, Object>();
        queryObjectIdx = 1;
        paramIdx = 1;
        maxResults = 0;
        startPosition = 0;
        initQueryParameters();
    }

    /**
     * Get the parameters used in the query.
     * 
     * @return Parameters.
     */
    public Map<String, Object> getParameters() {
        return queryParams;
    }

    /**
     * Get the index position of the first result
     * to be returned.
     * 
     * @return Index position of first result.
     */
    public int getStartPosition() {
        return startPosition;
    }

    /**
     * Get the maximum number of results to return.
     * 
     * @return Maximum numbers of results.
     */
    public int getMaxResults() {
        return maxResults;
    }

    /**
     * Get the object to be returned in the query result.
     * Returns <code>null</code> if object not found in <code>queryTypes</code>.
     * 
     * @return Object to be returned in query.
     */
    private QueryObject getReturnObject() {
        Iterator<String> it = queryTypes.keySet().iterator();

        while (it.hasNext()) {
            QueryObject qo = queryTypes.get(it.next());

            if (qo.isReturnType()) {
                return qo;
            }
        }
        return null;
    }

    /**
     * Get a unique queriable object alias.
     * 
     * @return Unique alias.
     */
    private String getNextQueryObjectAlias() {
        return OBJECT_ALIAS + queryObjectIdx++;
    }

    /**
     * Get a unique parameter alias.
     * 
     * @return Unique alias.
     */
    private String getNextParamAlias() {
        return PARAM_ALIAS + paramIdx++;
    }

    /**
     * Initializes the query parameters:
     * Objects to be queried, return objects, amount of objects to return and
     * start position for paging.
     * 
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void initQueryParameters() throws QueryException {

        if (request.isSetMaxRecords()) {
            maxResults = request.getMaxRecords().intValue();
        }
        if (request.isSetStartPosition()) {
            startPosition = request.getStartPosition().intValue();
        }

        // get returned objects
        List<String> returnTypeNames = new ArrayList<String>();

        for (QName qname : queryType.getElementSetName().getTypeNames()) {
            if (qname != null) {
                returnTypeNames.add(qname.getLocalPart());
            } else {
                throw new QueryException("Invalid QName in ElementSetName elemens typeNames attribute");
            }
        }

        // get queried objects
        List<QName> qnames = queryType.getTypeNames();

        if (!qnames.isEmpty()) {
            for (QName qname : qnames) {
                if (qname != null) {
                    String fullType = qname.getLocalPart();

                    int idx = fullType.indexOf("_");

                    QueryObject qo = null;
                    if (idx > 0) {
                        String[] split = fullType.split("_");
                        qo = new QueryObject(split[0], getNextQueryObjectAlias());
                        queryTypes.put(split[1], qo);
                    } else {
                        qo = new QueryObject(fullType, getNextQueryObjectAlias());
                        queryTypes.put(fullType, qo);
                    }

                    qo.setReturnType(returnTypeNames.contains(fullType));
                } else {
                    throw new QueryException("Invalid QName in Query elements typeNames attribute");
                }
            }
        } else {
            throw new QueryException("No queriable types specified in Query elements typeNames attribute");
        }
    }

    /**
     * Builds the SQL query from OGC Query element.
     * 
     * @return SQL query string.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    public String build() throws QueryException {
        FilterType filter = queryType.getConstraint().getFilter();
        JAXBElement queryOperator = null;

        if (filter.isSetLogicOps()) {
            createSelectFromClause();
            queryOperator = filter.getLogicOps();
        } else if (filter.isSetComparisonOps()) {
            createSelectFromClause();
            queryOperator = filter.getComparisonOps();
        } else if (filter.isSetSpatialOps()) {
            createSelectFromClause();
            queryOperator = filter.getSpatialOps();
        } else {
            // query all
            QueryObject queryObject = getReturnObject();
            if (queryObject != null) {
                sql.append("select ").append(queryObject.getSqlAlias()).append(" from ").append(queryObject.getObjectType()).append(" ").append(queryObject.getSqlAlias());
            } else {
                throw new QueryException("No return object provided in the query");
            }
        }

        recurseQueryOperator(queryOperator);
        return sql.toString();
    }

    /**
     * Creates the "select {params} from {params} where " 
     * for the SQL query without the conditions.
     */
    private void createSelectFromClause() {
        Iterator<String> it = queryTypes.keySet().iterator();

        StringBuilder selectClause = new StringBuilder("select ");
        StringBuilder fromClause = new StringBuilder(" from ");

        log.debug("Query params:");
        while (it.hasNext()) {
            String key = it.next();
            QueryObject qo = queryTypes.get(key);

            if (log.isDebugEnabled()) {
                log.debug("  alias: " + key);
                log.debug("  obj: " + qo.getObjectType());
                log.debug("  sqlAlias: " + qo.getSqlAlias());
                log.debug("  return: " + qo.isReturnType());
                log.debug("");
            }

            fromClause.append(qo.getObjectType()).append(" ").append(qo.getSqlAlias()).append(", ");

            if (qo.isReturnType()) {
                selectClause.append(qo.getSqlAlias()).append(", ");
            }
        }

        sql.append(selectClause.substring(0, selectClause.length() - 2)); // strip the last ", "
        sql.append(fromClause.substring(0, fromClause.length() - 2)); // strip the last ", "
        sql.append(" where ");
    }

    /**
     * Create the same query as the original <code>sql</code>
     * but for returning the total count of the matched query
     * results instead of the results themselves.
     * 
     * @return Query to count the matched objects.
     */
    public String createCountQuery() {
        StringBuilder countSql = new StringBuilder();


        int idx1 = sql.indexOf(" ") + 1;
        int idx2 = sql.indexOf(" from ");

        countSql.append(sql.substring(0, idx1)).append("count(");
        countSql.append(sql.substring(idx1, idx2)).append(")");
        countSql.append(sql.substring(idx2));

        return countSql.toString();
    }

    /**
     * This method is called recursively to loop and process 
     * all the operators of the OGC Query.
     * 
     * Java methods handling the Query operators are called
     * through reflection using the name of the XML binding class.
     * 
     * @param queryOperator The next query operator to process.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void recurseQueryOperator(JAXBElement queryOperator) throws QueryException {
        if (log.isDebugEnabled()) {
            log.debug("queryOperator: " + queryOperator.getClass().toString());
        }

        String opName = "op" + queryOperator.getClass().getSimpleName();

        try {
            Method method = this.getClass().getDeclaredMethod(opName, new Class[]{queryOperator.getValue().getClass()});
            method.invoke(this, new Object[]{queryOperator.getValue()});
        } catch (IllegalArgumentException ex) {
            throw new QueryException("Illegal argument invoking " + opName, ex);
        } catch (InvocationTargetException ex) {
            throw new QueryException("Exception invoking " + opName, ex);
        } catch (NoSuchMethodException ex) {
            throw new QueryException("Method not found: " + opName, ex);
        } catch (IllegalAccessException ex) {
            throw new QueryException("Illegal access: " + opName, ex);
        }
    }

    /**
     * Handles OGC query And operator.
     * 
     * @param opAnd And operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opAnd(BinaryLogicOpType opAnd) throws QueryException {
        List<JAXBElement<?>> els = opAnd.getComparisonOpsOrSpatialOpsOrLogicOps();
        sql.append("(");
        for (int i = 0; i < els.size(); i++) {
            JAXBElement<?> el = els.get(i);

            recurseQueryOperator(el);

            if (i + 1 < els.size()) {
                sql.append(" and ");
            }
        }
        sql.append(")");
    }

    /**
     * Handles OGC query Or operator.
     * @param opOr Or operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opOr(BinaryLogicOpType opOr) throws QueryException {
        List<JAXBElement<?>> els = opOr.getComparisonOpsOrSpatialOpsOrLogicOps();
        sql.append("(");
        for (int i = 0; i < els.size(); i++) {
            JAXBElement<?> el = els.get(i);

            recurseQueryOperator(el);

            if (i + 1 < els.size()) {
                sql.append(" or ");
            }
        }
        sql.append(")");
    }

    /**
     * Handles OGC query Not operator.
     * 
     * @param opNot Not operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opNot(BinaryLogicOpType opNot) throws QueryException {
        throw new QueryException("Not element is not supported");
    }

    /**
     * Handles OGC query PropertyIsLike operator.
     * 
     * @param propLike PropertyIsLike operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opPropertyIsLike(PropertyIsLikeType propLike) throws QueryException {
        if (propLike.getLiteral().getContent().size() > 0) {
            String queriedSlotType = "string"; // for now assume that it is just for strings
            PropertyNameType propName = propLike.getPropertyName();

            if (!propName.getContent().isEmpty()) {
                String xpath = (String) propName.getContent().get(0);

                if (propLike.isSetLiteral() && !propLike.getLiteral().getContent().isEmpty()) {
                    String likeClause = (String) propLike.getLiteral().getContent().get(0);
                    XPathObject xp = new XPathObject(xpath);
                    xp.process();

                    if (propLike.isSetWildCard()) {
                        likeClause = likeClause.replaceAll(propLike.getWildCard(), WILDCARD_CHAR);
                    }

                    if (propLike.isSetSingleChar()) {
                        likeClause = likeClause.replaceAll(propLike.getSingleChar(), SINGLE_CHAR);
                    }

                    if (propLike.isSetEscapeChar()) {
                        likeClause = likeClause.replaceAll(propLike.getEscapeChar(), ESCAPE_CHAR);
                    }

                    if (xp.isSlotQuery()) {
                        sql.append(queryTypes.get(xp.getObjectType()).getSqlAlias()).append(".id in (select s.parent from Slot s where s.slotName = '");
                        sql.append(xp.getSlotName()).append("' and s.").append(queriedSlotType).append("Value");
                    } else {
                        sql.append("(").append(queryTypes.get(xp.getObjectType()).getSqlAlias()).append(".");
                        sql.append(xp.getObjectAttribute());
                    }

                    sql.append(" like '").append(likeClause).append("')");

                } else {
                    throw new QueryException("ogc:PropertyIsLike/ogc:Literal does not have a content");
                }
            } else {
                throw new QueryException("ogc:PropertyIsLike/ogc:PropertyName does not have a content");
            }
        }
    }

    /**
     * Handles OGC query PropertyIsNull operator.
     * 
     * @param propNull PropertyIsNull operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opPropertyIsNull(PropertyIsNullType propNull) throws QueryException {
        PropertyNameType propName = propNull.getPropertyName();
        String queriedSlotType = "";

        if (!propName.getContent().isEmpty()) {
            String xpath = (String) propName.getContent().get(0);
            XPathObject xp = new XPathObject(xpath);
            xp.process();

            if (xp.isSlotQuery()) {
                queriedSlotType = SlotTypes.getInternalSlotType(xp.getSlotName());
                queriedSlotType = (queriedSlotType == null) ? "string" : queriedSlotType;

                sql.append(queryTypes.get(xp.getObjectType()).getSqlAlias()).append(".id in (select s.parent from Slot s where s.slotName = '");
                sql.append(xp.getSlotName()).append("' and s.").append(queriedSlotType).append("Value");
            } else {
                sql.append("(").append(queryTypes.get(xp.getObjectType()).getSqlAlias()).append(".");
                sql.append(xp.getObjectAttribute());
            }
            sql.append(" is null)");
        } else {
            throw new QueryException("PropertyName does not have a value");
        }
    }

    /**
     * Handles OGC query PropertyIsEqualTo operator.
     * 
     * @param propEqual PropertyIsEqualTo operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opPropertyIsEqualTo(BinaryComparisonOpType propEqual) throws QueryException {
        binaryComparisionQuery(propEqual, EQUAL_SIGN);
    }

    /**
     * Handles OGC query PropertyIsNoyEqualTo operator.
     * 
     * @param propNotEqual PropertyIsNoyEqualTo operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opPropertyIsNotEqualTo(BinaryComparisonOpType propNotEqual) throws QueryException {
        binaryComparisionQuery(propNotEqual, NOT_EQUAL_SIGN);
    }

    /**
     * Handles OGC query PropertyIsGreaterThan operator.
     * 
     * @param propGreater PropertyIsGreaterThan operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opPropertyIsGreaterThan(BinaryComparisonOpType propGreater) throws QueryException {
        binaryComparisionQuery(propGreater, GREATER_SIGN);
    }

    /**
     * Handles OGC query PropertyIsGreaterThanOrEqualTo operator.
     * 
     * @param propGreaterOrEqual PropertyIsGreaterThanOrEqualTo operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opPropertyIsGreaterThanOrEqualTo(BinaryComparisonOpType propGreaterOrEqual) throws QueryException {
        binaryComparisionQuery(propGreaterOrEqual, GREATER_OR_EQUAL_SIGN);
    }

    /**
     * Handles OGC query PropertyIsLessThan operator.
     * 
     * @param propLess PropertyIsLessThan operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opPropertyIsLessThan(BinaryComparisonOpType propLess) throws QueryException {
        binaryComparisionQuery(propLess, LESS_SIGN);
    }

    /**
     * Handles OGC query PropertyIsLessThanOrEqualTo operator.
     * 
     * @param propGreaterOrEqual PropertyIsLessThanOrEqualTo operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opPropertyIsLessThanOrEqualTo(BinaryComparisonOpType propGreaterOrEqual) throws QueryException {
        binaryComparisionQuery(propGreaterOrEqual, LESS_OR_EQUAL_SIGN);
    }

    /**
     * Handles OGC query PropertyIsBetween operator.
     * 
     * @param propBetween PropertyIsBetween operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opPropertyIsBetween(PropertyIsBetweenType propBetween) throws QueryException {
        String queriedSlotType = "";

        if (propBetween.isSetExpression()) {
            Object valObj1 = propBetween.getExpression().getValue();

            if (valObj1 instanceof PropertyNameType) {
                PropertyNameType val1 = (PropertyNameType) valObj1;

                if (!val1.getContent().isEmpty()) {
                    String xpath = (String) val1.getContent().get(0);
                    XPathObject xp = new XPathObject(xpath);
                    xp.process();

                    if (xp.isSlotQuery()) {
                        queriedSlotType = SlotTypes.getInternalSlotType(xp.getSlotName());
                        queriedSlotType = (queriedSlotType == null) ? "string" : queriedSlotType;

                        sql.append(queryTypes.get(xp.getObjectType()).getSqlAlias()).append(".id in (select s.parent from Slot s where s.slotName = '");
                        sql.append(xp.getSlotName()).append("' and s.").append(queriedSlotType).append("Value");
                    } else {
                        sql.append("(").append(queryTypes.get(xp.getObjectType()).getSqlAlias()).append(".");
                        sql.append(xp.getObjectAttribute());
                        queriedSlotType = "string";
                    }
                } else {
                    throw new QueryException("PropertyName does not have a value");
                }
            } else {
                throw new QueryException("Could not find PropertyName element");
            }

            sql.append(" between ");

            if (propBetween.isSetLowerBoundary() && propBetween.getLowerBoundary().isSetExpression() &&
                    propBetween.isSetUpperBoundary() && propBetween.getUpperBoundary().isSetExpression()) {

                Object lowerBoundaryObj = propBetween.getLowerBoundary().getExpression().getValue();
                Object upperBoundaryObj = propBetween.getUpperBoundary().getExpression().getValue();

                if (lowerBoundaryObj instanceof LiteralType) {
                    appendLiteralContent((LiteralType) lowerBoundaryObj, queriedSlotType);
                } else {
                    throw new QueryException("PropertyName not supported for PropertyIsBetween.LowerBoundary");
                }

                sql.append(" and ");

                if (upperBoundaryObj instanceof LiteralType) {
                    appendLiteralContent((LiteralType) upperBoundaryObj, queriedSlotType);
                } else {
                    throw new QueryException("PropertyName not supported for PropertyIsBetween.LowerBoundary");
                }
            }

            sql.append(")");
        }
    }

    /**
     * Generic handler for all OGC query BinaryComparisonOp operators.
     * 
     * @param binCopmparisonOp BinaryComparisonOp operator.
     * @param comparisonOperator Operator sign (E.g =, >, <, <=, >=)
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void binaryComparisionQuery(BinaryComparisonOpType binCopmparisonOp, String comparisonOperator) throws QueryException {
        String queriedSlotType = "";

        if (binCopmparisonOp.getExpression().size() > 1) {
            Object valObj1 = binCopmparisonOp.getExpression().get(0).getValue();
            Object valObj2 = binCopmparisonOp.getExpression().get(1).getValue();

            if (valObj1 instanceof PropertyNameType) {
                PropertyNameType val1 = (PropertyNameType) valObj1;

                if (!val1.getContent().isEmpty()) {
                    String xpath = (String) val1.getContent().get(0);
                    XPathObject xp = new XPathObject(xpath);
                    xp.process();

                    if (xp.isSlotQuery()) {
                        queriedSlotType = SlotTypes.getInternalSlotType(xp.getSlotName());
                        queriedSlotType = (queriedSlotType == null) ? "string" : queriedSlotType;

                        sql.append(queryTypes.get(xp.getObjectType()).getSqlAlias()).append(".id in (select s.parent from Slot s where s.slotName = '");
                        sql.append(xp.getSlotName()).append("' and s.").append(queriedSlotType).append("Value");
                    } else {
                        sql.append("(").append(queryTypes.get(xp.getObjectType()).getSqlAlias()).append(".");
                        sql.append(xp.getObjectAttribute());
                        queriedSlotType = "string";
                    }
                } else {
                    throw new QueryException("ogc:PropertyName does not have a value");
                }
            } else {
                throw new QueryException("First value was not a ogc:PropertyName element");
            }

            sql.append(comparisonOperator);

            if (valObj2 instanceof PropertyNameType) {
                PropertyNameType val2 = (PropertyNameType) valObj2;

                if (!val2.getContent().isEmpty()) {
                    String xpath = (String) val2.getContent().get(0);
                    XPathObject xp = new XPathObject(xpath);
                    xp.process();

                    if (xp.isSlotQuery()) {
                        throw new QueryException("Slot query as second parameter not yet supported");
                    } else {
                        sql.append(queryTypes.get(xp.getObjectType()).getSqlAlias()).append(".");
                        sql.append(xp.getObjectAttribute());
                    }
                }
            } else if (valObj2 instanceof LiteralType) {
                appendLiteralContent((LiteralType) valObj2, queriedSlotType);
            } else {
                throw new QueryException("Second value of PropertyIsEqualTo was not a PropertyName or Literal element");
            }

            sql.append(")");
        }
    }

    /**
     * Handles OGC query BBOX operator.
     * 
     * @param bbox BBOX operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opBBOX(BBOXType bbox) throws QueryException {

        if (bbox.isSetEnvelope()) {
            EnvelopeType env = bbox.getEnvelope().getValue();
            String polygonParamName = getNextParamAlias();

            if (bbox.isSetPropertyName() && !bbox.getPropertyName().getContent().isEmpty()) {
                String xpath = (String) bbox.getPropertyName().getContent().get(0);
                XPathObject xp = new XPathObject(xpath);
                xp.process();

                if (xp.isSlotQuery()) {
                    sql.append(queryTypes.get(xp.getObjectType()).getSqlAlias()).append(".id in (select s.parent from Slot s where s.slotName = '");
                    sql.append(xp.getSlotName()).append("' and within(s.queryGeometryValue, :").append(polygonParamName).append(") = true)");
                } else {
                    throw new QueryException("Can compare BBOX only to Slot values");
                }

            } else {
                throw new QueryException("PropertyName element value missing in BBOX element");
            }

            try {
                Polygon polygon = Transformer.polygonFromEnvelope(env);
                polygon = (Polygon) SrsTools.getInstance().transformGeometry(polygon);
                queryParams.put(polygonParamName, polygon);
            } catch (TransformException ex) {
                throw new QueryException("Could not transform BBOX to Polygon", ex);
            }

        } else {
            throw new QueryException("BBOX does not have an Envelope element");
        }
    }

    /**
     * Handles OGC query Disjoint operator.
     * 
     * @param disjoint Disjoint operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opDisjoint(BinarySpatialOpType disjoint) throws QueryException {
        binarySpatialQuery(disjoint, "disjoint");
    }

    /**
     * Handles OGC query Overlaps operator.
     * 
     * @param value Overlaps operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opOverlaps(BinarySpatialOpType value) throws QueryException {
        binarySpatialQuery(value, "overlaps");
    }

    /**
     * Handles OGC query Intersects operator.
     * 
     * @param value Intersects operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opIntersects(BinarySpatialOpType value) throws QueryException {
        binarySpatialQuery(value, "intersects");
    }

    /**
     * Handles OGC query Touches operator.
     * 
     * @param touches Touches operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opTouches(BinarySpatialOpType touches) throws QueryException {
        binarySpatialQuery(touches, "touches");
    }

    /**
     * Handles OGC query Beyond operator.
     * 
     * @param beyond Beyond operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opBeyond(DistanceBufferType beyond) throws QueryException {
        // how to return a distance value (int or double) if an EO is expected.
        throw new QueryException("Beyond is not supported yet");
    }

    /**
     * Handles OGC query Contains operator.
     * 
     * @param contains Contains operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opContains(BinarySpatialOpType contains) throws QueryException {
        binarySpatialQuery(contains, "contains");
    }

    /**
     * Handles OGC query Crosses operator.
     * 
     * @param crosses Crosses operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opCrosses(BinarySpatialOpType crosses) throws QueryException {
        binarySpatialQuery(crosses, "crosses");
    }

    /**
     * Handles OGC query Dwithin operator.
     * 
     * @param dwithin Dwithin operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opDwithin(DistanceBufferType dwithin) throws QueryException {

        if (dwithin.isSetDistance() && dwithin.getDistance().isSetAny()) {
            String geoParamName = getNextParamAlias();

            if (dwithin.isSetPropertyName() && !dwithin.getPropertyName().getContent().isEmpty()) {
                String xpath = (String) dwithin.getPropertyName().getContent().get(0);
                XPathObject xp = new XPathObject(xpath);
                xp.process();

                if (xp.isSlotQuery()) {
                    sql.append(queryTypes.get(xp.getObjectType()).getSqlAlias()).append(".id in (select s.parent from Slot s where s.slotName = '");
                    sql.append(xp.getSlotName()).append("' and dwithin(s.queryGeometryValue, :").append(geoParamName).append(",").append(dwithin.getDistance().getAny().toString()).append(") = true)");
                } else {
                    throw new QueryException("Can compare BBOX only to Slot values");
                }
            } else {
                throw new QueryException("PropertyName element value missing in Disjoint element");
            }

            try {
                Geometry geometry = Transformer.geometryFromAbstractGeometry(dwithin.getAbstractGeometry().getValue());
                geometry = SrsTools.getInstance().transformGeometry(geometry);
                queryParams.put(geoParamName, geometry);
            } catch (TransformException ex) {
                throw new QueryException("Could not transform geometry", ex);
            }
        } else {
            throw new QueryException("Distance is not set for DWithin");
        }
    }

    /**
     * Handles OGC query Equals operator.
     * 
     * @param equals Equals operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opEquals(BinarySpatialOpType equalz) throws QueryException {
        binarySpatialQuery(equalz, "equals");
    }

    /**
     * Handles OGC query Within operator.
     * 
     * @param within Within operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opWithin(BinarySpatialOpType within) throws QueryException {
        binarySpatialQuery(within, "within");
    }

    /**
     * Appends <code>BinarySpatialOpType</code> query data to <code>sql</code> statement.
     * 
     * @param binSpatialOp BinarySpatialOpType query date to append.
     * @param spatialQueryOperation The operation name of the spatial query.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void binarySpatialQuery(BinarySpatialOpType binSpatialOp, String spatialQueryOperation) throws QueryException {
        String geoParamName = getNextParamAlias();

        if (binSpatialOp.isSetPropertyName() && !binSpatialOp.getPropertyName().getContent().isEmpty()) {
            String xpath = (String) binSpatialOp.getPropertyName().getContent().get(0);
            XPathObject xp = new XPathObject(xpath);
            xp.process();

            if (xp.isSlotQuery()) {
                sql.append(queryTypes.get(xp.getObjectType()).getSqlAlias()).append(".id in (select s.parent from Slot s where s.slotName = '");
                sql.append(xp.getSlotName()).append("' and ").append(spatialQueryOperation).append("(s.queryGeometryValue, :").append(geoParamName).append(") = true)");
            } else {
                throw new QueryException("Can compare BBOX only to Slot values");
            }
        } else {
            throw new QueryException("PropertyName element value missing in Disjoint element");
        }

        if (binSpatialOp.isSetEnvelope()) {
            EnvelopeType env = binSpatialOp.getEnvelope().getValue();

            try {
                Polygon polygon = Transformer.polygonFromEnvelope(env);
                polygon = (Polygon) SrsTools.getInstance().transformGeometry(polygon);
                queryParams.put(geoParamName, polygon);
            } catch (TransformException ex) {
                throw new QueryException("Could not transform Envelope to Polygon", ex);
            }
        } else if (binSpatialOp.isSetAbstractGeometry()) {
            try {
                Geometry geometry = Transformer.geometryFromAbstractGeometry(binSpatialOp.getAbstractGeometry().getValue());
                geometry = SrsTools.getInstance().transformGeometry(geometry);
                queryParams.put(geoParamName, geometry);
            } catch (TransformException ex) {
                throw new QueryException("Could not transform geometry", ex);
            }
        } else {
            throw new QueryException("Disjoint does not have an Envelope element");
        }
    }

    /**
     * Append the <code>LiteralType</code> value to the SQL statement.
     * 
     * @param lit Literal whose value to append.
     * @param queriedSlotType Object type of the slot being queried.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void appendLiteralContent(LiteralType lit, String queriedSlotType) throws QueryException {
        if (!lit.getContent().isEmpty()) {
            if (queriedSlotType.equals("string")) {
                sql.append("'").append(lit.getContent().get(0)).append("'");
            } else if (queriedSlotType.equals("dateTime")) {
                try {
                    String paramKey = getNextParamAlias();
                    XMLGregorianCalendar cal = DatatypeFactory.newInstance().newXMLGregorianCalendar((String) lit.getContent().get(0));
                    sql.append(":" + paramKey);
                    queryParams.put(paramKey, cal.toGregorianCalendar().getTime());
                } catch (DatatypeConfigurationException ex) {
                    throw new QueryException("Invalid date: " + lit.getContent().get(0), ex);
                }
            } else {
                sql.append(lit.getContent().get(0));
            }
        } else {
            throw new QueryException("PropertyName does not have a value");
        }
    }
}
