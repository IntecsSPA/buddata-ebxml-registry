/*
 * Project: Buddata ebXML RegRep
 * Class: QueryBuilderImpl.java
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
package be.kzen.ergorr.query;

import be.kzen.ergorr.exceptions.QueryException;
import be.kzen.ergorr.model.csw.ElementSetType;
import java.util.List;

/**
 * Interface for a OGC filter to SQL builder.
 *
 * @author Yaman Ustuntas
 */
public interface QueryBuilder {

    /**
     * Process the OGC filter.
     *
     * @return Generqted SQL query string.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    public String build() throws QueryException;

    /**
     * Create the query which will return the
     * total number of objects matched.
     *
     * @return SQL query.
     */
    public String createCountQuery();

    /**
     * List of query parameters in the order
     * as they should appear in the SQL query statement.
     *
     * @return Query parameter list.
     */
    public List<Object> getParameters();

    /**
     * Get the object to be returned by query.
     *
     * @return Object to return.
     */
    public QueryObject getReturnObject();

    /**
     * Get the element set type of the query.
     *
     * @return ElementSetType
     */
    public ElementSetType getResultSet();

    /**
     * Get the start position of records.
     *
     * @return Start position.
     */
    public int getStartPosition();

    /**
     * Get the maximum number of records to fetch.
     *
     * @return Number of records;
     */
    public int getMaxRecords();
}
