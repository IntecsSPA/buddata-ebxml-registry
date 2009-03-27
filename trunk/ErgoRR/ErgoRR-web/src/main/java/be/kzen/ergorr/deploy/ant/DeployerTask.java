/*
 * Project: Buddata ebXML RegRep
 * Class: DeployerTask.java
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

import be.kzen.ergorr.deploy.Deployer;
import be.kzen.ergorr.deploy.DeployerException;
import be.kzen.ergorr.persist.service.DbConnectionParams;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * An ant task wrapping the Deployer implementation.
 * 
 * @author yamanustuntas
 */
public class DeployerTask extends Task {

    private String deployName = "";
    private String dbUrl = "";
    private String dbUser = "";
    private String dbPassword = "";
    private String appServerUrl = "";
    private String appServerUser = "";
    private String appServerPassword = "";

    @Override
    public void execute() throws BuildException {
        DbConnectionParams connParams = new DbConnectionParams(dbUrl, "", dbUser, dbPassword);
        Deployer deployer = new Deployer(deployName, connParams, appServerUrl, appServerUser, appServerPassword);

        try {
            deployer.deploy();
        } catch (DeployerException ex) {
            throw new BuildException(ex);
        }
    }

    public void setDeployName(String deployName) {
        this.deployName = deployName;
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

    public void setAppServerPassword(String appServerPassword) {
        this.appServerPassword = appServerPassword;
    }

    public void setAppServerUrl(String appServerUrl) {
        this.appServerUrl = appServerUrl;
    }

    public void setAppServerUser(String appServerUser) {
        this.appServerUser = appServerUser;
    }
}
