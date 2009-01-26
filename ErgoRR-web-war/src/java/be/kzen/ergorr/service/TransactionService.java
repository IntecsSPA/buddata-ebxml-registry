/*
 * Project: Buddata ebXML RegRep
 * Class: TransactionService.java
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
package be.kzen.ergorr.service;

import be.kzen.ergorr.commons.CommonFunctions;
import be.kzen.ergorr.commons.NamespaceConstants;
import be.kzen.ergorr.exceptions.TranslationException;
import be.kzen.ergorr.commons.RequestContext;
import be.kzen.ergorr.interfaces.soap.ServiceExceptionReport;
import be.kzen.ergorr.model.csw.BriefRecordType;
import be.kzen.ergorr.model.csw.DeleteType;
import be.kzen.ergorr.model.csw.ElementSetNameType;
import be.kzen.ergorr.model.csw.ElementSetType;
import be.kzen.ergorr.model.csw.GetRecordsResponseType;
import be.kzen.ergorr.model.csw.GetRecordsType;
import be.kzen.ergorr.model.csw.InsertResultType;
import be.kzen.ergorr.model.csw.InsertType;
import be.kzen.ergorr.model.csw.QueryType;
import be.kzen.ergorr.model.csw.TransactionResponseType;
import be.kzen.ergorr.model.csw.TransactionSummaryType;
import be.kzen.ergorr.model.csw.TransactionType;
import be.kzen.ergorr.model.csw.UpdateType;
import be.kzen.ergorr.model.purl.elements.SimpleLiteral;
import be.kzen.ergorr.model.rim.IdentifiableType;
import be.kzen.ergorr.model.rim.RegistryObjectListType;
import be.kzen.ergorr.model.rim.RegistryObjectType;
import be.kzen.ergorr.model.util.JAXBUtil;
import be.kzen.ergorr.model.util.OFactory;
import be.kzen.ergorr.query.QueryManager;
import be.kzen.ergorr.service.translator.TranslationFactory;
import java.math.BigInteger;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

/**
 *
 * @author Yaman Ustuntas
 */
public class TransactionService {

    private static Logger logger = Logger.getLogger(TransactionService.class.getName());
    private RequestContext requestContext;

    public TransactionService() {
    }

    public TransactionService(RequestContext requestContext) {
        this.requestContext = requestContext;
    }

    public TransactionResponseType process() throws ServiceExceptionReport {
        TransactionResponseType response = new TransactionResponseType();
        response.setVersion("1.0");

        List<Object> iuds = ((TransactionType) requestContext.getRequest()).getInsertOrUpdateOrDelete();

        for (Object iud : iuds) {
            if (iud instanceof InsertType) {
                return doInsert((InsertType) iud);
            } else if (iud instanceof UpdateType) {
                doUpdate((UpdateType) iud);
            } else if (iud instanceof DeleteType) {
                doDelete((DeleteType) iud);
            } else {
                logger.severe("Transaction request not an Insert, Update or Delete");
            }
        }

        return response;
    }

