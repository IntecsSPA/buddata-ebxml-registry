/*
 * Project: Buddata ebXML RegRep
 * Class: OrganizationTypeV.java
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
import be.kzen.ergorr.model.rim.OrganizationType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Validates Organizations.
 * 
 * @author yamanustuntas
 */
public class OrganizationTypeV extends RegistryObjectTypeV<OrganizationType> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() throws InvalidReferenceException, SQLException {
        super.validate();

        if (rimObject.isSetParent() && !rimObject.getParent().trim().equals("")) {
            if (!idExistsInRequest(rimObject.getParent(), OrganizationType.class)) {

                if (!persistence.idExists(rimObject.getObjectType(), OrganizationType.class)) {
                    String err = "Organization with ID '" + rimObject.getId() + "' references a parent Organization with ID '" + rimObject.getParent() + "' which does not exist.";
                    throw new InvalidReferenceException(err);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validateToDelete() throws ReferenceExistsException, SQLException {
        super.validateToDelete();

        String sql = "select id from t_organization where parent='" + rimObject.getId() + "'";
        List<String> childOrgIds = persistence.getIds(sql);

        if (childOrgIds.size() > 0) {
            List<String> childOrgIdsCausingErr = new ArrayList<String>();

            for (String childOrgId : childOrgIds) {
                if (!idExistsInRequest(childOrgId, OrganizationType.class)) {
                    childOrgIdsCausingErr.add(childOrgId);
                }
            }

            if (childOrgIdsCausingErr.size() > 0) {
                String err = "Cannot delete Organization with ID '" + rimObject.getId() + "' because it is referenced by doughter Organizations: ";
                for (String childOrgIdCausingErr : childOrgIdsCausingErr) {
                    err += "'" + childOrgIdCausingErr + "' ";
                }

                throw new ReferenceExistsException(err);
            }
        }
    }
}
