/*
 * Project: Buddata ebXML RegRep
 * Class: RegistryObjectTypeV.java
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
package be.kzen.ergorr.service.validator;

import be.kzen.ergorr.commons.RIMConstants;
import be.kzen.ergorr.exceptions.InvalidReferenceException;
import be.kzen.ergorr.exceptions.ReferenceExistsException;
import be.kzen.ergorr.model.rim.AssociationType;
import be.kzen.ergorr.model.rim.ClassificationNodeType;
import be.kzen.ergorr.model.rim.ClassificationType;
import be.kzen.ergorr.model.rim.ExternalIdentifierType;
import be.kzen.ergorr.model.rim.RegistryObjectType;
import be.kzen.ergorr.model.rim.RegistryPackageType;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;

/**
 * Validates RegistryObjects.
 * 
 * @author yamanustuntas
 */
public class RegistryObjectTypeV<T extends RegistryObjectType> extends AbstractValidator<T> {

    private static Logger logger = Logger.getLogger(RegistryObjectTypeV.class.getName());
    private static final String URN_REGEX = "^urn:[\\w-.]*:[\\w-.:]*";

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() throws InvalidReferenceException, SQLException {
        // TODO: make sure that ClassificationNode is from ObjectType ClassificationScheme
        // TODO: check that two objects don't have same ID in one request

        rimObject.setStatus(RIMConstants.CN_STATUS_TYPE_CODE_Submitted);
        rimObject.setLid(rimObject.getId());

        if (!rimObject.isSetId() || !rimObject.getId().matches(URN_REGEX)) {
            throw new InvalidReferenceException("'" + (rimObject.isSetId() ? rimObject.getId() : "EMPTY") + "' is not a valid URN");
        }

        if (rimObject.getObjectType() == null || rimObject.getObjectType().equals("") || rimObject.hasStaticObjectTypeUrn()) {
            rimObject.setObjectType(rimObject.getObjectTypeUrn());
        } else {
            if (!rimObject.getObjectType().equals(rimObject.getObjectTypeUrn())) {
                boolean valid = idExistsInRequest(rimObject.getObjectType(), ClassificationNodeType.class);

                if (!valid) {
                    valid = persistence.idExists(rimObject.getObjectType(), ClassificationNodeType.class);

                    if (!valid) {
                        String err = rimObject.getClass().getName() + " with id '" + rimObject.getId() + "' does not have a valid objectType '" + rimObject.getObjectType() + "'";
                        throw new InvalidReferenceException(err);
                    }
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validateToDelete() throws ReferenceExistsException, SQLException {
        valToDelPkgAsso();
        addReletedObjectToDel();
        valToDelClassification();
        valToDelExtIdent();
    }

    /**
     * Validates if the <code>rimObject</code> is associated to a
     * RegistryPackage. The HasMember association type is the only association
     * which will prevent a RegistryObject from being deleted.
     *
     * @throws java.sql.SQLException
     * @throws be.kzen.ergorr.exceptions.ReferenceExistsException If <code>rimObject</code> is associated to a RegistryPackage.
     */
    protected void valToDelPkgAsso() throws SQLException, ReferenceExistsException {
        String sql = "select sourceobject from t_association where associationtype ='" + RIMConstants.CN_ASSOCIATION_TYPE_ID_HasMember + "' and " +
                "targetobject ='" + rimObject.getId() + "'";

        List<String> ids = persistence.getIds(sql);

        for (String id : ids) {
            // Ignore if package is also in the delete request.
            if (!idExistsInRequest(id, RegistryPackageType.class)) {
                String err = "Cannot delete object " + rimObject.getId() + " because it has a HasMember association to RegistryPackage with ID: " + id;
                throw new ReferenceExistsException(err);
            }
        }
    }

    /**
     * Add objects related to {@code rimObject} to be deleted in addition to the {@code rimObject} itself.
     * 
     * @throws SQLException
     * @throws ReferenceExistsException
     */
    protected void addReletedObjectToDel() throws SQLException, ReferenceExistsException {
        String sql = "select * from t_association where sourceobject='" + rimObject.getId() +
                "' or targetobject='" + rimObject.getId() + "'";

        List<JAXBElement<AssociationType>> assoEls = persistence.query(sql, null, (Class) AssociationType.class);


        for (JAXBElement<AssociationType> assoEl : assoEls) {
            AssociationType asso = assoEl.getValue();

            if (!idExistsInRequest(asso.getId())) {
                AssociationTypeV assoV = new AssociationTypeV();
                assoV.setRimObject(asso);
                assoV.setFlatIdents(flatIdents);
                assoV.setRequestContext(requestContext);
                assoV.validateToDelete();
                addedIdents.add(asso);
            }
        }
    }

    /**
     * Validates the classifications of <code>rimObject</code>.
     * 
     * @throws java.sql.SQLException
     * @throws be.kzen.ergorr.exceptions.ReferenceExistsException
     */
    protected void valToDelClassification() throws SQLException, ReferenceExistsException {
        String sql = "select * from t_classification where classifiedobject ='" + rimObject.getId() + "'";

        List<JAXBElement<ClassificationType>> clsEls = persistence.query(sql, null, (Class) ClassificationType.class);

        for (JAXBElement<ClassificationType> clsEl : clsEls) {
            ClassificationType cls = clsEl.getValue();

            // if classification is already to be deleted then don't bother with re-validating it.
            if (!idExistsInRequest(cls.getId())) {
                ClassificationTypeV clsV = new ClassificationTypeV();
                clsV.setFlatIdents(flatIdents);
                clsV.setRequestContext(requestContext);
                clsV.setRimObject(cls);
                clsV.validateToDelete();
            }
        }
    }

    /**
     * Validats the external identifiers of <code>rimObject</code>.
     * 
     * @throws java.sql.SQLException
     * @throws be.kzen.ergorr.exceptions.ReferenceExistsException
     */
    protected void valToDelExtIdent() throws SQLException, ReferenceExistsException {
        String sql = "select * from t_externalidentifier where registryobject ='" + rimObject.getId() + "'";

        List<JAXBElement<ExternalIdentifierType>> exIdentEls = persistence.query(sql, null, (Class) ExternalIdentifierType.class);

        for (JAXBElement<ExternalIdentifierType> exIdentEl : exIdentEls) {
            ExternalIdentifierType exIdent = exIdentEl.getValue();

            // if external identifier is already to be deleted then don't bother with re-validating it.
            if (!idExistsInRequest(exIdent.getId())) {
                ExternalIdentifierTypeV exIdentV = new ExternalIdentifierTypeV();
                exIdentV.setFlatIdents(flatIdents);
                exIdentV.setRequestContext(requestContext);
                exIdentV.setRimObject(exIdent);
                exIdentV.validateToDelete();
            }
        }
    }
}
