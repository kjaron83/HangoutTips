/*
 * Ipv4LocationDAOImpl.java
 * Create Date: Aug 11, 2019
 * Initial-Author: Janos Aron Kiss
 */
package com.abixe.hangouttips.dao;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.abixe.hangouttips.model.Ipv4Location;

/**
 * This class implements the {@link Ipv4LocationDAO} interface and uses {@link Session} to get {@link Ipv4Location}
 * instances from the database.
 * @author kjaron83
 */
public class Ipv4LocationDAOImpl implements Ipv4LocationDAO {

    private static final Logger logger = LoggerFactory.getLogger(Ipv4LocationDAOImpl.class);

    private SessionFactory sessionFactory;

    public void setSessionFactory(@NonNull SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Nullable
    @Override
    public Ipv4Location get(long id) {
        Session session = sessionFactory.getCurrentSession();
        Ipv4Location location = session.get(Ipv4Location.class, id);
        if ( logger.isInfoEnabled() ) {
            if ( location == null )
                logger.info("Ipv4Location was not found by id: " + id);
            else
                logger.info("Ipv4Location loaded successfully: " + location);
        }
        return location;
    }

    @Nullable
    @Override
    public Ipv4Location getLocation(@NonNull String ip) {
        return getLocation(Ipv4Location.convert(ip));
    }

    @Nullable
    @Override
    public Ipv4Location getLocation(@NonNull Long ip) {
        Session session = sessionFactory.getCurrentSession();

        try {
            Long id = (Long) session
                    .createQuery("SELECT l.id FROM Ipv4Location l WHERE ?1 BETWEEN l.ipFrom AND l.ipTo")
                    .setParameter(1, ip)
                    .setFirstResult(0)
                    .setMaxResults(1)
                    .getSingleResult();
            return get(id);
        }
        catch ( NoResultException e ) {
            logger.info("Ipv4Location was not found by ip: " + ip);
        }

        return null;
    }

}
