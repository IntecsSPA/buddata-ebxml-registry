package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.commons.CommonFunctions;
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

            if (contentVersionName != null && contentVersionName.trim().length() > 0) {
                VersionInfoType versionInfo = new VersionInfoType();
                versionInfo.setVersionName(contentVersionName);

                if (contentVersionComment != null) {
                    versionInfo.setComment(contentVersionComment);
                }
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

                if (isSetSimpleLink(wrsactuate, wrsarcrole, wrshref, wrsrole, wrsshow, wrstitle)) {
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

    protected boolean isSetSimpleLink(String wrsactuate, String wrsarcrole, String wrshref, String wrsrole, String wrsshow, String wrstitle) {
        return (wrsactuate != null && !wrsactuate.equals("")) ||
                (wrsarcrole != null && !wrsarcrole.equals("")) ||
                (wrshref != null && !wrshref.equals("")) ||
                (wrsrole != null && wrsrole.equals("")) ||
                (wrsshow != null && wrsshow.equals("")) ||
                (wrstitle != null && wrstitle.equals(""));
    }

    @Override
    protected String createValues() {
        StringBuilder vals = new StringBuilder();
        vals.append(super.createValues());

        vals.append(xmlObject.isNewObject() ? "," : ",isopaque=");
        appendBooleanValue(xmlObject.isIsOpaque(), vals);
        vals.append(xmlObject.isNewObject() ? "," : ",mimetype=");
        appendStringValue(xmlObject.getMimeType(), vals);
        vals.append(xmlObject.isNewObject() ? "," : ",contentversionname=");

        if (xmlObject.isSetContentVersionInfo()) {
            appendStringValue(xmlObject.getContentVersionInfo().getVersionName(), vals);
            vals.append(xmlObject.isNewObject() ? "," : ",contentversioncomment=");
            appendStringValue(xmlObject.getContentVersionInfo().getComment(), vals);
        } else {
            vals.append("''");
            vals.append(xmlObject.isNewObject() ? ",''" : ",contentversioncomment=''");
        }

        vals.append(xmlObject.isNewObject() ? "," : ",spectype=");

        if (xmlObject instanceof WrsExtrinsicObjectType) {
            WrsExtrinsicObjectType wrsEo = (WrsExtrinsicObjectType) xmlObject;
            vals.append("'wrs'");
            vals.append(xmlObject.isNewObject() ? "," : ",wrsactuate=");

            if (wrsEo.isSetRepositoryItemRef()) {
                appendStringValue(wrsEo.getRepositoryItemRef().getActuate(), vals);
                vals.append(xmlObject.isNewObject() ? "," : ",wrsarcrole=");
                appendStringValue(wrsEo.getRepositoryItemRef().getArcrole(), vals);
                vals.append(xmlObject.isNewObject() ? "," : ",wrshref=");
                appendStringValue(wrsEo.getRepositoryItemRef().getHref(), vals);
                vals.append(xmlObject.isNewObject() ? "," : ",wrsrole=");
                appendStringValue(wrsEo.getRepositoryItemRef().getRole(), vals);
                vals.append(xmlObject.isNewObject() ? "," : ",wrsshow=");
                appendStringValue(wrsEo.getRepositoryItemRef().getShow(), vals);
                vals.append(xmlObject.isNewObject() ? "," : ",wrstitle=");
                appendStringValue(wrsEo.getRepositoryItemRef().getTitle(), vals);
            } else {
                if (xmlObject.isNewObject()) {
                    vals.append("'','','','','',''");
                } else {
                    vals.append("'',wrsarcrole='',wrshref='',wrsrole='',wrsshow='',wrstitle=''");
                }
            }

        } else {
            if (xmlObject.isNewObject()) {
                vals.append("'rim','','','','','',''");
            } else {
                vals.append("'rim',wrsactuate='',wrsarcrole='',wrshref='',wrsrole='',wrsshow='',wrstitle=''");
            }
        }

        return vals.toString();
    }

    @Override
    public String getTableName() {
        return "t_extrinsicobject";
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
