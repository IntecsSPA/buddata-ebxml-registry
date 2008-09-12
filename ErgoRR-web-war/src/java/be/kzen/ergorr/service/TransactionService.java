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

import be.kzen.ergorr.exceptions.TranslationException;
import be.kzen.ergorr.interfaces.soap.RequestContext;
import be.kzen.ergorr.interfaces.soap.ServiceExceptionReport;
import be.kzen.ergorr.model.csw.BriefRecordType;
import be.kzen.ergorr.model.csw.DeleteType;
import be.kzen.ergorr.model.csw.InsertResultType;
import be.kzen.ergorr.model.csw.InsertType;
import be.kzen.ergorr.model.csw.TransactionResponseType;
import be.kzen.ergorr.model.csw.TransactionSummaryType;
import be.kzen.ergorr.model.csw.TransactionType;
import be.kzen.ergorr.model.csw.UpdateType;
import be.kzen.ergorr.model.purl.elements.SimpleLiteral;
import be.kzen.ergorr.model.rim.IdentifiableType;
import be.kzen.ergorr.model.rim.RegistryObjectListType;
import be.kzen.ergorr.model.rim.RegistryObjectType;
import be.kzen.ergorr.model.util.OFactory;
import be.kzen.ergorr.service.translator.TranslationFactory;
import java.math.BigInteger;
import java.util.List;
import javax.xml.bind.JAXBElement;
import org.apache.log4j.Logger;

/**
 *
 * @author Yaman Ustuntas
 */
public class TransactionService {

    private static Logger log = Logger.getLogger(TransactionService.class);
    private RequestContext request;

    public TransactionService() {
    }

    public TransactionService(RequestContext request) {
        this.request = request;
    }

    public TransactionResponseType process() throws ServiceExceptionReport {
        TransactionResponseType response = new TransactionResponseType();
        response.setVersion("1.0");

        List<Object> iuds = ((TransactionType) request.getRequest()).getInsertOrUpdateOrDelete();

        for (Object iud : iuds) {
            if (iud instanceof InsertType) {
                return doInsert((InsertType) iud);
            } else if (iud instanceof UpdateType) {
                doUpdate((UpdateType) iud);
            } else if (iud instanceof DeleteType) {
                doDelete((DeleteType) iud);
            } else {
                log.error("Transaction request not an Insert, Update or Delete");
            }
        }

        return response;
    }

    private TransactionResponseType doInsert(InsertType insert) throws ServiceExceptionReport {
        log.debug("Transtaction.doInsert");
        TranslationFactory transFac = new TranslationFactory();
        RegistryObjectListType regObjList = new RegistryObjectListType();

        try {
            for (Object o : insert.getAny()) {
                Object obj = ((JAXBElement) o).getValue();
                
                if (log.isDebugEnabled()) {
                    log.debug("Translation root object: " + obj.getClass().toString());
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

            log.debug("Got " + regObjList.getIdentifiable().size() + " translated objects");
            LCManager lcm = new LCManager(request.getRimDAO());
            lcm.submit(regObjList);

            // prepare response
            return buildResponse(regObjList);
        } catch (TranslationException ex) {
            throw new ServiceExceptionReport("Transaction error", null, ex);
        }
    }

    private void doUpdate(UpdateType update) throws ServiceExceptionReport {
        throw new ServiceExceptionReport("Transaction.update not supported");
    }

    private void doDelete(DeleteType delete) throws ServiceExceptionReport {
        throw new ServiceExceptionReport("Transaction.delete not supported");
    }

    public TransactionResponseType buildResponse(RegistryObjectListType regObjList) {
        TransactionResponseType response = new TransactionResponseType();
        InsertResultType insertResult = new InsertResultType();

        for (JAXBElement<? extends IdentifiableType> identEl : regObjList.getIdentifiable()) {
            BriefRecordType breifRecord = new BriefRecordType();

            SimpleLiteral identifier = new SimpleLiteral();
            identifier.getContent().add(identEl.getValue().getId());
            breifRecord.getIdentifier().add(OFactory.purl_elements.createIdentifier(identifier));

            if (identEl.getValue() instanceof RegistryObjectType) {
                SimpleLiteral type = new SimpleLiteral();
                type.getContent().add(((RegistryObjectType) identEl.getValue()).getObjectType());
                breifRecord.setType(type);
            }
            insertResult.getBriefRecord().add(breifRecord);
        }

        response.getInsertResult().add(insertResult);
        TransactionSummaryType transSummary = new TransactionSummaryType();
        transSummary.setTotalInserted(BigInteger.valueOf(regObjList.getIdentifiable().size()));
        response.setTransactionSummary(transSummary);

        return response;
    }
}
