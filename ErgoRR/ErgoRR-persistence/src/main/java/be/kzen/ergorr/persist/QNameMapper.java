package be.kzen.ergorr.persist;

import be.kzen.ergorr.persist.dao.AdhocQueryTypeDAO;
import be.kzen.ergorr.persist.dao.AssociationTypeDAO;
import be.kzen.ergorr.persist.dao.ClassificationNodeTypeDAO;
import be.kzen.ergorr.persist.dao.ClassificationSchemeTypeDAO;
import be.kzen.ergorr.persist.dao.ClassificationTypeDAO;
import be.kzen.ergorr.persist.dao.ExternalIdentifierTypeDAO;
import be.kzen.ergorr.persist.dao.ExtrinsicObjectTypeDAO;
import be.kzen.ergorr.persist.dao.IdentifiableTypeDAO;
import be.kzen.ergorr.persist.dao.RegistryObjectTypeDAO;
import be.kzen.ergorr.persist.dao.RegistryPackageTypeDAO;
import be.kzen.ergorr.persist.dao.ServiceBindingTypeDAO;
import be.kzen.ergorr.persist.dao.ServiceTypeDAO;
import be.kzen.ergorr.persist.dao.SpecificationLinkTypeDAO;
import be.kzen.ergorr.persist.dao.WrsExtrinsicObjectTypeDAO;
import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;

/**
 *
 * @author yamanustuntas
 */
public class QNameMapper {
    private final static Map<String, Class> map;

    static {
        map = new HashMap<String, Class>();
        map.put("urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0_AdhocQuery", AdhocQueryTypeDAO.class);
        map.put("urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0_Association", AssociationTypeDAO.class);
        map.put("urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0_ClassificationNode", ClassificationNodeTypeDAO.class);
        map.put("urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0_ClassificationScheme", ClassificationSchemeTypeDAO.class);
        map.put("urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0_Classification", ClassificationTypeDAO.class);
        map.put("urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0_ExternalIdentifier", ExternalIdentifierTypeDAO.class);
        map.put("urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0_ExtrinsicObject", ExtrinsicObjectTypeDAO.class);
        map.put("urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0_Identifiable", IdentifiableTypeDAO.class);
        map.put("urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0_RegistryObject", RegistryObjectTypeDAO.class);
        map.put("urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0_RegistryPackage", RegistryPackageTypeDAO.class);
        map.put("urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0_ServiceBinding", ServiceBindingTypeDAO.class);
        map.put("urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0_Service", ServiceTypeDAO.class);
        map.put("urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0_SpecificationLink", SpecificationLinkTypeDAO.class);
        map.put("http://www.opengis.net/cat/wrs/1.0_ExtrinsicObject", WrsExtrinsicObjectTypeDAO.class);
    }

    public static Class getDaoClass(QName qName) {
        return map.get(qName.getNamespaceURI() + "_" + qName.getLocalPart());
    }
}
