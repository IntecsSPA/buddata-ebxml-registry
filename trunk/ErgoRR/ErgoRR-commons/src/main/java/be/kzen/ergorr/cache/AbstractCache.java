/*
 * Project: Buddata ebXML RegRep
 * Class: AbstractCache.java
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
package be.kzen.ergorr.cache;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * Abstract class for all caches.
 *
 * @author Yaman Ustuntas
 */
public abstract class AbstractCache {
    private static Logger logger = Logger.getLogger(AbstractCache.class.getName());
    protected static CacheManager cacheManager;
    protected Cache cache;
    
    /**
     * Constructor
     */
    protected AbstractCache() {
        if (cacheManager == null) {
            try {
                cacheManager = new CacheManager(this.getClass().getResourceAsStream("ehcache.xml"));
            } catch (CacheException ex) {
                logger.log(Level.SEVERE, "Could not load cache manager", ex);
            }
        }
    }
    
    /**
     * Add an item to cache
     *
     * @param key Key of item.
     * @param value Item to cache.
     */
    public void add(String key, Object value) {
        Element e = new Element(key, value);
        cache.put(e);
    }
    
    /**
     * Get item from cache.
     *
     * @param key Key of item.
     * @return Item. <code>null</code> if item was not found.
     */
    public Object get(String key) {
        Element e = cache.get(key);
        if (e != null) {
            return e.getObjectValue();
        } else {
            return null;
        }
    }
    
    /**
     * Remove element from cache
     *
     * @param key Key of item to remove 
     */
    public void remove(String key) {
        cache.remove(key);
    }
    
    /**
     * Clear cache
     */
    public void clear() {
        cache.removeAll();
    }
    
    /**
     * Flush cache
     */
    public void writeNow() {
        cache.flush();
    }
    
    /**
     * Shutdown cache manager
     */
    public static void shutdownManager() {
        if (cacheManager != null) {
            cacheManager.shutdown();
        }
    }
    
    /**
     * Initialize cache.
     */
    public abstract void init();
}
