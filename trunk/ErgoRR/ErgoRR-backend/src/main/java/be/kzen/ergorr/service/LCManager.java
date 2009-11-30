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
import be.kzen.ergorr.commons.InternalConstants;
import be.kzen.ergorr.commons.RequestContext;
import be.kzen.ergorr.exceptions.ErrorCodes;
import be.kzen.ergorr.exceptions.InvalidReferenceException;
import be.kzen.ergorr.exceptions.ReferenceExistsException;
import be.kzen.ergorr.exceptions.ServiceException;
import be.kzen.ergorr.model.rim.AssociationType;
import be.kzen.ergorr.model.rim.ClassificationNodeType;
import be.kzen.ergorr.model.rim.ClassificationSchemeType;
import be.kzen.ergorr.model.rim.ClassificationType;
import be.kzen.ergorr.model.rim.ExternalIdentifierType;
import be.kzen.ergorr.model.rim.ExtrinsicObjectType;
import be.kzen.ergorr.model.rim.IdentifiableType;
import be.kzen.ergorr.model.rim.ObjectRefType;
import be.kzen.ergorr.model.rim.RegistryObjectListType;
import be.kzen.ergorr.model.rim.RegistryObjectType;
import be.kzen.ergorr.model.rim.RegistryPackageType;
import be.kzen.ergorr.model.rim.ServiceBindingType;
import be.kzen.ergorr.model.rim.ServiceType;
import be.kzen.ergorr.model.rim.SlotType;
import be.kzen.ergorr.model.rim.SpecificationLinkType;
import be.kzen.ergorr.model.util.JAXBUtil;
import be.kzen.ergorr.model.wrs.WrsExtrinsicObjectType;
import be.kzen.ergorr.persist.InternalSlotTypes;
import be.kzen.ergorr.persist.service.SqlPersistence;
import be.kzen.ergorr.service.validator.RimValidator;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

    /**
     * Constructor
     *
     * @param requestContext Request context.
     */
    public LCManager(RequestContext requestContext) {
        this.requestContext = requestContext;
    }

    /**
     * Submit object list to the registry.
     * 
     * @param regObjList List to submit.
     * @throws be.kzen.ergorr.exceptions.ServiceException
     */
    public void submit(RegistryObjectListType regObjList) throws ServiceException {
        commit(regObjList);
    }

    /**
     * Update object list in the registy.
     *
     * @param regObjList List to update.
     * @throws be.kzen.ergorr.exceptions.ServiceException
     */
    public void update(RegistryObjectListType regObjList) throws ServiceException {
        commit(regObjList);
    }

    /**
     * Handles submit and update of RegistryObjects.
     * Validates RegistryObjects for invalid object references.
     * Depending on if the object already exists in the registry
     * it will insert or update it accordingly.
     *
     * @param regObjList List to insert or update.
     * @throws be.kzen.ergorr.exceptions.ServiceException
     */
    public void commit(RegistryObjectListType regObjList) throws ServiceException {
        List<IdentifiableType> idents = JAXBUtil.getExtendedObjects(regObjList.getIdentifiable());
        List<IdentifiableType> flatIdents = new ArrayList<IdentifiableType>();
        flatten(idents, flatIdents, true);

        RimValidator validator = new RimValidator(flatIdents, requestContext);
        try {
            validator.validate();
        } catch (InvalidReferenceException ex) {
            logger.log(Level.WARNING, "Validation failed", ex);
            throw new ServiceException(ErrorCodes.TRANSACTION_FAILED, ex.getMessage(), ex);
        } catch (SQLException ex) {
            String err = "SQL error while validating";
            logger.log(Level.SEVERE, err, ex);
            throw new ServiceException(ErrorCodes.TRANSACTION_FAILED, err, ex);
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
                            throw new ServiceException(ErrorCodes.TRANSACTION_FAILED, err);
                        }
                        break;
                    }
                }
                flatIdent.setNewObject(isNewObject);
            }

            List<ExtrinsicObjectType> slotDescEos = getSlotDescExtrinsicObjects(flatIdents);
            validToCache(slotDescEos);
            persistence.persist(flatIdents);
            updateSlotCache(slotDescEos);
            insertRepositoryItems(flatIdents);
        } catch (SQLException ex) {
            String err = "Error while committing data";
            logger.log(Level.WARNING, err, ex);
            throw new ServiceException(ErrorCodes.TRANSACTION_FAILED, err, ex);
        }
    }

    /**
     * Deletes the list of Identifiables.
     *
     * @param regObjList Identifiables to delete.
     * @throws be.kzen.ergorr.exceptions.ServiceException
     */
    public List<IdentifiableType> delete(RegistryObjectListType regObjList) throws ServiceException {
        List<IdentifiableType> idents = JAXBUtil.getExtendedObjects(regObjList.getIdentifiable());
        List<IdentifiableType> flatIdents = new ArrayList<IdentifiableType>();
        flatten(idents, flatIdents, true);

        RimValidator validator = new RimValidator(flatIdents, requestContext);

        try {
            validator.validateToDelete();
            SqlPersistence persistence = new SqlPersistence(requestContext);
            persistence.delete(flatIdents);

        } catch (ReferenceExistsException ex) {
            logger.log(Level.WARNING, "Cannot delete object because of existing references", ex);
            throw new ServiceException(ErrorCodes.TRANSACTION_FAILED, ex.getMessage(), ex);
        } catch (SQLException ex) {
            String err = "SQL exception while deleting objects";
            logger.log(Level.WARNING, err, ex);
            throw new ServiceException(ErrorCodes.TRANSACTION_FAILED, err, ex);
        }

        return flatIdents;
    }

    /**
     * Puts the RepositoryItems of WrsExtrinsicObjects to the repository.
     */
    private void insertRepositoryItems(List<IdentifiableType> idents) {
        RepositoryManager repoManager = null;
        String deployName = (String) requestContext.getParam(InternalConstants.DEPLOY_NAME);

        if (deployName == null) {
            repoManager = new RepositoryManager();
        } else {
            repoManager = new RepositoryManager(deployName);
        }

        for (IdentifiableType ident : idents) {

            if (ident instanceof WrsExtrinsicObjectType) {
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
     * @param removeNested Remove nested object which are flattened and added to {@code flatIdents}.
     */
    public static void flatten(List<? extends IdentifiableType> idents, List<IdentifiableType> flatIdents, boolean removeNested) {
        for (IdentifiableType ident : idents) {

            if (ident instanceof ObjectRefType) {
                continue; // TODO: quickfix, for now ignore ObjectRefs :)
            }

            if (ident instanceof RegistryObjectType) {
                RegistryObjectType ro = (RegistryObjectType) ident;

                if (ro.isSetClassification()) {
                    for (ClassificationType cl : ro.getClassification()) {
                        cl.setClassifiedObject(ro.getId());
                        flatIdents.add(cl);
                    }
                    if (removeNested) {
                        ro.unsetClassification();
                    }
                }

                if (ro.isSetExternalIdentifier()) {
                    for (ExternalIdentifierType ei : ro.getExternalIdentifier()) {
                        ei.setRegistryObject(ro.getId());
                        flatIdents.add(ei);
                    }
                    if (removeNested) {
                        ro.unsetExternalIdentifier();
                    }
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
                    flatten(JAXBUtil.getExtendedObjects(rp.getRegistryObjectList().getIdentifiable()), flatIdents, removeNested);

                    if (removeNested) {
                        rp.setRegistryObjectList(null);
                    }
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
                    flatten(cs.getClassificationNode(), flatIdents, removeNested);
                    if (removeNested) {
                        cs.unsetClassificationNode();
                    }
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
                            flatten(childCn.getClassificationNode(), flatIdents, removeNested);
                        }
                    }

                    if (removeNested) {
                        cn.unsetClassificationNode();
                    }
                }
            }

            if (ident instanceof ServiceType) {
                ServiceType service = (ServiceType) ident;

                if (!service.getServiceBinding().isEmpty()) {
                    List<ServiceBindingType> bindings = service.getServiceBinding();

                    for (ServiceBindingType binding : bindings) {
                        binding.setService(service.getId());
                    }
                    flatten(bindings, flatIdents, removeNested);

                    if (removeNested) {
                        service.unsetServiceBinding();
                    }
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
     * Get the ExtrinsicObject from the {@code flatIdents} which
     * have the object type to define slots.
     *
     * @param flatIdents List of objects to search.
     * @return ExtrinsicObject which describe slots of an object.
     */
    private List<ExtrinsicObjectType> getSlotDescExtrinsicObjects(List<IdentifiableType> flatIdents) {
        List<ExtrinsicObjectType> eos = new ArrayList<ExtrinsicObjectType>();

        for (IdentifiableType ident : flatIdents) {
            if (ident instanceof ExtrinsicObjectType) {
                if (((ExtrinsicObjectType) ident).getObjectType().equals(RIMConstants.CN_OBJ_DEF)) {
                    eos.add((ExtrinsicObjectType) ident);
                }
            }
        }

        return eos;
    }
    
    /**
     * Updates the slot cache if needed.
     *
     * @param idents List of processed identifiables.
     */
    private void updateSlotCache(List<ExtrinsicObjectType> eos) {
        for (ExtrinsicObjectType eo : eos) {
            for (SlotType slot : eo.getSlot()) {
                try {
                    InternalSlotTypes.getInstance().putSlot(slot.getName(), slot.getSlotType());
                } catch (Exception ex) {
                    logger.log(Level.SEVERE, "Error while updating slot type cache", ex);
                }
            }
        }
    }

    /**
     * Checks if slots of {@code eos} are valid to cache.
     * {@code eos} list should be ExtrinsicObjects with objectType {@code RIMConstants.CN_OBJ_DEF}.
     * The content is valid if:
     * - slot definition does not exist in the registry yet
     * - slot definition exists
     *
     * @param eos
     * @throws ServiceException
     */
    private void validToCache(List<ExtrinsicObjectType> eos) throws ServiceException {

        for (ExtrinsicObjectType eo : eos) {
            for (SlotType slot : eo.getSlot()) {
                String slotType = InternalSlotTypes.getInstance().getSlotType(slot.getName());

                if (slotType != null && !slotType.equals(slot.getSlotType())) {
                    String err = "Slot description with name '" + slot.getName() +
                            "' has existing type '" + slotType + "' but was inserted as '" + slot.getSlotType() + "'";
                    
                    logger.warning(err);
                    throw new ServiceException(err);
                }
            }
        }
    }
}
