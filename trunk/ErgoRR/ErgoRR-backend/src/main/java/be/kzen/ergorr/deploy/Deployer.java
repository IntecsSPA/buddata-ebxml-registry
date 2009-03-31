/*
 * Project: Buddata ebXML RegRep
 * Class: Deployer.java
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
package be.kzen.ergorr.deploy;

import be.kzen.ergorr.commons.CommonProperties;
import be.kzen.ergorr.commons.FileUtil;
import be.kzen.ergorr.commons.InternalConstants;
import be.kzen.ergorr.commons.RequestContext;
import be.kzen.ergorr.persist.service.DbConnectionParams;
import be.kzen.ergorr.persist.service.SqlPersistence;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.catalina.ant.DeployTask;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

/**
 *
 * @author yamanustuntas
 */
public class Deployer {

    private static Logger logger = Logger.getLogger(Deployer.class.getName());
    private static final String ERGO_HOME = System.getenv("ERGO_HOME");
    private String deploymentName;
    // Name can only contain characters a-z, A-Z and 0-9. It must start with a non-numeric character.
    private static final String NAME_REGEX = "^[a-zA-Z]{1}[0-9a-zA-Z]*";
    private static final String POSTGRES_MAIN_DB = "postgres";
    private static final int NAME_MAX_LENGTH = 12;
    private SqlPersistence persistence;
    private DbConnectionParams connParams;
    private DeployTask tomcatDeploy;

    public Deployer(String deploymentName, DbConnectionParams connParams, String appServerUrl, String appServerUser, String appServerPassword) {
        this.deploymentName = deploymentName;
        this.connParams = connParams;
        tomcatDeploy = new DeployTask();
        tomcatDeploy.setUrl(appServerUrl);
        tomcatDeploy.setUsername(appServerUser);
        tomcatDeploy.setPassword(appServerPassword);
        RequestContext reqContext = new RequestContext();
        reqContext.putParam(InternalConstants.DB_CONNECTION_PARAMS, connParams);
        persistence = new SqlPersistence(reqContext);
    }

    public void deploy() throws DeployerException {
        if (deploymentName.length() <= NAME_MAX_LENGTH) {

            if (deploymentName.matches(NAME_REGEX)) {
                if (!appInstanceExists() && !dbInstanceExists()) {
                    connParams.setDbName(POSTGRES_MAIN_DB);
                    createDatabase();
                    connParams.setDbName(deploymentName);
                    loadTables();
                    configureApp();
                    buildApp();
                    deployApp();
                    loadInitData();
                } else {
                    throw new DeployerException("A registry (db or app) instance with the name " + deploymentName + " already exists.");
                }
            } else {
                throw new DeployerException("Name can only contain characters a-z, A-Z and 0-9. It must start with a non-numeric character.");
            }
        } else {
            throw new DeployerException("Deployment name too long. Max: " + NAME_MAX_LENGTH);
        }
    }

    public boolean appInstanceExists() throws DeployerException {
        boolean appExists = false;

        String tomcatHome = System.getenv("CATALINA.HOME");

        if (tomcatHome != null) {
            File file = new File(tomcatHome + "/webapps/" + deploymentName);
            appExists = file.exists();
        } else {
            throw new DeployerException("Env. variable CATALINA.HOME is not set.");
        }

        return appExists;
    }

    /**
     * Check if a database with the deploymentName already exists.
     * Not bullet proof. Return false also if the database is not running.
     *
     * @return True if database exists.
     */
    public boolean dbInstanceExists() {
        boolean dbExists = true;
        connParams.setDbName(deploymentName);
        try {
            String testSql = "select 1 as num;";
            executeSql(testSql);
        } catch (DeployerException ex) {
            dbExists = false;
        }
        return dbExists;
    }

