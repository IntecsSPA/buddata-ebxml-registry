/*
 * Project: Buddata ebXML RegRep
 * Class: RimQueryServiceImpl.java
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
package be.kzen.ergorr.interfaces.soap;

import be.kzen.ergorr.commons.RIMConstants;
import be.kzen.ergorr.commons.RequestContext;
import be.kzen.ergorr.exceptions.ServiceException;
import be.kzen.ergorr.interfaces.soap.rim.QueryManagerPortType;
import be.kzen.ergorr.model.csw.GetRecordsType;
import be.kzen.ergorr.model.csw.QueryType;
import be.kzen.ergorr.model.csw.SearchResultsType;
import be.kzen.ergorr.model.query.AdhocQueryRequest;
import be.kzen.ergorr.model.query.AdhocQueryResponse;
import be.kzen.ergorr.model.rim.AdhocQueryType;
import be.kzen.ergorr.model.rim.IdentifiableType;
import be.kzen.ergorr.model.rim.QueryExpressionType;
import be.kzen.ergorr.model.rim.RegistryObjectListType;
import be.kzen.ergorr.model.util.OFactory;
import be.kzen.ergorr.query.QueryManager;
import java.util.List;
import javax.jws.WebService;
import javax.xml.bind.JAXBElement;
import javax.xml.ws.WebServiceException;

/**
 * RIM Query ManagerSOAP interface implemetation.
 * 
 * @author yamanustuntas
 */
@WebService(serviceName = "ebXMLRegistrySOAPService", portName = "QueryManagerPort",
targetNamespace = "http://www.kzen.be/ergorr/interfaces/soap/rim",
endpointInterface = "be.kzen.ergorr.interfaces.soap.rim.QueryManagerPortType")
public class RimQueryServiceImpl implements QueryManagerPortType {

    /**
     * Process adhoc query requests.
     *
     * @param adhocQueryReq Request.
     * @return Adhoc query response.
     */
    public AdhocQueryResponse submitAdhocQuery(AdhocQueryRequest adhocQueryReq) {
        AdhocQueryType adhocQuery = adhocQueryReq.getAdhocQuery();

        if (!adhocQuery.isSetQueryExpression()) {
            throw new WebServiceException("QueryExpression not set");
        }
        
        QueryExpressionType queryExpr = adhocQuery.getQueryExpression();

        if (queryExpr.getQueryLanguage() == null || !queryExpr.getQueryLanguage().equals(RIMConstants.CN_QUERY_LANG_GML_FILTER)) {
            throw new WebServiceException("Query language not supported");
        }

        QueryType ogcQuery = null;

        if (queryExpr.getContent().size() > 0) {
            Object ogcQueryEl = queryExpr.getContent().get(0);

            if (ogcQueryEl instanceof JAXBElement) {
                Object ogcQueryObj = ((JAXBElement) ogcQueryEl).getValue();

                if (ogcQueryObj instanceof QueryType) {
                    ogcQuery = (QueryType) ogcQueryObj;
                } else {
                    throw new WebServiceException("Query not an instance of ogc:Query");
                }
            } else {
                throw new WebServiceException("Query not an instance of ogc:Query");
            }
        } else {
            throw new WebServiceException("No ogc:Query provided");
        }


        GetRecordsType getRecords = new GetRecordsType();
        getRecords.setMaxRecords(adhocQueryReq.getMaxResults());
        getRecords.setStartPosition(adhocQueryReq.getStartIndex());
        getRecords.setAbstractQuery(OFactory.csw.createAbstractQuery(ogcQuery));

        RequestContext requestContext = new RequestContext();
        requestContext.setRequest(getRecords);

        QueryManager qm = new QueryManager(requestContext);
        AdhocQueryResponse response = new AdhocQueryResponse();

        try {
            SearchResultsType searchResult = qm.query().getSearchResults();
            List<Object> regObjs = searchResult.getAny();

            response.setRegistryObjectList(new RegistryObjectListType());
            response.setTotalResultCount(searchResult.getNumberOfRecordsMatched());

            for (Object regObj : regObjs) {
                response.getRegistryObjectList().getIdentifiable().add((JAXBElement<? extends IdentifiableType>) regObj);
            }

        } catch (ServiceException ex) {
            throw new WebServiceException(ex);
        }


        return response;
    }
}
