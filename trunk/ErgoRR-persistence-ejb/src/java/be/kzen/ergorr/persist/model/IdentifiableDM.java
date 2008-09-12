/*
 * Project: Buddata ebXML RegRep
 * Class: IdentifiableDM.java
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
package be.kzen.ergorr.persist.model;

import be.kzen.ergorr.commons.SrsTools;
import be.kzen.ergorr.commons.Transformer;
import be.kzen.ergorr.exceptions.MappingException;
import be.kzen.ergorr.exceptions.TransformException;
import be.kzen.ergorr.model.gml.AbstractGeometryType;
import be.kzen.ergorr.model.rim.ClassificationType;
import be.kzen.ergorr.persist.*;
import be.kzen.ergorr.model.rim.IdentifiableType;
import be.kzen.ergorr.model.rim.SlotType1;
import be.kzen.ergorr.model.rim.ValueListType;
import be.kzen.ergorr.model.util.JAXBUtil;
import be.kzen.ergorr.model.util.OFactory;
import be.kzen.ergorr.model.wrs.AnyValueType;
import be.kzen.ergorr.model.wrs.WrsValueListType;
import be.kzen.ergorr.persist.dao.RimDAO;
import com.vividsolutions.jts.geom.Geometry;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.xml.bind.JAXBElement;
import org.apache.log4j.Logger;

/**
 *
 * @author Yaman Ustuntas
 */
@Entity(name = "Identifiable")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class IdentifiableDM implements Serializable {

    private static Logger log = Logger.getLogger(IdentifiableDM.class);
    @Id
    protected String id;
    protected String home;
    @OneToMany(mappedBy = "parent", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    protected Set<SlotDM> slots;
    @Transient
    protected RimDAO rimDAO;

    public IdentifiableDM() {
        id = "";
        home = "";
    }

    public IdentifiableDM(IdentifiableType ident, RimDAO rimDAO) throws MappingException {
        this.rimDAO = rimDAO;
        loadJaxb(ident);
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<SlotDM> getSlots() {
        if (slots == null) {
            slots = new HashSet<SlotDM>();
        }
        return slots;
    }

    public void setSlots(Set<SlotDM> slots) {
        this.slots = slots;
    }

    public void setRimDAO(RimDAO rimDAO) {
        this.rimDAO = rimDAO;
    }

    public JAXBElement<? extends IdentifiableType> createJAXBElement() {
        return OFactory.rim.createIdentifiable(createJaxb(new IdentifiableType()));
    }

    public <T extends IdentifiableType> T createJaxb(T ident) {
        ident.setId(id);
        ident.setHome(home);

        if (!(this instanceof ClassificationDM) && !(this instanceof ExternalIdentifierDM) && !(this instanceof AssociationDM)) {
            Map<String, SlotType1> jaxbSlots = new HashMap<String, SlotType1>();

            for (SlotDM slotDao : slots) {
                SlotType1 slotJaxb = jaxbSlots.get(slotDao.getSlotName());

                if (slotJaxb == null) {
                    slotJaxb = new SlotType1();
                    slotJaxb.setName(slotDao.getSlotName());
                    slotJaxb.setSlotType(slotDao.getSlotType());

                    if (slotDao.getSpecType().equals("rim")) {
                        slotJaxb.setValueList(OFactory.rim.createValueList(new ValueListType()));
                    } else {
                        slotJaxb.setValueList(OFactory.wrs.createValueList(new WrsValueListType()));
                    }

                    jaxbSlots.put(slotDao.getSlotName(), slotJaxb);
                }

                if (slotJaxb.getValueList().getValue() instanceof WrsValueListType) {
                    WrsValueListType wrsValList = (WrsValueListType) slotJaxb.getValueList().getValue();
                    wrsValList.getAnyValue().add((AnyValueType) slotDao.createJaxb().getValue());
                } else {
                    slotJaxb.getValueList().getValue().getValue().add(slotDao.getStringValue());
                }
            }

            Iterator<String> keys = jaxbSlots.keySet().iterator();
            while (keys.hasNext()) {
                ident.getSlot().add(jaxbSlots.get(keys.next()));
            }
        }

        return ident;
    }

    public void loadJaxb(IdentifiableType ident) throws MappingException {
        id = ident.getId();
        home = ident.getHome();

        for (SlotType1 slotXml : ident.getSlot()) {

            JAXBElement<? extends ValueListType> valListEl = slotXml.getValueList();



            if (valListEl != null && valListEl.getValue() != null) {
                if (valListEl.getValue() instanceof WrsValueListType) {
                    WrsValueListType wrsValList = (WrsValueListType) valListEl.getValue();

                    for (AnyValueType anyVal : wrsValList.getAnyValue()) {
                        if (!anyVal.getContent().isEmpty()) {

                            SlotDM slotDM = new SlotDM();
                            Object slotWrsValue = null;
                            if (anyVal.getContent().get(0) instanceof JAXBElement) {
                                JAXBElement anyValEl = (JAXBElement) anyVal.getContent().get(0);

                                try {
                                    if (anyValEl.getValue() instanceof AbstractGeometryType) {
                                        AbstractGeometryType absGeometry = (AbstractGeometryType) anyValEl.getValue();
                                        Geometry geometry = Transformer.geometryFromAbstractGeometry(absGeometry);
                                        slotWrsValue = geometry;
                                        slotDM.setQueryGeometryValue(SrsTools.getInstance().transformGeometry(geometry));
                                    } else {
                                        throw new MappingException("Unknown slot value type: " + anyValEl.getValue().getClass());
                                    }

                                } catch (TransformException ex) {
                                    throw new MappingException("Could not map geometry", ex);
                                }
                            } else {
                                slotWrsValue = anyVal.getContent().get(0);
                            }

                            if (slotWrsValue != null) {
                                slotDM.setAnyValue(slotWrsValue);
                                slotDM.setSlotName(slotXml.getName());
                                slotDM.setSlotType(slotXml.getSlotType());
                                slotDM.setSpecType("wrs");
                                slotDM.setParent(this);
                                getSlots().add(slotDM);
                            }
                        }
                    }
                } else {
                    for (String val : valListEl.getValue().getValue()) {
                        SlotDM slot = new SlotDM();
                        slot.setSlotName(slotXml.getName());
                        slot.setSlotType(slotXml.getSlotType());
                        slot.setAnyValueByType(val, slotXml.getSlotType());
                        slot.setParent(this);
                        getSlots().add(slot);
                    }
                }
            } else {
                SlotDM slot = new SlotDM();
                slot.setSlotName(slotXml.getName());
                slot.setSlotType(slotXml.getSlotType());
                slot.setStringValue("");
                slot.setParent(this);
                getSlots().add(slot);
            }
        }
    }
}
