package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.model.rim.ExtrinsicObjectType;
import be.kzen.ergorr.model.rim.VersionInfoType;
import be.kzen.ergorr.model.util.OFactory;
import be.kzen.ergorr.model.wrs.SimpleLinkType;
import be.kzen.ergorr.model.wrs.WrsExtrinsicObjectType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import javax.xml.bind.JAXBElement;

/**
 *
 * @author Yaman Ustuntas
 */
public class ExtrinsicObjectTypeDAO<T extends ExtrinsicObjectType> extends RegistryObjectTypeDAO<T> {

    public ExtrinsicObjectTypeDAO() {
    }

    public ExtrinsicObjectTypeDAO(T eoXml) {
        super(eoXml);
    }

    @Override
    public T newXmlObject(ResultSet result) throws SQLException {
        xmlObject = (T) (result.getString("spectype").equals("rim") ? new ExtrinsicObjectType() : new WrsExtrinsicObjectType());
        return loadCompleteXmlObject(result);
    }

    @Override
    protected T loadXmlObject(ResultSet result) throws SQLException {
        super.loadXmlObject(result);

        if (!isBrief()) {
            xmlObject.setIsOpaque(result.getBoolean("isopaque"));
            xmlObject.setMimeType(result.getString("mimetype"));

            VersionInfoType versionInfo = new VersionInfoType();
            versionInfo.setVersionName(result.getString("contentversionname"));
            versionInfo.setComment(result.getString("contentversioncomment"));
            xmlObject.setContentVersionInfo(versionInfo);

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
                (wrsrole != null && !wrsrole.equals("")) ||
                (wrsshow != null && !wrsshow.equals("")) ||
                (wrstitle != null && !wrstitle.equals(""));
    }

    @Override
    protected void setParameters(PreparedStatement stmt) throws SQLException {
        super.setParameters(stmt);
        stmt.setBoolean(8, xmlObject.isIsOpaque());
        stmt.setString(9, xmlObject.getMimeType());

        if (xmlObject.isSetContentVersionInfo()) {
            stmt.setString(10, xmlObject.getContentVersionInfo().getVersionName());
            stmt.setString(11, xmlObject.getContentVersionInfo().getComment());
        } else {
            stmt.setString(10, "1.1");
            stmt.setNull(11, Types.VARCHAR);
        }

        if (xmlObject instanceof WrsExtrinsicObjectType) {
            WrsExtrinsicObjectType wrsEo = (WrsExtrinsicObjectType) xmlObject;
            stmt.setString(12, "wrs");

            if (wrsEo.isSetRepositoryItemRef()) {
                stmt.setString(13, wrsEo.getRepositoryItemRef().getActuate());
                stmt.setString(14, wrsEo.getRepositoryItemRef().getArcrole());
                stmt.setString(15, wrsEo.getRepositoryItemRef().getHref());
                stmt.setString(16, wrsEo.getRepositoryItemRef().getRole());
                stmt.setString(17, wrsEo.getRepositoryItemRef().getShow());
                stmt.setString(18, wrsEo.getRepositoryItemRef().getTitle());
            } else {
                stmt.setNull(13, Types.VARCHAR);
                stmt.setNull(14, Types.VARCHAR);
                stmt.setNull(15, Types.VARCHAR);
                stmt.setNull(16, Types.VARCHAR);
                stmt.setNull(17, Types.VARCHAR);
                stmt.setNull(18, Types.VARCHAR);
            }
        } else {
            stmt.setString(12, "rim");
            stmt.setNull(13, Types.VARCHAR);
            stmt.setNull(14, Types.VARCHAR);
            stmt.setNull(15, Types.VARCHAR);
            stmt.setNull(16, Types.VARCHAR);
            stmt.setNull(17, Types.VARCHAR);
            stmt.setNull(18, Types.VARCHAR);
        }
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
    protected String getPlaceHolders() {
        return super.getPlaceHolders() + (xmlObject.isNewObject() ? ",?,?,?,?,?,?,?,?,?,?,?" : ",isopaque=?,mimetype=?,contentversionname=?,contentversioncomment=?,spectype=?,wrsactuate=?,wrsarcrole=?,wrshref=?,wrsrole=?,wrsshow=?,wrstitle=?");
    }

    @Override
    public JAXBElement<T> createJAXBElement() {
        if (xmlObject instanceof WrsExtrinsicObjectType) {
            return (JAXBElement<T>) OFactory.wrs.createExtrinsicObject((WrsExtrinsicObjectType) xmlObject);
        } else {
            return (JAXBElement<T>) OFactory.rim.createExtrinsicObject(xmlObject);
        }
    }
}
