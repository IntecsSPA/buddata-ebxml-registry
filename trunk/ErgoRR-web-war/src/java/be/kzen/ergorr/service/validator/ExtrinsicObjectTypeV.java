/*
 * Project: Buddata ebXML RegRep
 * Class: ExtrinsicObjectTypeV.java
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
import be.kzen.ergorr.model.rim.ExtrinsicObjectType;
import be.kzen.ergorr.model.rim.SlotType;
import be.kzen.ergorr.persist.InternalSlotTypes;
import java.sql.SQLException;

/**
 *
 * @author yamanustuntas
 */
public class ExtrinsicObjectTypeV extends RegistryObjectTypeV<ExtrinsicObjectType> {

    @Override
    public void validate() throws InvalidReferenceException, SQLException {
        super.validate();
        if (rimObject.getObjectType().equals(RIMConstants.CN_OBJ_DEF)) {
            for (SlotType s : rimObject.getSlot()) {
                try {
                    InternalSlotTypes.getInstance().putSlot(s.getName(), s.getSlotType());
                } catch (Exception ex) {
                    throw new InvalidReferenceException(ex.getMessage());
                }
            }
        }
    }
}
