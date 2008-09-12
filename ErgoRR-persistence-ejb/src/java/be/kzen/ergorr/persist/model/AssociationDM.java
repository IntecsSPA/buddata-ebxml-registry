/*
 * Project: Buddata ebXML RegRep
 * Class: AssociationDM.java
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
import be.kzen.ergorr.model.rim.AssociationType1;
import be.kzen.ergorr.model.rim.IdentifiableType;
import be.kzen.ergorr.model.util.OFactory;
import be.kzen.ergorr.persist.dao.RimDAO;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.xml.bind.JAXBElement;

/**
 *
 * @author Yaman Ustuntas
 */
@Entity(name="Association")
public class AssociationDM extends RegistryObjectDM implements Serializable {

    protected String associationType;
    protected String targetObject;
    protected String sourceObject;

    public AssociationDM() {
    }
    
    public AssociationDM(AssociationType1 asso, RimDAO rimDAO) throws MappingException {
        super(asso, rimDAO);
        loadJaxb(asso);
    }

    public String getAssociationType() {
        return associationType;
    }

    public void setAssociationType(String associationType) {
        this.associationType = associationType;
    }

    public String getSourceObject() {
        return sourceObject;
    }

    public void setSourceObject(String sourceObject) {
        this.sourceObject = sourceObject;
    }

    public String getTargetObject() {
        return targetObject;
    }

    public void setTargetObject(String targetObject) {
        this.targetObject = targetObject;
    }
    
    @Override
    public JAXBElement<? extends IdentifiableType> createJAXBElement() {
        return OFactory.rim.createAssociation(createJaxb(new AssociationType1()));
    }
    
    @Override
    public <T extends IdentifiableType> T createJaxb(T ident) {
        AssociationType1 asso = (AssociationType1) super.createJaxb(ident);
        asso.setAssociationType(associationType);
        asso.setTargetObject(targetObject);
        asso.setSourceObject(sourceObject);
        
        return (T) asso;
    }
    
    public void loadJaxb(AssociationType1 asso) {
        
        if (asso.isSetAssociationType()) {
            associationType = asso.getAssociationType();
        }
        
        if (asso.isSetTargetObject()) {
            targetObject = asso.getTargetObject();
        }
        
        if (asso.isSetSourceObject()) {
            sourceObject = asso.getSourceObject();
        }
    }
}
