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

/**
 *
 * @author Yaman Ustuntas
 */
public class QueryObject {
    private String objectType;
    private String sqlAlias;
    private boolean returnType;

    public QueryObject() {
    }

    public QueryObject(String objectType, String sqlAlias) {
        this.objectType = objectType;
        this.sqlAlias = sqlAlias;
    }
    
    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getSqlAlias() {
        return sqlAlias;
    }

    public void setSqlAlias(String sqlAlias) {
        this.sqlAlias = sqlAlias;
    }

    public boolean isReturnType() {
        return returnType;
    }

    public void setReturnType(boolean returnType) {
        this.returnType = returnType;
    }
}
