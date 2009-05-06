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
import be.kzen.ergorr.model.rim.AdhocQueryType;
import be.kzen.ergorr.model.rim.AssociationType;
import be.kzen.ergorr.model.rim.ClassificationNodeType;
import be.kzen.ergorr.model.rim.ClassificationSchemeType;
import be.kzen.ergorr.model.rim.ExternalIdentifierType;
import be.kzen.ergorr.model.rim.RegistryObjectType;
import be.kzen.ergorr.model.rim.RegistryPackageType;
import be.kzen.ergorr.model.rim.ServiceBindingType;
import be.kzen.ergorr.model.rim.ServiceType;
import be.kzen.ergorr.model.rim.SpecificationLinkType;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Validates RegistryObjects.
 * 
 * @author yamanustuntas
 */
public class RegistryObjectTypeV<T extends RegistryObjectType> extends AbstractValidator<T> {
    private static Logger logger = Logger.getLogger(RegistryObjectTypeV.class.getName());
    
    // Objects which only can have a certain objectType
    private static final String URN_REGEX = "^urn:[\\w-.]*:[\\w-.:]*";

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() throws InvalidReferenceException, SQLException {
        // TODO: make sure that ClassificationNode is from ObjectType ClassificationScheme

        if (!rimObject.getId().matches(URN_REGEX)) {
            throw new InvalidReferenceException("'" + rimObject.getId() + "' is not a valid URN");
        }

        rimObject.setStatus(RIMConstants.CN_STATUS_TYPE_CODE_Submitted);
        rimObject.setLid(rimObject.getId());

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
        String sql = "select id from t_association where sourceobject ='" + rimObject.getId() + "' or targetobject = '" + rimObject.getId() + "'";

        List<String> ids = persistence.getIds(sql);

        if (!ids.isEmpty()) {
            String idStr = "";
            for (String id : ids) {
                idStr += id + " | ";
            }
            String err = "Object " + rimObject.getId() + " to be deleted is used in associations with IDs " + idStr;
            throw new ReferenceExistsException(err);
        }
    }
}
