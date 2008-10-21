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

import be.kzen.ergorr.exceptions.TranslationException;
import be.kzen.ergorr.service.translator.eo.SARTranslator;
import be.kzen.ergorr.service.translator.eo.OPTTranslator;
import be.kzen.ergorr.service.translator.eo.ATMTranslator;
import be.kzen.ergorr.service.translator.eo.HMATranslator;
import be.kzen.ergorr.model.rim.RegistryObjectListType;
import java.util.logging.Logger;

/**
 *
 * @author Yaman Ustuntas
 */
public class TranslationFactory {

    private static Logger logger = Logger.getLogger(TranslationFactory.class.getName());

    public RegistryObjectListType translate(Object obj) throws TranslationException {
        Translator translator = null;

        if (obj instanceof be.kzen.ergorr.model.eo.atm.EarthObservationType) {
            translator = new ATMTranslator((be.kzen.ergorr.model.eo.atm.EarthObservationType) obj);
        } else if (obj instanceof be.kzen.ergorr.model.eo.opt.EarthObservationType) {
            translator = new OPTTranslator((be.kzen.ergorr.model.eo.opt.EarthObservationType) obj);
        } else if (obj instanceof be.kzen.ergorr.model.eo.sar.EarthObservationType) {
            translator = new SARTranslator((be.kzen.ergorr.model.eo.sar.EarthObservationType) obj);
        } else if (obj instanceof be.kzen.ergorr.model.eo.hma.EarthObservationType) {
            translator = new HMATranslator((be.kzen.ergorr.model.eo.hma.EarthObservationType) obj);
        } else {
            logger.warning("Objects translation is not supported");
            throw new TranslationException("Translation of this object is not supported");
        }

        return translator.translate();
    }
}
