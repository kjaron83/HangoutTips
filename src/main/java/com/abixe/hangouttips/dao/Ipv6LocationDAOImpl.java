package com.abixe.hangouttips.dao;

import java.math.BigDecimal;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.abixe.hangouttips.model.Ipv6Location;

public class Ipv6LocationDAOImpl implements Ipv6LocationDAO {

    private static final Logger logger = LoggerFactory.getLogger(Ipv6LocationDAOImpl.class);

    private SessionFactory sessionFactory;

    public void setSessionFactory(@NonNull SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Nullable
    @Override
    public Ipv6Location get(long id) {
        Session session = sessionFactory.getCurrentSession();
        Ipv6Location location = session.get(Ipv6Location.class, id);
        if ( logger.isInfoEnabled() ) {
            if ( location == null )
                logger.info("Ipv6Location was not found by id: " + id);
            else
                logger.info("Ipv6Location loaded successfully: " + location);
        }
        return location;
    }

    @Nullable
    @Override
    public Ipv6Location getLocation(@NonNull String ip) {
        return getLocation(Ipv6Location.convert(ip));
    }

    @Nullable
    @Override
    public Ipv6Location getLocation(@NonNull BigDecimal ip) {
        Session session = sessionFactory.getCurrentSession();

        try {
            Long id = (Long) session
                    .createQuery("SELECT l.id FROM Ipv6Location l WHERE ?1 BETWEEN l.ipFrom AND l.ipTo")
                    .setParameter(1, ip)
                    .setFirstResult(0)
                    .setMaxResults(1)
                    .getSingleResult();
            return get(id);
        }
        catch ( NoResultException e ) {
            logger.info("Ipv6Location was not found by ip: " + ip);
        }

        return null;
    }

}
