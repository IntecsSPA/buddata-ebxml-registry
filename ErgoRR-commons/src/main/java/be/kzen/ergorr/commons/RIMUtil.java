/*
 * Project: Buddata ebXML RegRep
 * Class: RIMUtil.java
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

import be.kzen.ergorr.model.rim.AssociationType;
import be.kzen.ergorr.model.rim.ExternalIdentifierType;
import be.kzen.ergorr.model.rim.InternationalStringType;
import be.kzen.ergorr.model.rim.LocalizedStringType;
import be.kzen.ergorr.model.rim.SlotType;
import be.kzen.ergorr.model.rim.ValueListType;
import be.kzen.ergorr.model.util.OFactory;
import be.kzen.ergorr.model.wrs.AnyValueType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;

/**
 * Utility class to help building RIM objects.
 *
 * @author Yaman Ustuntas
 */
public class RIMUtil {

    /**
     * Creates an <code>InternationalStringType</code> for one language.
     * 
     * @param lang Language.
     * @param encoding Encoding of the language.
     * @param value Text content.
     * @return International string.
     */
    public static InternationalStringType createString(String lang, String encoding, String value) {
        InternationalStringType i = new InternationalStringType();
        LocalizedStringType ls = new LocalizedStringType();
        ls.setLang(lang);
        ls.setCharset(encoding);
        ls.setValue(value);
        i.getLocalizedString().add(ls);

        return i;
    }

    /**
     * Create an <code>InternationalStringType</code> for one language
     * with the application default language and encoding.
     * 
     * @param value Text content.
     * @return International string.
     */
    public static InternationalStringType createString(String value) {
        return createString(CommonProperties.getInstance().get("lang"), CommonProperties.getInstance().get("encoding"), value);
    }

    /**
     * Create a <code>SlotType1</code> with one value.
     * 
     * @param slotName Name of slot.
     * @param slotType Type of slot.
     * @param values Values of slot.
     * @return Slot with one value.
     */
    public static SlotType createSlot(String slotName, String slotType, String value) {
        List<String> values = new ArrayList<String>();
        values.add(value);
        return createSlot(slotName, slotType, values);
    }

    /**
     * Create a <code>SlotType1</code> with one value.
     * 
     * @param slotName Name of slot.
     * @param slotType Type of slot.
     * @param values Values of slot.
     * @return Slot with one value.
     */
    public static SlotType createSlot(String slotName, String slotType, List<String> values) {
        SlotType slot = new SlotType();
        slot.setName(slotName);
        slot.setSlotType(slotType);

        ValueListType valList = new ValueListType();

        for (String value : values) {
            valList.getValue().add(value);
        }
        slot.setValueList(OFactory.rim.createValueList(valList));
        return slot;
    }
    
    /**
     * Create a <code>SlotType1</code> with one value.
     * 
     * @param slotName Name of slot.
     * @param slotType Type of slot.
     * @param values Values of slot.
     * @return Slot with one value.
     */
    public static SlotType createDateSlots(String slotName, String slotType, List<String> values) {
        SlotType slot = new SlotType();
        slot.setName(slotName);
        slot.setSlotType(slotType);

        be.kzen.ergorr.model.wrs.WrsValueListType valList = new be.kzen.ergorr.model.wrs.WrsValueListType();

        for (String value : values) {
            AnyValueType anyVal = new AnyValueType();
            anyVal.getContent().add(value);
            valList.getAnyValue().add(anyVal);
        }
        slot.setValueList(OFactory.wrs.createValueList(valList));
        return slot;
    }

    /**
     * Creates a WRS slot.
     *
     * @param slotName Slot name.
     * @param slotType Slot type.
     * @param value Slot value.
     * @return RIM Slot.
     */
    public static SlotType createWrsSlot(String slotName, String slotType, String value) {
        return createWrsSlotObj(slotName, slotType, value);
    }

    /**
     * Creates a WRS slot with a geometry value.
     *
     * @param slotName Slot name.
     * @param slotType Slot type.
     * @param jaxbEl Geometry JAXB element.
     * @return RIM Slot.
     */
    public static SlotType createGeomWrsSlot(String slotName, String slotType, JAXBElement jaxbEl) {
        return createWrsSlotObj(slotName, slotType, jaxbEl);
    }
    
    private static SlotType createWrsSlotObj(String slotName, String slotType, Object o) {
        SlotType slot = new SlotType();
        slot.setName(slotName);
        slot.setSlotType(slotType);
        
        be.kzen.ergorr.model.wrs.WrsValueListType valList = new be.kzen.ergorr.model.wrs.WrsValueListType();
        AnyValueType anyVal = new AnyValueType();
        anyVal.getContent().add(o);
        valList.getAnyValue().add(anyVal);
        
        slot.setValueList(OFactory.wrs.createValueList(valList));
        return slot;
    }
    
    /**
     * Create a RIM ExternalIdentifier.
     *
     * @param id ExternalIdentifier ID.
     * @param regObjId RegistryObject ID of ExternalIdentifier.
     * @param value Identifier value.
     * @param scheme Identification scheme.
     * @return RIM ExternalIdentifier.
     */
    public static ExternalIdentifierType createExternalIdentifier(String id, String regObjId, String value, String scheme) {
        ExternalIdentifierType e = new ExternalIdentifierType();
        e.setId(id);
        e.setRegistryObject(regObjId);
        e.setValue(value);
        e.setIdentificationScheme(scheme);

        return e;
    }

    /**
     * Create a RIM Association.
     *
     * @param id Association ID.
     * @param type Association type.
     * @param source Source object ID.
     * @param target Target object ID.
     * @return RIM Association.
     */
    public static AssociationType createAssociation(String id, String type, String source, String target) {
        AssociationType asso = new AssociationType();
        asso.setId(id);
        asso.setAssociationType(type);
        asso.setSourceObject(source);
        asso.setTargetObject(target);
        return asso;
    }
}
