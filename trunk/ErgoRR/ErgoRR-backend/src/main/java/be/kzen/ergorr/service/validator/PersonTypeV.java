/*
 * Project: Buddata ebXML RegRep
 * Class: PersonTypeV.java
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

import be.kzen.ergorr.exceptions.ReferenceExistsException;
import be.kzen.ergorr.model.rim.PersonType;
import be.kzen.ergorr.model.rim.OrganizationType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Validates Person.
 *
 * @author yamanustuntas
 */
public class PersonTypeV extends RegistryObjectTypeV<PersonType> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void validateToDelete() throws ReferenceExistsException, SQLException {
        super.validateToDelete();

        String sql = "select id from t_organization where primarycontact = '" + rimObject.getId() + "'";
        List<String> orgIdsWithPerson = persistence.getIds(sql);

        if (orgIdsWithPerson.size() > 0) {

            List<String> orgIdsCausingErr = new ArrayList<String>();

            for (String orgIdWithPerson : orgIdsWithPerson) {
                if (!idExistsInRequest(orgIdWithPerson, OrganizationType.class)) {
                    orgIdsCausingErr.add(orgIdWithPerson);
                }
            }

            if (orgIdsCausingErr.size() > 0) {
                String err = "Cannot delete Person with ID '" + rimObject.getId() + "' because it is referenced as primary contact by " + orgIdsCausingErr.size() + " Organization(s): ";
                for (String orgIdCausingErr : orgIdsCausingErr) {
                    err += "'" + orgIdCausingErr + "' ";
                }
                throw new ReferenceExistsException(err);
            }
        }
    }
}
