package com.abixe.hangouttips.service;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import com.abixe.hangouttips.dao.LocationDAO;
import com.abixe.hangouttips.model.Coordinate;
import com.abixe.hangouttips.model.Location;

public class LocationServiceImpl implements LocationService {
	
	private LocationDAO locationDAO;
	
	public void setLocationDAO(LocationDAO locationDAO) {
		this.locationDAO = locationDAO;
	}
	
	@Nullable
	@Override
	@Transactional	
	public Location get(long id) {
		return locationDAO.get(id);
	}

	@NonNull
	@Override
	@Transactional	
	public Location get(@NonNull Coordinate coordinate) {
		Location location = locationDAO.get(coordinate);
		
		if ( location == null ) {
			location = new Location();
			location.setLatitude(coordinate.getLatitude());
			location.setLongitude(coordinate.getLongitude());
			add(location);
			return get(coordinate);
		}
		
		return location;		
	}

	@Override
	public void add(@NonNull Location location) {
		locationDAO.add(location);
	}

	@Override
	public void update(@NonNull Location location) {
		locationDAO.update(location);
	}

	@Override
	public void remove(@NonNull Location location) {
		locationDAO.remove(location);
	}

}
