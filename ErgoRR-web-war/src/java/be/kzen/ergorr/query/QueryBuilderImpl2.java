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

import be.kzen.ergorr.commons.InternalConstants;
import be.kzen.ergorr.exceptions.QueryException;
import be.kzen.ergorr.model.csw.ElementSetType;
import be.kzen.ergorr.model.csw.GetRecordsType;
import be.kzen.ergorr.model.csw.QueryType;
import be.kzen.ergorr.model.ogc.BinaryComparisonOpType;
import be.kzen.ergorr.model.ogc.BinaryLogicOpType;
import be.kzen.ergorr.model.ogc.LiteralType;
import be.kzen.ergorr.model.ogc.PropertyNameType;
import be.kzen.ergorr.persist.InternalSlotTypes;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

/**
 *
 * @author yaman
 */
public class QueryBuilderImpl2 implements QueryBuilder {

    private static Logger logger = Logger.getLogger(QueryBuilderImpl2.class.getName());
    private Map<String, QueryObject> queryrObjs;
    private QueryObject returnObj;
    private ElementSetType resultSet;
    private GetRecordsType request;
    private QueryType filterQuery;
    private StringBuilder sqlSelect;
    private StringBuilder sqlJoin;
    private StringBuilder sqlJoinCriteria;
    private StringBuilder sqlCriteria;
    private List<Object> queryParams;
    private int aliasIdx;
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
    private static final String SLOT_ALIAS = "s";
    private static final String NAME_ALIAS = "n";
    private static final String DESC_ALIAS = "d";

    public QueryBuilderImpl2() {
        queryrObjs = new HashMap<String, QueryObject>();
        sqlCriteria = new StringBuilder(256);
        aliasIdx = 0;
        maxResults = 0;
        startPosition = 0;
    }

    /**
     * Init query. To be called before building the query.
     *
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    public void init() throws QueryException {
        if (request.isSetMaxRecords()) {
            maxResults = request.getMaxRecords().intValue();
        }
        if (request.isSetStartPosition()) {
            startPosition = request.getStartPosition().intValue();
        }
        if (filterQuery.getElementSetName().isSetValue()) {
            resultSet = filterQuery.getElementSetName().getValue();
        }

        initQueriedObjects();
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

                    int idx = fullType.indexOf("_");

                    QueryObject qo = null;
                    if (idx > 0) {
                        String[] split = fullType.split("_");
                        qo = new QueryObject(split[0], getNextObjectAlias());
                        queryrObjs.put(split[1], qo);
                    } else {
                        qo = new QueryObject(fullType, getNextObjectAlias());
                        queryrObjs.put(fullType, qo);
                    }

                    if (returnType.equals(fullType)) {
                        qo.setReturnType(true);
                        returnObj = qo;
                    } else {
                        qo.setReturnType(false);
                    }
                } else {
                    throw new QueryException("Invalid QName in Query elements typeNames attribute");
                }
            }

            if (returnObj == null) {
                throw new QueryException("Return type not valid or not specified in the query");
            }
        } else {
            throw new QueryException("No queriable types specified in Query elements typeNames attribute");
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
                throw new QueryException("Invalid QName in ElementSetName elemens typeNames attribute");
            }
        }

        return returnType;
    }

    /**
     * Get a unique queriable object alias.
     *
     * @return Unique alias.
     */
    private String getNextObjectAlias() {
        return OBJECT_ALIAS + aliasIdx++;
    }

