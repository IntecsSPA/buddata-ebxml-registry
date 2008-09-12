/*
 * Project: Buddata ebXML RegRep
 * Class: ClassificationDM.java
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
import be.kzen.ergorr.model.rim.ClassificationType;
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
@Entity(name = "Classification")
public class ClassificationDM extends RegistryObjectDM implements Serializable {

    protected String classifiedObject;
    protected String classificationScheme;
    protected String classificationNode;
    protected String nodeRepresentation;

    public ClassificationDM() {
    }

    public ClassificationDM(ClassificationType cl, RimDAO rimDAO) throws MappingException {
        super(cl, rimDAO);
        loadJaxb(cl);
    }

    public String getClassificationNode() {
        return classificationNode;
    }

    public void setClassificationNode(String classificationNode) {
        this.classificationNode = classificationNode;
    }

    public String getClassificationScheme() {
        return classificationScheme;
    }

    public void setClassificationScheme(String classificationScheme) {
        this.classificationScheme = classificationScheme;
    }

    public String getNodeRepresentation() {
        return nodeRepresentation;
    }

    public void setNodeRepresentation(String nodeRepresentation) {
        this.nodeRepresentation = nodeRepresentation;
    }

    public String getClassifiedObject() {
        return classifiedObject;
    }

    public void setClassifiedObject(String classifiedObject) {
        this.classifiedObject = classifiedObject;
    }

    @Override
    public JAXBElement<? extends IdentifiableType> createJAXBElement() {
        return OFactory.rim.createClassification(createJaxb(new ClassificationType()));
    }

    @Override
    public <T extends IdentifiableType> T createJaxb(T ident) {
        ClassificationType cl = (ClassificationType) super.createJaxb(ident);

        cl.setClassificationNode(classificationNode);
        cl.setClassificationScheme(classificationScheme);
        cl.setNodeRepresentation(nodeRepresentation);
        cl.setClassifiedObject(classifiedObject);

        return (T) cl;
    }

    public void loadJaxb(ClassificationType cl) {
        
        classifiedObject = cl.getClassifiedObject();

        if (cl.isSetClassificationNode()) {
            classificationNode = cl.getClassificationNode();
        }

        if (cl.isSetClassificationScheme()) {
            classificationScheme = cl.getClassificationScheme();
        }
    }
}
