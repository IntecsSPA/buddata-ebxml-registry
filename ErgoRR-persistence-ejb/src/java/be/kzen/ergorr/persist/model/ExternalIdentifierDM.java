/*
 * Project: Buddata ebXML RegRep
 * Class: ExternalIdentifierDM.java
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
import be.kzen.ergorr.persist.*;
import be.kzen.ergorr.model.rim.ExternalIdentifierType;
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
@Entity(name = "ExternalIdentifier")
public class ExternalIdentifierDM extends RegistryObjectDM implements Serializable {

    protected String registryObject;
    protected String identificationScheme;
    protected String value_;

    public ExternalIdentifierDM() {
    }

    public ExternalIdentifierDM(ExternalIdentifierType exIdent, RimDAO rimDAO) throws MappingException {
        super(exIdent, rimDAO);
        loadJaxb(exIdent);
    }

    public String getIdentificationScheme() {
        return identificationScheme;
    }

    public void setIdentificationScheme(String identificationScheme) {
        this.identificationScheme = identificationScheme;
    }

    public String getRegistryObject() {
        return registryObject;
    }

    public void setRegistryObject(String registryObject) {
        this.registryObject = registryObject;
    }

    public String getValue_() {
        return value_;
    }

    public void setValue_(String value_) {
        this.value_ = value_;
    }

    @Override
    public JAXBElement<? extends IdentifiableType> createJAXBElement() {
        return OFactory.rim.createExternalIdentifier(createJaxb(new ExternalIdentifierType()));
    }

    @Override
    public <T extends IdentifiableType> T createJaxb(T ident) {
        ExternalIdentifierType ei = (ExternalIdentifierType) super.createJaxb(ident);

        ei.setIdentificationScheme(identificationScheme);
        ei.setValue(value_);
        ei.setRegistryObject(registryObject);

        return (T) ei;
    }

    public void loadJaxb(ExternalIdentifierType exIdent) throws MappingException {
        value_ = exIdent.getValue();
        registryObject = exIdent.getRegistryObject();
        identificationScheme = exIdent.getIdentificationScheme();
    }
}
