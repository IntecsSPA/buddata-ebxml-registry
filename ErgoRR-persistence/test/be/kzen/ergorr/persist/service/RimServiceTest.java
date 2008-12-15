
package be.kzen.ergorr.persist.service;

import be.kzen.ergorr.persist.service.SqlPersistence;
import be.kzen.ergorr.commons.IDGenerator;
import be.kzen.ergorr.model.rim.ExtrinsicObjectType;
import be.kzen.ergorr.model.rim.IdentifiableType;
import be.kzen.ergorr.model.rim.InternationalStringType;
import be.kzen.ergorr.model.rim.LocalizedStringType;
import be.kzen.ergorr.model.rim.SlotType;
import be.kzen.ergorr.model.rim.ValueListType;
import be.kzen.ergorr.model.util.OFactory;
import be.kzen.ergorr.model.wrs.WrsValueListType;
import be.kzen.ergorr.persist.GenericTest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import org.junit.Test;

/**
 *
 * @author Yaman Ustuntas
 */
public class RimServiceTest extends GenericTest {

    public RimServiceTest() {
    }
    
//    @Test
//    public void testQuery() throws Exception {
//        System.out.println("query");
//        String query = "";
//        Class<T> clazz = null;
//        RimService instance = new RimService();
//        List<IdentifiableType> expResult = null;
//        List<IdentifiableType> result = instance.query(query, clazz);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }

    @Test
    public void testInsert() throws Exception {
        List<IdentifiableType> idents = new ArrayList<IdentifiableType>();
        
        ExtrinsicObjectType eo = new ExtrinsicObjectType();
        eo.setId(IDGenerator.generateUuid() + IDGenerator.generateInt());
        eo.setLid(eo.getId());
        eo.setMimeType("application/xml");
        eo.setStatus("inserted");
        SlotType slot = new SlotType();
        slot.setName("blablaname3");
        slot.setSlotType("blablatype");
        ValueListType valList = new ValueListType();
        slot.setValueList(OFactory.rim.createValueList(valList));
        valList.getValue().add("val1");
        valList.getValue().add("val2");
        eo.getSlot().add(slot);
        
        InternationalStringType name = new InternationalStringType();
        LocalizedStringType localName = new LocalizedStringType();
        localName.setLang("en");
        localName.setCharset("utf-8");
        localName.setValue("thename");
        name.getLocalizedString().add(localName);
        eo.setName(name);
        
        InternationalStringType desc = new InternationalStringType();
        LocalizedStringType localDesc = new LocalizedStringType();
        localDesc.setLang("en");
        localDesc.setCharset("utf-8");
        localDesc.setValue("thedesc");
        desc.getLocalizedString().add(localDesc);
        eo.setDescription(desc);
        
        idents.add(eo);
        
        SqlPersistence instance = new SqlPersistence();
        instance.insert(idents);
    }
    
    @Test
    public void testQuery() throws Exception {
        SqlPersistence service = new SqlPersistence();
        
        List<JAXBElement<? extends IdentifiableType>> idents = service.query("select * from extrinsicobject", new ArrayList<Object>(), ExtrinsicObjectType.class);
        
        for (JAXBElement<? extends IdentifiableType> identEl : idents) {
            ExtrinsicObjectType eo = (ExtrinsicObjectType) identEl.getValue();
            System.out.println(eo.getId());
            
            for (SlotType s : eo.getSlot()) {
                System.out.println("  Slot name: " + s.getName());
                System.out.println("  Slot type: " + s.getSlotType());
                
                if (s.isSetValueList()) {
                    if (s.getValueList().getValue() instanceof WrsValueListType) {
                        
                    } else {
                        ValueListType valList = s.getValueList().getValue();
                        for (String value : valList.getValue()) {
                            System.out.println("    Val: " + value);
                        }
                    }
                }
            }
            
            if (eo.isSetName()) {
                for (LocalizedStringType name : eo.getName().getLocalizedString()) {
                    System.out.println("  Name: " + name.getCharset() + " | " + name.getLang() + " | " + name.getValue());
                }
            }
            
            if (eo.isSetDescription()) {
                for (LocalizedStringType desc : eo.getDescription().getLocalizedString()) {
                    System.out.println("  Desc: " + desc.getCharset() + " | " + desc.getLang() + " | " + desc.getValue());
                }
            }
        }
    }

    @Test
    public void testGetConnection() throws Exception {
        SqlPersistence rs = new SqlPersistence();
        Connection c = rs.getConnection();
        c.close();
        c = rs.getConnection();
        c.close();
    }

}