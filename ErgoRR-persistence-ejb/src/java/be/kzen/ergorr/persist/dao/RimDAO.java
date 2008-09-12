/*
 * Project: Buddata ebXML RegRep
 * Class: RimService.java
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
package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.model.rim.IdentifiableType;
import be.kzen.ergorr.persist.model.IdentifiableDM;
import be.kzen.ergorr.persist.model.RegistryObjectDM;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import javax.xml.bind.JAXBElement;

/**
 * DAO bean for RIM objects.
 * 
 * @author Yaman Ustuntas
 */
@Local
public interface RimDAO {

    /**
     * Save an object to the database.
     * 
     * @param obj Object to save.
     */
    public void save(Object obj);
    
    /**
     * Merge an object.
     * 
     * @param obj Object to merge.
     * @return Merged object.
     */
    public Object merge(Object obj);
    
    /**
     * Query the database for any <code>IdentifiableType</code>.
     * 
     * @param sql HQL statement.
     * @param params Parameters to be added to the HQL statement.
     * @param maxResults Maximum amount of results.
     * @param firstResult Index of the first result.
     * @return List of objects matching the query.
     */
    public List<JAXBElement<? extends IdentifiableType>> query(String sql, Map<String, Object> params, int maxResults, int firstResult);
    
    /**
     * Query the database for a specific object type.
     * This is useful when you want to get an object by ID and
     * you know its object type. Giving the object type will make
     * the query faster as it wouldn't have to look into all Identifiables.
     * 
     * @param <T> Expected return type.
     * @param sql HQL statement.
     * @param params Parameters to be added to the HQL statement.
     * @param clazz Extected return type.
     * @return List of <code>T</code> matching the query.
     */
    public <T> List<T> query(String sql, Map<String, String> params, Class<T> clazz);
    
    /**
     * Get objects by IDs.
     * 
     * @param ids IDs of objects to fetch.
     * @return List of requested objects by ID.
     */
    public List<JAXBElement <? extends IdentifiableType>> getByIds(List<String> ids);
    
    /**
     * Get all the different slot name/type pairs from server.
     * 
     * @return Map of name/type pair.
     */
    public Map<String, String> getSlotTypes();
    
    /**
     * Get a single object by ID.
     * 
     * @param id ID of requested object.
     * @return Request object or null if object is not found.
     */
    public RegistryObjectDM getById(String id);
    
    /**
     * Count the matching objects of a query.
     * 
     * @param sql HQL count statement.
     * @param params Parameters to be added to the HQL statement.
     * @return Number of matched objects.
     */
    public long getResultCount(String sql, Map<java.lang.String, Object> params);
    public boolean idExists(String id, Class clazz);
    public <T extends IdentifiableDM> T find(String id, Class<T> clazz);
}
