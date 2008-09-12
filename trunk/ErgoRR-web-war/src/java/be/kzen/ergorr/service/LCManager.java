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
import be.kzen.ergorr.interfaces.soap.ServiceExceptionReport;
import be.kzen.ergorr.model.rim.AssociationType1;
import be.kzen.ergorr.model.rim.ClassificationNodeType;
import be.kzen.ergorr.model.rim.ClassificationSchemeType;
import be.kzen.ergorr.model.rim.ClassificationType;
import be.kzen.ergorr.model.rim.ExternalIdentifierType;
import be.kzen.ergorr.model.rim.IdentifiableType;
import be.kzen.ergorr.model.rim.RegistryObjectListType;
import be.kzen.ergorr.model.rim.RegistryObjectType;
import be.kzen.ergorr.model.rim.RegistryPackageType;
import be.kzen.ergorr.model.rim.SlotType1;
import be.kzen.ergorr.model.util.JAXBUtil;
import be.kzen.ergorr.persist.dao.RimDAO;
import be.kzen.ergorr.persist.model.IdentifiableDM;
import be.kzen.ergorr.commons.SlotTypes;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import org.apache.log4j.Logger;

/**
 * Life Cycle Manager for RIM objects.
 * 
 * @author Yaman Ustuntas
 */
public class LCManager {

    private static Logger log = Logger.getLogger(LCManager.class);
    private RimDAO rimDao;

    public LCManager(RimDAO rimDao) {
        this.rimDao = rimDao;
    }

    public void submit(RegistryObjectListType regObjList) throws ServiceExceptionReport {
        List<IdentifiableType> idents = getIdentifiableList(regObjList.getIdentifiable());
        List<IdentifiableType> flatIdents = new ArrayList<IdentifiableType>();

        flatten(idents, flatIdents);

        // check if all slot name/type pairs are unique.
        for (IdentifiableType ident : flatIdents) {
            for (SlotType1 s : ident.getSlot()) {
                try {
                    SlotTypes.getInstance().putSlotType(s.getName(), s.getSlotType());
                } catch (Exception ex) {
                    throw new ServiceExceptionReport(ex.getMessage());
                }
            }
        }

        for (IdentifiableType ident : flatIdents) {

            if (log.isTraceEnabled()) {
                log.trace("submitting jaxb object");
                try {
                    log.trace(JAXBUtil.getInstance().marshall(ident));
                } catch (Exception ex) {
                    log.trace("Error while marshalling JAXBElement", ex);
                }
            }

            try {
                Class dmClass = Class.forName(ident.getDatabaseModelClass());
                Constructor constructor = dmClass.getConstructor(ident.getClass(), RimDAO.class);
                IdentifiableDM identDM = (IdentifiableDM) constructor.newInstance(ident, rimDao);
                rimDao.save(identDM);
            } catch (Exception ex) {
                throw new ServiceExceptionReport("Could not load class " + ident.getDatabaseModelClass() + " for type " + ident.getClass().getSimpleName(), ex);
            }
        }
    }

    private void validate(List<IdentifiableType> idents) {

        for (IdentifiableType ident : idents) {
            if (ident instanceof RegistryObjectType) {
            }
        }
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
                        AssociationType1 asso = new AssociationType1();
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