    private TransactionResponseType doInsert(InsertType insert) throws ServiceExceptionReport {
        logger.fine("Transtaction.doInsert");
        TranslationFactory transFac = new TranslationFactory();
        RegistryObjectListType regObjList = new RegistryObjectListType();

        try {
            for (Object o : insert.getAny()) {
                Object obj = ((JAXBElement) o).getValue();

                if (logger.isLoggable(Level.FINE)) {
                    logger.fine("Translation root object: " + obj.getClass().toString());
                }
                if (obj instanceof IdentifiableType) {
                    regObjList.getIdentifiable().add((JAXBElement<? extends IdentifiableType>) o);
                } else if (obj instanceof RegistryObjectListType) {
                    RegistryObjectListType reqList = (RegistryObjectListType) obj;
                    regObjList.getIdentifiable().addAll(reqList.getIdentifiable());
                } else {
                    regObjList.getIdentifiable().addAll(transFac.translate(obj).getIdentifiable());
                }
            }

            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Got " + regObjList.getIdentifiable().size() + " translated objects");
            }
            LCManager lcm = new LCManager(requestContext);
            lcm.submit(regObjList);

            // prepare response
            return buildResponse(regObjList);
        } catch (TranslationException ex) {
            throw new ServiceExceptionReport("Transaction error", null, ex);
        }
    }

    private TransactionResponseType doUpdate(UpdateType update) throws ServiceExceptionReport {

        if (update.isSetAny()) {
            Object updateObj = update.getAny();
            RegistryObjectListType regObjList = null;

            if (updateObj instanceof RegistryObjectListType) {
                regObjList = (RegistryObjectListType) updateObj;
            } else if (updateObj instanceof RegistryObjectType) {
                regObjList = new RegistryObjectListType();
                JAXBElement<RegistryObjectType> regObjEl = OFactory.rim.createRegistryObject((RegistryObjectType) updateObj);
                regObjList.getIdentifiable().add(regObjEl);
            }

            LCManager lcm = new LCManager(requestContext);
            lcm.update(regObjList);
        // process regObjList

        } else {
            throw new ServiceExceptionReport("No rim:RegistryObject or rim:RegistryObjectList specified for updating");
        }
        throw new ServiceExceptionReport("Transaction.update not supported");
    }

    private TransactionResponseType doDelete(DeleteType delete) throws ServiceExceptionReport {
        GetRecordsType getRecords = new GetRecordsType();
        QueryType query = new QueryType();
        String typeName = CommonFunctions.removePrefix(delete.getTypeName());
        QName objQNameToDelete = new QName(NamespaceConstants.RIM, typeName, "rim");
        query.getTypeNames().add(objQNameToDelete);

        ElementSetNameType elSetName = new ElementSetNameType();
        elSetName.getTypeNames().add(objQNameToDelete);
        elSetName.setValue(ElementSetType.BRIEF);
        query.setElementSetName(elSetName);
        
        query.setConstraint(delete.getConstraint());
        JAXBElement<QueryType> queryEl = OFactory.csw.createQuery(query);
        getRecords.setAbstractQuery(queryEl);
        
        RequestContext rq = new RequestContext();
        rq.setRequest(getRecords);

        QueryManager qm = new QueryManager(rq);

        GetRecordsResponseType response = qm.query();
        List<Object> objsToDelete = response.getSearchResults().getAny();

        RegistryObjectListType regObjList = new RegistryObjectListType();

        for (Object objToDelete : objsToDelete) {
            JAXBElement<? extends IdentifiableType> elToDelete = (JAXBElement<? extends IdentifiableType>) objToDelete;
            regObjList.getIdentifiable().add(elToDelete);
        }

        LCManager lcm = new LCManager(requestContext);
        lcm.delete(regObjList);

        throw new ServiceExceptionReport("Transaction.delete not supported");
    }

    public TransactionResponseType buildResponse(RegistryObjectListType regObjList) {
        TransactionResponseType response = new TransactionResponseType();
        InsertResultType insertResult = new InsertResultType();

        for (JAXBElement<? extends IdentifiableType> identEl : regObjList.getIdentifiable()) {
            BriefRecordType briefRecord = new BriefRecordType();

            SimpleLiteral identifier = new SimpleLiteral();
            identifier.getContent().add(identEl.getValue().getId());
            briefRecord.getIdentifier().add(OFactory.purl_elements.createIdentifier(identifier));

            if (identEl.getValue() instanceof RegistryObjectType) {
                SimpleLiteral type = new SimpleLiteral();
                type.getContent().add(((RegistryObjectType) identEl.getValue()).getObjectType());
                briefRecord.setType(type);
            }
            insertResult.getBriefRecord().add(briefRecord);
        }

        response.getInsertResult().add(insertResult);
        TransactionSummaryType transSummary = new TransactionSummaryType();
        transSummary.setTotalInserted(BigInteger.valueOf(regObjList.getIdentifiable().size()));
        response.setTransactionSummary(transSummary);

        return response;
    }
}
