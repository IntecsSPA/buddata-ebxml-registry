/*
 * Project: Buddata ebXML RegRep
 * Class: AssociationTypeV.java
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
import be.kzen.ergorr.model.rim.AssociationType;
import be.kzen.ergorr.persist.service.SqlPersistence;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yamanustuntas
 */
public class AssociationTypeV extends RegistryObjectTypeV<AssociationType> {

    @Override
    public void validate() throws InvalidReferenceException, SQLException {
        super.validate();
        boolean sourceObjValid = idExistsInRequest(rimObject.getSourceObject());
        boolean targetObjValid = idExistsInRequest(rimObject.getTargetObject());

        if (!sourceObjValid || !targetObjValid) {
            List<String> ids = new ArrayList<String>();
            if (!sourceObjValid) {
                ids.add(rimObject.getSourceObject());
            }
            if (!targetObjValid) {
                ids.add(rimObject.getTargetObject());
            }

            List<String> dbIds = null;
            dbIds = persistence.idsExist(ids);

            if (dbIds.size() != ids.size()) {
                String error = "association " + rimObject.getId();

                if (!dbIds.contains(rimObject.getSourceObject())) {
                    error += " has invalid sourceObject '" + rimObject.getSourceObject() + "'";
                }
                if (!dbIds.contains(rimObject.getTargetObject())) {
                    error += " has invalid targetObject '" + rimObject.getTargetObject() + "'";
                }

                throw new InvalidReferenceException(error);
            }
        }
    }
}
