/*
 * Project: Buddata ebXML RegRep
 * Class: ClassificationNodeDM.java
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
import be.kzen.ergorr.model.rim.ClassificationNodeType;
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
@Entity(name = "ClassificationNode")
public class ClassificationNodeDM extends RegistryObjectDM implements Serializable {
    private static Logger log = Logger.getLogger(ClassificationNodeDM.class);

    protected String code;
    protected String path_;
    protected String parent;

    @Transient
    protected List<ClassificationNodeDM> childClassificationNodes;
    @Transient
    protected RegistryObjectDM parentObject;
    
    public ClassificationNodeDM() {
    }

    public ClassificationNodeDM(ClassificationNodeType cn, RimDAO rimDAO) throws MappingException {
        super(cn, rimDAO);
        loadJaxb(cn);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    private void fetchChildClassificationNodes() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);

        childClassificationNodes = rimDAO.query("select cn from ClassificationNode cn where cn.parent = :id", params, ClassificationNodeDM.class);
    }

    public String getPath_() {
        return path_;
    }

    public void setPath_(String path_) {
        this.path_ = path_;
    }

    @Override
    public JAXBElement<? extends IdentifiableType> createJAXBElement() {
        return OFactory.rim.createClassificationNode(createJaxb(new ClassificationNodeType()));
    }

    @Override
    public <T extends IdentifiableType> T createJaxb(T ident) {
        fetchChildClassificationNodes();
        ClassificationNodeType cn = (ClassificationNodeType) super.createJaxb(ident);

        cn.setCode(code);
        cn.setPath(path_);

        for (ClassificationNodeDM child : childClassificationNodes) {
            child.setRimDAO(rimDAO);
            cn.getClassificationNode().add((ClassificationNodeType) child.createJaxb(new ClassificationNodeType()));
        }

        cn.setParent(parent);

        return (T) cn;
    }

    public void loadJaxb(ClassificationNodeType cn) throws MappingException {
        this.path_ = cn.getPath();
        this.code = cn.getCode();
        this.parent = cn.getParent();
    }
}
