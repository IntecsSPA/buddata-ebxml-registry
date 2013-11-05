/*
 * Project: Buddata ebXML RegRep
 * Class: CommonProperties.java
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
package be.kzen.ergorr.commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Common properties used by different packages.
 *
 * @author <a href="mailto:yaman.ustuntas@kzen.be">Yaman Ustuntas</a>
 */
public class CommonProperties {

    private static Logger logger = Logger.getLogger(CommonProperties.class.getName());
    private static CommonProperties instance = null;
    private Properties props;

    private CommonProperties() {
        loadProperties();
    }

    /**
     * Implement Singleton class, this method is only way to get this object.
     *
     * @return CommonProperties instance.
     */
    public synchronized static CommonProperties getInstance() {
        if (instance == null) {
            instance = new CommonProperties();
        }
        return instance;
    }

    /**
     * Load the common properties.
     * Loads "ergorr.properties" file from class path.
     *
     */
    public void loadProperties() {
        try {
            props = new Properties();
         
            File propFile = new File(getClass().getClassLoader().getResource("ergorr.properties").getFile());

            if (propFile.exists()) {
                props.load(new FileInputStream(propFile));
            } else {
                props.load(this.getClass().getClassLoader().getResourceAsStream("ergorr.properties"));
            }
            // }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Could not load ergorr.properties", ex);
        }
    }

    /** 
     * OBSOLETE: common properties are read directly by the "ergorr.properties" file
     * 
     * Load the common properties.
     * First looks up the system variable "ergorr.common.properties"
     * and if it exists, uses the path for the properties file.
     * Otherwise loads "ergorr.properties" file from class path.
     *
     *
    public void loadProperties() {
    try {
    props = new Properties();
    File propFile=null;
    String sysPropPath = System.getProperty("ergorr.common.properties");
    
    if (sysPropPath == null) {
    propFile = new File(getClass().getClassLoader().getResource("ergorr.properties").getFile());
    // props.load(this.getClass().getClassLoader().getResourceAsStream("ergorr.properties"));
    
    } else {
    propFile = new File(sysPropPath);
    }     
    if (propFile.exists()) {
    props.load(new FileInputStream(propFile));
    } else {
    logger.log(Level.WARNING, "System property 'ergorr.common.properties' is set but file could not be found: " + sysPropPath);
    props.load(this.getClass().getClassLoader().getResourceAsStream("ergorr.properties"));
    }
    // }
    } catch (IOException ex) {
    logger.log(Level.SEVERE, "Could not load ergorr.properties", ex);
    }
    } */
    
    
    /**
     * Get property from common properties.
     *
     * @param key Key of property.
     * @return Property.
     */
    public String get(String key) {
        return props.getProperty(key, "");
    }

    /**
     * Get property from common properties.
     *
     * @param key Key of property.
     * @param defaultValue Default value for property.
     * @return Property.
     */
    public String get(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }

    /**
     * Gets a int property.
     * Returns 0  if property value cannot be parsed to an int.
     *
     * @param key Property key.
     * @return int value.
     */
    public int getInt(String key) {
        int i = 0;
        try {
            i = Integer.parseInt(props.getProperty(key));
        } catch (Throwable t) {
            logger.log(Level.WARNING, "Could not parse Integer value", t);
        }

        return i;
    }

    /**
     * Gets a long property.
     * Returns 0 if property value cannot be parsed to a long.
     *
     * @param key Property key.
     * @return long value.
     */
    public long getLong(String key) {
        long l = 0;
        try {
            l = Long.parseLong(props.getProperty(key));
        } catch (Throwable t) {
            logger.log(Level.WARNING, "Could not parse Long value", t);
        }

        return l;
    }

    /**
     * Gets a boolean property.
     * Returns <code>false</code> if property value cannot be parsed to a boolean.
     *
     * @param key Property key.
     * @return Boolean value.
     */
    public boolean getBoolean(String key) {
        boolean b = false;

        try {
            b = Boolean.valueOf(props.getProperty(key));
        } catch (Throwable t) {
            logger.log(Level.WARNING, "Could not parse Boolean value", t);
        }

        return b;
    }

    /**
     * Gets a string array from one property.
     * Values should be split by |. E.g
     * one|two|three
     *
     * @param key Property key.
     * @return Value strings.
     */
    public String[] getStringArray(String key) {
        String val = props.getProperty(key);

        return (val != null) ? val.split("\\|") : new String[0];
    }

    /**
     * Get an integer array from one property.
     * Values should be split by |. E.g
     * 5|6|7
     *
     * @param key Property key.
     * @return Value integers.
     */
    public Integer[] getIntArray(String key) {
        String val = props.getProperty(key);

        String[] vals = val.split("\\|");
        Integer[] arr = new Integer[vals.length];

        for (int i = 0; i < vals.length; i++) {
            String v = vals[i];
            arr[i] = Integer.parseInt(v);
        }

        return arr;
    }

    public static void removeInstance() {
        CommonProperties.instance = null;
    }
}
