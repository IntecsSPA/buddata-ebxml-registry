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

import be.kzen.ergorr.exceptions.QueryException;
import be.kzen.ergorr.interfaces.soap.RequestContext;
import be.kzen.ergorr.interfaces.soap.ServiceExceptionReport;
import be.kzen.ergorr.model.csw.GetRecordsResponseType;
import be.kzen.ergorr.model.csw.SearchResultsType;
import be.kzen.ergorr.model.rim.IdentifiableType;
import java.math.BigInteger;
import java.util.List;
import javax.xml.bind.JAXBElement;
import org.apache.log4j.Logger;

/**
 * Manages query requests to the server.
 * 
 * @author Yaman Ustuntas
 */
public class QueryManager {

    private static Logger log = Logger.getLogger(QueryManager.class);
    private RequestContext requestContext;

    public QueryManager(RequestContext requestContext) {
        this.requestContext = requestContext;
    }

    public GetRecordsResponseType query() throws ServiceExceptionReport {
        GetRecordsResponseType response = new GetRecordsResponseType();
        
        try {
            QueryBuilder qb = new QueryBuilder(requestContext);
            String sql = qb.build();
            long recordsMatched = requestContext.getRimDAO().getResultCount(qb.createCountQuery(), qb.getParameters());
            List<JAXBElement<? extends IdentifiableType>> idents = requestContext.getRimDAO().query(sql, qb.getParameters(), qb.getMaxResults(), qb.getStartPosition());
            
            SearchResultsType searchResult = new SearchResultsType();
            searchResult.setNumberOfRecordsReturned(BigInteger.valueOf(idents.size()));
            searchResult.setNumberOfRecordsMatched(BigInteger.valueOf(recordsMatched));
            searchResult.getAny().addAll(idents);
            response.setSearchResults(searchResult);

        } catch (QueryException e) {
            throw new ServiceExceptionReport("Error while translating OGC query to SQL", null, e);
        }

        return response;
    }

    public List<JAXBElement<? extends IdentifiableType>> getByIds(List<String> ids) {
        return requestContext.getRimDAO().getByIds(ids);
    }
}
