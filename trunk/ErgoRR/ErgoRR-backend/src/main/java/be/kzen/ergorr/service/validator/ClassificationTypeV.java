/*
 * Project: Buddata ebXML RegRep
 * Class: ClassificationTypeV.java
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
import be.kzen.ergorr.model.rim.ClassificationNodeType;
import be.kzen.ergorr.model.rim.ClassificationType;
import be.kzen.ergorr.persist.service.SqlPersistence;
import java.sql.SQLException;

/**
 * Validates Classifications.
 *
 * @author yamanustuntas
 */
public class ClassificationTypeV extends RegistryObjectTypeV<ClassificationType> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() throws InvalidReferenceException, SQLException {
        super.validate();
        boolean validCn = idExistsInRequest(rimObject.getClassificationNode(), ClassificationNodeType.class);
        boolean validCo = idExistsInRequest(rimObject.getClassifiedObject());


        if (!validCn || !validCo) {
            if (!validCn) {
                validCn = persistence.idExists(rimObject.getClassificationNode(), ClassificationNodeType.class);

                if (!validCn) {
                    String err = "ClassificationNode id '" + rimObject.getClassificationNode() + "' referenced by the Classification '" + rimObject.getId() + "' does not exist";
                    throw new InvalidReferenceException(err);
                }
            }

            if (!validCo) {
                validCo = persistence.idExist(rimObject.getClassifiedObject());

                if (!validCo) {
                    String err = "Could not find classified object'" + rimObject.getClassifiedObject() + "' for Classification '" + rimObject.getId() + "'";
                    throw new InvalidReferenceException(err);
                }
            }
        }
    }
}
