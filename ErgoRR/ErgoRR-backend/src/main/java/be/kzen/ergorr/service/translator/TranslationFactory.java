/*
 * Project: Buddata ebXML RegRep
 * Class: TranslationFactory.java
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
package be.kzen.ergorr.service.translator;

import be.kzen.ergorr.commons.CommonFunctions;
import be.kzen.ergorr.exceptions.TranslationException;
import be.kzen.ergorr.model.rim.RegistryObjectListType;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;

/**
 * Initializes and invokes translators for registered XML elements.
 * 
 * @author Yaman Ustuntas
 */
public class TranslationFactory {

    private static Logger logger = Logger.getLogger(TranslationFactory.class.getName());
    private static Properties transProps;

    public TranslationFactory() {
        if (transProps == null) {
            transProps = new Properties();
            try {
                transProps.load(this.getClass().getResourceAsStream("translator.properties"));
            } catch (IOException ex) {
                logger.log(Level.SEVERE, "Could not load translator properties", ex);
            }
        }
    }

    /**
     * Translates the <code>jaxbEl</code> to a RegistryObjectListType.
     * First checks if the namespace of the <code>jaxbEl</code> has a
     * registered translator and continues processing if tranlator exists.
     * Throw an exception if translator for namespace doesn't exist or
     * if a translation error occurs.
     *
     * @param jaxbEl Element to translate.
     * @return Translated registry object list.
     * @throws be.kzen.ergorr.exceptions.TranslationException
     */
    public RegistryObjectListType translate(JAXBElement jaxbEl) throws TranslationException {
        String nsWithoutHttpPrefix = CommonFunctions.removeHttpPrefix(jaxbEl.getName().getNamespaceURI());

        String className = transProps.getProperty(nsWithoutHttpPrefix);

        if (className != null) {
            try {
                Class clazz = Class.forName(className);
                Constructor constructor = clazz.getConstructor(jaxbEl.getValue().getClass());
                Translator translator = (Translator) constructor.newInstance(jaxbEl.getValue());
                return translator.translate();
            } catch (Exception ex) {
                String err = "Could not invoke translator for namespace: " + jaxbEl.getName().getNamespaceURI();
                logger.log(Level.SEVERE, err, ex);
                throw new TranslationException(err, ex);
            }
        } else {
            throw new TranslationException("Could not find translator for namespace: " + jaxbEl.getName().getNamespaceURI());
        }
    }
}
