/*
 * Project: Buddata ebXML RegRep
 * Class: ClassificationSchemeTypeV.java
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
import be.kzen.ergorr.model.rim.ClassificationSchemeType;
import java.sql.SQLException;
import java.util.List;

/**
 * Validates ClassificationSchemes.
 * 
 * @author yamanustuntas
 */
public class ClassificationSchemeTypeV extends RegistryObjectTypeV<ClassificationSchemeType> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void validateToDelete() throws ReferenceExistsException, SQLException {
        super.validateToDelete();
        List<String> ids = persistence.getIds("select id from t_classificationnode where parent='" + rimObject.getId() + "'");

        if (!ids.isEmpty()) {
            String idStr = "";
            for (String id : ids) {
                idStr += id + " | ";
            }
            
            String err = "ClassificationScheme " + rimObject.getId() + " cannot be deleted because this has child ClassificationNode(s) " + idStr;
            throw new ReferenceExistsException(err);
        }
    }
}
