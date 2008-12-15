package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.commons.InternalConstants;
import be.kzen.ergorr.model.csw.ElementSetType;
import be.kzen.ergorr.model.rim.RegistryObjectType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    @Override
    protected T loadXmlObject(ResultSet result) throws SQLException {
        super.loadXmlObject(result);
        xmlObject.setLid(result.getString("lid"));
        xmlObject.setObjectType(result.getString("objecttype"));
        return xmlObject;
    }

    @Override
    protected String createValues() {
        StringBuilder vals = new StringBuilder(super.createValues());
        vals.append(",");
        appendStringValue(xmlObject.getLid(), vals);
        vals.append(",");
        appendStringValue(xmlObject.getObjectType(), vals);
        vals.append(",");
        appendStringValue(xmlObject.getStatus(), vals);
        vals.append(",");

        if (xmlObject.isSetVersionInfo()) {
            appendStringValue(xmlObject.getVersionInfo().getVersionName(), vals);
            vals.append(",");
            appendStringValue(xmlObject.getVersionInfo().getComment(), vals);
        } else {
            vals.append("null,null");
        }

        return vals.toString();
    }

    @Override
    protected String createUpdateValues() {
        StringBuilder vals = new StringBuilder(super.createUpdateValues());
        vals.append(",lid=");
        appendStringValue(xmlObject.getLid(), vals);
        vals.append(",objecttype=");
        appendStringValue(xmlObject.getObjectType(), vals);
        vals.append(",status=");
        appendStringValue(xmlObject.getStatus(), vals);
        vals.append(",versionname=");

        if (xmlObject.isSetVersionInfo()) {
            appendStringValue(xmlObject.getVersionInfo().getVersionName(), vals);
            vals.append(",versioncomment=");
            appendStringValue(xmlObject.getVersionInfo().getComment(), vals);
        } else {
            vals.append("null,versioncomment=null");
        }
        
        return vals.toString();
    }

    @Override
    protected void insertRelatedObjects(Statement batchStmt) throws SQLException {
        super.insertRelatedObjects(batchStmt);

        if (xmlObject.isSetName()) {
            LocalizedStringNameDAO nameDAO = new LocalizedStringNameDAO();
            nameDAO.insert(xmlObject, batchStmt);
        }

        if (xmlObject.isSetDescription()) {
            LocalizedStringDescDAO descDAO = new LocalizedStringDescDAO();
            descDAO.insert(xmlObject, batchStmt);
        }
    }

    @Override
    protected void loadRelatedObjects() throws SQLException {
        super.loadRelatedObjects();

        if (!isBrief()) {
            LocalizedStringNameDAO nameDAO = new LocalizedStringNameDAO();
            nameDAO.setConnection(connection);
            nameDAO.addComposedObjects(xmlObject);

            LocalizedStringDescDAO descDAO = new LocalizedStringDescDAO();
            descDAO.setConnection(connection);
            descDAO.addComposedObjects(xmlObject);

            if (!isSummary()) {
                ClassificationTypeDAO clDAO = new ClassificationTypeDAO();
                clDAO.setConnection(connection);
                clDAO.addClassifications(xmlObject);

                ExternalIdentifierTypeDAO eiDAO = new ExternalIdentifierTypeDAO();
                eiDAO.setConnection(connection);
                eiDAO.addExternalIdentifiers(xmlObject);
            }
        }
    }

    @Override
    protected String getParamList() {
        return super.getParamList() + ",lid,objecttype,status,versionname,versioncomment";
    }

    @Override
    protected String getQueryParamList() {

        if (alias != null && !alias.isEmpty()) {
            return new StringBuilder(super.getQueryParamList()).append(",").append(alias).append(".lid,").append(alias).append(".objecttype,").append(alias).append(".status,").append(alias).append(".versionname,").append(alias).append(".versioncomment").toString();
        } else {
            return getParamList();
        }
    }

    @Override
    public JAXBElement<T> createJAXBElement() {
        throw new UnsupportedOperationException("Should not return Identifiable");
    }
}
