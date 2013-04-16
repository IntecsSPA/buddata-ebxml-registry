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

import be.kzen.ergorr.commons.InternalConstants;
import be.kzen.ergorr.commons.RequestContext;
import be.kzen.ergorr.exceptions.InvalidReferenceException;
import be.kzen.ergorr.exceptions.ReferenceExistsException;
import be.kzen.ergorr.model.rim.IdentifiableType;
import be.kzen.ergorr.persist.service.SqlPersistence;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Abstract validator to be extended by RIM object validators.
 * A Validator may add Identifiables to the <code>addedIdents</code>
 * depending on the validate or validateToDelete being performed.
 * E.g a RegistryObjects Associations will be also added to the
 * <code>addedIdents</code> list to be deleted if the Associations are
 * not already in the list.
 * 
 * @author yamanustuntas
 */
public abstract class AbstractValidator<T extends IdentifiableType> {

    protected List<IdentifiableType> flatIdents;
    protected List<IdentifiableType> addedIdents;
    protected T rimObject;
    protected RequestContext requestContext;
    protected SqlPersistence persistence;

    public AbstractValidator() {
        addedIdents = new ArrayList<IdentifiableType>();
    }

    /**
     * Set the request context.
     *
     * @param requestContext Request context.
     */
    public void setRequestContext(RequestContext requestContext) {
        this.requestContext = requestContext;
        persistence = new SqlPersistence(requestContext.copyForNewConn());
    }

    /**
     * Get flat list of IdentifiableType.
     *
     * @return List of IdentifiableType.
     */
    public List<IdentifiableType> getFlatIdents() {
        return flatIdents;
    }

    /**
     * Set flat list of IdentifiableType.
     *
     * @param flatIdents List of IdentifiableType
     */
    public void setFlatIdents(List<IdentifiableType> flatIdents) {
        this.flatIdents = flatIdents;
    }

    /**
     * Get the list of added IdentifiableTypes.
     * These objects are a result of validation of an insert or delete.
     * Example, if an object is to be deleted in {@code flatIdents},
     * a validator would add nested/related object to be deleted
     * in this list.
     *
     * @return List of added IdentifiableTypes.
     */
    public List<IdentifiableType> getAddedIdents() {
        return addedIdents;
    }

    /**
     * Set the list of added IdentifiableTypes.
     * These objects are a result of validation of an insert or delete.
     * Example, if an object is to be deleted in {@code flatIdents},
     * a validator would add nested/related object to be deleted
     * in this list.
     *
     * @param addedIdents List of added IdentifiableTypes.
     */
    public void setAddedIdents(List<IdentifiableType> addedIdents) {
        this.addedIdents = addedIdents;
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
     * Check if a RIM object with <code>id</code> exists in <code>flatIdents</code>.
     *
     * @param id ID of RIM object.
     * @param clazz Type of RIM object.
     * @return True if object exists.
     */
    protected boolean idExistsInRequest(String id) {
        boolean exists = false;

        for (IdentifiableType ident : flatIdents) {
            if (ident.getId().equals(id)) {
                exists = true;
                break;
            }
        }

        if (!exists) {
            for (IdentifiableType ident : addedIdents) {
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
    protected boolean idExistsInRequest(String id, Class<? extends IdentifiableType> identClass) {
        boolean exists = false;

        for (IdentifiableType ident : flatIdents) {
            if (ident.getId().equals(id) && ident.getClass().getName().equals(identClass.getName())) {
                exists = true;
                break;
            }
        }

        if (!exists) {
            for (IdentifiableType ident : addedIdents) {
                if (ident.getId().equals(id) && ident.getClass().getName().equals(identClass.getName())) {
                    exists = true;
                    break;
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
