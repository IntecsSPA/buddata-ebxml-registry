/*
 * Project: Buddata ebXML RegRep
 * Class: RimValidator.java
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
package be.kzen.ergorr.service.validator;

import be.kzen.ergorr.exceptions.InvalidReferenceException;
import be.kzen.ergorr.model.rim.IdentifiableType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Checks that all the ID references specified in RIM are valid.
 * ID's such as id, sourceObject, targetObject, objectType, ...
 * 
 * Also handles cache updates while doing validations.
 *
 * @author yamanustuntas
 */
public class RimValidator {
    private static Logger logger = Logger.getLogger(RimValidator.class.getName());
    private Map<String, List<IdentifiableType>> identMap;
    private List<IdentifiableType> idents;

    public RimValidator(List<IdentifiableType> idents) {
        this.idents = idents;
        identMap = new HashMap<String, List<IdentifiableType>>();
        loadMap();
    }

    public void validate() throws InvalidReferenceException, SQLException {
        for (IdentifiableType ident : idents) {
            String validatorClassName = "be.kzen.ergorr.service.validator." + ident.getClass().getSimpleName() + "V";
            try {
                Class validatorClass = Class.forName(validatorClassName);
                AbstractValidator validator = (AbstractValidator) validatorClass.newInstance();
                validator.setIdentMap(identMap);
                validator.setRimObject(ident);
                validator.validate();
                
            } catch (InstantiationException ex) {
                logger.log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                logger.log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Load <code>indetMap</code> with <code>idents</code>
     * sorting them by RIM type.
     */
    private void loadMap() {
        for (IdentifiableType ident : idents) {
            String key = ident.getClass().getName();

            List<IdentifiableType> list = identMap.get(key);

            if (list == null) {
                list = new ArrayList<IdentifiableType>();
                identMap.put(key, list);
            }
            
            list.add(ident);
        }
    }
}
