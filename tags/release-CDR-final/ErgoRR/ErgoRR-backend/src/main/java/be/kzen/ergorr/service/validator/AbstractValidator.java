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

import be.kzen.ergorr.commons.RequestContext;
import be.kzen.ergorr.exceptions.InvalidReferenceException;
import be.kzen.ergorr.exceptions.ReferenceExistsException;
import be.kzen.ergorr.model.rim.IdentifiableType;
import be.kzen.ergorr.persist.service.SqlPersistence;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Abstract validator to be extended by RIM object validators.
 * 
 * @author yamanustuntas
 */
public abstract class AbstractValidator<T extends IdentifiableType> {
    protected Map<String, List<IdentifiableType>> identMap;
    protected T rimObject;
    protected RequestContext requestContext;
    protected SqlPersistence persistence;

    /**
     * Set the identifiables which are processed together
     * with the corrent <code>rimObject</code>.
     * 
     * @param identMap
     */
    public void setIdentMap(Map<String, List<IdentifiableType>> identMap) {
        this.identMap = identMap;
    }

    /**
     * Set the request context.
     *
     * @param requestContext Request context.
     */
    public void setRequestContext(RequestContext requestContext) {
        this.requestContext = requestContext;
        persistence = new SqlPersistence(requestContext);
    }

    /**
     * Get the RIM object.
     *
     * @return RIM object.
     */
    public T getRimObject() {
        return rimObject;
    }

    /**
     * Set RIM object.
     *
     * @param rimObject RIM object.
     */
    public void setRimObject(T rimObject) {
        this.rimObject = rimObject;
    }

    /**
     * Checks if an object with a certain type <code>clazz</code> and
     * a certain <code>id</code> exists in <code>identMap</code>.
     *
     * @param id ID of RIM object.
     * @param clazz Type of RIM object.
     * @return True if object exists.
     */
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

    /**
     * Check if a RIM object with <code>id</code> exists in <code>identMap</code>.
     * 
     * @param id ID to find.
     * @return True if RIM object with ID exits.
     */
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

    /**
     * Validates an inserted or updated RIM object.
     * Makes sure that all the references to other objects are correct.
     * 
     * @throws be.kzen.ergorr.exceptions.InvalidReferenceException
     * @throws java.sql.SQLException
     */
    public abstract void validate() throws InvalidReferenceException, SQLException;

    /**
     * Validates a delted RIM objects.
     * Makes sure that the deleted object is not refered in the registry.
     *
     * @throws be.kzen.ergorr.exceptions.ReferenceExistsException
     * @throws java.sql.SQLException
     */
    public abstract void validateToDelete() throws ReferenceExistsException, SQLException;
}
