/*
 * Project: Buddata ebXML RegRep
 * Class: ClassificationNodeTypeV.java
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

import be.kzen.ergorr.exceptions.InvalidReferenceException;
import be.kzen.ergorr.exceptions.ReferenceExistsException;
import be.kzen.ergorr.model.rim.ClassificationNodeType;
import be.kzen.ergorr.model.rim.ClassificationSchemeType;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author yamanustuntas
 */
public class ClassificationNodeTypeV extends RegistryObjectTypeV<ClassificationNodeType> {

    @Override
    public void validate() throws InvalidReferenceException, SQLException {
        super.validate();
        boolean valid = idExistsInRequest(rimObject.getParent(), ClassificationNodeType.class);

        if (!valid) {
            valid = idExistsInRequest(rimObject.getParent(), ClassificationSchemeType.class);
        }

        if (!valid) {
            valid = persistence.idExists(rimObject.getParent(), ClassificationNodeType.class);

            if (!valid) {
                valid = persistence.idExists(rimObject.getParent(), ClassificationSchemeType.class);
            }
        }

        if (!valid) {
            String err = "ClassificationNode with id '" + rimObject.getId() + "' is refering to a not existing parent object '" + rimObject.getParent() + "'";
            throw new InvalidReferenceException(err);
        }
    }

    @Override
    public void validateToDelete() throws ReferenceExistsException, SQLException {
        super.validateToDelete();
        List<String> ids = persistence.getIds("select id from t_identifiable where parent='" + rimObject.getId() + "'");

        if (!ids.isEmpty()) {
            String err = "ClassificationNode " + rimObject.getId() + " cannot be deleted because it classified one or more RegistryObjects";
            throw new ReferenceExistsException(err);
        }

        ids = persistence.getIds("select id from t_classification where classificationnode='" + rimObject.getId() + "'");

        if (!ids.isEmpty()) {
            String err = "ClassificationNode " + rimObject.getId() + " cannot be deleted because it is used by one or more Classifications";
            throw new ReferenceExistsException(err);
        }
    }
}
