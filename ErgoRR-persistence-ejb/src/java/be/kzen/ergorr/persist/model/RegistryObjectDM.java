/*
 * Project: Buddata ebXML RegRep
 * Class: RegistryObjectDM.java
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
package be.kzen.ergorr.persist.model;

import be.kzen.ergorr.exceptions.MappingException;
import be.kzen.ergorr.model.rim.ClassificationType;
import be.kzen.ergorr.model.rim.ExternalIdentifierType;
import be.kzen.ergorr.model.rim.IdentifiableType;
import be.kzen.ergorr.model.rim.InternationalStringType;
import be.kzen.ergorr.model.rim.LocalizedStringType;
import be.kzen.ergorr.model.rim.RegistryObjectType;
import be.kzen.ergorr.model.rim.VersionInfoType;
import be.kzen.ergorr.model.util.OFactory;
import be.kzen.ergorr.persist.dao.RimDAO;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.xml.bind.JAXBElement;

/**
 *
 * @author Yaman Ustuntas
 */
@Entity(name = "RegistryObject")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class RegistryObjectDM extends IdentifiableDM implements Serializable {

    protected String lid;
    protected String status;
    protected String objectType;
    protected String versionName;
    protected String versionComment;
    @OneToMany(mappedBy = "parent", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    protected Set<LocalizedStringNameDM> name;
    @OneToMany(mappedBy = "parent", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    protected Set<LocalizedStringDescDM> description;
    @Transient
    protected List<ExternalIdentifierDM> externalIdentifiers;
    @Transient
    protected List<ClassificationDM> classifications;

    public RegistryObjectDM() {
        lid = "";
        status = "";
        objectType = "";
    }

    public RegistryObjectDM(RegistryObjectType regObj, RimDAO rimDAO) throws MappingException {
        super(regObj, rimDAO);
        lid = "";
        status = "";
        objectType = "";
        loadJaxb(regObj);
    }

    public Set<LocalizedStringDescDM> getDescription() {
        return description;
    }

    public void setDescription(Set<LocalizedStringDescDM> description) {
        this.description = description;
    }

    public List<ClassificationDM> getClassifications() {
        return classifications;
    }

    public void setClassifications(List<ClassificationDM> classifications) {
        this.classifications = classifications;
    }

    public List<ExternalIdentifierDM> getExternalIdentifiers() {
        return externalIdentifiers;
    }

    public void setExternalIdentifiers(List<ExternalIdentifierDM> externalIdentifiers) {
        this.externalIdentifiers = externalIdentifiers;
    }

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public Set<LocalizedStringNameDM> getName() {
        return name;
    }

    public void setName(Set<LocalizedStringNameDM> name) {
        this.name = name;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVersionComment() {
        return versionComment;
    }

    public void setVersionComment(String versionComment) {
        this.versionComment = versionComment;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    private void fetchExternalIdetifiers() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        externalIdentifiers = rimDAO.query("select ei from ExternalIdentifier ei where ei.registryObject = :id", params, ExternalIdentifierDM.class);
    }

    private void fetchClassifications() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        classifications = rimDAO.query("select cl from Classification cl where cl.classifiedObject = :id", params, ClassificationDM.class);
    }

    @Override
    public JAXBElement<? extends IdentifiableType> createJAXBElement() {
        return OFactory.rim.createRegistryObject(createJaxb(new RegistryObjectType()));
    }

    @Override
    public <T extends IdentifiableType> T createJaxb(T ident) {
        RegistryObjectType regObj = (RegistryObjectType) super.createJaxb(ident);
        fetchClassifications();
        fetchExternalIdetifiers();
        regObj.setLid(lid);
        regObj.setStatus(status);
        regObj.setObjectType(objectType);

        if (!(this instanceof ClassificationDM) && !(this instanceof ExternalIdentifierDM) && !(this instanceof AssociationDM)) {

            if (versionComment != null || versionName != null) {
                VersionInfoType version = new VersionInfoType();
                version.setComment(versionComment);
                version.setVersionName(versionName);
                regObj.setVersionInfo(version);
            }

            if (getName() != null && !getName().isEmpty()) {
                InternationalStringType intStringName = new InternationalStringType();

                for (LocalizedStringNameDM n : name) {
                    intStringName.getLocalizedString().add(n.createJaxb());
                }

                regObj.setName(intStringName);
            }

            if (getDescription() != null && !getDescription().isEmpty()) {
                InternationalStringType intStringDesc = new InternationalStringType();

                for (LocalizedStringDescDM d : description) {
                    intStringDesc.getLocalizedString().add(d.createJaxb());
                }

                regObj.setDescription(intStringDesc);
            }

            if (getClassifications() != null) {
                for (ClassificationDM cl : classifications) {
                    cl.setRimDAO(rimDAO);
                    regObj.getClassification().add(cl.createJaxb(new ClassificationType()));
                }
            }

            if (getExternalIdentifiers() != null) {
                for (ExternalIdentifierDM ei : externalIdentifiers) {
                    ei.setRimDAO(rimDAO);
                    regObj.getExternalIdentifier().add(ei.createJaxb(new ExternalIdentifierType()));
                }
            }
        }

        return (T) regObj;
    }

    public void loadJaxb(RegistryObjectType regObj) throws MappingException {
        lid = regObj.getLid();
        status = regObj.getStatus();
        objectType = regObj.getObjectType();

        VersionInfoType versionInfo = regObj.getVersionInfo();

        if (versionInfo != null) {
            if (versionInfo.isSetComment()) {
                versionComment = versionInfo.getComment();
            }
            if (versionInfo.isSetVersionName()) {
                versionName = versionInfo.getVersionName();
            }
        }

        if (regObj.isSetName()) {
            if (name == null) {
                name = new HashSet<LocalizedStringNameDM>();
            }

            InternationalStringType nameIs = regObj.getName();

            for (LocalizedStringType localStr : nameIs.getLocalizedString()) {
                LocalizedStringNameDM localNameDM = new LocalizedStringNameDM();
                localNameDM.setCharset(localStr.getCharset());
                localNameDM.setLang(localStr.getLang());
                localNameDM.setValue(localStr.getValue());
                localNameDM.setParent(this);
                getName().add(localNameDM);
            }
        }

        if (regObj.isSetDescription()) {
            if (description == null) {
                description = new HashSet<LocalizedStringDescDM>();
            }

            InternationalStringType descIs = regObj.getDescription();

            for (LocalizedStringType localStr : descIs.getLocalizedString()) {
                LocalizedStringDescDM localDescDM = new LocalizedStringDescDM();
                localDescDM.setCharset(localStr.getCharset());
                localDescDM.setLang(localStr.getLang());
                localDescDM.setValue(localStr.getValue());
                localDescDM.setParent(this);
                getDescription().add(localDescDM);
            }
        }
    }
}
