/*
 * Project: Buddata ebXML RegRep
 * Class: RequestContext.java
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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Holds data for a registry request.
 *
 * @author Yaman Ustuntas
 */
public class RequestContext {

    private static Logger logger = Logger.getLogger(RequestContext.class.getName());
    private Object request;
    private Map<Integer, Object> params;

    public RequestContext() {
        params = new ConcurrentHashMap<Integer, Object>();
    }

    public Object getRequest() {
        return request;
    }

    public void setRequest(Object request) {
        this.request = request;
    }

    public void putParam(Integer key, Object value) {
        params.put(key, value);
    }

    public Object getParam(Integer key) {
        return params.get(key);
    }

    public <T> T getParam(Integer key, Class<T> clazz) {
        Object value = params.get(key);

        if (value != null) {
            if (clazz.isInstance(value)) {
                return (T) value;
            } else {
                logger.log(Level.WARNING, "Context with key '" + key + "' was expected to be of type " + clazz.getSimpleName() + " but was inserted as " + value.getClass().getSimpleName());
            }
        }

        return null;
    }
}
