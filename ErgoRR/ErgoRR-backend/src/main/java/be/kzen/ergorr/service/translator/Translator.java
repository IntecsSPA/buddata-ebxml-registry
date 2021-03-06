/*
 * Project: Buddata ebXML RegRep
 * Class: Translator.java
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

import be.kzen.ergorr.exceptions.ServiceException;
import be.kzen.ergorr.model.rim.RegistryObjectListType;
import java.io.IOException;
import javax.xml.bind.JAXBException;

/**
 * Any RIM translator must implement this interface.
 *
 * @author Yaman Ustuntas
 */
public interface Translator<T> {
    /**
     * Set the object to be translated.
     *
     * @param obj Object to translate.
     */
    //public void setObject(T obj);
    public void setObject(Object obj) throws JAXBException,ServiceException,IOException;
    /**
     * Perform the translation.
     *
     * @return Translted registry object list.
     */
    public RegistryObjectListType translate() throws TranslationException;
}
