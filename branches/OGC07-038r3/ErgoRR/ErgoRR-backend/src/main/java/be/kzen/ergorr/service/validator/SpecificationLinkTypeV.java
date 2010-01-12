/*
 * Project: Buddata ebXML RegRep
 * Class: SpecificationLinkTypeV.java
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
import be.kzen.ergorr.model.rim.ServiceBindingType;
import be.kzen.ergorr.model.rim.SpecificationLinkType;
import be.kzen.ergorr.persist.service.SqlPersistence;
import java.sql.SQLException;

/**
 * Validates SpecificationLinks.
 * 
 * @author yamanustuntas
 */
public class SpecificationLinkTypeV extends RegistryObjectTypeV<SpecificationLinkType> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() throws InvalidReferenceException, SQLException {
        super.validate();
        boolean valid = idExistsInRequest(rimObject.getServiceBinding(), ServiceBindingType.class);


        if (!valid) {
            valid = persistence.idExists(rimObject.getServiceBinding(), ServiceBindingType.class);

            if (!valid) {
                String err = "SpecificationLink '" + rimObject.getId() + "' is refering to a not existing ServiceBinding '" + rimObject.getServiceBinding() + "'";
                throw new InvalidReferenceException(err);
            }
        }
    }
}
