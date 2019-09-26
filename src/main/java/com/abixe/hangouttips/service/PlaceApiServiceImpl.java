package com.abixe.hangouttips.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import com.abixe.hangouttips.dao.LocationDAO;
import com.abixe.hangouttips.dao.PlaceDAO;
import com.abixe.hangouttips.model.Coordinate;
import com.abixe.hangouttips.model.Location;
import com.abixe.hangouttips.model.Place;
import com.google.maps.GeoApiContext;
import com.google.maps.ImageResult;
import com.google.maps.NearbySearchRequest;
import com.google.maps.PhotoRequest;
import com.google.maps.PlaceDetailsRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlaceDetails;
import com.google.maps.model.PlaceType;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import com.google.maps.model.RankBy;

public class PlaceApiServiceImpl implements PlaceApiService {

	private static final Logger logger = LoggerFactory.getLogger(PlaceApiServiceImpl.class);

	private LocationDAO locationDAO;
	
	private PlaceDAO placeDAO;
	
	public void setLocationDAO(LocationDAO locationDAO) {
		this.locationDAO = locationDAO;
	}
	
	public void setPlaceDAO(PlaceDAO placeDAO) {
		this.placeDAO = placeDAO;
	}	
	
	@Value("${google.apiKey}")
	private String apiKey;
	
	@Value("${location.nearby.radius}")
	private int radius;	
	
	@Value("${location.update}")
	private int locationUpdate;	

	@Value("${place.update}")
	private int placeUpdate;	
	
	@Value("${place.rating.minimum}")
	private double ratingMinimum;	

	@Value("${place.photo.path}")
	private String placePhotoPath;	

	@Value("${place.photo.maxheigth}")
	private int placePhotoMaxheight;	

	@Value("${place.photo.maxwidht}")
	private int placePhotoMaxwidth;	

	private GeoApiContext context;
	
	private GeoApiContext getContext() {
		if ( context == null ) {
			logger.info("Creating context with api key: " + apiKey);
			context = new GeoApiContext.Builder().apiKey(apiKey).build();
			}		
		return context;
	}
	
	@Async
	@Override
	@Transactional
	public void update(@NonNull Location location) {
		LatLng latLng = convert(location);

		try {
			location.getPlaces().clear();
			findNearBy(location, latLng, PlaceType.BAR, PlaceType.CAFE, PlaceType.RESTAURANT, PlaceType.NIGHT_CLUB);
			locationDAO.update(location);
			for ( Place place : location.getPlaces() ) {
				if ( isExpired(place) ) {
					update(place);
		    		Thread.sleep(1000);					
				}
			}
		}
		catch (Throwable t) {
			logger.error("Cannot find places near by " + location, t);
		}		

		location = locationDAO.get(location.getId());
		location.setUpdated(new Date());
		locationDAO.update(location);			
	}	

    private void findNearBy(@NonNull Location location, @NonNull LatLng latLng, @NonNull PlaceType... placeTypes) throws ApiException, InterruptedException, IOException {
    	for ( int i = 0; i < placeTypes.length; i++ ) {
    		findNearBy(location, latLng, placeTypes[i]);
    		Thread.sleep(1000);
    	}
    }		

    private void findNearBy(@NonNull Location location, @NonNull LatLng latLng, @NonNull PlaceType placeType) throws ApiException, InterruptedException, IOException {		
		NearbySearchRequest request = new NearbySearchRequest(getContext());
		
		logger.info("Searching places near by location: " + location);
		PlacesSearchResponse response = request
				.location(latLng)
				.radius(radius)
				.type(placeType)
				.rankby(RankBy.PROMINENCE)
				.await();
		logger.info("Found " + response.results.length + " places.");
		
		Set<Place> places = location.getPlaces();
		Place place;
		for ( int i = 0; i < response.results.length; i++ ) {
			if ( !isTypeOf(response.results[i], PlaceType.LODGING, PlaceType.SPA) && response.results[i].rating >= ratingMinimum ) {
				place = placeDAO.get(response.results[i].placeId);
				if ( place == null || isExpired(place) ) 
					place = convert(response.results[i], place);

				place.getLocations().add(location);
		    	if ( place.getId() == 0 ) {
		    		placeDAO.add(place);
		    		place = placeDAO.get(place.getPlaceId());
		    	}
		    	else
		    		placeDAO.update(place);
				
				places.add(place);
			}
		}		
	}
    
