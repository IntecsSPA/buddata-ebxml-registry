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
import be.kzen.ergorr.interfaces.soap.ServiceExceptionReport;
import be.kzen.ergorr.model.util.OFactory;
import be.kzen.ergorr.persist.dao.GenericObjectDAO;
import be.kzen.ergorr.persist.dao.IdentifiableTypeDAO;

/**
 *
 * @author Yaman Ustuntas
 */
public class QueryObject {

    private String objectName;
    private String sqlAlias;
    private boolean returnType;
    private Class objClass;

    public QueryObject() {
    }

    public QueryObject(String objectName, String sqlAlias) throws QueryException {
        this.objectName = objectName;
        this.sqlAlias = sqlAlias;

        try {
            objClass = OFactory.getXmlClassByElementName(objectName);
        } catch (ClassNotFoundException ex) {
            throw new QueryException("Could not find object: " + objectName, ex);
        }
    }

    public Class getObjClass() {
        return objClass;
    }

    public String getTableName() {
        return objectName.toLowerCase();
    }

    public String getObjectName() {
        return objectName;
    }

    public String getSqlAlias() {
        return sqlAlias;
    }

    public boolean isReturnType() {
        return returnType;
    }

    public void setReturnType(boolean returnType) {
        this.returnType = returnType;
    }
}
