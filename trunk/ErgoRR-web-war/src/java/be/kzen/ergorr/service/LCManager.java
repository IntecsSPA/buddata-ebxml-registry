/*
 * Project: Buddata ebXML RegRep
 * Class: LCManager.java
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
import be.kzen.ergorr.commons.RIMConstants;
import be.kzen.ergorr.commons.IDGenerator;
import be.kzen.ergorr.commons.RequestContext;
import be.kzen.ergorr.exceptions.InvalidReferenceException;
import be.kzen.ergorr.interfaces.soap.ServiceExceptionReport;
import be.kzen.ergorr.model.rim.AssociationType;
import be.kzen.ergorr.model.rim.ClassificationNodeType;
import be.kzen.ergorr.model.rim.ClassificationSchemeType;
import be.kzen.ergorr.model.rim.ClassificationType;
import be.kzen.ergorr.model.rim.ExternalIdentifierType;
import be.kzen.ergorr.model.rim.ExtrinsicObjectType;
import be.kzen.ergorr.model.rim.IdentifiableType;
import be.kzen.ergorr.model.rim.RegistryObjectListType;
import be.kzen.ergorr.model.rim.RegistryObjectType;
import be.kzen.ergorr.model.rim.RegistryPackageType;
import be.kzen.ergorr.model.rim.ServiceBindingType;
import be.kzen.ergorr.model.rim.ServiceType;
import be.kzen.ergorr.model.rim.SlotType;
import be.kzen.ergorr.model.rim.SpecificationLinkType;
import be.kzen.ergorr.model.wrs.WrsExtrinsicObjectType;
import be.kzen.ergorr.persist.InternalSlotTypes;
import be.kzen.ergorr.persist.service.SqlPersistence;
import be.kzen.ergorr.query.QueryManager;
import be.kzen.ergorr.service.validator.RimValidator;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.xml.bind.JAXBElement;

/**
 * Life Cycle Manager for RIM objects.
 * 
 * @author Yaman Ustuntas
 */
public class LCManager {

    private static Logger logger = Logger.getLogger(LCManager.class.getName());
    private RequestContext requestContext;
    private Map<String, List<IdentifiableType>> identMap;

    public LCManager(RequestContext requestContext) {
        this.requestContext = requestContext;
        identMap = new HashMap<String, List<IdentifiableType>>();
    }

    /**
     * Submit object list to the registry.
     * 
     * @param regObjList List to submit.
     * @throws be.kzen.ergorr.interfaces.soap.ServiceExceptionReport
     */
    public void submit(RegistryObjectListType regObjList) throws ServiceExceptionReport {
        List<IdentifiableType> idents = getIdentifiableList(regObjList.getIdentifiable());
        List<IdentifiableType> flatIdents = new ArrayList<IdentifiableType>();

        flatten(idents, flatIdents);
        loadMap(flatIdents);
        RimValidator validator = new RimValidator(flatIdents, identMap, requestContext);
        try {
            validator.validate();
            SqlPersistence service = new SqlPersistence(requestContext);
            service.insert(flatIdents);
            insertRepositoryItems();
        } catch (SQLException ex) {
            throw new ServiceExceptionReport("Could not insert objects", ex);
        } catch (InvalidReferenceException ex) {
            throw new ServiceExceptionReport("Could not validate objects", ex);
        }
    }

    public void update(RegistryObjectListType regObjList) throws ServiceExceptionReport {
        List<String> ids = new ArrayList<String>();
        List<JAXBElement<? extends IdentifiableType>> updateIdentEls = regObjList.getIdentifiable();
        RegistryObjectListType newObjList = new RegistryObjectListType();

        for (JAXBElement<? extends IdentifiableType> identEl : updateIdentEls) {
            ids.add(identEl.getValue().getId());
        }

        SqlPersistence persist = new SqlPersistence(requestContext);
        try {
            List<JAXBElement<? extends IdentifiableType>> existingIdentEls = persist.getByIds(ids);

            for (JAXBElement<? extends IdentifiableType> identEl : regObjList.getIdentifiable()) {
                boolean exists = false;

                for (JAXBElement<? extends IdentifiableType> existingIdentEl : existingIdentEls) {
                    if (existingIdentEl.getValue().getId().equals(identEl.getValue().getId())) {
                        exists = true;
                        break;
                    }
                }

                if (!exists) {
                }
            }
        } catch (SQLException ex) {
            logger.log(Level.WARNING, "Could not fetch object to update by ID");
            throw new ServiceExceptionReport(ex.getMessage(), ex);
        }
    }

    public void delete(RegistryObjectListType regObjList) {

        for (JAXBElement<? extends IdentifiableType> identEl : regObjList.getIdentifiable()) {
            IdentifiableType ident = identEl.getValue();
            String sql = "select id from association where sourceobject ='" + ident.getId() + "' or targetobject = '" + ident.getId() + "'";


        }
    }

    private void insertRepositoryItems() {
        List<IdentifiableType> eoList = identMap.get(WrsExtrinsicObjectType.class.getName());

        if (eoList != null) {
            RepositoryManager repoManager = new RepositoryManager();

            for (IdentifiableType ident : eoList) {
                WrsExtrinsicObjectType eo = (WrsExtrinsicObjectType) ident;

                if (eo.getRepositoryItem() != null) {
                    try {
                        repoManager.save(eo.getId(), eo.getRepositoryItem());
                    } catch (IOException ex) {
                        logger.log(Level.SEVERE, "Could not save RepositoryItem of object: " + eo.getId(), ex);
                    }
                }
            }
        }
    }

