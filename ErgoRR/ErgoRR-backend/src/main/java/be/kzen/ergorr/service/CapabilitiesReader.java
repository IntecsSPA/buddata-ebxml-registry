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
import be.kzen.ergorr.model.ows.ResponsiblePartySubsetType;
import be.kzen.ergorr.model.ows.ServiceProvider;
import be.kzen.ergorr.model.util.JAXBUtil;
import be.kzen.ergorr.xpath.XMLNamespaces;
import be.kzen.ergorr.xpath.XPathUtil;
import java.io.IOException;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author yamanustuntas
 */
public class CapabilitiesReader {

    private Document cababilities;
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
    private static final String PARENT_IDENTIFIERS = "parentIdenfiers";
    private static final String DEPLOY_NAME = "deployName";

    public CapabilitiesReader() {
    }

    public void load() throws ParserConfigurationException, SAXException, IOException, JAXBException {
        JAXBElement capEl = (JAXBElement) JAXBUtil.getInstance().unmarshall(this.getClass().getResource("/resources/Capabilities.xml"));
        CapabilitiesType cap = (CapabilitiesType) capEl.getValue();

        ServiceProvider sp = cap.getServiceProvider();
        sp.setProviderName(CommonProperties.getInstance().get(SP_NAME));
        sp.getProviderSite().setHref(CommonProperties.getInstance().get(SP_SITE));
        
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


        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        cababilities = docBuilder.parse(this.getClass().getResourceAsStream("/resources/Capabilities.xml"));
    }

    public Document getCababilities() {
        return cababilities;
    }
}
