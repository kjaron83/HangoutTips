/*
 * PlaceApiServiceImpl.java
 * Create Date: Aug 24, 2019
 * Initial-Author: Janos Aron Kiss
 */
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
import com.abixe.hangouttips.model.Location;
import com.abixe.hangouttips.model.Place;
import com.google.maps.GeoApiContext;
import com.google.maps.ImageResult;
import com.google.maps.NearbySearchRequest;
import com.google.maps.PhotoRequest;
import com.google.maps.PlaceDetailsRequest;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlaceDetails;
import com.google.maps.model.PlaceType;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import com.google.maps.model.RankBy;

/**
 * This class implements the {@link PlaceApiService} interface and uses {@link LocationDAO}, and {@link PlaceDAO}, to
 * update {@link Location}, and {@link Place} instances in the database. This class also uses
 * <a href="https://developers.google.com/places/web-service/intro"> Google Places API</a> during the update.
 * @author kjaron83
 */
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

    /**
     * To use the Google Places API you must have an API key. For more information about the API key, see the
     * <a href="https://developers.google.com/places/web-service/get-api-key">API key overview</a>.
     */
    @Value("${google.apiKey}")
    private String apiKey;

    /**
     * Updating period of a location in milliseconds.
     */
    @Value("${location.update}")
    private int locationUpdate;

    /**
     * Radius of the searching nearby places (in meters).
     */
    @Value("${location.nearby.radius}")
    private int radius;

    /**
     * If too few places were found nearby the location, the application increases the search radius repeatedly.
     */
    @Value("${location.nearby.increment}")
    private int increment;

    /**
     * Minimum number of places per location.
     */
    @Value("${location.places.minimum}")
    private int minPlaces;

    /**
     * Waiting time after a location was fetched in milliseconds.
     */
    @Value("${location.fetch.sleep}")
    private int locationSleep;

    /**
     * Updating period of a place in milliseconds.
     */
    @Value("${place.update}")
    private int placeUpdate;

    /**
     * Waiting time after a place was fetched in milliseconds.
     */
    @Value("${place.fetch.sleep}")
    private int placeSleep;

    /**
     * Minimum rating of places.
     */
    @Value("${place.rating.minimum}")
    private double ratingMinimum;

    /**
     * The folder name where the photos of the places will be saved.
     */
    @Value("${place.photo.path}")
    private String placePhotoPath;

    @Value("${place.photo.maxheigth}")
    private int placePhotoMaxheight;

    @Value("${place.photo.maxwidht}")
    private int placePhotoMaxwidth;

    /**
     * Updating period of a place photo in milliseconds.
     */
    @Value("${place.photo.update}")
    private long placePhotoUpdate;

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
        try {
            location = repeatedlyUpdate(location, radius);
        }
        catch ( Throwable t ) {
            logger.error("An exception occurred while searching places nearby " + location, t);
        }

        location = locationDAO.get(location.getId());
        location.setUpdated(new Date());
        locationDAO.update(location);
    }

    private Location repeatedlyUpdate(@NonNull Location location, int currentRadius) {
        location = new LocationUpdater(location, currentRadius, PlaceType.BAR, PlaceType.CAFE, PlaceType.RESTAURANT, PlaceType.NIGHT_CLUB).update();
        locationDAO.update(location);

        Set<Place> places = location.getPlaces();
        for ( Place place : places ) {
            if ( isExpired(place) ) {
                place = new PlaceUpdater(place).update();
                placeDAO.update(place);
                sleep(placeSleep);
            }
        }

        if ( places.size() < minPlaces ) {
            currentRadius += increment;
            logger.info("Too few places were found nearby the location. Trying again with radius: " + currentRadius);
            return repeatedlyUpdate(location, currentRadius);
        }

        return location;
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        }
        catch ( InterruptedException e ) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    public boolean isExpired(@NonNull Place place) {
        Date updated = place.getUpdated();

        if ( updated == null )
            return true;

        long now = new Date().getTime();
        boolean expired = updated.getTime() + placeUpdate < now;

        if ( expired )
            logger.info("Place [" + place.getId() + "] is expired. " + updated.getTime() + " + " + placeUpdate + " < "
                    + now);

        return expired;
    }

    public boolean isExpired(@NonNull Location location) {
        Date updated = location.getUpdated();

        if ( updated == null )
            return true;

        long now = new Date().getTime();
        boolean expired = updated.getTime() + locationUpdate < now;

        if ( expired )
            logger.info("Location [" + location.getId() + "] is expired. " + updated.getTime() + " + " + locationUpdate
                    + " < " + now);

        return expired;
    }

    /**
     * This class can be used to update the set of the nearby {@link Place}s of a {@link Location}. It also updates
     * the public details of the {@link Place} which are described in the {@link PlacesSearchResult} class.
     * @author kjaron83
     */
    private class LocationUpdater {

        private final Location location;
        private final Set<Place> places;
        private final LatLng latLng;

        private final PlaceType[] placeTypes;

        private int currentRadius;

        public LocationUpdater(@NonNull Location location, int radius, @NonNull PlaceType... placeTypes) {
            this.location = location;
            this.places = location.getPlaces();
            this.latLng = new LatLng(location.getLatitude(), location.getLongitude());
            this.placeTypes = placeTypes;
            this.currentRadius = radius;
        }

        public Location update() {
            places.clear();
            for ( int i = 0; i < placeTypes.length; i++ )
                findByPlaceType(placeTypes[i]);
            return location;
        }

        private void findByPlaceType(@NonNull PlaceType placeType) {
            PlacesSearchResult[] retults = findNearBy(placeType);
            processResults(retults);
            sleep(locationSleep);
        }

        @NonNull
        private PlacesSearchResult[] findNearBy(@NonNull PlaceType placeType) {
            NearbySearchRequest request = new NearbySearchRequest(getContext());
            logger.info("Searching places near by location: " + location);
            PlacesSearchResponse response;
            try {
                response = request
                        .location(latLng)
                        .radius(currentRadius)
                        .type(placeType)
                        .rankby(RankBy.PROMINENCE)
                        .await();
            }
            catch ( Exception e ) {
                throw new RuntimeException(e);
            }
            logger.info("Found " + response.results.length + " places.");
            return response.results;
        }

        private void processResults(@NonNull PlacesSearchResult[] results) {
            for ( int i = 0; i < results.length; i++ )
                processResult(results[i]);
        }

        private void processResult(@NonNull PlacesSearchResult result) {
            if ( isOfUnexpectedType(result) || result.rating < ratingMinimum )
                return;

            Place place = placeDAO.get(result.placeId);
            if ( place == null || isExpired(place) )
                place = convert(result, place);

            if ( place.getId() == 0 ) {
                placeDAO.add(place);
                place = placeDAO.get(place.getPlaceId());
            }
            else
                placeDAO.update(place);

            places.add(place);
        }

        @NonNull
        private Place convert(@NonNull PlacesSearchResult result, @Nullable Place place) {
            logger.info(result.name);
            for ( int i = 0; i < result.types.length; i++ )
                logger.info("- Type " + i + ": " + result.types[i]);

            if ( place == null )
                place = new Place();

            place.setPlaceId(result.placeId);
            place.setName(result.name);
            place.setRating(result.rating * 1D);
            if ( result.formattedAddress != null )
                place.setAddress(result.formattedAddress);
            else
                place.setAddress(result.vicinity);

            if ( result.photos != null && result.photos.length > 0 )
                updatePlacePhoto(place, result.photos[0].photoReference);

            return place;
        }

        private void updatePlacePhoto(@NonNull Place place, @NonNull String reference) {
            if ( reference.equals(place.getPhotoReference()) )
                return;

            if ( !isPhotoExpired(place) )
                return;

            String oldPhoto = place.getPhoto();
            if ( oldPhoto != null ) {
                logger.info("Removing old photo: " + oldPhoto + " " + place.getPhotoReference());
                if ( new File(placePhotoPath + File.separator + oldPhoto).delete() ) {
                    logger.info("Removing succeed.");
                    place.setPhoto(null);
                }
                else
                    logger.warn("Removing failed!");
            }

            ImageResult image = downloadPhoto(reference);
            place.setPhotoReference(reference);
            place.setPhoto(savePhoto(reference, image));
            place.setPhotoUpdated(new Date());
        }

        private boolean isPhotoExpired(@NonNull Place place) {
            String photo = place.getPhoto();
            if ( photo == null )
                return true;

            Date updated = place.getPhotoUpdated();
            if ( updated == null )
                return true;

            long now = new Date().getTime();
            boolean expired = updated.getTime() + placePhotoUpdate < now;

            if ( expired )
                logger.info("Place photo [" + place.getId() + " " + photo + "] is expired. " + updated.getTime()
                        + " + " + placePhotoUpdate + " < " + now);

            return expired;
        }

        private ImageResult downloadPhoto(@NonNull String reference) {
            logger.info("Downloading photo reference: " + reference);
            PhotoRequest photoRequest = new PhotoRequest(getContext());
            photoRequest.photoReference(reference);
            photoRequest.maxHeight(placePhotoMaxheight);
            photoRequest.maxWidth(placePhotoMaxwidth);
            ImageResult image;
            try {
                image = photoRequest.await();
            }
            catch ( Exception e ) {
                throw new RuntimeException(e);
            }
            logger.info("Download finished. Content-type: " + image.contentType);
            return image;
        }

        private String savePhoto(@NonNull String reference, @NonNull ImageResult image) {
            String fileName = String.format("%1$10s", Math.abs(reference.hashCode())).replace(' ', '0') + "-"
                    + new Date().getTime() + ".jpg";
            String folderName = fileName.substring(0, 3) + File.separator + fileName.substring(3, 6)
                    + File.separator + fileName.substring(6, 9);

            File finalFolder = new File(placePhotoPath + File.separator + folderName);
            String finalFileName = folderName + File.separator + fileName;

            try {
                if ( !finalFolder.exists() )
                    Files.createDirectories(finalFolder.toPath());

                logger.info("Saving photo to: " + finalFileName);
                FileOutputStream out = new FileOutputStream(new File(placePhotoPath + File.separator + finalFileName));
                out.write(image.imageData);
                out.close();
                logger.info("Saving finished.");
            }
            catch ( IOException e ) {
                throw new RuntimeException(e);
            }

            return finalFileName;
        }

        private boolean isOfUnexpectedType(@NonNull PlacesSearchResult result) {
            return isTypeOf(result, PlaceType.LODGING, PlaceType.SPA);
        }

        private boolean isTypeOf(@NonNull PlacesSearchResult result, @NonNull PlaceType... types) {
            for ( int i = 0; i < result.types.length; i++ ) {
                for ( int j = 0; j < types.length; j++ ) {
                    if ( result.types[i].equals(types[j].toUrlValue()) )
                        return true;
                }
            }

            return false;
        }

    }

    /**
     * This class can be used to update contact data of the {@link Place} which are described in the
     * {@link PlaceDetails} class.
     * @author kjaron83
     */
    private class PlaceUpdater {

        private final Place place;

        public PlaceUpdater(@NonNull Place place) {
            this.place = place;
        }

        public Place update() {
            PlaceDetails details = getDetails();

            place.setPhone(details.internationalPhoneNumber);
            place.setWebsite(details.website != null ? details.website.toString() : null);
            place.setMapUrl(details.url != null ? details.url.toString() : null);
            place.setUpdated(new Date());

            if ( place.getId() == 0 ) {
                placeDAO.add(place);
                return placeDAO.get(place.getPlaceId());
            }

            return place;
        }

        private PlaceDetails getDetails() {
            PlaceDetailsRequest request = new PlaceDetailsRequest(getContext());

            logger.info("Downloading details of place: " + place.getPlaceId());
            PlaceDetails response;
            try {
                response = request
                        .placeId(place.getPlaceId())
                        .fields(
                                PlaceDetailsRequest.FieldMask.INTERNATIONAL_PHONE_NUMBER,
                                PlaceDetailsRequest.FieldMask.WEBSITE,
                                PlaceDetailsRequest.FieldMask.URL
                                )
                        .await();
            }
            catch ( Exception e ) {
                throw new RuntimeException(e);
            }
            logger.info("Downloading was successfull.");

            return response;
        }

    }

}
