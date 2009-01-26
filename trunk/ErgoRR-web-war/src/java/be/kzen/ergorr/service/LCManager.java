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
import be.kzen.ergorr.model.rim.SlotType;
import be.kzen.ergorr.persist.InternalSlotTypes;
import be.kzen.ergorr.persist.service.SqlPersistence;
import be.kzen.ergorr.query.QueryManager;
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

    public LCManager(RequestContext requestContext) {
        this.requestContext = requestContext;
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

        for (IdentifiableType ident : flatIdents) {
            if (ident instanceof RegistryObjectType) {
                RegistryObjectType ro = (RegistryObjectType) ident;
                ro.setStatus(RIMConstants.CN_STATUS_TYPE_ID_Submitted);

                if (!ro.isSetLid()) {
                    ro.setLid(ro.getId());
                }
            }

            if (ident instanceof ExtrinsicObjectType &&
                    ((ExtrinsicObjectType) ident).getObjectType().equals(RIMConstants.CN_OBJ_DEF)) {

                for (SlotType s : ident.getSlot()) {
                    try {
                        // check if all slot name/type pairs are unique.
                        InternalSlotTypes.getInstance().putSlot(s.getName(), s.getSlotType());
                    } catch (Exception ex) {
                        throw new ServiceExceptionReport(ex.getMessage());
                    }
                }
            }
        }

        SqlPersistence service = new SqlPersistence(requestContext);

        try {
            service.insert(flatIdents);
        } catch (SQLException ex) {
            throw new ServiceExceptionReport("Could not insert objects", ex);
        }
    }

    private boolean validateRelations(IdentifiableType ident, List<IdentifiableType> insertedObjs) throws ServiceExceptionReport {
        if (ident instanceof RegistryObjectType) {
            RegistryObjectType regObj = (RegistryObjectType) ident;

        }

        if (ident instanceof AssociationType) {
            AssociationType asso = (AssociationType) ident;
            validateAssociation(asso, insertedObjs);
        }

        return true;
    }

    private boolean validateAssociation(AssociationType asso, List<IdentifiableType> insertedObjs) throws ServiceExceptionReport {
        boolean sourceObjValid = false;
        boolean targetObjValid = false;

        for (IdentifiableType insIdent : insertedObjs) {
            if (asso.getSourceObject().equals(insIdent.getId())) {
                sourceObjValid = true;
            }
            if (asso.getTargetObject().equals(insIdent.getId())) {
                targetObjValid = true;
            }
            if (sourceObjValid && targetObjValid) {
                break;
            }
        }


        if (!sourceObjValid || !targetObjValid) {
            List<String> ids = new ArrayList<String>();
            if (!sourceObjValid) {
                ids.add(asso.getSourceObject());
            }
            if (!targetObjValid) {
                ids.add(asso.getTargetObject());
            }

            List<String> dbIds = null;
            SqlPersistence persistence = new SqlPersistence(requestContext);
            try {
                dbIds = persistence.idsExist(ids);
            } catch (SQLException ex) {
                throw new ServiceExceptionReport("Could not fetch objects", ex);
            }

            if (dbIds.size() != ids.size()) {
                String error = "association " + asso.getId();

                if (!dbIds.contains(asso.getSourceObject())) {
                    error += " has invalid sourceObject '" + asso.getSourceObject() + "'";
                }
                if (!dbIds.contains(asso.getTargetObject())) {
                    error += " has invalid targetObject '" + asso.getTargetObject() + "'";
                }

                throw new ServiceExceptionReport(error);
            }
        }

        return true;
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


    // check if exists
    }

    public void delete(RegistryObjectListType regObjList) {

        for (JAXBElement<? extends IdentifiableType> identEl : regObjList.getIdentifiable()) {
            IdentifiableType ident = identEl.getValue();
            String sql = "select id from association where sourceobject ='" + ident.getId() + "' or targetobject = '" + ident.getId() + "'";


        }
    }

    /**
     * Validate that all the references in the identifiables are valid.
     * 
     * @param idents Identifiables to validate.
     * @throws be.kzen.ergorr.interfaces.soap.ServiceExceptionReport
     */
    private void validate(List<IdentifiableType> idents) throws ServiceExceptionReport {

        for (IdentifiableType ident : idents) {
            if (ident instanceof RegistryObjectType) {
                String objectType = ((RegistryObjectType) ident).getObjectType();

                if (!findIdentifiableWithId(objectType, idents)) {
                    throw new ServiceExceptionReport("Could not find objectType '" + objectType + "'");
                }
            }
        }
    }

    /**
     * Find an identifiable with <code>id</code> in the submitted
     * identifiables <code>idents</code> or in objects in the registry.
     * 
     * @param id ID to find.
     * @param idents Submitted identifiables
     * @return True if identifiable with <code>id</code> exists.
     * @throws be.kzen.ergorr.interfaces.soap.ServiceExceptionReport
     */
    private boolean findIdentifiableWithId(String id, List<IdentifiableType> idents) throws ServiceExceptionReport {
        boolean found = false;

        for (IdentifiableType ident : idents) {
            if (ident.getId().equals(id)) {
                found = true;
                break;
            }
        }

        if (!found) {
            SqlPersistence service = new SqlPersistence(requestContext);
            List<String> ids = new ArrayList<String>();

            try {
                List<JAXBElement<? extends IdentifiableType>> identEls = service.getByIds(ids);
                found = identEls.size() > 0;
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, null, ex);
                throw new ServiceExceptionReport("Error searching for IDs", ex);
            }
        }

        return found;
    }

    /**
     * Flatten the object tree in <code>idents</code> to a flat list <code>flatIdents</code>.
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
}
