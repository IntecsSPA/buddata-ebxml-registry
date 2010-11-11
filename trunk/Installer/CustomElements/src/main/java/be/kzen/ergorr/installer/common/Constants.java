/*
 * Project: Buddata ebXML Installer
 * Class: Constants.java
 * Copyright (C) 2009 Bram Van Dam
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
package be.kzen.ergorr.installer.common;

/**
 * Constants needed to process IzPack installer data.
 */
public class Constants {
	public static final String URL_PARAM = "ERGO_DB_URL";
	public static final String DB_PARAM = "ERGO_DEPLOY_NAME";
	public static final String USER_PARAM = "ERGO_DB_USERNAME";
	public static final String PASS_PARAM = "ERGO_DB_PASSWORD";
	public static final String TEMPLATE_PARAM = "ERGO_DB_POSTGIS";
	public static final String DROP_PARAM = "ERGO_DB_DROP";
	public static final String WRITE_DB_PARAM = "ERGO_DB_WRITE";
	public static final String DATAFILES_PARAM = "ERGO_XML_FILES";
	public static final String INSTALLTYPE_PARAM = "ERGO_INSTALL_TYPE";
	public static final String BACKEND_INSTALL_PATH_PARAM = "ERGO_BACKEND_INSTALL_PATH";
	public static final String TOMCAT_INSTALL_PATH_PARAM = "ERGO_TOMCAT_INSTALL_PATH";

	public static final String DEPLOY_PATH = "%s/webapps/%s";

	public static final String WEBAPP_CLASSPATH = "WEB-INF/classes/";
	public static final String ERGORR_PROPERTY = "ergorr.common.properties";
	public static final String ERGORR_PROPERTIES_FILE = "ergorr.properties";
	public static final String STANDALONE_TYPE = "alone";
	public static final String MODEL_DIR = "/defaults/model";
	
	public static final String TRUE = "true";
}