    private Place update(@NonNull Place place) throws ApiException, InterruptedException, IOException {
    	PlaceDetailsRequest request = new PlaceDetailsRequest(getContext());
    	
    	logger.info("Downloading details of place: " + place.getPlaceId());
    	PlaceDetails response = request
    			.placeId(place.getPlaceId())
    			.await();
    	logger.info("Downloading was successfull.");
    	
    	place.setPhone(response.internationalPhoneNumber);
    	place.setWebsite(response.website != null ? response.website.toString() : null);
    	place.setMapUrl(response.url != null ? response.url.toString() : null);
    	place.setUpdated(new Date());
    	
    	if ( place.getId() == 0 ) {
    		placeDAO.add(place);
    		return placeDAO.get(place.getPlaceId());
    	}

    	placeDAO.update(place);
    	return place;
    }
	
	private static boolean isTypeOf(@NonNull PlacesSearchResult result, @NonNull PlaceType... types) {
		for ( int i = 0; i < result.types.length; i++ ) {
			for ( int j = 0; j < types.length; j++ ) {
				if ( result.types[i].equals(types[j].toUrlValue())  )
					return true;
			}
		}
		
		return false;
	}
		
	@NonNull
	private static LatLng convert(@NonNull Coordinate coordinate) {
		return new LatLng(coordinate.getLatitude(), coordinate.getLongitude());
	}
	
	@NonNull
	private Place convert(@NonNull PlacesSearchResult result, @Nullable Place place) throws ApiException, InterruptedException, IOException {
		logger.info(result.name);
		for ( int i = 0; i < result.types.length; i++ )
			logger.info("- Type " + i + ": " + result.types[i]);		
		
		if ( place == null )
			place = new Place();
		
		place.setPlaceId(result.placeId);
		place.setName(result.name);
		place.setRating(result.rating * 1D);
		if ( result.formattedAddress != null)
			place.setAddress(result.formattedAddress);
		else
			place.setAddress(result.vicinity);			
		
		if ( result.photos != null && result.photos.length > 0 ) {
			String reference = result.photos[0].photoReference;
			if ( !reference.equals(place.getPhotoReference()) ) {
				String oldPhoto = place.getPhoto();
				if ( oldPhoto != null ) {
					logger.info("Removing old photo: " + oldPhoto);
					if ( new File(placePhotoPath + File.separator + oldPhoto).delete() ) {
						logger.info("Removing succeed.");
						place.setPhoto(null);
					}
					else
						logger.warn("Removing failed!");
				}
				
				place.setPhotoReference(reference);
				
				logger.info("Downloading photo reference: " + reference);
				PhotoRequest photoRequest = new PhotoRequest(getContext());
				photoRequest.photoReference(reference);
				photoRequest.maxHeight(placePhotoMaxheight);
				photoRequest.maxWidth(placePhotoMaxwidth);
				ImageResult image = photoRequest.await();
				logger.info("Download finished. Content-type: " + image.contentType);
				
				String fileName = String.format("%1$10s", Math.abs(reference.hashCode())).replace(' ', '0') + "-" + new Date().getTime() + ".jpg";
				String folderName = fileName.substring(0, 3) + File.separator + fileName.substring(3, 6) + File.separator + fileName.substring(6, 9);
				
				File finalFolder = new File(placePhotoPath + File.separator + folderName);
				if ( !finalFolder.exists() )
					Files.createDirectories(finalFolder.toPath());
				
				String finalFileName = folderName + File.separator + fileName;
				
				logger.info("Saving photo to: " + finalFileName);
				FileOutputStream out = new FileOutputStream(new File(placePhotoPath + File.separator + finalFileName));
				out.write(image.imageData);
				out.close();
				logger.info("Saving finished.");
				
				place.setPhoto(finalFileName);
			}
		}
		
		return place;
	}
	
	public boolean isExpired(@NonNull Place place) {
		Date updated = place.getUpdated();
		
		if ( updated == null )
			return true;
		
		long now = new Date().getTime();
		boolean expired = updated.getTime() + placeUpdate < now;

		if ( expired ) 
			logger.info("Place [" + place.getId() + "] is expired. " + updated.getTime() + " + " + placeUpdate + " < " + now);
		
		return expired;
	}	
	
	public boolean isExpired(@NonNull Location location) {
		Date updated = location.getUpdated();
		
		if ( updated == null )
			return true;
		
		long now = new Date().getTime();
		boolean expired = updated.getTime() + locationUpdate < now;

		if ( expired ) 
			logger.info("Location [" + location.getId() + "] is expired. " + updated.getTime() + " + " + locationUpdate + " < " + now);
		
		return expired;
	}	

}
