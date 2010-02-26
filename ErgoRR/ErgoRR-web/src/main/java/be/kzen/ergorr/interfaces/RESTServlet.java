/*
 * Project: Buddata ebXML RegRep
 * Class: RegistryHTTPServlet.java
 * Copyright (C) 2009
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
package be.kzen.ergorr.interfaces;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.Reader;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;

/**
 * CSW HTTP interface.
 * 
 * @author Massimiliano Fanciulli
 * @author Yaman Ustuntas
 */
public class RESTServlet extends HttpServlet {

    protected static final String REST_RESOURCE_CONFIG = "config";
    protected static final String REST_RESOURCE_AUTHENTICATE = "authenticate";
    private static Logger logger = Logger.getLogger(RESTServlet.class.getName());

    /**
     * {@inheritDoc}
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri;
        String jsonStr;

        if (authenticateUser(request)) {

            uri = request.getRequestURI();
            if (uri.endsWith(REST_RESOURCE_CONFIG)) {
                jsonStr = getConfiguration(request);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            response.setHeader("Content-Type", "application/json");
            response.getOutputStream().print(jsonStr);
        } else {
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri;
        JsonObject obj = null;
        if (authenticateUser(request)) {
            obj = readConfigurationFromRequest(request.getReader());

            uri = request.getRequestURI();
            if (uri.endsWith(REST_RESOURCE_CONFIG)) {
                saveProperties(obj);
            } else if (uri.endsWith(REST_RESOURCE_AUTHENTICATE)) {
                return;
            }
        } else {
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        }
    }

    /**
     *
     * @param request
     * @return
     */
    private String getConfiguration(HttpServletRequest request) {
        String jsonStr;
        JsonObject obj = null;
        Gson gson;
        Properties props;
        Enumeration<Object> keys;

        props = loadProperties();
        keys = props.keys();

        obj = new JsonObject();
        while (keys.hasMoreElements()) {
            String key;
            key = (String) keys.nextElement();

            obj.addProperty(key, props.getProperty(key));
        }

        gson = new Gson();
        jsonStr = gson.toJson(obj);
        return jsonStr;
    }

    /**
     * This method has been copied fromt the CommonProperties class.
     * @return Properties read from ergorr.properties file
     */
    public Properties loadProperties() {
        try {
            Properties props;

            props = new Properties();

            props.load(new FileInputStream(getPropertiesFile()));

            return props;
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Could not load ergorr.properties", ex);
            return null;
        }
    }

    /**
     * This method has been copied fromt the CommonProperties class.
     * @return Properties read from ergorr.properties file
     */
    public void saveProperties(JsonObject obj) throws IOException {
        Properties oldprops;
        File propFile;

        oldprops = loadProperties();
        propFile = getPropertiesFile();

        Set<Entry<String, JsonElement>> entryset = obj.entrySet();
        Iterator<Entry<String, JsonElement>> iterator = entryset.iterator();

        while (iterator.hasNext()) {
            Entry<String, JsonElement> entry = iterator.next();
            oldprops.setProperty(entry.getKey(), entry.getValue().getAsString());
        }

        oldprops.store(new FileOutputStream(propFile), null);

    }

    /**
     *
     * @param input
     * @return
     */
    private JsonObject readConfigurationFromRequest(Reader input) {
        JsonParser parser;
        JsonObject obj;

        parser = new JsonParser();
        obj = (JsonObject) parser.parse(input);

        return obj;
    }

    /**
     * 
     * @return
     */
    private File getPropertiesFile() {
        String sysPropPath = System.getProperty("ergorr.common.properties");

        if (sysPropPath == null) {
            return new File(this.getClass().getClassLoader().getResource("ergorr.properties").getFile());
        } else {
            return new File(sysPropPath);

        }
    }

    /**
     * This method checks client authorization.
     * @param request Request class containing all information to use to authenticate
     * the user
     * @return True or False depending of the user authorization.
     */
    private boolean authenticateUser(HttpServletRequest request) {
        return true;
    }
}
