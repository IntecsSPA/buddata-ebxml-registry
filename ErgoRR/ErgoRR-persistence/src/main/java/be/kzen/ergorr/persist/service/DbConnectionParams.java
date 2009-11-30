/*
 * Project: Buddata ebXML RegRep
 * Class: DbConnectionParams.java
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
package be.kzen.ergorr.persist.service;

import be.kzen.ergorr.commons.CommonProperties;

/**
 * Class which wraps database connection parameters.
 *
 * @author Yaman Ustuntas
 */
public class DbConnectionParams {
    private static DbConnectionParams defaultInstance;
    private String dbUrl;
    private String dbName;
    private String dbUser;
    private String dbPassword;

    public DbConnectionParams() {
    }

    /**
     * Constructor.
     *
     * @param dbUrl Database URL. Just address and port. E.g localhost:5432
     * @param dbName Database name.
     * @param dbUser Database connection user.
     * @param dbPassword Database connection password.
     */
    public DbConnectionParams(String dbUrl, String dbName, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbName = dbName;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    /**
     * Get database name.
     *
     * @return Database name.
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * Set database name.
     *
     * @param dbName Database name.
     */
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    /**
     * Get database password.
     *
     * @return Database password.
     */
    public String getDbPassword() {
        return dbPassword;
    }

    /**
     * Set database password.
     *
     * @param dbPassword Database password.
     */
    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    /**
     * Get database URL.
     *
     * @return Database URL.
     */
    public String getDbUrl() {
        return dbUrl;
    }

    /**
     * Set database URL.
     * Format: <host>:<port>
     * E.g: localhost:5432
     *
     * @param dbUrl Database URL
     */
    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    /**
     * Get database user.
     *
     * @return Database user.
     */
    public String getDbUser() {
        return dbUser;
    }

    /**
     * Set database user.
     *
     * @param dbUser Database user.
     */
    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    /**
     * Get the default database connection parameters from configuration.
     *
     * @return Default database connection parameters.
     */
    public synchronized static DbConnectionParams getDefaultInstance() {

        if (defaultInstance == null) {
            defaultInstance = new DbConnectionParams(
                    CommonProperties.getInstance().get("db.url"),
                    CommonProperties.getInstance().get("deployName"),
                    CommonProperties.getInstance().get("db.user"),
                    CommonProperties.getInstance().get("db.password"));
        }


        return defaultInstance;
    }

    /**
     * Creates the full database conneciton string.
     * E.g jdbc:postgresql://localhost:5432/myDB
     *
     * @return Database connection string.
     */
    public String createConnectionString() {
        return "jdbc:postgresql://" + dbUrl + "/" + dbName;
    }
    
    @Override
    public String toString() {
        return createConnectionString() + " (user: " + dbUser + " , pw: " + dbPassword + ")";
    }
}
