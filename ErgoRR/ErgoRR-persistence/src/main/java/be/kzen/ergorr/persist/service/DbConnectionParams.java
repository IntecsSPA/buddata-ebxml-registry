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

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

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
