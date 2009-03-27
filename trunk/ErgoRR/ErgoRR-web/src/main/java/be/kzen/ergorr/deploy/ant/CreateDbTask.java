/*
 * Project: Buddata ebXML RegRep
 * Class: DbTask.java
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
package be.kzen.ergorr.deploy.ant;

import be.kzen.ergorr.commons.CommonProperties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 *
 * @author yamanustuntas
 */
public class CreateDbTask extends Task {

    private String dbUrl;
    private String dbName;
    private String dbUser;
    private String dbPassword;
    private String template;
    private final static String MAIN_DB = "postgres";

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    @Override
    public void execute() throws BuildException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            throw new BuildException("Could not load driver: org.postgresql.Driver, make sure it is in your classpath");
        }

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(createConnectionString(), dbUser, dbPassword);
        } catch (SQLException ex) {
            throw new BuildException(ex);
        }

        StringBuilder sql = new StringBuilder();
        sql.append("CREATE DATABASE ").append(dbName);
        sql.append(" WITH OWNER = ").append(dbUser);
        sql.append(" ENCODING = 'UTF8'");

        if (template != null && template.length() > 0) {
             sql.append(" TEMPLATE = ").append(template);
        }
        sql.append(";");
        System.out.println(sql.toString());
        
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql.toString());
        } catch (SQLException ex) {
            closeConnection(conn);
            throw new BuildException(ex);
        }

        closeConnection(conn);
    }

    private void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception ex) {}
        }
    }

    private String createConnectionString() {
        return "jdbc:postgresql://" + dbUrl + "/" + MAIN_DB;
    }
}
