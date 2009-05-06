/*
 * Project: Buddata ebXML RegRep
 * Class: RimLcmServiceImpl.java
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
import be.kzen.ergorr.exceptions.QueryException;
import be.kzen.ergorr.interfaces.soap.csw.ServiceExceptionReport;
import be.kzen.ergorr.interfaces.soap.rim.LifeCycleManagerPortType;
import be.kzen.ergorr.interfaces.soap.rim.QueryManagerPortType;
import be.kzen.ergorr.model.csw.GetRecordsResponseType;
import be.kzen.ergorr.model.csw.GetRecordsType;
import be.kzen.ergorr.model.csw.QueryType;
import be.kzen.ergorr.model.lcm.ApproveObjectsRequest;
import be.kzen.ergorr.model.lcm.DeprecateObjectsRequest;
import be.kzen.ergorr.model.lcm.RemoveObjectsRequest;
import be.kzen.ergorr.model.lcm.SubmitObjectsRequest;
import be.kzen.ergorr.model.lcm.UndeprecateObjectsRequest;
import be.kzen.ergorr.model.lcm.UpdateObjectsRequest;
import be.kzen.ergorr.model.query.AdhocQueryRequest;
import be.kzen.ergorr.model.query.AdhocQueryResponse;
import be.kzen.ergorr.model.rim.AdhocQueryType;
import be.kzen.ergorr.model.rim.IdentifiableType;
import be.kzen.ergorr.model.rim.QueryExpressionType;
import be.kzen.ergorr.model.rim.RegistryObjectListType;
import be.kzen.ergorr.model.rs.RegistryError;
import be.kzen.ergorr.model.rs.RegistryErrorList;
import be.kzen.ergorr.model.rs.RegistryResponseType;
import be.kzen.ergorr.model.util.OFactory;
import be.kzen.ergorr.query.QueryManager;
import be.kzen.ergorr.service.LCManager;
import java.math.BigInteger;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.xml.bind.JAXBElement;
import javax.xml.ws.Holder;

/**
 * RIM Life Cycle Manager SOAP interface implementation.
 * 
 * @author yamanustuntas
 */
@WebService(serviceName = "ebXMLRegistrySOAPService", portName = "LifeCycleManagerPort",
targetNamespace = "http://www.kzen.be/ergorr/interfaces/soap/rim",
endpointInterface = "be.kzen.ergorr.interfaces.soap.rim.LifeCycleManagerPortType")
public class RimLcmServiceImpl implements LifeCycleManagerPortType {

    private static Logger logger = Logger.getLogger(RimLcmServiceImpl.class.getName());

    public RimLcmServiceImpl() {
    }

    /**
     * Submit objects.
     *
     * @param submitObjReq Submit objects request.
     * @return Status submitted or registry error.
     */
    public RegistryResponseType submitObjects(SubmitObjectsRequest submitObjReq) {
        LCManager lcm = new LCManager(new RequestContext());
        RegistryResponseType response = new RegistryResponseType();

        try {
            lcm.submit(submitObjReq.getRegistryObjectList());
            response.setStatus(RIMConstants.RESPONSE_STATUS_CODE_Success);
        } catch (ServiceExceptionReport ex) {
            logger.log(Level.WARNING, "Could not submit objects", ex);
            response.setStatus(RIMConstants.RESPONSE_STATUS_CODE_Failure);
            response.setRegistryErrorList(createRegistryErrorList(ex.toString()));
        }

        return response;
    }

    /**
     * Update objects.
     *
     * @param updateObjReq Update objects request and response help by the <code>Holder</code>.
     */
    public void updateObjects(Holder<UpdateObjectsRequest> updateObjReq) {
        UpdateObjectsRequest req = updateObjReq.value;
        req.getRegistryObjectList();

        LCManager lcm = new LCManager(new RequestContext());

        try {
            lcm.update(req.getRegistryObjectList());
        } catch (ServiceExceptionReport ex) {
            logger.log(Level.WARNING, "Could not update objects", ex);
            req.setRegistryObjectList(new RegistryObjectListType());
        }
    }

    /**
     * Remove objects.
     *
     * @param removeObjReq Remove objects request.
     * @return
     */
    public RegistryResponseType removeObjects(RemoveObjectsRequest removeObjReq) {
        AdhocQueryType adhocQuery = removeObjReq.getAdhocQuery();
        RegistryResponseType response = new RegistryResponseType();

        QueryExpressionType queryExp = adhocQuery.getQueryExpression();

        if (queryExp.isSetContent()) {
            Object content = queryExp.getContent().get(0);

            if (content instanceof JAXBElement) {
                JAXBElement getRecordsEl = (JAXBElement) content;

                if (getRecordsEl.getValue() instanceof QueryType) {
                    QueryType query = (QueryType) getRecordsEl.getValue();

                    GetRecordsType getRecords = new GetRecordsType();
                    getRecords.setAbstractQuery(OFactory.csw.createQuery(query));

                    RequestContext reqContext = new RequestContext();
                    reqContext.setRequest(null);
                    QueryManager qm = new QueryManager(reqContext);

                    try {
                        GetRecordsResponseType getRecordsRes = qm.query();
                        List<Object> objsToDelete = getRecordsRes.getSearchResults().getAny();
                        RegistryObjectListType regObjList = new RegistryObjectListType();

                        for (Object objToDelete : objsToDelete) {
                            JAXBElement<? extends IdentifiableType> elToDelete = (JAXBElement<? extends IdentifiableType>) objToDelete;
                            regObjList.getIdentifiable().add(elToDelete);
                        }

                        LCManager lcm = new LCManager(reqContext);
                        lcm.delete(regObjList);
                        
                        response.setStatus(RIMConstants.RESPONSE_STATUS_CODE_Success);
                    } catch (ServiceExceptionReport ex) {
                        response.setStatus(RIMConstants.RESPONSE_STATUS_CODE_Failure);
                        response.setRegistryErrorList(createRegistryErrorList(ex.getMessage()));
                    }
                }
            }
        }

        return response;
    }

    /**
     * TODO
     * @param approveObjReq
     * @return
     */
    public RegistryResponseType approveObjects(ApproveObjectsRequest approveObjReq) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * TODO
     * @param deprecateObjReq
     * @return
     */
    public RegistryResponseType deprecateObjects(DeprecateObjectsRequest deprecateObjReq) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * TODO
     * @param undeprecateObjReq
     * @return
     */
    public RegistryResponseType undeprecateObjects(UndeprecateObjectsRequest undeprecateObjReq) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Helper method to create a registry error list.
     *
     * @param error Error message.
     * @return Registry error.
     */
    private RegistryErrorList createRegistryErrorList(String error) {
        RegistryError registryError = new RegistryError();
        registryError.setErrorCode("");
        registryError.setCodeContext("");
        registryError.setValue(error);
        RegistryErrorList registryErrorList = new RegistryErrorList();
        registryErrorList.getRegistryError().add(registryError);
        return registryErrorList;
    }
}
