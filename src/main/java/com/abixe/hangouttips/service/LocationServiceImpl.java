package com.abixe.hangouttips.service;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import com.abixe.hangouttips.dao.LocationDAO;
import com.abixe.hangouttips.model.IpLocation;
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
    public Location get(@NonNull IpLocation ipLocation) {
        Location location = locationDAO.get(ipLocation);
        Location pathMatchingLocation;

        if ( location == null ) {
            location = new Location();
            location.setLatitude(ipLocation.getLatitude());
            location.setLongitude(ipLocation.getLongitude());
            location.setCountryName(ipLocation.getCountryName());
            location.setCityName(ipLocation.getCityName());

            String country = safeName(ipLocation.getCountryName());
            String city = safeName(ipLocation.getCityName());
            StringBuilder pathBuilder = new StringBuilder(country.length() + city.length() + 3);
            String path;
            for ( int i = 1;; i++ ) {
                pathBuilder.append(country).append('/').append(city);
                if ( i > 1 )
                    pathBuilder.append('-').append(i);
                path = pathBuilder.toString();

                pathMatchingLocation = get(path);
                if ( pathMatchingLocation == null ) {
                    location.setPath(path);
                    break;
                }

                pathBuilder.setLength(0);
            }

            add(location);
            return get(ipLocation);
        }

        return location;
    }

    private static String safeName(@NonNull String s) {
        s = s.trim();
        s = s.toLowerCase();
        s = s.replaceAll("[^\\w]+", "-");
        return s;
    }

    @NonNull
    @Override
    @Transactional
    public Location get(@NonNull String path) {
        return locationDAO.get(path);
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
