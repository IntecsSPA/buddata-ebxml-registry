/*
 * Project: Buddata ebXML RegRep
 * Class: QueryObject.java
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
import be.kzen.ergorr.interfaces.soap.csw.ServiceExceptionReport;
import be.kzen.ergorr.model.util.OFactory;
import be.kzen.ergorr.persist.dao.GenericObjectDAO;
import be.kzen.ergorr.persist.dao.IdentifiableTypeDAO;

/**
 * Helper class used by <code>SqlQuery</code> and
 * <code>QueryBuilder</code> to store information of a queried object.
 *
 * @author Yaman Ustuntas
 */
public class QueryObject {

    private String objectName;
    private String sqlAlias;
    // if object is defined in typeNames but not used in the filter.
    private boolean usedInQuery;
    private static final String TABLE_NAME_PREFIX = "t_";

    /**
     * Constructor.
     *
     * @param objectName Name of the queried object.
     * @param sqlAlias SQL alias of the queried object.
     */
    public QueryObject(String objectName, String sqlAlias) {
        usedInQuery = false;
        this.objectName = objectName;
        this.sqlAlias = sqlAlias;
    }

    /**
     * Get the class of the queried object.
     *
     * @return Class of queried object.
     * @throws QueryException
     */
    public Class getObjClass() throws QueryException {
        try {
            return OFactory.getXmlClassByElementName(objectName);
        } catch (ClassNotFoundException ex) {
            throw new QueryException("Could not find object: " + objectName, ex);
        }
    }

    /**
     * Table name of the queried object.
     *
     * @return Table name.
     */
    public String getTableName() {
        return TABLE_NAME_PREFIX + objectName.toLowerCase();
    }

    /**
     * Get object name.
     *
     * @return Object name.
     */
    public String getObjectName() {
        return objectName;
    }

    /**
     * Get SQL alias.
     *
     * @return SQL alias.
     */
    public String getSqlAlias() {
        return sqlAlias;
    }

    /**
     * Check if this auery object is used in the query.
     *
     * @return True if object used in query.
     */
    public boolean isUsedInQuery() {
        return usedInQuery;
    }

    /**
     * Set if this auery object is used in the query.
     *
     * @param usedInQuery Set to {@code true} if object used in query.
     */
    public void setUsedInQuery(boolean usedInQuery) {
        this.usedInQuery = usedInQuery;
    }
}
