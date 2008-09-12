/*
 * Project: Buddata ebXML RegRep
 * Class: RimServiceLocalImpl.java
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

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.log4j.Logger;

/**
 * Bean to manage database transactions used for transactions not managed by a container (application server).
 * 
 * @author Yaman Ustuntas
 */
public class RimDAOLocalImpl extends RimDAOImpl implements RimDAO {

    private static Logger log = Logger.getLogger(RimDAOLocalImpl.class);
    EntityManagerFactory emf;

    public RimDAOLocalImpl() {
        emf = Persistence.createEntityManagerFactory("ErgoRRPUTest", new java.util.HashMap());
        em = emf.createEntityManager();
    }

    public void closeEntityManagerFactory() {
        emf.close();
    }

    @Override
    public void save(Object obj) {
        em.getTransaction().begin();
        super.save(obj);
        em.getTransaction().commit();
    }
    
    @Override
    public Object merge(Object obj) {
        em.getTransaction().begin();
        Object o = super.merge(obj);
        em.getTransaction().commit();
        return o;
    }
}
