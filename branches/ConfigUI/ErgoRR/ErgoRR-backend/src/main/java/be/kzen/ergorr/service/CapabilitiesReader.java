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

import be.kzen.ergorr.commons.CommonFunctions;
import be.kzen.ergorr.commons.CommonProperties;
import be.kzen.ergorr.commons.EOPConstants;
import be.kzen.ergorr.commons.NamespaceConstants;
import be.kzen.ergorr.commons.RequestContext;
import be.kzen.ergorr.model.csw.CapabilitiesType;
import be.kzen.ergorr.model.ows.ContactType;
import be.kzen.ergorr.model.ows.DCP;
import be.kzen.ergorr.model.ows.Operation;
import be.kzen.ergorr.model.ows.RequestMethodType;
import be.kzen.ergorr.model.ows.ResponsiblePartySubsetType;
import be.kzen.ergorr.model.ows.ServiceProvider;
import be.kzen.ergorr.model.rim.SlotListType;
import be.kzen.ergorr.model.rim.SlotType;
import be.kzen.ergorr.model.rim.ValueListType;
import be.kzen.ergorr.model.util.JAXBUtil;
import be.kzen.ergorr.model.util.OFactory;
import be.kzen.ergorr.persist.service.SqlPersistence;
import com.sun.tools.ws.processor.model.jaxb.JAXBProperty;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

/**
 * Helper class to read an populate csw:Capabilities.
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
    private static final String SQL_QUERY_PARENT_IDENTIFIER = "select stringvalue from t_slot where name_ = '" +
            EOPConstants.S_PARENT_IDENTIFIER + "' group by stringvalue";
    private static final String CAPABILITIES_FILE = "/resources/Capabilities.xml";

    private RequestContext requestContext;

    public CapabilitiesReader() {
    }

    public CapabilitiesReader(RequestContext requestContext) {
        this.requestContext = requestContext;
    }

    public JAXBElement<CapabilitiesType> getCapabilities(String servletUrl) throws JAXBException {
        return load(servletUrl);
    }

    /**
     * Load and populate the Capabilities document.
     *
     * @param servletUrl URL of the servlet.
     * @return Capabilities document.
     * @throws JAXBException
     */
    private JAXBElement<CapabilitiesType> load(String servletUrl) throws JAXBException {

        URL capFile = this.getClass().getResource(CAPABILITIES_FILE);

        if (capFile == null) {
            throw new JAXBException("Could not find capabilities document");
        }

        JAXBElement<CapabilitiesType> capabilities =
                (JAXBElement<CapabilitiesType>) JAXBUtil.getInstance().unmarshall(capFile);
        CapabilitiesType cap = capabilities.getValue();

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

        // set service URLs
        List<Operation> operations = cap.getOperationsMetadata().getOperation();

        for (Operation operation : operations) {

            for (DCP dcp : operation.getDCP()) {
                for (JAXBElement<RequestMethodType> reqMethodEl : dcp.getHTTP().getGetOrPost()) {
                    reqMethodEl.getValue().setHref(servletUrl + reqMethodEl.getValue().getHref());
                }
            }
        }

        setExtendedCapabilities((Element) cap.getOperationsMetadata().getExtendedCapabilities(), servletUrl);

        return capabilities;

    }

    /**
     * Add additional capabilities through csw:ExtendedCapabilities.
     * Appends DOM nodes to {@code calEl}.
     *
     * @param capEl ExtendedCapabilities element.
     * @param servletUrl URL of the servlet.
     */
    private void setExtendedCapabilities(Element capEl, String servletUrl) {
        Document doc = capEl.getOwnerDocument();

        capEl.setAttribute("xmlns:wsdi", "http://www.w3.org/ns/wsdl-instance");
        capEl.setAttributeNS("http://www.w3.org/ns/wsdl-instance", "wsdi:wsdlLocation", servletUrl + "/webservice?wsdl");


        Element slotEl = doc.createElementNS(NamespaceConstants.RIM, "rim:Slot");
        capEl.appendChild(slotEl);
        slotEl.setAttribute("name", EOPConstants.S_PARENT_IDENTIFIER);
        Element valListEl = doc.createElementNS(NamespaceConstants.RIM, "rim:ValueList");
        slotEl.appendChild(valListEl);

        List<String> parentIdents = getParentIdentifiers();

        for (String parentIdent : parentIdents) {
            Element valEl = doc.createElementNS(NamespaceConstants.RIM, "rim:Value");
            valListEl.appendChild(valEl);
            valEl.setTextContent(parentIdent);
        }
    }

    /**
     * Get all used parentIdentifier slots in EOP profile.
     *
     * @return List of parentIdentifiers.
     */
    private List<String> getParentIdentifiers() {
        List<String> parentIdents = new ArrayList<String>();

        SqlPersistence persistence = new SqlPersistence(requestContext);
        Connection conn = null;

        try {
            conn = persistence.getConnection();
            ResultSet result = conn.createStatement()
                    .executeQuery(SQL_QUERY_PARENT_IDENTIFIER);

            while (result.next()) {
                String parentIdent = result.getString(1);

                if (CommonFunctions.stringHasData(parentIdent)) {
                    parentIdents.add(parentIdent);
                }
            }

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Could not get parentIdentifier list from database", ex);
        } finally {
            persistence.closeConnection(conn);
        }
        return parentIdents;
    }
}
