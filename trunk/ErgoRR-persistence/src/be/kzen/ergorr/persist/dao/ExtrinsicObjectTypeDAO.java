package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.model.rim.ExtrinsicObjectType;
import be.kzen.ergorr.model.rim.VersionInfoType;
import be.kzen.ergorr.model.util.OFactory;
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
        xmlObject = new ExtrinsicObjectType();
        return loadXmlObject(result);
    }

    @Override
    protected ExtrinsicObjectType loadXmlObject(ResultSet result) throws SQLException {
        super.loadXmlObject(result);
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

        String specType = result.getString("spectype");

        if (specType.equals("wrs")) {
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
        if (alias != null && !alias.isEmpty()) {
            return new StringBuilder(super.getQueryParamList()).append(",").append(alias).append(".isopaque,").append(alias).append(".mimetype,")
                    .append(alias).append(".contentversionname,").append(alias).append(".contentversioncomment,").append(alias).append(".spectype,")
                    .append(alias).append(",wrsactuate,").append(alias).append(".wrsarcrule,").append(alias).append(".wrshref").append(alias)
                    .append(".wrsrole,").append(alias).append(".wrsshow").append(alias).append(".wrstitle").toString();
        } else {
            return getParamList();
        }
    }
    
    @Override
    public JAXBElement<ExtrinsicObjectType> createJAXBElement() {
            return OFactory.rim.createExtrinsicObject(xmlObject);
    }
}
