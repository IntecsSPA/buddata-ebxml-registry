package be.kzen.ergorr.persist.service;

/**
 *
 * @author Yaman Ustuntas
 */
public class DbConnectionParams {
    private String dbUrl;
    private String dbName;
    private String dbUser;
    private String dbPassword;

    public DbConnectionParams() {
    }

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
    
    public String createConnectionString() {
        return "jdbc:postgresql://" + dbUrl + "/" + dbName;
    }
    
    @Override
    public String toString() {
        return createConnectionString() + " (user: " + dbUser + " , pw: " + dbPassword + ")";
    }
}
