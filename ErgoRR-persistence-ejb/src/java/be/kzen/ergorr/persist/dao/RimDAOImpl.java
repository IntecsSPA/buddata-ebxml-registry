/*
 * Project: Buddata ebXML RegRep
 * Class: RimServiceImpl.java
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
package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.commons.CommonProperties;
import be.kzen.ergorr.commons.SlotTypes;
import be.kzen.ergorr.model.rim.IdentifiableType;
import be.kzen.ergorr.persist.model.IdentifiableDM;
import be.kzen.ergorr.persist.model.RegistryObjectDM;
import com.vividsolutions.jts.geom.Geometry;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.bind.JAXBElement;
import org.apache.log4j.Logger;
import org.hibernate.type.CustomType;
import org.hibernate.type.Type;
import org.hibernatespatial.GeometryUserType;

/**
 * Bean to manage database transactions used by the application server.
 * 
 * @author Yaman Ustuntas
 */
@Stateless
public class RimDAOImpl implements RimDAO {

    private static Logger log = Logger.getLogger(RimDAOImpl.class);
    @PersistenceContext(unitName = "ErgoRRPU")
    protected EntityManager em;

    public void save(Object obj) {
        em.persist(obj);
    }

    public Object merge(Object obj) {
        return em.merge(obj);
    }

    public RegistryObjectDM getById(String id) {
        Query query = em.createQuery("select i from Identifiable i where i.id = '" + id + "'");
        RegistryObjectDM roDao = (RegistryObjectDM) query.getSingleResult();
        return roDao;
    }

    public List<JAXBElement<? extends IdentifiableType>> getByIds(List<String> ids) {
        List<JAXBElement<? extends IdentifiableType>> idents = new ArrayList<JAXBElement<? extends IdentifiableType>>();
        List<IdentifiableDM> identDMs;

        if (!ids.isEmpty()) {
            StringBuilder values = new StringBuilder("(");

            for (String id : ids) {
                values.append("'").append(id).append("',");
            }
            values.replace(values.length() - 1, values.length(), ")");

            Query query = em.createQuery("select i from Identifiable i where i.id in " + values.toString());
            identDMs = query.getResultList();

        } else {
            identDMs = new ArrayList<IdentifiableDM>();
        }


        for (IdentifiableDM identDM : identDMs) {
            identDM.setRimDAO(this);
            idents.add(identDM.createJAXBElement());
        }

        return idents;
    }

    public List<JAXBElement<? extends IdentifiableType>> query(String sql, Map<String, Object> params, int maxResults, int firstResult) {
        Query query = em.createQuery(sql);

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        int allowedMaxResults = CommonProperties.getInstance().getInt("db.maxResponse");

        if ((maxResults > 0) && (maxResults < allowedMaxResults)) {
            allowedMaxResults = maxResults;
        }

        if (log.isDebugEnabled()) {
            log.debug("max results: " + allowedMaxResults);
        }
        query.setMaxResults(allowedMaxResults);

        if (firstResult > 0) {
            query.setFirstResult(firstResult);
        }

        if (params != null) {
            org.hibernate.Query hQuery = ((org.hibernate.ejb.QueryImpl) query).getHibernateQuery();
            Iterator<String> it = params.keySet().iterator();

            while (it.hasNext()) {
                String key = it.next();
                Object param = params.get(key);

                if (param instanceof Date) {
                    hQuery.setTimestamp(key, (Date) param);
                } else if (param instanceof Geometry) {
                    Type geometryType = new CustomType(GeometryUserType.class, null);
                    hQuery.setParameter(key, param, geometryType);
                }

                if (log.isDebugEnabled()) {
                    log.debug("  setting param " + key + ": " + param.getClass().getSimpleName());
                }
            }
        }

        List<JAXBElement<? extends IdentifiableType>> idents = new ArrayList<JAXBElement<? extends IdentifiableType>>();
        List<IdentifiableDM> identDMs = query.getResultList();


        for (IdentifiableDM identDM : identDMs) {
            identDM.setRimDAO(this);
            idents.add(identDM.createJAXBElement());
        }

        return idents;
    }

    public boolean idExists(String id, Class clazz) {
        return em.find(clazz, id) != null;
    }

    public <T extends IdentifiableDM> T find(String id, Class<T> clazz) {
        return em.find(clazz, id);
    }

    public long getResultCount(String sql, Map<String, Object> params) {
        Query query = em.createQuery(sql);

        if (params != null) {
            org.hibernate.Query hQuery = ((org.hibernate.ejb.QueryImpl) query).getHibernateQuery();
            Iterator<String> it = params.keySet().iterator();

            while (it.hasNext()) {
                String key = it.next();
                Object param = params.get(key);

                if (param instanceof Date) {
                    hQuery.setTimestamp(key, (Date) param);
                } else if (param instanceof Geometry) {
                    Type geometryType = new CustomType(GeometryUserType.class, null);
                    hQuery.setParameter(key, param, geometryType);
                }
            }
        }

        return (Long) query.getSingleResult();
    }

    public Map<String, String> getSlotTypes() {
        Map<String, String> slotNameTypes = new ConcurrentHashMap<String, String>();
//        Query q = em.createQuery("select distinct(s.slotName), s.slotType from Slot s where s.slotType in ('dateTime', 'double', 'int', 'geometry')");
        Query q = em.createQuery("select distinct(s.slotName), s.slotType from Slot s");
        List list = q.getResultList();

        for (Object o : list) {
            Object[] oa = (Object[]) o;
            String slotName = (String) oa[0];
            String slotType = "string";

            if (oa[1] != null) {
                String val = ((String) oa[1]).toLowerCase();
                
                if (SlotTypes.isNoneStringType(val)) {
                    slotType = val;
                }
            }
            
            slotNameTypes.put(slotName, slotType);
        }

        return slotNameTypes;
    }

    public <T> List<T> query(String sql, Map<String, String> params, Class<T> clazz) {
        Query q = em.createQuery(sql);

        Iterator<String> it = params.keySet().iterator();

        while (it.hasNext()) {
            String key = it.next();
            q.setParameter(key, params.get(key));
        }

        return q.getResultList();
    }
}
