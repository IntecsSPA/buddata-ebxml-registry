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
import be.kzen.ergorr.interfaces.soap.ServiceExceptionReport;
import be.kzen.ergorr.model.csw.GetRecordsResponseType;
import be.kzen.ergorr.model.csw.SearchResultsType;
import be.kzen.ergorr.model.rim.IdentifiableType;
import be.kzen.ergorr.persist.service.SqlPersistence;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;
import javax.xml.bind.JAXBElement;

/**
 * Manages query requests to the server.
 * 
 * @author Yaman Ustuntas
 */
public class QueryManager {

    private RequestContext requestContext;

    public QueryManager(RequestContext requestContext) {
        this.requestContext = requestContext;
    }

    public GetRecordsResponseType query() throws ServiceExceptionReport {
        GetRecordsResponseType response = new GetRecordsResponseType();

        try {
            QueryBuilderImpl queryBuilder = new QueryBuilderImpl(requestContext);
            String sql = queryBuilder.build();
            requestContext.putParam(InternalConstants.MAX_RESULTS, queryBuilder.getMaxResults());
            requestContext.putParam(InternalConstants.START_POSITION, queryBuilder.getStartPosition());

            SqlPersistence service = new SqlPersistence(requestContext);
            long recordsMatched = service.getResultCount(queryBuilder.createCountQuery(), queryBuilder.getParameters());
            List<JAXBElement<? extends IdentifiableType>> idents = 
                    service.query(sql, queryBuilder.getParameters(), queryBuilder.getReturnObject().getObjClass());

            SearchResultsType searchResult = new SearchResultsType();
            searchResult.setNumberOfRecordsReturned(BigInteger.valueOf(idents.size()));
            searchResult.setNumberOfRecordsMatched(BigInteger.valueOf(recordsMatched));
            searchResult.getAny().addAll(idents);
            response.setSearchResults(searchResult);

        } catch (QueryException ex) {
            throw new ServiceExceptionReport("Error while translating OGC query to SQL", null, ex);
        } catch (SQLException ex) {
            throw new ServiceExceptionReport("Error while constructing SQL query", null, ex);
        }

        return response;
    }

    public List<JAXBElement<? extends IdentifiableType>> getByIds(List<String> ids) throws ServiceExceptionReport {
        SqlPersistence service = new SqlPersistence(requestContext);
        try {
            return service.getByIds(ids);
        } catch (SQLException ex) {
            throw new ServiceExceptionReport("Error while constructing SQL query", null, ex);
        }
    }
}
