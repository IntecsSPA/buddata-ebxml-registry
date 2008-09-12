/*
 * Project: Buddata ebXML RegRep
 * Class: ClassificationSchemeDM.java
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
import be.kzen.ergorr.model.rim.ClassificationNodeType;
import be.kzen.ergorr.persist.*;
import be.kzen.ergorr.model.rim.ClassificationSchemeType;
import be.kzen.ergorr.model.rim.IdentifiableType;
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
@Entity(name="ClassificationScheme")
public class ClassificationSchemeDM extends RegistryObjectDM implements Serializable {

    private static Logger log = Logger.getLogger(ClassificationSchemeDM.class);
    protected boolean isInternal;
    protected String nodeType;
    
    @Transient
    protected List<ClassificationNodeDM> classificationNodes;
    
    public ClassificationSchemeDM() {
        isInternal = false;
    }

    public ClassificationSchemeDM(ClassificationSchemeType cs, RimDAO rimDAO) throws MappingException {
        super(cs, rimDAO);
        isInternal = false;
        loadJaxb(cs);
    }

    private void fetchClassificationNodes() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        classificationNodes = rimDAO.query("select cn from ClassificationNode cn where cn.parent = :id", params, ClassificationNodeDM.class);
    }

    @Override
    public JAXBElement<? extends IdentifiableType> createJAXBElement() {
        return OFactory.rim.createClassificationScheme(createJaxb(new ClassificationSchemeType()));
    }

    @Override
    public <T extends IdentifiableType> T createJaxb(T ident) {
        ClassificationSchemeType cs = (ClassificationSchemeType) super.createJaxb(ident);
        fetchClassificationNodes();

        cs.setIsInternal(isInternal);
        cs.setNodeType(nodeType);
        
        if (classificationNodes == null) {
            fetchClassificationNodes();
        }

        for (ClassificationNodeDM cn : classificationNodes) {
            cn.setRimDAO(rimDAO);
            cs.getClassificationNode().add((ClassificationNodeType) cn.createJaxb(new ClassificationNodeType()));
        }

        return (T) cs;
    }

    public void loadJaxb(ClassificationSchemeType cs) throws MappingException {

        if (cs.isSetIsInternal()) {
            isInternal = cs.isIsInternal();
        }
        nodeType = cs.getNodeType();
    }
}
