package com.abixe.hangouttips.dao;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.abixe.hangouttips.model.Location;
import com.abixe.hangouttips.model.Coordinate;

public class LocationDAOImpl implements LocationDAO {

	private static final Logger logger = LoggerFactory.getLogger(LocationDAOImpl.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(@NonNull SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}		
	
	@Nullable
	@Override
	public Location get(long id) {
		Session session = sessionFactory.getCurrentSession();		
		Location location = (Location) session.get(Location.class, id);
		if ( logger.isInfoEnabled() ) {
			if ( location == null )
				logger.info("Location was not found by id: " + id);
			else
				logger.info("Location loaded successfully: " + location);		
		}
		return location;
	}

	@Nullable
	@Override
	public Location get(@NonNull Coordinate coordinate) {
		Session session = sessionFactory.getCurrentSession();

		try {
			Long id = (Long) session
					.createQuery("SELECT l.id FROM Location l WHERE l.latitude = ?1 AND l.longitude = ?2")
					.setParameter(1, coordinate.getLatitude())
					.setParameter(2, coordinate.getLongitude())
					.setFirstResult(0)
					.setMaxResults(1)
					.getSingleResult();
			return get(id);			
		}
		catch (NoResultException e) {
			logger.info(
					"Location was not found by coordinate: "
					+ coordinate.getLatitude() + ":" + coordinate.getLongitude()
			);		
		}
		
		return null;
	}

	@Nullable
	@Override
	public Location get(@NonNull String path) {
		Session session = sessionFactory.getCurrentSession();

		try {
			Long id = (Long) session
					.createQuery("SELECT l.id FROM Location l WHERE l.path = ?1")
					.setParameter(1, path)
					.setFirstResult(0)
					.setMaxResults(1)
					.getSingleResult();
			return get(id);			
		}
		catch (NoResultException e) {
			logger.info("Location was not found by path: " + path);		
		}
		
		return null;
	}

	@Override
	public void add(@NonNull Location location) {
		Session session = sessionFactory.getCurrentSession();
		session.persist(location);
		logger.info("Location saved successfully. Details: " + location);
	}

	@Override
	public void update(@NonNull Location location) {
		Session session = sessionFactory.getCurrentSession();
		session.update(location);
		logger.info("Location updated successfully. Details: " + location);
	}

	@Override
	public void remove(@NonNull Location location) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(location);
		logger.info("Location deleted successfully. Details: " + location);
	}

}
