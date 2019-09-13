package com.abixe.hangouttips.dao;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.abixe.hangouttips.model.Place;

public class PlaceDAOImpl implements PlaceDAO {

	private static final Logger logger = LoggerFactory.getLogger(PlaceDAOImpl.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(@NonNull SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}		
	
	@Nullable
	@Override
	public Place get(long id) {
		Session session = sessionFactory.getCurrentSession();		
		Place place = session.get(Place.class, id);
		if ( logger.isInfoEnabled() ) {
			if ( place == null )
				logger.info("Place was not found by id: " + id);
			else
				logger.info("Place loaded successfully: " + place);		
		}
		return place;
	}

	@Nullable
	@Override
	public Place get(@NonNull String placeId) {
		Session session = sessionFactory.getCurrentSession();
	
		try {
			Long id = (Long) session
					.createQuery("SELECT p.id FROM Place p WHERE p.placeId = ?1")
					.setParameter(1, placeId)
					.setFirstResult(0)
					.setMaxResults(1)
					.getSingleResult();
			return get(id);			
		}
		catch (NoResultException e) {
			logger.info("Place was not found by placeId:  " + placeId);		
		}
		
		return null;
	}	
	
	@Override
	public void add(@NonNull Place place) {
		Session session = sessionFactory.getCurrentSession();
		session.persist(place);
		logger.info("Place saved successfully. Details: " + place);
	}

	@Override
	public void update(@NonNull Place place) {
		Session session = sessionFactory.getCurrentSession();
		session.update(place);
		logger.info("Place updated successfully. Details: " + place);
	}

	@Override
	public void remove(@NonNull Place place) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(place);
		logger.info("Place deleted successfully. Details: " + place);
	}

}
