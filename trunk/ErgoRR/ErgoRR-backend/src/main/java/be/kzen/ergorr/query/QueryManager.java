/*
 * Project: Buddata ebXML RegRep
 * Class: QueryManager.java
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
import be.kzen.ergorr.commons.RIMConstants;
import be.kzen.ergorr.exceptions.QueryException;
import be.kzen.ergorr.commons.RequestContext;
import be.kzen.ergorr.exceptions.ErrorCodes;
import be.kzen.ergorr.exceptions.ServiceException;
import be.kzen.ergorr.model.csw.ElementSetType;
import be.kzen.ergorr.model.csw.GetRecordByIdType;
import be.kzen.ergorr.model.csw.GetRecordsResponseType;
import be.kzen.ergorr.model.csw.GetRecordsType;
import be.kzen.ergorr.model.csw.QueryType;
import be.kzen.ergorr.model.csw.RequestStatusType;
import be.kzen.ergorr.model.csw.SearchResultsType;
import be.kzen.ergorr.model.query.AdhocQueryRequest;
import be.kzen.ergorr.model.rim.AdhocQueryType;
import be.kzen.ergorr.model.rim.AssociationType;
import be.kzen.ergorr.model.rim.IdentifiableType;
import be.kzen.ergorr.model.rim.QueryExpressionType;
import be.kzen.ergorr.model.util.OFactory;
import be.kzen.ergorr.persist.service.SqlPersistence;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;

/**
 * Manages query requests to the server.
 * 
 * @author Yaman Ustuntas
 */
public class QueryManager {

    private static Logger logger = Logger.getLogger(QueryManager.class.getName());
    private RequestContext requestContext;

    /**
     * Constructor
     *
     * @param requestContext Request context.
     */
    public QueryManager(RequestContext requestContext) {
        this.requestContext = requestContext;
    }

    public GetRecordsResponseType query() throws ServiceException {

        Object requestObj = requestContext.getRequest();
        GetRecordsType getRecords = null;

        if (requestObj instanceof GetRecordsType) {
            getRecords = handleGetRecords((GetRecordsType) requestObj);
        } else if (requestObj instanceof AdhocQueryRequest) {
            getRecords = handleAdhocQuery((AdhocQueryRequest) requestObj);
        } else {
            throw new ServiceException("Unsupported query object: " + requestObj.getClass().getName());
        }

        return query(getRecords);
    }

    private GetRecordsType handleAdhocQuery(AdhocQueryRequest adhocQueryReq) throws ServiceException {
        AdhocQueryType adhocQuery = adhocQueryReq.getAdhocQuery();
        GetRecordsType getRecords = new GetRecordsType();

        if (adhocQuery.isSetQueryExpression()) {
            QueryType ogcQuery = adhocQueryToOgcQuery(adhocQuery.getQueryExpression());
            getRecords.setAbstractQuery(OFactory.csw.createAbstractQuery(ogcQuery));
        } else { // it is a stored query request

            getRecords.setAny(OFactory.rim.createAdhocQuery(adhocQuery));
        }

        getRecords.setMaxRecords(adhocQueryReq.getMaxResults());
        getRecords.setStartPosition(adhocQueryReq.getStartIndex());

        return getRecords;
    }

    private GetRecordsType handleGetRecords(GetRecordsType getRecords) throws ServiceException {
        if (!getRecords.isSetAbstractQuery() && getRecords.isSetAny()) {
            JAXBElement queryEl = (JAXBElement) getRecords.getAny();
            AdhocQueryType adhocParams = (AdhocQueryType) queryEl.getValue();
            StoredQueryBuilder storeQueryBuilder = new StoredQueryBuilder(adhocParams, requestContext);

            try {
                getRecords.setAbstractQuery(storeQueryBuilder.build());
            } catch (Exception ex) {
                logger.log(Level.WARNING, "Error while building stored query", ex);
                throw new ServiceException(ErrorCodes.INTERNAL_ERROR, "Error while building stored query", ex);
            }
        }

        return getRecords;
    }

