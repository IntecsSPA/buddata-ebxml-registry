/*
 * Project: Buddata ebXML RegRep
 * Class: CapabilitiesReader.java
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

import be.kzen.ergorr.commons.CommonProperties;
import be.kzen.ergorr.commons.IoUtil;
import be.kzen.ergorr.commons.NamespaceConstants;
import be.kzen.ergorr.model.csw.CapabilitiesType;
import be.kzen.ergorr.model.ows.ContactType;
import be.kzen.ergorr.model.ows.DCP;
import be.kzen.ergorr.model.ows.Operation;
import be.kzen.ergorr.model.ows.RequestMethodType;
import be.kzen.ergorr.model.ows.ResponsiblePartySubsetType;
import be.kzen.ergorr.model.ows.ServiceProvider;
import be.kzen.ergorr.model.rim.IdentifiableType;
import be.kzen.ergorr.model.rim.SlotListType;
import be.kzen.ergorr.model.rim.SlotType;
import be.kzen.ergorr.model.rim.ValueListType;
import be.kzen.ergorr.model.util.JAXBUtil;
import be.kzen.ergorr.model.util.OFactory;
import be.kzen.ergorr.xpath.XMLNamespaces;
import be.kzen.ergorr.xpath.XPathUtil;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author yamanustuntas
 */
public class CapabilitiesReader {

    private static Logger logger = Logger.getLogger(CapabilitiesReader.class.getName());
    private static final String SP_NAME = "serviceProvider.name";
    private static final String SP_SITE = "serviceProvider.site";
    private static final String SP_CONTACT_NAME = "serviceProvider.contact.name";
    private static final String SP_CONTACT_POSITION = "serviceProvider.contact.position";
    private static final String SP_CONTACT_PHONE = "serviceProvider.contact.phone";
    private static final String SP_CONTACT_ADDR_DELIVERPOINT = "serviceProvider.contact.address.deliveryPoint";
    private static final String SP_CONTACT_ADDR_CITY = "serviceProvider.contact.address.city";
    private static final String SP_CONTACT_ADDR_AREA = "serviceProvider.contact.address.administrativeArea";
    private static final String SP_CONTACT_ADDR_POSTALCODE = "serviceProvider.contact.address.postalCode";
    private static final String SP_CONTACT_ADDR_COUNTRY = "serviceProvider.contact.address.country";
    private static final String SP_CONTACT_ADDR_EMAIL = "serviceProvider.contact.address.electronicMailAddress";
    private static final String SP_CONTACT_SERVICEHOURS = "serviceProvider.contact.hoursOfService";
    private static final String SP_CONTACT_INSTRUCTIONS = "serviceProvider.contact.contactInstructions";
    private static final String SP_ROLE = "serviceProvider.role";
    private static final String PARENT_IDENTIFIERS = "parentIdentifiers";
    private static final String DEPLOY_NAME = "deployName";

    public CapabilitiesReader() {
    }

    public JAXBElement<CapabilitiesType> getCapabilities(String requestUrl) throws JAXBException {
        return load(getServletUrl(requestUrl));
    }

    private JAXBElement<CapabilitiesType> load(String servletUrl) throws JAXBException {

        JAXBElement<CapabilitiesType> capabilities = (JAXBElement<CapabilitiesType>) JAXBUtil.getInstance().unmarshall(this.getClass().getResource("/resources/Capabilities.xml"));
        CapabilitiesType cap = (CapabilitiesType) capabilities.getValue();

        ServiceProvider sp = cap.getServiceProvider();
        sp.setProviderName(CommonProperties.getInstance().get(SP_NAME));
        sp.getProviderSite().setHref(CommonProperties.getInstance().get(SP_SITE));

        // set ServiceProvider info
        ResponsiblePartySubsetType contact = sp.getServiceContact();
        contact.setIndividualName(CommonProperties.getInstance().get(SP_CONTACT_NAME));
        contact.setPositionName(CommonProperties.getInstance().get(SP_CONTACT_POSITION));

        ContactType contactInfo = contact.getContactInfo();
        contactInfo.getPhone().getVoice().set(0, CommonProperties.getInstance().get(SP_CONTACT_PHONE));
        contactInfo.getAddress().getDeliveryPoint().set(0, CommonProperties.getInstance().get(SP_CONTACT_ADDR_DELIVERPOINT));
        contactInfo.getAddress().setCity(CommonProperties.getInstance().get(SP_CONTACT_ADDR_CITY));
        contactInfo.getAddress().setAdministrativeArea(CommonProperties.getInstance().get(SP_CONTACT_ADDR_AREA));
        contactInfo.getAddress().setPostalCode(CommonProperties.getInstance().get(SP_CONTACT_ADDR_POSTALCODE));
        contactInfo.getAddress().setCountry(CommonProperties.getInstance().get(SP_CONTACT_ADDR_COUNTRY));
        contactInfo.getAddress().getElectronicMailAddress().set(0, CommonProperties.getInstance().get(SP_CONTACT_ADDR_EMAIL));
        contactInfo.setHoursOfService(CommonProperties.getInstance().get(SP_CONTACT_SERVICEHOURS));
        contactInfo.setContactInstructions(CommonProperties.getInstance().get(SP_CONTACT_INSTRUCTIONS));

        contact.getRole().setValue(CommonProperties.getInstance().get(SP_ROLE));

        // set ParentIdentifiers
        SlotType slot = new SlotType();
        slot.setName("http://earth.esa.int/eop/parentIdentifier");
        ValueListType valList = new ValueListType();
        slot.setValueList(OFactory.rim.createValueList(valList));
        String[] parantIdents = CommonProperties.getInstance().getStringArray(PARENT_IDENTIFIERS);
        valList.getValue().addAll(Arrays.asList(parantIdents));

        SlotListType slotList = new SlotListType();
        slotList.getSlot().add(slot);
        cap.getOperationsMetadata().setExtendedCapabilities(slotList);

        // set service URLs
        List<Operation> operations = cap.getOperationsMetadata().getOperation();

        for (Operation operation : operations) {

            for (DCP dcp : operation.getDCP()) {
                for (JAXBElement<RequestMethodType> reqMethodEl : dcp.getHTTP().getGetOrPost()) {
                    reqMethodEl.getValue().setHref(servletUrl + reqMethodEl.getValue().getHref());
                }
            }
        }

        return capabilities;
    }

    private String getServletUrl(String requestUrl) {
        int idx = requestUrl.lastIndexOf("/");
        return (idx > 0) ? requestUrl.substring(0, idx + 1) : requestUrl;
    }
}