    public void createDatabase() throws DeployerException {
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE DATABASE ").append(deploymentName);
        sql.append(" WITH OWNER = ").append(connParams.getDbUser());
        sql.append(" ENCODING = 'UTF8' TEMPLATE = ").append(CommonProperties.getInstance().get("db.templatePostgis")).append(";");

        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "Creating database " + deploymentName);
        }

        executeSql(sql.toString());
    }

    public void loadTables() throws DeployerException {
        File sqlScript = new File(System.getenv("ERGO_HOME") + "/ErgoRR-persistence/conf/database.sql");

        try {
            String sql = FileUtil.readFileAsString(sqlScript);

            if (logger.isLoggable(Level.FINE)) {
                logger.log(Level.FINE, "Creating tables in database " + deploymentName);
            }

            executeSql(sql);

        } catch (IOException ex) {
            throw new DeployerException(ex);
        }
    }

    public void configureApp() throws DeployerException {
        try {
            configureProps();
            configureDataSource();
        } catch (IOException ex) {
            throw new DeployerException(ex);
        }
    }

    public void buildApp() throws DeployerException {
        String tmpDirPath = System.getProperty("java.io.tmpdir");
        File deployDir = new File(tmpDirPath + "/ergorr");
        deployDir.delete();
        deployDir.mkdir();
    }

    public void deployApp() {

        tomcatDeploy.setWar(System.getenv("ERGO_HOME") + "/ErgoRR-web-war/dist/" + deploymentName + ".war");
        tomcatDeploy.setPath("/" + deploymentName);
        tomcatDeploy.execute();
    }

    public void loadInitData() {
    }

    private void configureProps() throws IOException {
        Properties props = new Properties();
        File propsTemplate = new File(System.getenv("ERGO_HOME") + "/ErgoRR-Commons/conf/template-common.properties");
        props.load(new FileInputStream(propsTemplate));
        props.put("db.url", connParams.getDbUrl());
        props.put("db.user", connParams.getDbUser());
        props.put("db.password", connParams.getDbPassword());
        props.put("deployName", deploymentName);
        props.put("db.datasource", "java:comp/env/jdbc/" + deploymentName);

        File propsFile = new File(System.getenv("ERGO_HOME") + "/ErgoRR-Commons/conf/common.properties");
        props.store(new FileOutputStream(propsFile), "");
    }

    private void configureDataSource() throws IOException {
        String dataSourceConfPath = System.getenv("ERGO_HOME") + "/ErgoRR-web-war/conf/template-datasource.xml";
        File dataSourceConfTemp = new File(dataSourceConfPath);
        String dataSourceConfStr = FileUtil.readFileAsString(dataSourceConfTemp);
        dataSourceConfStr = dataSourceConfStr.replaceAll("!!name!!", deploymentName);
        dataSourceConfStr = dataSourceConfStr.replaceAll("!!db.url!!", connParams.getDbUrl());
        dataSourceConfStr = dataSourceConfStr.replaceAll("!!db.user!!", connParams.getDbUser());
        dataSourceConfStr = dataSourceConfStr.replaceAll("!!db.password!!", connParams.getDbPassword());
        
        File newDataSourceConf = new File(System.getenv("ERGO_HOME") + "/ErgoRR-web-war/conf/" + deploymentName + ".xml");
        FileUtil.writeFile(newDataSourceConf, dataSourceConfStr);
    }

    private void executeSql(String sql) throws DeployerException {
        try {
            if (logger.isLoggable(Level.FINE)) {
                logger.log(Level.FINE, "Connection params: " + connParams.createConnectionString());
            }

            Connection conn = persistence.getConnection();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            persistence.closeConnection(conn);
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Could not execute script", ex);
            throw new DeployerException("Could not connect to database", ex);
        }
    }

    private void runAnt(String target) throws BuildException {
        File buildFile = new File(ERGO_HOME + "/deploy.xml");
        Project p = new Project();
        DefaultLogger consoleLogger = new DefaultLogger();
        consoleLogger.setErrorPrintStream(System.err);
        consoleLogger.setOutputPrintStream(System.out);
        consoleLogger.setMessageOutputLevel(Project.MSG_INFO);
        p.addBuildListener(consoleLogger);
        p.fireBuildStarted();
        p.init();
        ProjectHelper helper = ProjectHelper.getProjectHelper();
        p.addReference("ant.projectHelper", helper);
        helper.parse(p, buildFile);
        p.executeTarget(target);
        p.fireBuildFinished(null);
    }
}
