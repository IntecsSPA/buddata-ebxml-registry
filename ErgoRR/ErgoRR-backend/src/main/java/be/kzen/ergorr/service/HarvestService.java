/*
 * Project: Buddata ebXML RegRep
 * Class: HarvestService.java
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

import be.kzen.ergorr.commons.RequestContext;
import be.kzen.ergorr.exceptions.ErrorCodes;
import be.kzen.ergorr.exceptions.ServiceException;
import be.kzen.ergorr.interfaces.soap.csw.ServiceExceptionReport;
import be.kzen.ergorr.model.csw.BriefRecordType;
import be.kzen.ergorr.model.csw.HarvestResponseType;
import be.kzen.ergorr.model.csw.HarvestType;
import be.kzen.ergorr.model.csw.InsertResultType;
import be.kzen.ergorr.model.csw.TransactionResponseType;
import be.kzen.ergorr.model.csw.TransactionSummaryType;
import be.kzen.ergorr.model.purl.elements.SimpleLiteral;
import be.kzen.ergorr.model.rim.IdentifiableType;
import be.kzen.ergorr.model.rim.RegistryObjectListType;
import be.kzen.ergorr.model.rim.RegistryObjectType;
import be.kzen.ergorr.model.util.JAXBUtil;
import be.kzen.ergorr.model.util.OFactory;
import be.kzen.ergorr.service.translator.TranslationException;
import be.kzen.ergorr.service.translator.TranslatorFactory;
import be.kzen.ergorr.service.translator.Translator;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

/**
 * Handles the Harvest requests.
 *
 * @author Yaman Ustuntas
 */
public class HarvestService {

    private static Logger logger = Logger.getLogger(HarvestService.class.getName());
    private RequestContext requestContext;
    private HarvestType request;

    /**
     * Constructor.
     *
     * @param requestContext Request context.
     */
    public HarvestService(RequestContext requestContext) {
        this.requestContext = requestContext;
        this.request = (HarvestType) requestContext.getRequest();
    }

    /**
     * Fetches the data from the remote URL provided in the request
     * and inserts it into the registry.
     *
     * @return Harvest response.
     * @throws be.kzen.ergorr.exceptions.ServiceException
     */
    public HarvestResponseType process() throws ServiceException {

        URL xmlLocaltion = null;

        try {
            xmlLocaltion = new URL(request.getSource());
        } catch (MalformedURLException ex) {
            logger.log(Level.WARNING, "Invalid URL", ex);
            throw new ServiceException(ErrorCodes.INVALID_REQUEST, "Invalid URL", ex);
        }

        JAXBElement remoteXmlEl = null;

        try {
            remoteXmlEl = (JAXBElement) JAXBUtil.getInstance().unmarshall(xmlLocaltion);
        } catch (JAXBException ex) {
            logger.log(Level.WARNING, "Could not load remote object", ex);
            throw new ServiceException(ErrorCodes.INVALID_REQUEST, "Could not load remote object", ex);
        }

        Object remoteXml = remoteXmlEl.getValue();
        RegistryObjectListType regObjList = null;

        if (remoteXml instanceof IdentifiableType) {
            regObjList = new RegistryObjectListType();
            regObjList.getIdentifiable().add(remoteXmlEl);
        } else if (remoteXml instanceof RegistryObjectListType) {
            regObjList = (RegistryObjectListType) remoteXml;
        } else {

            try {
                Translator translator = TranslatorFactory.getInstance(remoteXmlEl);
                regObjList = translator.translate();
            } catch (TranslationException ex) {
                logger.log(Level.WARNING, "Translation failed", ex);
                throw new ServiceException(ErrorCodes.INVALID_REQUEST, "Translation failed", ex);
            }
        }

        // submit data
        LCManager lcm = new LCManager(requestContext);
        lcm.submit(regObjList);

        // create response
        HarvestResponseType response = new HarvestResponseType();
        response.setTransactionResponse(buildResponse(regObjList));

        return response;
    }

    /**
     * Builds a transaction response from the inserted objects.
     *
     * @param regObjList Inserted registry object list.
     * @return Transaction response.
     */
    private TransactionResponseType buildResponse(RegistryObjectListType regObjList) {
        TransactionResponseType response = new TransactionResponseType();
        InsertResultType insertResult = new InsertResultType();

        for (JAXBElement<? extends IdentifiableType> identEl : regObjList.getIdentifiable()) {
            BriefRecordType briefRecord = new BriefRecordType();

            SimpleLiteral identifier = new SimpleLiteral();
            identifier.getContent().add(identEl.getValue().getId());
            briefRecord.getIdentifier().add(OFactory.purl_elements.createIdentifier(identifier));

            SimpleLiteral title = new SimpleLiteral();
            title.getContent().add("-");
            briefRecord.getTitle().add(OFactory.purl_elements.createTitle(title));

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