    /**
     * Flatten the object trees in <code>idents</code> to a flat list <code>flatIdents</code>.
     * Example: RegistryObject can have nested Classification.
     *
     * @param idents IdentifiableType tree.
     * @param flatIdents Flat list of IdentifiableType.
     */
    private void flatten(List<? extends IdentifiableType> idents, List<IdentifiableType> flatIdents) {
        for (IdentifiableType ident : idents) {

            if (ident instanceof RegistryObjectType) {
                RegistryObjectType ro = (RegistryObjectType) ident;

                if (ro.isSetClassification()) {
                    for (ClassificationType cl : ro.getClassification()) {
                        cl.setClassifiedObject(ro.getId());
                        flatIdents.add(cl);
                    }
                    ro.unsetClassification();
                }

                if (ro.isSetExternalIdentifier()) {
                    for (ExternalIdentifierType ei : ro.getExternalIdentifier()) {
                        ei.setRegistryObject(ro.getId());
                        flatIdents.add(ei);
                    }
                    ro.unsetExternalIdentifier();
                }
            }

            if (ident instanceof RegistryPackageType) {
                RegistryPackageType rp = (RegistryPackageType) ident;

                if (rp.isSetRegistryObjectList() && rp.getRegistryObjectList().isSetIdentifiable() &&
                        !rp.getRegistryObjectList().getIdentifiable().isEmpty()) {

                    for (JAXBElement<? extends IdentifiableType> identEl : rp.getRegistryObjectList().getIdentifiable()) {
                        AssociationType asso = new AssociationType();
                        String id = IDGenerator.generateUuid();
                        asso.setId(id);
                        asso.setLid(id);
                        asso.setAssociationType(RIMConstants.CN_ASSOCIATION_TYPE_ID_HasMember);
                        asso.setSourceObject(rp.getId());
                        asso.setTargetObject(identEl.getValue().getId());
                        flatIdents.add(asso);
                    }
                    flatten(getIdentifiableList(rp.getRegistryObjectList().getIdentifiable()), flatIdents);
                    rp.setRegistryObjectList(null);
                }
            }

            if (ident instanceof ClassificationSchemeType) {
                ClassificationSchemeType cs = (ClassificationSchemeType) ident;

                if (cs.isSetClassificationNode() && !cs.getClassificationNode().isEmpty()) {
                    // Make sure that the child ClassificationNode parent attribute is set to
                    // the ID of the parent ClassificationScheme <code>cs</code>.
                    for (ClassificationNodeType cn : cs.getClassificationNode()) {
                        cn.setParent(cs.getId());
                    }
                    flatten(cs.getClassificationNode(), flatIdents);
                    cs.unsetClassificationNode();
                }
            }

            if (ident instanceof ClassificationNodeType) {
                ClassificationNodeType cn = (ClassificationNodeType) ident;

                if (cn.isSetClassificationNode() && !cn.getClassificationNode().isEmpty()) {
                    for (ClassificationNodeType childCn : cn.getClassificationNode()) {
                        childCn.setParent(cn.getId());
                        flatIdents.add(childCn);

                        if (childCn.isSetClassificationNode() && !childCn.getClassificationNode().isEmpty()) {
                            // Make sure that the child ClassificationNode parent attribute is set to
                            // the ID of the parent ClassificationNode <code>cn</code>.
                            for (ClassificationNodeType childOfChildCn : childCn.getClassificationNode()) {
                                childOfChildCn.setParent(childCn.getId());
                            }
                            flatten(childCn.getClassificationNode(), flatIdents);
                        }
                    }

                    cn.unsetClassificationNode();
                }
            }

            if (ident instanceof ServiceType) {
                ServiceType service = (ServiceType) ident;

                if (!service.getServiceBinding().isEmpty()) {
                    List<ServiceBindingType> bindings = service.getServiceBinding();

                    for (ServiceBindingType binding : bindings) {
                        binding.setService(service.getId());
                    }
                    flatten(bindings, flatIdents);
                    service.unsetServiceBinding();
                }
            }

            if (ident instanceof ServiceBindingType) {
                ServiceBindingType binding = (ServiceBindingType) ident;

                for (SpecificationLinkType specLink : binding.getSpecificationLink()) {
                    specLink.setServiceBinding(binding.getId());
                    flatIdents.add(specLink);
                }
            }

            flatIdents.add(ident);
        }
    }

    private List<IdentifiableType> getIdentifiableList(List<JAXBElement<? extends IdentifiableType>> identEls) {
        List<IdentifiableType> idents = new ArrayList<IdentifiableType>();

        for (JAXBElement<? extends IdentifiableType> identEl : identEls) {
            idents.add(identEl.getValue());
        }

        return idents;
    }

    /**
     * Load <code>indetMap</code> with <code>idents</code>
     * sorting them by RIM type.
     */
    private void loadMap(List<IdentifiableType> idents) {
        for (IdentifiableType ident : idents) {
            String key = ident.getClass().getName();

            List<IdentifiableType> list = identMap.get(key);

            if (list == null) {
                list = new ArrayList<IdentifiableType>();
                identMap.put(key, list);
            }

            list.add(ident);
        }
    }
}
