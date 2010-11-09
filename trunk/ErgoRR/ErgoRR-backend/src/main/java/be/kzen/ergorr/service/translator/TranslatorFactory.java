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
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;

/**
 * Translator factory for XML elements.
 * Looks up namespace to translator implementation binding from translator.properties file.
 * 
 * @author Yaman Ustuntas
 */
public class TranslatorFactory {

    private static Logger logger = Logger.getLogger(TranslatorFactory.class.getName());
    private static Properties transProps;

    static {
        transProps = new Properties();
        try {
            transProps.load(TranslatorFactory.class.getResourceAsStream("translator.properties"));
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Could not load translator properties", ex);
        }
    }

    /**
     * Get a translator for <code>jaxbEl</code>.
     * Looks at the namespace of <code>jaxbEl</code> to find the
     * appropriate translator.
     *
     * @param jaxbEl XML element to translate.
     * @return Translator for element.
     * @throws be.kzen.ergorr.exceptions.TranslationException
     */
    public static Translator getInstance(JAXBElement jaxbEl) throws TranslationException {
        String nsWithoutHttpPrefix = CommonFunctions.removeHttpPrefix(jaxbEl.getName().getNamespaceURI());

        String className = transProps.getProperty(nsWithoutHttpPrefix);

        if (className != null) {

            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Found translator class: " + className + " for ns: " + jaxbEl.getName().getNamespaceURI());
            }

            try {
                Class clazz = Class.forName(className);
                Translator translator = (Translator) clazz.newInstance();
                translator.setObject(jaxbEl.getValue());
                return translator;
            } catch (Exception ex) {
                String err = "Could not invoke translator for namespace: " + jaxbEl.getName().getNamespaceURI();
                logger.log(Level.WARNING, err, ex);
                throw new TranslationException(err, ex);
            }
        } else {
            String err = "Could not find translator for namespace: " + jaxbEl.getName().getNamespaceURI();
            logger.log(Level.WARNING, err);
            throw new TranslationException(err);
        }
    }

    public static Translator getInstance(Object object, String objType) throws TranslationException {
        String nsWithoutHttpPrefix = CommonFunctions.removeHttpPrefix(objType);
        nsWithoutHttpPrefix = nsWithoutHttpPrefix.replaceAll(":", "_");
        String className = transProps.getProperty(nsWithoutHttpPrefix);

        if (className != null) {

            if (logger.isLoggable(Level.FINE)) {
                logger.info("Found translator class: " + className + " for ns: " + objType);
            }

            try {
                Class clazz = Class.forName(className);
                Translator translator = (Translator) clazz.newInstance();
                translator.setObject(object);
                return translator;
            } catch (Exception ex) {
                String err = "Could not invoke translator for namespace: " + objType;
                logger.log(Level.SEVERE, err, ex);
                throw new TranslationException(err, ex);
            }
        } else {
            String err = "Could not find translator for namespace: " + objType;
            logger.log(Level.SEVERE, err);
            throw new TranslationException(err);
        }
    }
}
