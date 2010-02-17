package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.model.rim.RegistryObjectType;
import be.kzen.ergorr.model.rim.VersionInfoType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import javax.xml.bind.JAXBElement;

/**
 *
 * @author Yaman Ustuntas
 */
public class RegistryObjectTypeDAO<T extends RegistryObjectType> extends IdentifiableTypeDAO<T> {

    public RegistryObjectTypeDAO() {
    }

    public RegistryObjectTypeDAO(String alias) {
        super(alias);
    }

    public RegistryObjectTypeDAO(T roXml) {
        super(roXml);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected T loadXmlObject(ResultSet result) throws SQLException {
        super.loadXmlObject(result);
        xmlObject.setLid(result.getString("lid"));
        xmlObject.setObjectType(result.getString("objecttype"));
        xmlObject.setStatus(result.getString("status"));
        VersionInfoType versionInfo = new VersionInfoType();
        versionInfo.setVersionName(versionInfo.getVersionName()); // set the default
        xmlObject.setVersionInfo(versionInfo);
        return xmlObject;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void insertRelatedObjects() throws SQLException {
        super.insertRelatedObjects();

        if (xmlObject.isSetName()) {
            NameDAO nameDAO = new NameDAO(xmlObject);
            nameDAO.setConnection(connection);
            nameDAO.insert();
        }

        if (xmlObject.isSetDescription()) {
            DescriptionDAO descDAO = new DescriptionDAO(xmlObject);
            descDAO.setConnection(connection);
            descDAO.insert();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void loadRelatedObjects() throws SQLException {
        super.loadRelatedObjects();

        if (returnNameDesc()) {
            NameDAO nameDAO = new NameDAO(xmlObject);
            nameDAO.setContext(context);
            nameDAO.setConnection(connection);
            nameDAO.addComposedObjects();

            DescriptionDAO descDAO = new DescriptionDAO(xmlObject);
            descDAO.setContext(context);
            descDAO.setConnection(connection);
            descDAO.addComposedObjects();
        }

        if (returnNestedObjects()) {
            ClassificationTypeDAO clDAO = new ClassificationTypeDAO();
            clDAO.setContext(context);
            clDAO.setConnection(connection);
            clDAO.addClassifications(xmlObject);

            ExternalIdentifierTypeDAO eiDAO = new ExternalIdentifierTypeDAO();
            eiDAO.setContext(context);
            eiDAO.setConnection(connection);
            eiDAO.addExternalIdentifiers(xmlObject);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void updateRelatedObjects() throws SQLException {
        super.updateRelatedObjects();

        NameDAO nameDAO = new NameDAO(xmlObject);
        nameDAO.setContext(context);
        nameDAO.setConnection(connection);
        nameDAO.update();
        DescriptionDAO descDAO = new DescriptionDAO(xmlObject);
        descDAO.setContext(context);
        descDAO.setConnection(connection);
        descDAO.update();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void deleteRelatedObjects() throws SQLException {
        super.deleteRelatedObjects();
        NameDAO nameDAO = new NameDAO(xmlObject);
        nameDAO.setContext(context);
        nameDAO.setConnection(connection);
        nameDAO.delete();
        DescriptionDAO descDAO = new DescriptionDAO(xmlObject);
        descDAO.setContext(context);
        descDAO.setConnection(connection);
        descDAO.delete();
        ClassificationTypeDAO clDAO = new ClassificationTypeDAO();
        clDAO.setContext(context);
        clDAO.setConnection(connection);
        clDAO.deleteClassifications(xmlObject);
        ExternalIdentifierTypeDAO eiDAO = new ExternalIdentifierTypeDAO();
        eiDAO.setContext(context);
        eiDAO.setConnection(connection);
        eiDAO.deleteExternalIdentifiers(xmlObject);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getParamList() {
        return super.getParamList() + ",lid,objecttype,status,versionname,versioncomment";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getPlaceHolders() {
        return super.getPlaceHolders() + (xmlObject.isNewObject() ? ",?,?,?,?,?" : ",lid=?,objecttype=?,status=?,versionname=?,versioncomment=?");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getQueryParamList() {

        if (alias != null && !alias.equals("")) {
            return new StringBuilder(super.getQueryParamList()).append(",").append(alias).append(".lid,").append(alias).append(".objecttype,").append(alias).append(".status,").append(alias).append(".versionname,").append(alias).append(".versioncomment").toString();
        } else {
            return getParamList();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JAXBElement<T> createJAXBElement() {
        throw new UnsupportedOperationException("Should not return Identifiable");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setParameters(PreparedStatement stmt) throws SQLException {
        super.setParameters(stmt);
        stmt.setString(3, xmlObject.getLid());
        stmt.setString(4, xmlObject.getObjectType());
        stmt.setString(5, xmlObject.getStatus());

        if (xmlObject.isSetVersionInfo()) {
            stmt.setString(6, xmlObject.getVersionInfo().getVersionName());
            stmt.setString(7, xmlObject.getVersionInfo().getComment());
        } else {
            stmt.setNull(6, Types.VARCHAR);
            stmt.setNull(7, Types.VARCHAR);
        }
    }
}
