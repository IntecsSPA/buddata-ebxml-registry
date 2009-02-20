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
import be.kzen.ergorr.model.rim.AdhocQueryType;
import be.kzen.ergorr.model.rim.AssociationType;
import be.kzen.ergorr.model.rim.ClassificationNodeType;
import be.kzen.ergorr.model.rim.ClassificationSchemeType;
import be.kzen.ergorr.model.rim.ExternalIdentifierType;
import be.kzen.ergorr.model.rim.IdentifiableType;
import be.kzen.ergorr.model.rim.RegistryObjectType;
import be.kzen.ergorr.model.rim.RegistryPackageType;
import be.kzen.ergorr.model.rim.ServiceBindingType;
import be.kzen.ergorr.model.rim.ServiceType;
import be.kzen.ergorr.model.rim.SpecificationLinkType;
import be.kzen.ergorr.persist.service.SqlPersistence;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author yamanustuntas
 */
public class RegistryObjectTypeV<T extends RegistryObjectType> extends AbstractValidator<T> {

    // Objects which only can have a certain objectType
    private static final Map<String, String> constObjTypeNames;
    private static final String URN_REGEX = "^urn:[\\w-.]*:[\\w-.:]*";


    static {
        constObjTypeNames = new HashMap<String, String>();
        constObjTypeNames.put(AssociationType.class.getName(), "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Association");
        constObjTypeNames.put("_AuditableEvent_", "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:AuditableEvent");
        constObjTypeNames.put(ClassificationNodeType.class.getName(), "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ClassificationNode");
        constObjTypeNames.put(ClassificationSchemeType.class.getName(), "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ClassificationScheme");
        constObjTypeNames.put(ExternalIdentifierType.class.getName(), "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ExternalIdentifier");
        constObjTypeNames.put("_Federation_", "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Federation");
        constObjTypeNames.put("_Organization_", "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Organization");
        constObjTypeNames.put(RegistryPackageType.class.getName(), "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:RegistryPackage");
        constObjTypeNames.put("_Registry_", "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Registry");
        constObjTypeNames.put(ServiceType.class.getName(), "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Service");
        constObjTypeNames.put(ServiceBindingType.class.getName(), "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ServiceBinding");
        constObjTypeNames.put(SpecificationLinkType.class.getName(), "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:SpecificationLink");
        constObjTypeNames.put("_Subscription_", "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Subscription");
        constObjTypeNames.put("_Notification_", "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Notification");
        constObjTypeNames.put(AdhocQueryType.class.getName(), "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:AdhocQuery");
        constObjTypeNames.put("_User_", "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Person:User");
        constObjTypeNames.put("_Person_", "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Person");
    }

    @Override
    public void validate() throws InvalidReferenceException, SQLException {
        // TODO: make sure that ClassificationNode is from ObjectType ClassificationScheme

        if (!rimObject.getId().matches(URN_REGEX)) {
            throw new InvalidReferenceException("'" + rimObject.getId() + "' is not a valid URN");
        }

        rimObject.setStatus(RIMConstants.CN_STATUS_TYPE_CODE_Submitted);
        rimObject.setLid(rimObject.getId());
        String constObjTypeName = constObjTypeNames.get(rimObject.getClass().getName());

        if (constObjTypeName == null) {
            boolean valid = idExistsInRequest(rimObject.getObjectType(), ClassificationNodeType.class);

            if (!valid) {
                valid = persistence.idExists(rimObject.getObjectType(), ClassificationNodeType.class);

                if (!valid) {
                    String err = "Object with id '" + rimObject.getId() + "' does not have a valid objectType '" + rimObject.getObjectType() + "'";
                    throw new InvalidReferenceException(err);
                }
            }
        } else {
            if (rimObject.getObjectType() == null) {
                rimObject.setObjectType(constObjTypeName);
            } else {
                if (!rimObject.getObjectType().equals(constObjTypeName)) {
                    String err = rimObject.getClass().getSimpleName() + " '" + rimObject.getId() + "' should have the objectType '" + constObjTypeName + "'";
                    throw new InvalidReferenceException(err);
                }
            }
        }
    }
}
