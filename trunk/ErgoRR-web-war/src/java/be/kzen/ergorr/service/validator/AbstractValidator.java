/*
 * Project: Buddata ebXML RegRep
 * Class: AbstractValidator.java
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
import be.kzen.ergorr.model.rim.IdentifiableType;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author yamanustuntas
 */
public abstract class AbstractValidator<T extends IdentifiableType> {
    protected Map<String, List<IdentifiableType>> identMap;
    protected T rimObject;

    public void setIdentMap(Map<String, List<IdentifiableType>> identMap) {
        this.identMap = identMap;
    }

    public T getRimObject() {
        return rimObject;
    }

    public void setRimObject(T rimObject) {
        this.rimObject = rimObject;
    }

    protected boolean idExistsInRequest(String id, Class<? extends IdentifiableType> clazz) {
        boolean exists = false;

        List<IdentifiableType> idents = identMap.get(clazz.getName());

        if (idents != null) {
            for (IdentifiableType ident : idents) {
                if (ident.getId().equals(id)) {
                    exists = true;
                    break;
                }
            }
        }

        return exists;
    }

    protected boolean idExistsInRequest(String id) {
        boolean exists = false;
        Iterator<String> it = identMap.keySet().iterator();

        outer:
        while (it.hasNext()) {
            List<IdentifiableType> idents = identMap.get(it.next());

            inner:
            for (IdentifiableType ident : idents) {
                if (ident.getId().equals(id)) {
                    exists = true;
                    break outer;
                }
            }
        }

        return exists;
    }

    public abstract void validate() throws InvalidReferenceException, SQLException;
}
