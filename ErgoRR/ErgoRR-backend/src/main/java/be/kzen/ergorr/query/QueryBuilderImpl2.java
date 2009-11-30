/*
 * Project: Buddata ebXML RegRep
 * Class: QueryBuilderImpl.java
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
import be.kzen.ergorr.commons.DateUtil;
import be.kzen.ergorr.commons.InternalConstants;
import be.kzen.ergorr.exceptions.QueryException;
import be.kzen.ergorr.exceptions.TransformException;
import be.kzen.ergorr.geometry.GeometryTranslator;
import be.kzen.ergorr.model.csw.ElementSetType;
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
import be.kzen.ergorr.model.ogc.SortByType;
import be.kzen.ergorr.model.ogc.SortOrderType;
import be.kzen.ergorr.model.ogc.SortPropertyType;
import be.kzen.ergorr.persist.SyntaxElements;
import be.kzen.ergorr.query.xpath.XPathToSqlConverter;
import be.kzen.ergorr.query.xpath.parser.XPathNode;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.xpath.XPathException;

/**
 * TODO: Prevent SQL injection
 *
 * @author yaman
 */
public class QueryBuilderImpl2 implements QueryBuilder {

    private static Logger logger = Logger.getLogger(QueryBuilderImpl2.class.getName());
    private SqlQuery sqlQuery;
    private GetRecordsType request;
    private QueryType filterQuery;
    private List<Object> queryParams;
    private ElementSetType resultSet;

    /**
     * Constructor
     *
     * @param request GetRecords filter to process.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    public QueryBuilderImpl2(GetRecordsType request) throws QueryException {
        this.request = request;
        sqlQuery = new SqlQuery();
        filterQuery = (QueryType) request.getAbstractQuery().getValue();
        queryParams = new ArrayList<Object>();
        init();
    }

    /**
     * {@inheritDoc}
     */
    public List<Object> getParameters() {
        return queryParams;
    }

    /**
     * {@inheritDoc}
     */
    public QueryObject getReturnObject() {
        return sqlQuery.getReturnType();
    }

    /**
     * {@inheritDoc}
     */
    public ElementSetType getResultSet() {
        return resultSet;
    }

    /**
     * {@inheritDoc}
     */
    public String createCountQuery() {
        return sqlQuery.buildCountQuery();
    }

    /**
     * Init query. To be called before building the query.
     *
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    public void init() throws QueryException {
        if (request.isSetMaxRecords()) {
            sqlQuery.setMaxResults(request.getMaxRecords().intValue());
        } else {
            request.setMaxRecords(BigInteger.ZERO);
        }
        if (request.isSetStartPosition()) {
            // start position for OGC Filter is 1, for PostgreSQL is 0
            int startPos = request.getStartPosition().intValue() - 1;
            sqlQuery.setStartPosition(startPos);
        }

        initReturnElements();
        initQueriedObjects();
        initSortBy();
    }

    private void initReturnElements() {
        if (filterQuery.getElementSetName().isSetValue()) {
            resultSet = filterQuery.getElementSetName().getValue();
        } else {
            resultSet = ElementSetType.SUMMARY;
        }
    }

    /**
     * Init queried objects.
     * 
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void initQueriedObjects() throws QueryException {
        String returnType = getReturnType();
        List<QName> qnames = filterQuery.getTypeNames();


        if (!qnames.isEmpty()) {
            for (QName qname : qnames) {
                if (qname != null) {
                    String fullType = qname.getLocalPart();
                    QueryObject qo = sqlQuery.addNewQueryObject(fullType);

                    if (returnType.equals(fullType)) {
                        sqlQuery.setReturnType(qo);
                    }
                } else {
                    String err = "Invalid QName in Query csw:Query/csw:ElementSetName/@typeNames attribute";

                    if (logger.isLoggable(Level.INFO)) {
                        logger.info(err);
                    }
                    throw new QueryException(err);
                }
            }

            if (sqlQuery.getReturnType() == null) {
                String err = "Return type csw:Query/csw:ElementSetName/@typeNames not valid or not specified in the query";

                if (logger.isLoggable(Level.INFO)) {
                    logger.info(err);
                }
                throw new QueryException(err);
            }
        } else {
            String err = "No queriable types specified in Query csw:Query/csw:ElementSetName/@typeNames attribute";

            if (logger.isLoggable(Level.INFO)) {
                logger.info(err);
            }
            throw new QueryException(err);
        }
    }

    /**
     * Initialize the SortBy of the SQL query from the OGC filter.
     * 
     * @throws QueryException
     */
    private void initSortBy() throws QueryException {
        SortByType sortBy = filterQuery.getSortBy();
        logger.info("SortBy init");
        if (sortBy != null && !sortBy.getSortProperty().isEmpty()) {
            logger.info("SortBy exists");
            SortPropertyType sortProp = sortBy.getSortProperty().get(0);
            PropertyNameType propName = sortProp.getPropertyName();

            if (propName != null && !propName.getContent().isEmpty()) {
                String propXPath = (String) propName.getContent().get(0);
                logger.info("Sort property exists: " + propXPath);
                XPathToSqlConverter xpathConv = new XPathToSqlConverter(sqlQuery, propXPath);
                XPathNode xpath = null;

                try {
                    xpath = xpathConv.process();
                } catch (XPathException ex) {
                    throw new QueryException(ex);
                }

                String sortByStr = xpath.getQueryObject().getSqlAlias() + SyntaxElements.DOT + xpath.getAttributeName() + SyntaxElements.SPACE;
                SortOrderType order = (sortProp.getSortOrder() != null) ? sortProp.getSortOrder() : SortOrderType.ASC;
                sortByStr += order.value();
                sqlQuery.setSortBy(sortByStr);
            }
        }

    }