    private QueryType adhocQueryToOgcQuery(QueryExpressionType queryExpr) throws ServiceException {

        if (queryExpr.getQueryLanguage() == null || !queryExpr.getQueryLanguage().equals(RIMConstants.CN_QUERY_LANG_GML_FILTER)) {
            throw new ServiceException("Query language not supported");
        }

        if (queryExpr.getContent().size() > 0) {
            throw new ServiceException("No ogc:Query provided");
        }

        Object ogcQueryEl = queryExpr.getContent().get(0);

        if (ogcQueryEl instanceof JAXBElement) {
            throw new ServiceException("Query not an instance of ogc:Query");
        }

        Object ogcQueryObj = ((JAXBElement) ogcQueryEl).getValue();
        QueryType ogcQuery = null;

        if (ogcQueryObj instanceof QueryType) {
            ogcQuery = (QueryType) ogcQueryObj;
        } else {
            throw new ServiceException("Query not an instance of ogc:Query");
        }

        return ogcQuery;
    }

    /**
     * Processes the query.
     *
     * @return Query response.
     * @throws be.kzen.ergorr.exceptions.ServiceException
     */
    public GetRecordsResponseType query(GetRecordsType request) throws ServiceException {
        GetRecordsResponseType response = new GetRecordsResponseType();

        QueryBuilderImpl2 queryBuilder = null;
        String sql = null;

        try {
            queryBuilder = new QueryBuilderImpl2(request);
            sql = queryBuilder.build();
        } catch (QueryException ex) {
            throw new ServiceException(ErrorCodes.INVALID_REQUEST, ex.getMessage(), ex);
        }

        requestContext.putParam(InternalConstants.MAX_RESULTS, queryBuilder.getMaxResults());
        requestContext.putParam(InternalConstants.START_POSITION, queryBuilder.getStartPosition());
        requestContext.putParam(InternalConstants.RETURN_SLOTS, queryBuilder.returnSlots());
        requestContext.putParam(InternalConstants.RETURN_NAME_DESC, queryBuilder.returnNameDesc());
        requestContext.putParam(InternalConstants.RETURN_NESTED_OBJECTS, queryBuilder.returnNestedObjects());
        requestContext.putParam(InternalConstants.RETURN_ASSOCIATIONS, queryBuilder.returnAssociations());
        requestContext.putParam(InternalConstants.ORDER_BY, queryBuilder.getSortByStr());

        SqlPersistence service = new SqlPersistence(requestContext);
        long recordsMatched = 0;
        List<JAXBElement<? extends IdentifiableType>> idents = null;

        try {
            recordsMatched = service.getResultCount(queryBuilder.createCountQuery(), queryBuilder.getParameters());
            idents = service.query(sql, queryBuilder.getParameters(), queryBuilder.getReturnObject().getObjClass());
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Could load load objects from database", ex);
            throw new ServiceException(ErrorCodes.INTERNAL_ERROR, "Could load load objects from database", ex);
        }

        int size = idents.size();
        if (queryBuilder.returnAssociations()) {
            List<JAXBElement<AssociationType>> assoEls = getAssociations(idents);
            idents.addAll(assoEls);
        }

        SearchResultsType searchResult = new SearchResultsType();
        searchResult.setNumberOfRecordsReturned(BigInteger.valueOf(size));
        searchResult.setNumberOfRecordsMatched(BigInteger.valueOf(recordsMatched));
        searchResult.getAny().addAll(idents);
        response.setSearchResults(searchResult);

        RequestStatusType reqStatus = new RequestStatusType();
        response.setSearchStatus(reqStatus);


        return response;
    }

