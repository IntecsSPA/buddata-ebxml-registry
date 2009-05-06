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

import be.kzen.ergorr.interfaces.soap.rim.QueryManagerPortType;
import be.kzen.ergorr.model.query.AdhocQueryRequest;
import be.kzen.ergorr.model.query.AdhocQueryResponse;
import be.kzen.ergorr.model.rim.AdhocQueryType;
import be.kzen.ergorr.model.rim.QueryExpressionType;
import javax.jws.WebService;

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
     * TODO
     * @param adhocQueryReq
     * @return
     */
    public AdhocQueryResponse submitAdhocQuery(AdhocQueryRequest adhocQueryReq) {
        AdhocQueryResponse response = new AdhocQueryResponse();

        AdhocQueryType adhocQuery = adhocQueryReq.getAdhocQuery();

        if (adhocQuery.isSetQueryExpression()) {
            QueryExpressionType queryExpr = adhocQuery.getQueryExpression();

            
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }
}
