
package be.kzen.ergorr.persist.service;

import be.kzen.ergorr.persist.dao.AssociationTypeDAO;
import be.kzen.ergorr.persist.dao.ClassificationNodeTypeDAO;
import be.kzen.ergorr.persist.dao.ClassificationSchemeTypeDAO;
import be.kzen.ergorr.persist.dao.ClassificationTypeDAO;
import be.kzen.ergorr.persist.dao.ExternalIdentifierTypeDAO;
import be.kzen.ergorr.persist.dao.ExtrinsicObjectTypeDAO;
import be.kzen.ergorr.persist.dao.IdentifiableTypeDAO;
import be.kzen.ergorr.persist.dao.RegistryPackageTypeDAO;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Yaman Ustuntas
 */
public class ServiceHelper {
    private static Map<Integer,Class<? extends IdentifiableTypeDAO>> queriables;
    
    static {
        queriables = new HashMap<Integer,Class<? extends IdentifiableTypeDAO>>();
        
        queriables.put(1, AssociationTypeDAO.class);
        queriables.put(2, ClassificationTypeDAO.class);
        queriables.put(3, ClassificationNodeTypeDAO.class);
        queriables.put(4, ClassificationSchemeTypeDAO.class);
        queriables.put(5, ExternalIdentifierTypeDAO.class);
//        queriables.put(6, ExternalLinkTypeDAO.class);
        queriables.put(7, ExtrinsicObjectTypeDAO.class);
        queriables.put(8, RegistryPackageTypeDAO.class);
    }
    
    public static Class<? extends IdentifiableTypeDAO> getClass(int id) {
        return queriables.get(id);
    }
}