    /**
     * Process  GetRecordsById filter
     * .
     * @param getRecordsByIdReq Query request.
     * @return Indetifiables with the matched IDs.
     * @throws be.kzen.ergorr.exceptions.ServiceException
     */
    public List<JAXBElement<? extends IdentifiableType>> getByIds() throws ServiceException {
        GetRecordByIdType getRecordsByIdReq = (GetRecordByIdType) requestContext.getRequest();
        List<String> ids = getRecordsByIdReq.getId();
        ElementSetType resultSet = null;

        if (getRecordsByIdReq.getElementSetName() != null) {
            resultSet = getRecordsByIdReq.getElementSetName().getValue();
        } else {
            resultSet = ElementSetType.SUMMARY;
        }

        requestContext.putParam(InternalConstants.RETURN_SLOTS, resultSet.returnSlots());
        requestContext.putParam(InternalConstants.RETURN_NAME_DESC, resultSet.returnNameDesc());
        requestContext.putParam(InternalConstants.RETURN_NESTED_OBJECTS, resultSet.returnNestedObjects());
        requestContext.putParam(InternalConstants.RETURN_ASSOCIATIONS, resultSet.returnAssociations());

        SqlPersistence service = new SqlPersistence(requestContext);

        try {
            List<JAXBElement<? extends IdentifiableType>> idents = service.getByIds(ids);

            if (resultSet != null && resultSet == ElementSetType.FULL) {
                List<JAXBElement<AssociationType>> assoEls = getAssociations(idents);
                idents.addAll(assoEls);
            }

            return idents;
        } catch (SQLException ex) {
            throw new ServiceException(ErrorCodes.INTERNAL_ERROR, "Error while constructing SQL query", ex);
        }
    }

    /**
     * Get the associations of the objects in <code>identEls</code>.
     *
     * @param identEls Identifiables to get associations of.
     * @return List of associations.
     * @throws be.kzen.ergorr.exceptions.ServiceException
     */
    private List<JAXBElement<AssociationType>> getAssociations(List<JAXBElement<? extends IdentifiableType>> identEls) throws ServiceException {
        List<JAXBElement<AssociationType>> assoEls = new ArrayList<JAXBElement<AssociationType>>();
        // TODO: improve query, load all associations at once by first collecting ident IDs and then using SQL 'in' in one query.
        for (JAXBElement<? extends IdentifiableType> identEl : identEls) {
            IdentifiableType ident = identEl.getValue();
            String sql = "select * from t_association where targetobject='" + ident.getId() + "' or sourceobject='" + ident.getId() + "'";

            SqlPersistence service = new SqlPersistence(requestContext);

            try {
                List<JAXBElement<AssociationType>> assos =
                        service.query(sql, new ArrayList<Object>(), (Class) AssociationType.class);

                assoEls.addAll(assos);
            } catch (SQLException ex) {
                throw new ServiceException(ErrorCodes.INTERNAL_ERROR, "Could not load associations of object with ID: " + ident.getId(), ex);
            }
        }

        return assoEls;
    }

    /**
     * Get the associations and associated objects to the objects in <code>identEls</code>.
     * 
     * @param identEls Identifiables to get association and association objects from.
     * @return List of associations and associated identifiables.
     * @throws be.kzen.ergorr.exceptions.ServiceException
     */
    private List<JAXBElement<? extends IdentifiableType>> getAssociatedObjects(List<JAXBElement<? extends IdentifiableType>> identEls) throws ServiceException {
        List<JAXBElement<? extends IdentifiableType>> relatedIdents = new ArrayList<JAXBElement<? extends IdentifiableType>>();

        try {
            for (JAXBElement<? extends IdentifiableType> identEl : identEls) {
                IdentifiableType ident = identEl.getValue();
                String sql = "select * from t_association where targetobject='" + ident.getId() + "' or sourceobject='" + ident.getId() + "'";

                SqlPersistence service = new SqlPersistence(requestContext);

                List<JAXBElement<AssociationType>> assos =
                        service.query(sql, new ArrayList<Object>(), OFactory.getXmlClassByElementName("Association"));

                List<String> relatedObjIds = new ArrayList<String>();

                for (JAXBElement<AssociationType> assoEl : assos) {
                    relatedIdents.add(assoEl);
                    AssociationType asso = assoEl.getValue();

                    if (asso.getSourceObject().equals(ident.getId())) {
                        relatedObjIds.add(asso.getTargetObject());
                    } else {
                        relatedObjIds.add(asso.getSourceObject());
                    }
                }

                List<JAXBElement<? extends IdentifiableType>> relatedObjs = service.getByIds(relatedObjIds);
                relatedIdents.addAll(relatedObjs);
            }
        } catch (Exception ex) {
            throw new ServiceException(ErrorCodes.INTERNAL_ERROR, "Could not load associated objects", ex);
        }

        return relatedIdents;
    }
}