    public String build() throws QueryException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getStartPosition() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getMaxResults() {
        throw new UnsupportedOperationException("Not supported yet.");
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
        if (logger.isLoggable(Level.FINE)) {
            logger.fine("queryOperator: " + queryOperator.getClass().toString());
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
        sqlCriteria.append("(");
        for (int i = 0; i < els.size(); i++) {
            JAXBElement<?> el = els.get(i);

            recurseQueryOperator(el);

            if (i + 1 < els.size()) {
                sqlCriteria.append(" and ");
            }
        }
        sqlCriteria.append(")");
    }

    /**
     * Handles OGC query Or operator.
     * @param opOr Or operator.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void opOr(BinaryLogicOpType opOr) throws QueryException {
        List<JAXBElement<?>> els = opOr.getComparisonOpsOrSpatialOpsOrLogicOps();
        sqlCriteria.append("(");
        for (int i = 0; i < els.size(); i++) {
            JAXBElement<?> el = els.get(i);

            recurseQueryOperator(el);

            if (i + 1 < els.size()) {
                sqlCriteria.append(" or ");
            }
        }
        sqlCriteria.append(")");
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
     * Generic handler for all OGC query BinaryComparisonOp operators.
     *
     * @param binCopmparisonOp BinaryComparisonOp operator.
     * @param comparisonOperator Operator sign (E.g =, >, <, <=, >=)
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    private void binaryComparisionQuery(BinaryComparisonOpType binCopmparisonOp, String comparisonOperator) throws QueryException {
        String queriedType = "";

        if (binCopmparisonOp.getExpression().size() > 1) {
            Object valObj1 = binCopmparisonOp.getExpression().get(0).getValue();
            Object valObj2 = binCopmparisonOp.getExpression().get(1).getValue();

            if (valObj1 instanceof PropertyNameType) {
                queriedType = handlePropertyName((PropertyNameType) valObj1);
            } else if (valObj1 instanceof LiteralType) {
                queriedType = InternalConstants.TYPE_STRING;
                handleLiteral((LiteralType) valObj1);
            }

            sqlCriteria.append(comparisonOperator);

            handleLiteralOrPropertyName(valObj2);

            if (valObj2 instanceof PropertyNameType) {
                PropertyNameType val2 = (PropertyNameType) valObj2;

                if (!val2.getContent().isEmpty()) {
                    String xpath = (String) val2.getContent().get(0);
                    XPathObject xp = new XPathObject(xpath);
                    xp.process();

                    if (xp.isSlotQuery()) {
                        throw new QueryException("Slot query as second parameter not yet supported");
                    } else {
                        sqlCriteria.append(queryrObjs.get(xp.getObjectType()).getSqlAlias()).append(".");
                        sqlCriteria.append(xp.getObjectAttribute());
                    }
                }
            } else if (valObj2 instanceof LiteralType) {
                appendLiteralContent((LiteralType) valObj2, queriedType);
            } else {
                throw new QueryException("Second value of PropertyIsEqualTo was not a PropertyName or Literal element");
            }

            sqlCriteria.append(")");
        }
    }

    private void handleLiteralOrPropertyName(Object val) {
        if (val instanceof PropertyNameType) {
            handlePropertyName((PropertyNameType) val);
        } else if (val instanceof LiteralType) {
            handleLiteral((LiteralType) val);
        }
    }

    private void handleLiteral(LiteralType lit) {
    }

    private String handlePropertyName(PropertyNameType propName) {
        return "";
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
            if (queriedSlotType.equals(InternalConstants.TYPE_STRING)) {
                sqlCriteria.append("'").append(lit.getContent().get(0)).append("'");
            } else if (queriedSlotType.equals(InternalConstants.TYPE_DATETIME)) {
                try {
                    XMLGregorianCalendar cal = DatatypeFactory.newInstance().newXMLGregorianCalendar((String) lit.getContent().get(0));
                    sqlCriteria.append("?");
                    Timestamp ts = new Timestamp(cal.normalize().toGregorianCalendar().getTimeInMillis());
                    queryParams.add(ts);
                } catch (DatatypeConfigurationException ex) {
                    throw new QueryException("Invalid date: " + lit.getContent().get(0), ex);
                }
            } else {
                sqlCriteria.append(lit.getContent().get(0));
            }
        } else {
            throw new QueryException("PropertyName does not have a value");
        }
    }
}
