package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.commons.InternalConstants;
import be.kzen.ergorr.model.rim.ExtrinsicObjectType;
import be.kzen.ergorr.model.rim.VersionInfoType;
import be.kzen.ergorr.model.util.OFactory;
import be.kzen.ergorr.model.wrs.SimpleLinkType;
import be.kzen.ergorr.model.wrs.WrsExtrinsicObjectType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.xml.bind.JAXBElement;

/**
 *
 * @author Yaman Ustuntas
 */
public class ExtrinsicObjectTypeDAO extends RegistryObjectTypeDAO<ExtrinsicObjectType> {

    public ExtrinsicObjectTypeDAO() {
    }

    public ExtrinsicObjectTypeDAO(ExtrinsicObjectType eoXml) {
        super(eoXml);
    }

    @Override
    public ExtrinsicObjectType newXmlObject(ResultSet result) throws SQLException {
        String specType = result.getString("spectype");
        xmlObject = specType.equals(InternalConstants.SPEC_TYPE_RIM) ? new ExtrinsicObjectType() : new WrsExtrinsicObjectType();
        return loadCompleteXmlObject(result);
    }

    @Override
    protected ExtrinsicObjectType loadXmlObject(ResultSet result) throws SQLException {
        super.loadXmlObject(result);

        if (!isBrief()) {
            xmlObject.setIsOpaque(result.getBoolean("isopaque"));
            xmlObject.setMimeType(result.getString("mimetype"));

            String contentVersionName = result.getString("contentversionname");
            String contentVersionComment = result.getString("contentversioncomment");

            if (contentVersionComment != null && contentVersionName != null) {
                VersionInfoType versionInfo = new VersionInfoType();
                versionInfo.setVersionName(contentVersionName);
                versionInfo.setComment(contentVersionComment);
                xmlObject.setContentVersionInfo(versionInfo);
            }

            if (xmlObject instanceof WrsExtrinsicObjectType) {
                WrsExtrinsicObjectType wrsEo = (WrsExtrinsicObjectType) xmlObject;
                
                String wrsactuate = result.getString("wrsactuate");
                String wrsarcrole = result.getString("wrsarcrole");
                String wrshref = result.getString("wrshref");
                String wrsrole = result.getString("wrsrole");
                String wrsshow = result.getString("wrsshow");
                String wrstitle = result.getString("wrstitle");
                
                if (wrsactuate != null || wrsarcrole != null || wrshref != null || wrsrole != null || wrsshow != null || wrstitle != null) {
                    SimpleLinkType simpleLink = new SimpleLinkType();
                    simpleLink.setActuate(wrsactuate);
                    simpleLink.setArcrole(wrsarcrole);
                    simpleLink.setHref(wrshref);
                    simpleLink.setShow(wrsshow);
                    simpleLink.setTitle(wrstitle);
                    wrsEo.setRepositoryItemRef(simpleLink);
                }
            }
        }
        return xmlObject;
    }

    @Override
    protected String createValues() {
        StringBuilder vals = new StringBuilder();
        vals.append(super.createValues());

        vals.append(",");
        appendBooleanValue(xmlObject.isIsOpaque(), vals);
        vals.append(",");
        appendStringValue(xmlObject.getMimeType(), vals);
        vals.append(",");

        if (xmlObject.isSetContentVersionInfo()) {
            vals.append("'").append(xmlObject.getContentVersionInfo().getVersionName()).append("','");
            vals.append(xmlObject.getContentVersionInfo().getComment()).append("'");
        } else {
            vals.append("null,null");
        }

        vals.append(",");

        if (xmlObject instanceof WrsExtrinsicObjectType) {
            WrsExtrinsicObjectType wrsEo = (WrsExtrinsicObjectType) xmlObject;

            vals.append("'wrs',");

            if (wrsEo.isSetRepositoryItemRef()) {
                appendStringValue(wrsEo.getRepositoryItemRef().getActuate(), vals);
                vals.append(",");
                appendStringValue(wrsEo.getRepositoryItemRef().getArcrole(), vals);
                vals.append(",");
                appendStringValue(wrsEo.getRepositoryItemRef().getHref(), vals);
                vals.append(",");
                appendStringValue(wrsEo.getRepositoryItemRef().getRole(), vals);
                vals.append(",");
                appendStringValue(wrsEo.getRepositoryItemRef().getShow(), vals);
                vals.append(",");
                appendStringValue(wrsEo.getRepositoryItemRef().getTitle(), vals);
            } else {
                vals.append("null,null,null,null,null,null");
            }

        } else {
            vals.append("'rim',null,null,null,null,null,null");
        }

        return vals.toString();
    }

    @Override
    public String getTableName() {
        return "extrinsicobject";
    }

    @Override
    protected String getParamList() {
        return super.getParamList() + ",isopaque,mimetype,contentversionname,contentversioncomment,spectype,wrsactuate,wrsarcrole,wrshref,wrsrole,wrsshow,wrstitle";
    }

    @Override
    protected String getQueryParamList() {
        if (alias != null && !alias.equals("")) {
            return new StringBuilder(super.getQueryParamList()).append(",").append(alias).append(".isopaque,").append(alias).append(".mimetype,").append(alias).append(".contentversionname,").append(alias).append(".contentversioncomment,").append(alias).append(".spectype,").append(alias).append(",wrsactuate,").append(alias).append(".wrsarcrule,").append(alias).append(".wrshref").append(alias).append(".wrsrole,").append(alias).append(".wrsshow").append(alias).append(".wrstitle").toString();
        } else {
            return getParamList();
        }
    }

    @Override
    public JAXBElement<ExtrinsicObjectType> createJAXBElement() {
        return OFactory.rim.createExtrinsicObject(xmlObject);
    }
}
