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
import be.kzen.ergorr.exceptions.QueryException;
import be.kzen.ergorr.commons.RequestContext;
import be.kzen.ergorr.interfaces.soap.csw.ServiceExceptionReport;
import be.kzen.ergorr.model.csw.ElementSetType;
import be.kzen.ergorr.model.csw.GetRecordByIdType;
import be.kzen.ergorr.model.csw.GetRecordsResponseType;
import be.kzen.ergorr.model.csw.GetRecordsType;
import be.kzen.ergorr.model.csw.QueryType;
import be.kzen.ergorr.model.csw.RequestStatusType;
import be.kzen.ergorr.model.csw.SearchResultsType;
import be.kzen.ergorr.model.rim.AdhocQueryType;
import be.kzen.ergorr.model.rim.AssociationType;
import be.kzen.ergorr.model.rim.IdentifiableType;
import be.kzen.ergorr.model.util.OFactory;
import be.kzen.ergorr.persist.service.SqlPersistence;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

/**
 * Manages query requests to the server.
 * 
 * @author Yaman Ustuntas
 */
public class QueryManager {

    private RequestContext requestContext;

    /**
     * Constructor
     *
     * @param requestContext Request context.
     */
    public QueryManager(RequestContext requestContext) {
        this.requestContext = requestContext;
    }

    /**
     * Processes the query.
     *
     * @return Query response.
     * @throws be.kzen.ergorr.interfaces.soap.csw.ServiceExceptionReport
     */
    public GetRecordsResponseType query() throws ServiceExceptionReport {
        GetRecordsResponseType response = new GetRecordsResponseType();

        try {
            GetRecordsType request = (GetRecordsType) requestContext.getRequest();
            
            // if stored query request, process the AdhocQuery
            if (!request.isSetAbstractQuery() && request.isSetAny()) {
                JAXBElement queryEl = (JAXBElement) request.getAny();
                AdhocQueryType adhocParams = (AdhocQueryType) queryEl.getValue();
                StoredQueryBuilder storeQueryBuilder = new StoredQueryBuilder(adhocParams);
                request.setAbstractQuery(storeQueryBuilder.build());
            }
            
            QueryBuilderImpl2 queryBuilder = new QueryBuilderImpl2(request);
            String sql = queryBuilder.build();
            requestContext.putParam(InternalConstants.MAX_RESULTS, queryBuilder.getMaxResults());
            requestContext.putParam(InternalConstants.START_POSITION, queryBuilder.getStartPosition());
            requestContext.putParam(InternalConstants.ELEMENT_SET, queryBuilder.getResultSet());

            SqlPersistence service = new SqlPersistence(requestContext);
            long recordsMatched = service.getResultCount(queryBuilder.createCountQuery(), queryBuilder.getParameters());
            List<JAXBElement<? extends IdentifiableType>> idents =
                    service.query(sql, queryBuilder.getParameters(), queryBuilder.getReturnObject().getObjClass());

            int size = idents.size();
            if (queryBuilder.getResultSet() != null && queryBuilder.getResultSet() == ElementSetType.FULL) {
                List<JAXBElement<? extends IdentifiableType>> relatedIdents =
                        getAssociatedObjects(idents);

                idents.addAll(relatedIdents);
            }

            SearchResultsType searchResult = new SearchResultsType();
            searchResult.setNumberOfRecordsReturned(BigInteger.valueOf(size));
            searchResult.setNumberOfRecordsMatched(BigInteger.valueOf(recordsMatched));
            searchResult.getAny().addAll(idents);
            response.setSearchResults(searchResult);

            RequestStatusType reqStatus = new RequestStatusType();
            response.setSearchStatus(reqStatus);

        } catch (QueryException ex) {
            throw new ServiceExceptionReport("Error while translating OGC query to SQL", null, ex);
        } catch (SQLException ex) {
            throw new ServiceExceptionReport("Error while constructing SQL query", null, ex);
        } catch (JAXBException ex) {
            throw new ServiceExceptionReport("Error while constructing SQL query", null, ex);
        }

        return response;
    }

    /**
     * Process  GetRecordsById filter
     * .
     * @param getRecordsByIdReq Query request.
     * @return Indetifiables with the matched IDs.
     * @throws be.kzen.ergorr.interfaces.soap.csw.ServiceExceptionReport
     */
    public List<JAXBElement<? extends IdentifiableType>> getByIds(GetRecordByIdType getRecordsByIdReq) throws ServiceExceptionReport {
        List<String> ids = getRecordsByIdReq.getId();
        ElementSetType elementSet = null;

        if (getRecordsByIdReq.getElementSetName() != null) {
            elementSet = getRecordsByIdReq.getElementSetName().getValue();
            requestContext.putParam(InternalConstants.ELEMENT_SET, elementSet);
        }

        SqlPersistence service = new SqlPersistence(requestContext);
        
        try {
            List<JAXBElement<? extends IdentifiableType>> idents = service.getByIds(ids);
            
            if (elementSet != null && elementSet == ElementSetType.FULL) {
                List<JAXBElement<? extends IdentifiableType>> relatedIdents =
                        getAssociatedObjects(idents);
                
                idents.addAll(relatedIdents);
            }

            return idents;
        } catch (SQLException ex) {
            throw new ServiceExceptionReport("Error while constructing SQL query", null, ex);
        }
    }

    /**
     * Get the associations and associated objects to the objects in <code>identEls</code>.
     * 
     * @param identEls Identifiables to get association and association objects from.
     * @return List of associations and associated identifiables.
     * @throws be.kzen.ergorr.interfaces.soap.csw.ServiceExceptionReport
     */
    private List<JAXBElement<? extends IdentifiableType>> getAssociatedObjects(List<JAXBElement<? extends IdentifiableType>> identEls) throws ServiceExceptionReport {
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
            throw new ServiceExceptionReport("Could not load associated objects", ex);
        }

        return relatedIdents;
    }
}
