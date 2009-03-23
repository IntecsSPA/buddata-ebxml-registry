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

import be.kzen.ergorr.commons.RIMConstants;
import be.kzen.ergorr.commons.IDGenerator;
import be.kzen.ergorr.commons.RequestContext;
import be.kzen.ergorr.exceptions.InvalidReferenceException;
import be.kzen.ergorr.exceptions.ReferenceExistsException;
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
import be.kzen.ergorr.service.validator.RimValidator;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        commit(regObjList);
    }

    /**
     * Update object list in the registy.
     *
     * @param regObjList List to update.
     * @throws be.kzen.ergorr.interfaces.soap.ServiceExceptionReport
     */
    public void update(RegistryObjectListType regObjList) throws ServiceExceptionReport {
        commit(regObjList);
    }

    /**
     * Handles submit and update of RegistryObjects.
     * Validates RegistryObjects for invalid object references.
     * Depending on if the object already exists in the registry
     * it will insert or update it accordingly.
     *
     * @param regObjList List to insert or update.
     * @throws be.kzen.ergorr.interfaces.soap.ServiceExceptionReport
     */
    public void commit(RegistryObjectListType regObjList) throws ServiceExceptionReport {
        List<IdentifiableType> idents = getIdentifiableList(regObjList.getIdentifiable());
        List<IdentifiableType> flatIdents = new ArrayList<IdentifiableType>();
        flatten(idents, flatIdents);
        loadMap(flatIdents);

        RimValidator validator = new RimValidator(flatIdents, identMap, requestContext);
        try {
            validator.validate();
        } catch (InvalidReferenceException ex) {
            logger.log(Level.WARNING, "Validation failed", ex);
            throw new ServiceExceptionReport(ex.getMessage(), ex);
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "SQL error while validating", ex);
            throw new ServiceExceptionReport(ex.getMessage(), ex);
        }

        List<String> ids = new ArrayList<String>();

        for (IdentifiableType ident : flatIdents) {
            ids.add(ident.getId());
        }

        SqlPersistence persistence = new SqlPersistence(requestContext);

        try {
            List<JAXBElement<? extends IdentifiableType>> existingIdentEls = persistence.getByIds(ids);

            for (IdentifiableType flatIdent : flatIdents) {
                boolean isNewObject = true;

                for (JAXBElement<? extends IdentifiableType> existingIdentEl : existingIdentEls) {
                    if (existingIdentEl.getValue().getId().equals(flatIdent.getId())) {
                        isNewObject = false;

                        if (!existingIdentEl.getValue().getClass().equals(flatIdent.getClass())) {
                            String err = flatIdent.getClass().getSimpleName() + " with ID " + flatIdent.getId() +
                                    " has a different type then the existing object: " + existingIdentEl.getValue().getClass().getSimpleName();
                            throw new ServiceExceptionReport(err);
                        }
                        break;
                    }
                }
                flatIdent.setNewObject(isNewObject);
            }

            persistence.persist(flatIdents);
            updateSlotCache(identMap.get(ExtrinsicObjectType.class.getName()));
            insertRepositoryItems();
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error while committing data", ex);
            throw new ServiceExceptionReport(ex.getMessage(), ex);
        }
    }

    /**
     * Deletes the list of Identifiables.
     *
     * @param regObjList Identifiables to delete.
     * @throws be.kzen.ergorr.interfaces.soap.ServiceExceptionReport
     */
    public void delete(RegistryObjectListType regObjList) throws ServiceExceptionReport {
        List<IdentifiableType> idents = getIdentifiableList(regObjList.getIdentifiable());
        List<IdentifiableType> flatIdents = new ArrayList<IdentifiableType>();
        flatten(idents, flatIdents);

        RimValidator validator = new RimValidator(flatIdents, requestContext);

        try {
            validator.validateToDelete();
            SqlPersistence persistence = new SqlPersistence(requestContext);
            persistence.delete(flatIdents);

        } catch (ReferenceExistsException ex) {
            logger.log(Level.SEVERE, "Cannot delete object because of existing references", ex);
            throw new ServiceExceptionReport(ex.getMessage(), ex);
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "SQL exception while deleting objects", ex);
            throw new ServiceExceptionReport(ex.getMessage(), ex);
        }
    }

    /**
     * Puts the RepositoryItems of WrsExtrinsicObjects to the repository.
     */
    private void insertRepositoryItems() {
        List<IdentifiableType> eoList = identMap.get(WrsExtrinsicObjectType.class.getName());

        if (eoList != null) {
            RepositoryManager repoManager = new RepositoryManager();

            for (IdentifiableType ident : eoList) {
                WrsExtrinsicObjectType eo = (WrsExtrinsicObjectType) ident;

                if (eo.getRepositoryItem() != null) {
                    try {
                        repoManager.save(eo.getId(), eo.getMimeType(), eo.getRepositoryItem());
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

    /**
     * Utility method to get list of Identifiables from JAXBElements.
     *
     * @param identEls JAXBElement to get Identifiables from.
     * @return List of Identifiables.
     */
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

    private void updateSlotCache(List<IdentifiableType> idents) {
        if (idents != null) {
            for (IdentifiableType ident : idents) {
                ExtrinsicObjectType eo = (ExtrinsicObjectType) ident;

                if (eo.getObjectType().equals(RIMConstants.CN_OBJ_DEF)) {
                    for (SlotType slot : eo.getSlot()) {
                        try {
                            InternalSlotTypes.getInstance().putSlot(slot.getName(), slot.getSlotType());
                        } catch (Exception ex) {
                            logger.log(Level.WARNING, "Error while updating slot type cache", ex);
                        }
                    }
                }
            }
        }
    }
}
