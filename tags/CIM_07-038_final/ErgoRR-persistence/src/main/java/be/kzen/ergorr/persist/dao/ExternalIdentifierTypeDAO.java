package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.model.rim.ExternalIdentifierType;
import be.kzen.ergorr.model.rim.RegistryObjectType;
import be.kzen.ergorr.model.util.OFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.xml.bind.JAXBElement;

/**
 * ExternalIdentifier DAO
 * 
 * @author Yaman Ustuntas
 */
public class ExternalIdentifierTypeDAO extends RegistryObjectTypeDAO<ExternalIdentifierType> {

    public ExternalIdentifierTypeDAO() {
    }

    public ExternalIdentifierTypeDAO(ExternalIdentifierType eiXml) {
        super(eiXml);
    }

    /**
     * Add ExternalIdentifiers belonging to the RegistryObject {@code parent}.
     *
     * @param parent RegistryObject to add its ExternalIdentifiers to.
     * @throws SQLException
     */
    public void addExternalIdentifiers(RegistryObjectType parent) throws SQLException {
        Statement stmt = connection.createStatement();
        StringBuilder sql = new StringBuilder();
        sql.append("select ").append(getParamList()).append(" from ").append(getTableName()).append(" where registryobject = '").append(parent.getId()).append("'");
        ResultSet result = stmt.executeQuery(sql.toString());

        while (result.next()) {
            parent.getExternalIdentifier().add(newXmlObject(result));
        }
    }

    /**
     * Delete ExternalIdentifiers belonging to the RegistryObject {@code parent}.
     *
     * @param parent RegistryObject whose ExternalIdentifiers should be deleted.
     * @throws SQLException
     */
    public void deleteExternalIdentifiers(RegistryObjectType parent) throws SQLException {
        Statement stmt = connection.createStatement();
        StringBuilder sql = new StringBuilder();
        sql.append("delete from ").append(getTableName()).append(" where registryobject='").append(parent.getId()).append("';");
        stmt.execute(sql.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExternalIdentifierType newXmlObject(ResultSet result) throws SQLException {
        xmlObject = new ExternalIdentifierType();
        return loadCompleteXmlObject(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ExternalIdentifierType loadXmlObject(ResultSet result) throws SQLException {
        super.loadXmlObject(result);
        xmlObject.setRegistryObject(result.getString("registryobject"));
        xmlObject.setIdentificationScheme(result.getString("identificationscheme"));
        xmlObject.setValue(result.getString("value_"));
        return xmlObject;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setParameters(PreparedStatement stmt) throws SQLException {
        super.setParameters(stmt);
        stmt.setString(8, xmlObject.getRegistryObject());
        stmt.setString(9, xmlObject.getIdentificationScheme());
        stmt.setString(10, xmlObject.getValue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getParamList() {
        return super.getParamList() + ",registryobject,identificationscheme,value_";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getQueryParamList() {
        if (alias != null && !alias.equals("")) {
            return new StringBuilder(super.getQueryParamList()).append(",").append(alias).append(".registryobject,").append(alias).append(".identificationscheme,").append(alias).append(".value_").toString();
        } else {
            return getParamList();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getPlaceHolders() {
        return super.getPlaceHolders() + (xmlObject.isNewObject() ? ",?,?,?" : ",registryobject=?,identificationscheme=?,value_=?");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTableName() {
        return "t_externalidentifier";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JAXBElement<ExternalIdentifierType> createJAXBElement() {
        return OFactory.rim.createExternalIdentifier(xmlObject);
    }
}
