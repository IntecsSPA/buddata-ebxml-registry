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
     * Load the common properties
     */
    private void loadProperties() {
        try {
            props = new Properties();
            props.load(this.getClass().getResourceAsStream("common.properties"));
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Could not load common.properties");
        }
    }

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
    
    public int getInt(String key) {
        int i = 0;
        try {
            i = Integer.parseInt(props.getProperty(key));
        } catch (Throwable t) {
            logger.log(Level.SEVERE, "Could not parse Integer value", t);
        }
        
        return i;
    }
    
    public long getLong(String key) {
        long l = 0;
        try {
            l = Long.parseLong(props.getProperty(key));
        } catch (Throwable t) {
            logger.log(Level.SEVERE, "Could not parse Long value", t);
        }
        
        return l;
    }
    
    public boolean getBoolean(String key) {
        boolean b = false;
        
        try {
            b = Boolean.valueOf(props.getProperty(key));
        } catch (Throwable t) {
            logger.log(Level.SEVERE, "Could not parse Boolean value", t);
        }
        
        return b;
    }
    
    public String[] getStringArray(String key) {
        String val = props.getProperty(key);
        
        return val.split("\\|");
    }
    
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
}
