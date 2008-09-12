/*
 * Project: Buddata ebXML RegRep
 * Class: RegistryPackageDM.java
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

import be.kzen.ergorr.commons.RIMConstants;
import be.kzen.ergorr.exceptions.MappingException;
import be.kzen.ergorr.model.rim.IdentifiableType;
import be.kzen.ergorr.model.rim.RegistryObjectListType;
import be.kzen.ergorr.model.rim.RegistryPackageType;
import be.kzen.ergorr.model.util.OFactory;
import be.kzen.ergorr.persist.dao.RimDAO;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.xml.bind.JAXBElement;
import org.apache.log4j.Logger;

/**
 *
 * @author Yaman Ustuntas
 */
@Entity(name = "RegistryPackage")
public class RegistryPackageDM extends RegistryObjectDM implements Serializable {

    private static Logger log = Logger.getLogger(RegistryObjectDM.class);
    @Transient
    protected List<IdentifiableDM> identifiables;

    public RegistryPackageDM() {
    }

    public RegistryPackageDM(RegistryPackageType pkg, RimDAO rimDAO) throws MappingException {
        super(pkg, rimDAO);
        loadJaxb(pkg);
    }

    private void fetchIdentifiables() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("assoType", RIMConstants.CN_ASSOCIATION_TYPE_ID_HasMember);
        String query = "select i from Identifiable i where i.id in " +
                "(select asso.targetObject from Association asso where asso.associationType = :assoType and " +
                "asso.sourceObject = :id)";
        
        identifiables = rimDAO.query(query, params, IdentifiableDM.class);
                
    }

    public void loadJaxb(RegistryPackageType pkg) {
    }

    @Override
    public <T extends IdentifiableType> T createJaxb(T ident) {
        fetchIdentifiables();
        RegistryPackageType pkg = (RegistryPackageType) super.createJaxb(ident);
        RegistryObjectListType regObjList = new RegistryObjectListType();
        pkg.setRegistryObjectList(regObjList);

        for (IdentifiableDM identDao : identifiables) {
            identDao.setRimDAO(rimDAO);
            regObjList.getIdentifiable().add(identDao.createJAXBElement());
        }

        return (T) pkg;
    }

    @Override
    public JAXBElement<? extends IdentifiableType> createJAXBElement() {
        return OFactory.rim.createRegistryPackage(createJaxb(new RegistryPackageType()));
    }
}