    /**
     * Get from the request the object type to return.
     * Return format is as is specified in the EO spec.
     *
     * @return Return object type of the query.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private String getReturnType() throws QueryException {
        if (filterQuery.getElementSetName().getTypeNames().size() > 1) {
            throw new QueryException("Only one return object supported");
        }

        String returnType = "";
        for (QName qname : filterQuery.getElementSetName().getTypeNames()) {
            if (qname != null) {
                returnType = qname.getLocalPart();
            } else {
                String err = "Invalid QName in ElementSetName elemens typeNames attribute";

                if (logger.isLoggable(Level.INFO)) {
                    logger.info(err);
                }
                throw new QueryException(err);
            }
        }

        return returnType;
    }

    /**
     * {@inheritDoc}
     */
    public String build() throws QueryException {
        FilterType filter = filterQuery.getConstraint().getFilter();
        JAXBElement queryOperator = null;

        if (filter.isSetLogicOps()) {
            queryOperator = filter.getLogicOps();
        } else if (filter.isSetComparisonOps()) {
            queryOperator = filter.getComparisonOps();
        } else if (filter.isSetSpatialOps()) {
            queryOperator = filter.getSpatialOps();
        }

        if (queryOperator != null) {
            try {
                recurseQueryOperator(queryOperator);
            } catch (XPathException ex) {
                throw new QueryException(ex);
            }
        }

        return sqlQuery.buildQuery();
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
    private void recurseQueryOperator(JAXBElement queryOperator) throws QueryException, XPathException {
        if (logger.isLoggable(Level.FINE)) {
            logger.fine("queryOperator: " + queryOperator.getClass().toString());
        }

        String opName = "op" + queryOperator.getClass().getSimpleName();

        try {
            Method method = this.getClass().getDeclaredMethod(opName, new Class[]{queryOperator.getValue().getClass()});
            method.invoke(this, new Object[]{queryOperator.getValue()});
        } catch (IllegalArgumentException ex) {
            logger.log(Level.SEVERE, "", ex);
            throw new QueryException("Illegal argument invoking " + opName, ex);
        } catch (InvocationTargetException ex) {
            throw new QueryException(ex.getCause().getMessage(), ex);
        } catch (NoSuchMethodException ex) {
            logger.log(Level.SEVERE, "", ex);
            throw new QueryException("Method not found: " + opName, ex);
        } catch (IllegalAccessException ex) {
            logger.log(Level.SEVERE, "", ex);
            throw new QueryException("Illegal access: " + opName, ex);
        }
    }

    /**
     * Handles OGC query And operator.
     *
     * @param opAnd And operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opAnd(BinaryLogicOpType opAnd) throws QueryException, XPathException {
        List<JAXBElement<?>> els = opAnd.getComparisonOpsOrSpatialOpsOrLogicOps();
        sqlQuery.append(SyntaxElements.OPEN_BR);
        for (int i = 0; i < els.size(); i++) {
            JAXBElement<?> el = els.get(i);

            recurseQueryOperator(el);

            if (i + 1 < els.size()) {
                sqlQuery.append(SyntaxElements.AND);
            }
        }
        sqlQuery.append(SyntaxElements.CLOSE_BR);
    }

    /**
     * Handles OGC query Or operator.
     * @param opOr Or operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opOr(BinaryLogicOpType opOr) throws QueryException, XPathException {
        List<JAXBElement<?>> els = opOr.getComparisonOpsOrSpatialOpsOrLogicOps();
        sqlQuery.append(SyntaxElements.OPEN_BR);
        for (int i = 0; i < els.size(); i++) {
            JAXBElement<?> el = els.get(i);

            recurseQueryOperator(el);

            if (i + 1 < els.size()) {
                sqlQuery.append(SyntaxElements.OR);
            }
        }
        sqlQuery.append(SyntaxElements.CLOSE_BR);
    }

    /**
     * Handles OGC query PropertyIsEqualTo operator.
     *
     * @param propEqual PropertyIsEqualTo operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opPropertyIsEqualTo(BinaryComparisonOpType propEqual) throws QueryException {
        binaryComparisionQuery(propEqual, SyntaxElements.EQUAL_SIGN);
    }

    /**
     * Handles OGC query PropertyIsNoyEqualTo operator.
     *
     * @param propNotEqual PropertyIsNoyEqualTo operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opPropertyIsNotEqualTo(BinaryComparisonOpType propNotEqual) throws QueryException {
        binaryComparisionQuery(propNotEqual, SyntaxElements.NOT_EQUAL_SIGN);
    }

    /**
     * Handles OGC query PropertyIsGreaterThan operator.
     *
     * @param propGreater PropertyIsGreaterThan operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opPropertyIsGreaterThan(BinaryComparisonOpType propGreater) throws QueryException {
        binaryComparisionQuery(propGreater, SyntaxElements.GREATER_SIGN);
    }

    /**
     * Handles OGC query PropertyIsGreaterThanOrEqualTo operator.
     *
     * @param propGreaterOrEqual PropertyIsGreaterThanOrEqualTo operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opPropertyIsGreaterThanOrEqualTo(BinaryComparisonOpType propGreaterOrEqual) throws QueryException {
        binaryComparisionQuery(propGreaterOrEqual, SyntaxElements.GREATER_OR_EQUAL_SIGN);
    }

    /**
     * Handles OGC query PropertyIsLessThan operator.
     *
     * @param propLess PropertyIsLessThan operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opPropertyIsLessThan(BinaryComparisonOpType propLess) throws QueryException {
        binaryComparisionQuery(propLess, SyntaxElements.LESS_SIGN);
    }

    /**
     * Handles OGC query PropertyIsLessThanOrEqualTo operator.
     *
     * @param propGreaterOrEqual PropertyIsLessThanOrEqualTo operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opPropertyIsLessThanOrEqualTo(BinaryComparisonOpType propGreaterOrEqual) throws QueryException {
        binaryComparisionQuery(propGreaterOrEqual, SyntaxElements.LESS_OR_EQUAL_SIGN);
    }

    /**
     * Generic handler for all OGC query BinaryComparisonOp operators.
     *
     * @param binCopmparisonOp BinaryComparisonOp operator.
     * @param comparisonOperator Operator sign (E.g =, >, <, <=, >=)
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void binaryComparisionQuery(BinaryComparisonOpType binCopmparisonOp, String comparisonOperator) throws QueryException {

        if (binCopmparisonOp.getExpression().size() > 1) {
            Object valObj1 = binCopmparisonOp.getExpression().get(0).getValue();
            Object valObj2 = binCopmparisonOp.getExpression().get(1).getValue();
            XPathNode xpath1 = null;

            if (!(valObj1 instanceof PropertyNameType)) {
                String err = "First element of operator " + comparisonOperator + " must be a ogc:PropertyName";

                if (logger.isLoggable(Level.INFO)) {
                    logger.info(err);
                }

                throw new QueryException(err);
            }

            PropertyNameType prop1 = (PropertyNameType) valObj1;

            if (!prop1.getContent().isEmpty()) {
                String prop1XPath = (String) prop1.getContent().get(0);
                XPathToSqlConverter xpathConv = new XPathToSqlConverter(sqlQuery, prop1XPath);
                try {
                    xpath1 = xpathConv.process();
                } catch (XPathException ex) {
                    throw new QueryException(ex);
                }

                sqlQuery.append(xpath1.getQueryObject().getSqlAlias()).append(SyntaxElements.DOT).append(SqlQuery.getColumnName(xpath1.getAttributeName())).append(SyntaxElements.SPACE).append(comparisonOperator);

                if (valObj2 instanceof PropertyNameType) {
                    PropertyNameType prop2 = (PropertyNameType) valObj2;
                    String prop2XPath = (String) prop2.getContent().get(0);
                    xpathConv = new XPathToSqlConverter(sqlQuery, prop2XPath);

                    XPathNode xpath2 = null;

                    try {
                        xpath2 = xpathConv.process();
                    } catch (XPathException ex) {
                        throw new QueryException(ex);
                    }

                    sqlQuery.append(xpath2.getQueryObject().getSqlAlias()).append(SyntaxElements.DOT).append(xpath2.getAttributeName());
                } else {
                    LiteralType lit = (LiteralType) valObj2;
                    appendLiteralContent(lit, xpath1.getQueryAttrType());
                }

            } else {
                String err = "Invalid (empty) value for PropertyName element";

                if (logger.isLoggable(Level.INFO)) {
                    logger.info(err);
                }
                throw new QueryException(err);
            }
        }
    }

    /**
     * Handles OGC query PropertyIsLike operator.
     *
     * @param propLike PropertyIsLike operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opPropertyIsLike(PropertyIsLikeType propLike) throws QueryException, XPathException {
        if (propLike.getLiteral().getContent().size() > 0) {
            PropertyNameType propName = propLike.getPropertyName();

            if (!propName.getContent().isEmpty()) {
                String xpath = (String) propName.getContent().get(0);

                if (propLike.isSetLiteral() && !propLike.getLiteral().getContent().isEmpty()) {
                    String likeClause = (String) propLike.getLiteral().getContent().get(0);

                    likeClause = SyntaxElements.replaceLike(likeClause, propLike.getSingleChar(), propLike.getWildCard(), propLike.getEscapeChar());

                    XPathToSqlConverter xpathConv = new XPathToSqlConverter(sqlQuery, xpath);
                    XPathNode xpNode = xpathConv.process();

                    sqlQuery.append("LOWER(");
                    sqlQuery.append(xpNode.getQueryObject().getSqlAlias()).append(SyntaxElements.DOT).append(xpNode.getAttributeName());
                    sqlQuery.append(SyntaxElements.CLOSE_BR);
                    sqlQuery.append(SyntaxElements.LIKE);
                    sqlQuery.append("LOWER(");
                    sqlQuery.append(SyntaxElements.SINGLE_QUOTE).append(likeClause).append(SyntaxElements.SINGLE_QUOTE);
                    sqlQuery.append(SyntaxElements.CLOSE_BR);


                } else {
                    String err = "ogc:PropertyIsLike/ogc:Literal does not have a content";

                    if (logger.isLoggable(Level.INFO)) {
                        logger.info(err);
                    }
                    throw new QueryException(err);
                }
            } else {
                String err = "ogc:PropertyIsLike/ogc:PropertyName does not have a content";

                if (logger.isLoggable(Level.INFO)) {
                    logger.info(err);
                }
                throw new QueryException(err);
            }
        }
    }

    /**
     * Handles OGC query PropertyIsNull operator.
     *
     * @param propNull PropertyIsNull operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opPropertyIsNull(PropertyIsNullType propNull) throws QueryException, XPathException {
        PropertyNameType propName = propNull.getPropertyName();

        if (!propName.getContent().isEmpty()) {
            String xpath = (String) propName.getContent().get(0);
            XPathToSqlConverter xpathConv = new XPathToSqlConverter(sqlQuery, xpath);
            XPathNode xpNode = xpathConv.process();

            sqlQuery.append(xpNode.getQueryObject().getSqlAlias()).append(SyntaxElements.DOT).append(xpNode.getAttributeName());
            sqlQuery.append(SyntaxElements.IS_NULL);
        } else {
            String err = "PropertyName does not have a value";

            if (logger.isLoggable(Level.INFO)) {
                logger.info(err);
            }
            throw new QueryException(err);
        }
    }

    /**
     * Handles OGC query PropertyIsBetween operator.
     *
     * @param propBetween PropertyIsBetween operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opPropertyIsBetween(PropertyIsBetweenType propBetween) throws QueryException, XPathException {
        String internalSlotType = "";

        if (propBetween.isSetExpression()) {
            Object valObj1 = propBetween.getExpression().getValue();

            if (valObj1 instanceof PropertyNameType) {
                PropertyNameType val1 = (PropertyNameType) valObj1;

                if (!val1.getContent().isEmpty()) {
                    String xpath = (String) val1.getContent().get(0);
                    XPathToSqlConverter xpathConv = new XPathToSqlConverter(sqlQuery, xpath);
                    XPathNode xpNode = xpathConv.process();

                    sqlQuery.append(xpNode.getQueryObject().getSqlAlias()).append(SyntaxElements.DOT).append(SqlQuery.getColumnName(xpNode.getAttributeName()));
                    internalSlotType = xpNode.getQueryAttrType();
                } else {
                    String err = "PropertyName does not have a value";

                    if (logger.isLoggable(Level.INFO)) {
                        logger.info(err);
                    }
                    throw new QueryException(err);
                }
            } else {
                String err = "Could not find PropertyName element";

                if (logger.isLoggable(Level.INFO)) {
                    logger.info(err);
                }
                throw new QueryException(err);
            }

            sqlQuery.append(SyntaxElements.BETWEEN);

            if (propBetween.isSetLowerBoundary() && propBetween.getLowerBoundary().isSetExpression() &&
                    propBetween.isSetUpperBoundary() && propBetween.getUpperBoundary().isSetExpression()) {

                Object lowerBoundaryObj = propBetween.getLowerBoundary().getExpression().getValue();
                Object upperBoundaryObj = propBetween.getUpperBoundary().getExpression().getValue();

                if (lowerBoundaryObj instanceof LiteralType) {
                    appendLiteralContent((LiteralType) lowerBoundaryObj, internalSlotType);
                } else {
                    String err = "PropertyName not supported for PropertyIsBetween.LowerBoundary";

                    if (logger.isLoggable(Level.INFO)) {
                        logger.info(err);
                    }
                    throw new QueryException(err);
                }

                sqlQuery.append(SyntaxElements.AND);

                if (upperBoundaryObj instanceof LiteralType) {
                    appendLiteralContent((LiteralType) upperBoundaryObj, internalSlotType);
                } else {
                    String err = "PropertyName not supported for PropertyIsBetween.LowerBoundary";

                    if (logger.isLoggable(Level.INFO)) {
                        logger.info(err);
                    }
                    throw new QueryException(err);
                }
            }
        }
    }

    /**
     * Handles OGC query BBOX operator.
     *
     * @param bbox BBOX operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opBBOX(BBOXType bbox) throws QueryException, XPathException {

        if (bbox.isSetEnvelope()) {
            EnvelopeType env = bbox.getEnvelope();

            if (bbox.isSetPropertyName() && !bbox.getPropertyName().getContent().isEmpty()) {
                String xpath = (String) bbox.getPropertyName().getContent().get(0);
                XPathToSqlConverter xpathConv = new XPathToSqlConverter(sqlQuery, xpath);
                XPathNode xpNode = xpathConv.process();

                if (xpNode.getQueryObject().getTableName().equals("t_slot")) {
                    sqlQuery.append("st_intersects(").append(xpNode.getQueryObject().getSqlAlias()).append(".geometryvalue, transform(geomfromwkb(?),");
                    sqlQuery.append(CommonProperties.getInstance().get("db.defaultSrsId")).append(")) = true");
                } else {
                    String err = "XPath not pointing to a geometry value: " + xpath;

                    if (logger.isLoggable(Level.INFO)) {
                        logger.info(err);
                    }
                    throw new QueryException(err);
                }
            } else {
                String err = "PropertyName element value missing in BBOX element";

                if (logger.isLoggable(Level.INFO)) {
                    logger.info(err);
                }
                throw new QueryException(err);
            }

            try {
                byte[] geom = GeometryTranslator.wkbFromGmlEnvelope(env);
                queryParams.add(geom);
            } catch (TransformException ex) {
                String err = "Could not transform BBOX to Polygon";

                if (logger.isLoggable(Level.INFO)) {
                    logger.log(Level.INFO, err, ex);
                }
                throw new QueryException(err, ex);
            }

        } else {
            String err = "BBOX does not have an Envelope element";

            if (logger.isLoggable(Level.INFO)) {
                logger.info(err);
            }
            throw new QueryException(err);
        }
    }

    /**
     * Handles OGC query Disjoint operator.
     *
     * @param disjoint Disjoint operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opDisjoint(BinarySpatialOpType disjoint) throws QueryException, XPathException {
        binarySpatialQuery(disjoint, "disjoint");
    }

    /**
     * Handles OGC query Overlaps operator.
     *
     * @param value Overlaps operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opOverlaps(BinarySpatialOpType value) throws QueryException, XPathException {
        binarySpatialQuery(value, "overlaps");
    }

    /**
     * Handles OGC query Intersects operator.
     *
     * @param value Intersects operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opIntersects(BinarySpatialOpType value) throws QueryException, XPathException {
        binarySpatialQuery(value, "intersects");
    }

    /**
     * Handles OGC query Touches operator.
     *
     * @param touches Touches operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opTouches(BinarySpatialOpType touches) throws QueryException, XPathException {
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
    private void opContains(BinarySpatialOpType contains) throws QueryException, XPathException {
        binarySpatialQuery(contains, "contains");
    }

    /**
     * Handles OGC query Crosses operator.
     *
     * @param crosses Crosses operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opCrosses(BinarySpatialOpType crosses) throws QueryException, XPathException {
        binarySpatialQuery(crosses, "crosses");
    }

    /**
     * Handles OGC query Equals operator.
     *
     * @param equals Equals operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opEquals(BinarySpatialOpType equalz) throws QueryException, XPathException {
        binarySpatialQuery(equalz, "equals");
    }

    /**
     * Handles OGC query Within operator.
     *
     * @param within Within operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opWithin(BinarySpatialOpType within) throws QueryException, XPathException {
        binarySpatialQuery(within, "within");
    }

    /**
     * Handles OGC query Dwithin operator.
     *
     * @param dwithin Dwithin operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opDWithin(DistanceBufferType dwithin) throws QueryException, XPathException {

        if (dwithin.isSetDistance() && dwithin.getDistance().isSetContent()) {

            if (dwithin.isSetPropertyName() && !dwithin.getPropertyName().getContent().isEmpty()) {
                String xpath = (String) dwithin.getPropertyName().getContent().get(0);

                XPathToSqlConverter xpathConv = new XPathToSqlConverter(sqlQuery, xpath);
                XPathNode xpNode = xpathConv.process();

                if (xpNode.getQueryObject().getTableName().equals("t_slot")) {
                    sqlQuery.append("st_within(").append(xpNode.getQueryObject().getSqlAlias()).append(".geometryvalue, transform(geomfromwkb(?),");
                    sqlQuery.append(CommonProperties.getInstance().get("db.defaultSrsId")).append(")) = true");
                } else {
                    String err = "XPath not pointing to a geometry value: " + xpath;

                    if (logger.isLoggable(Level.INFO)) {
                        logger.info(err);
                    }
                    throw new QueryException(err);
                }
            } else {
                String err = "PropertyName element value missing in Disjoint element";

                if (logger.isLoggable(Level.INFO)) {
                    logger.info(err);
                }
                throw new QueryException(err);
            }

            try {
                byte[] geom = GeometryTranslator.wkbFromGmlGeometry(dwithin.getGeometry().getValue());
                queryParams.add(geom);
            } catch (TransformException ex) {
                String err = "Could not transform geometry";

                if (logger.isLoggable(Level.INFO)) {
                    logger.log(Level.INFO, err, ex);
                }
                throw new QueryException(err, ex);
            }
        } else {
            String err = "Distance is not set for DWithin";

            if (logger.isLoggable(Level.INFO)) {
                logger.info(err);
            }
            throw new QueryException(err);
        }
    }

    /**
     * Appends <code>BinarySpatialOpType</code> query data to <code>sql</code> statement.
     *
     * @param binSpatialOp BinarySpatialOpType query date to append.
     * @param spatialQueryOperation The operation name of the spatial query.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void binarySpatialQuery(BinarySpatialOpType binSpatialOp, String spatialQueryOperation) throws QueryException, XPathException {

        if (binSpatialOp.isSetPropertyName() && !binSpatialOp.getPropertyName().getContent().isEmpty()) {
            String xpath = (String) binSpatialOp.getPropertyName().getContent().get(0);
            XPathToSqlConverter xpathConv = new XPathToSqlConverter(sqlQuery, xpath);
            XPathNode xpNode = xpathConv.process();

            if (xpNode.getQueryObject().getTableName().equals("t_slot")) {
                sqlQuery.append("st_").append(spatialQueryOperation).append("(").append(xpNode.getQueryObject().getSqlAlias()).append(".geometryvalue, transform(geomfromwkb(?),").append(CommonProperties.getInstance().get("db.defaultSrsId")).append(")) = true");
            } else {
                String err = "XPath not pointing to a geometry value: " + xpath;

                if (logger.isLoggable(Level.INFO)) {
                    logger.info(err);
                }
                throw new QueryException(err);
            }
        } else {
            String err = "PropertyName element value missing in" + spatialQueryOperation + " element";

            if (logger.isLoggable(Level.INFO)) {
                logger.info(err);
            }
            throw new QueryException(err);
        }

        if (binSpatialOp.isSetEnvelope()) {
            EnvelopeType env = binSpatialOp.getEnvelope();

            try {
                byte[] geom = GeometryTranslator.wkbFromGmlEnvelope(env);
                queryParams.add(geom);
            } catch (TransformException ex) {
                String err = "Could not transform Envelope to Polygon";

                if (logger.isLoggable(Level.INFO)) {
                    logger.log(Level.INFO, err, ex);
                }
                throw new QueryException(err, ex);
            }
        } else if (binSpatialOp.isSetGeometry()) {
            try {
                byte[] geom = GeometryTranslator.wkbFromGmlGeometry(binSpatialOp.getGeometry().getValue());
                queryParams.add(geom);
            } catch (TransformException ex) {
                String err = "Could not transform geometry";

                if (logger.isLoggable(Level.INFO)) {
                    logger.log(Level.INFO, err, ex);
                }
                throw new QueryException(err, ex);
            }
        } else {
            String err = "Does not have a geometry constraint";

            if (logger.isLoggable(Level.INFO)) {
                logger.info(err);
            }
            throw new QueryException(err);
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
            String value = lit.getContent().get(0).toString().trim();

            if (logger.isLoggable(Level.FINE)) {
                logger.log(Level.FINE, "adding literal content: " + value);
            }
            if (queriedSlotType.equals(InternalConstants.TYPE_STRING)) {
                sqlQuery.append(SyntaxElements.SINGLE_QUOTE).append(value).append(SyntaxElements.SINGLE_QUOTE);
            } else if (queriedSlotType.equals(InternalConstants.TYPE_DATETIME)) {
                DateUtil.getXMLCalendar(value); // validate that it is XSD datetime
                sqlQuery.append(SyntaxElements.SINGLE_QUOTE).append(value).append(SyntaxElements.SINGLE_QUOTE);
            } else {
                sqlQuery.append(value);
            }
        } else {
            String err = "Literal element does not have a value";

            if (logger.isLoggable(Level.INFO)) {
                logger.info(err);
            }
            throw new QueryException(err);
        }
    }
}
