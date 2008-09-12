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

import be.kzen.ergorr.exceptions.TranslationException;
import be.kzen.ergorr.interfaces.soap.RequestContext;
import be.kzen.ergorr.interfaces.soap.ServiceExceptionReport;
import be.kzen.ergorr.model.csw.HarvestResponseType;
import be.kzen.ergorr.model.csw.HarvestType;
import be.kzen.ergorr.model.rim.IdentifiableType;
import be.kzen.ergorr.model.rim.RegistryObjectListType;
import be.kzen.ergorr.model.util.JAXBUtil;
import be.kzen.ergorr.service.translator.TranslationFactory;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import org.apache.log4j.Logger;

/**
 *
 * @author Yaman Ustuntas
 */
public class HarvestService {

    private static Logger log = Logger.getLogger(HarvestService.class);
    private RequestContext requestContext;
    private HarvestType request;

    public HarvestService(RequestContext requestContext) {
        this.requestContext = requestContext;
        this.request = (HarvestType) requestContext.getRequest();
    }

    public HarvestResponseType process() throws ServiceExceptionReport {

        try {
            HarvestResponseType response = new HarvestResponseType();
            RegistryObjectListType regObjList = null;
            URL xmlLocaltion = new URL(request.getSource());
            JAXBElement remoteXmlEl = (JAXBElement) JAXBUtil.getInstance().unmarshall(xmlLocaltion);
            Object remoteXml = remoteXmlEl.getValue();

            if (remoteXml instanceof IdentifiableType) {
                regObjList = new RegistryObjectListType();
                regObjList.getIdentifiable().add(remoteXmlEl);
            } else if (remoteXml instanceof RegistryObjectListType) {
                regObjList = (RegistryObjectListType) remoteXml;
            } else {
                TranslationFactory transFac = new TranslationFactory();
                regObjList = transFac.translate(remoteXml);
            }

            TransactionService transService = new TransactionService();
            response.setTransactionResponse(transService.buildResponse(regObjList));
            LCManager lcm = new LCManager(requestContext.getRimDAO());
            lcm.submit(regObjList);
            return response;

        } catch (MalformedURLException ex) {
            throw new ServiceExceptionReport("Provided source URL is not valid", null, ex);
        } catch (JAXBException ex) {
            throw new ServiceExceptionReport("Could not unmarshall the data from the provided source URL", null, ex);
        } catch (TranslationException ex) {
            throw new ServiceExceptionReport("Could not translate the data from the provided source URL", null, ex);
        }
    }
}
