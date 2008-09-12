/*
 * Project: Buddata ebXML RegRep
 * Class: ExtrinsicObjectDM.java
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
import be.kzen.ergorr.model.rim.ExtrinsicObjectType;
import be.kzen.ergorr.model.rim.IdentifiableType;
import be.kzen.ergorr.model.rim.VersionInfoType;
import be.kzen.ergorr.model.util.OFactory;
import be.kzen.ergorr.model.wrs.SimpleLinkType;
import be.kzen.ergorr.model.wrs.WrsExtrinsicObjectType;
import be.kzen.ergorr.persist.*;
import be.kzen.ergorr.persist.dao.RimDAO;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.xml.bind.JAXBElement;

/**
 *
 * @author Yaman Ustuntas
 */
@Entity(name="ExtrinsicObject")
public class ExtrinsicObjectDM extends RegistryObjectDM implements Serializable {

    protected String contentVersionName;
    protected String contentVersionComment;
    protected String mimeType;
    protected Boolean isOpaque;
    protected String specType;
    protected String wrsHref;
    protected String wrsRole;
    protected String wrsArcrole;
    protected String wrsTitle;
    protected String wrsShow;
    protected String wrsActuate;

    public ExtrinsicObjectDM() {
        mimeType = "";
        isOpaque = false;
        specType = "rim";
    }

    public ExtrinsicObjectDM(ExtrinsicObjectType eo, RimDAO rimDAO) throws MappingException {
        super(eo, rimDAO);

        mimeType = "";
        isOpaque = false;
        specType = "rim";
        loadJaxb(eo);
    }
    
    public ExtrinsicObjectDM(WrsExtrinsicObjectType eo, RimDAO rimDAO) throws MappingException {
        this((ExtrinsicObjectType)eo, rimDAO);
    }

    public String getContentVersionComment() {
        return contentVersionComment;
    }

    public void setContentVersionComment(String contentVersionComment) {
        this.contentVersionComment = contentVersionComment;
    }

    public String getContentVersionName() {
        return contentVersionName;
    }

    public void setContentVersionName(String contentVersionName) {
        this.contentVersionName = contentVersionName;
    }

    public Boolean getIsOpaque() {
        return isOpaque;
    }

    public void setIsOpaque(Boolean isOpaque) {
        this.isOpaque = isOpaque;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getSpecType() {
        return specType;
    }

    public void setSpecType(String specType) {
        this.specType = specType;
    }

    public String getWrsActuate() {
        return wrsActuate;
    }

    public void setWrsActuate(String wrsActuate) {
        this.wrsActuate = wrsActuate;
    }

    public String getWrsArcrole() {
        return wrsArcrole;
    }

    public void setWrsArcrole(String wrsArcrole) {
        this.wrsArcrole = wrsArcrole;
    }

    public String getWrsHref() {
        return wrsHref;
    }

    public void setWrsHref(String wrsHref) {
        this.wrsHref = wrsHref;
    }

    public String getWrsRole() {
        return wrsRole;
    }

    public void setWrsRole(String wrsRole) {
        this.wrsRole = wrsRole;
    }

    public String getWrsShow() {
        return wrsShow;
    }

    public void setWrsShow(String wrsShow) {
        this.wrsShow = wrsShow;
    }

    public String getWrsTitle() {
        return wrsTitle;
    }

    public void setWrsTitle(String wrsTitle) {
        this.wrsTitle = wrsTitle;
    }

    @Override
    public JAXBElement<? extends IdentifiableType> createJAXBElement() {
        if (specType.equals("wrs")) {
            return OFactory.wrs.createExtrinsicObject(createJaxb(new WrsExtrinsicObjectType()));
        } else {
            return OFactory.rim.createExtrinsicObject(createJaxb(new ExtrinsicObjectType()));
        }

    }

    @Override
    public <T extends IdentifiableType> T createJaxb(T ident) {
        ExtrinsicObjectType eo = (ExtrinsicObjectType) super.createJaxb(ident);

        eo.setMimeType(mimeType);
        eo.setIsOpaque(isOpaque);

        if (contentVersionName != null || contentVersionComment != null) {
            VersionInfoType versionInfo = new VersionInfoType();
            versionInfo.setComment(contentVersionComment);
            versionInfo.setVersionName(contentVersionName);
            eo.setContentVersionInfo(versionInfo);
        }

        if (specType.equals("wrs")) {
            WrsExtrinsicObjectType wrsEo = (WrsExtrinsicObjectType) eo;

            if (wrsHref != null) {
                SimpleLinkType sl = new SimpleLinkType();
                sl.setActuate(wrsActuate);
                sl.setArcrole(wrsArcrole);
                sl.setHref(wrsHref);
                sl.setRole(wrsRole);
                sl.setShow(wrsShow);
                sl.setTitle(wrsTitle);
                wrsEo.setRepositoryItemRef(sl);
            }

            return (T) wrsEo;
        } else {
            return (T) eo;
        }
    }

    public void loadJaxb(ExtrinsicObjectType eo) {

        if (eo.isSetContentVersionInfo()) {
            VersionInfoType versionInfo = eo.getVersionInfo();

            if (versionInfo.isSetComment()) {
                contentVersionComment = versionInfo.getComment();
            }

            if (versionInfo.isSetVersionName()) {
                contentVersionName = versionInfo.getVersionName();
            }
        }

        if (eo.isSetMimeType()) {
            mimeType = eo.getMimeType();
        }

        if (eo.isSetIsOpaque()) {
            isOpaque = eo.isIsOpaque();
        }

        if (eo instanceof WrsExtrinsicObjectType) {
            WrsExtrinsicObjectType wrsEo = (WrsExtrinsicObjectType) eo;
            specType = "wrs";

            if (wrsEo.isSetRepositoryItemRef()) {
                SimpleLinkType sl = wrsEo.getRepositoryItemRef();

                if (sl.isSetActuate()) {
                    wrsActuate = sl.getActuate();
                }
                if (sl.isSetArcrole()) {
                    wrsArcrole = sl.getArcrole();
                }
                if (sl.isSetHref()) {
                    wrsHref = sl.getHref();
                }
                if (sl.isSetRole()) {
                    wrsRole = sl.getRole();
                }
                if (sl.isSetShow()) {
                    wrsShow = sl.getShow();
                }
                if (sl.isSetTitle()) {
                    wrsTitle = sl.getTitle();
                }
            }
        } else {
            specType = "rim";
        }
    }
}
