/*
 * PlaceDAOImpl.java
 * Create Date: Aug 24, 2019
 * Initial-Author: Janos Aron Kiss
 */
package com.abixe.hangouttips.model;

import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * This class represents a <a href="https://developers.google.com/places/web-service/details"> Google Places
 * entity</a>, stored in a database.
 * @author kjaron83
 */
@Entity
@Table(name = "place")
public class Place {

    /**
     * This comparator can be used to sort a collection of places by their rating in descending order.
     */
    public static final Comparator<Place> RATING_COMPARATOR = new RatingComparator();

    private long id;
    /**
     * A textual identifier that uniquely identifies a place. For more information about place IDs, see the
     * <a href="https://developers.google.com/places/web-service/place-id">place ID overview</a>.
     */
    private String placeId;
    private String name;
    private Double rating;
    private String address;
    private String phone;
    private String website;
    private String mapUrl;
    /**
     * A string used to identify the photo when you perform a photo request. For more information about photo
     * references, see the <a href="https://developers.google.com/places/web-service/photos">place photos
     * overview</a>.
     */
    private String photoReference;
    /**
     * A relative path on the local file system whither the {@link #photoReference} was downloaded. The absolute path
     * value will be ${place.photo.path} + {@link #photo}.
     */
    private String photo;
    /**
     * The date when the photo was downloaded and updated in the data source.
     */
    private Date photoUpdated;
    /**
     * The date when the details of the place were updated in the data source.
     */
    private Date updated;

    /**
     * A set of the locations which this place is near to.
     */
    private Set<Location> locations = new HashSet<>();

    @Id
    @Column(name = "TABLE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * @see #placeId
     */
    @Column(name = "place_id")
    @Nullable
    public String getPlaceId() {
        return placeId;
    }

    /**
     * @see #placeId
     */
    public void setPlaceId(@Nullable String placeId) {
        this.placeId = placeId;
    }

    @Column
    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Column
    @Nullable
    public Double getRating() {
        return rating;
    }

    public void setRating(@Nullable Double rating) {
        this.rating = rating;
    }

    @Column
    @Nullable
    public String getAddress() {
        return address;
    }

    public void setAddress(@Nullable String address) {
        this.address = address;
    }

    @Column
    @Nullable
    public String getPhone() {
        return phone;
    }

    public void setPhone(@Nullable String phone) {
        this.phone = phone;
    }

    @Column
    @Nullable
    public String getWebsite() {
        return website;
    }

    public void setWebsite(@Nullable String website) {
        this.website = website;
    }

    @Column(name = "map_url")
    @Nullable
    public String getMapUrl() {
        return mapUrl;
    }

    public void setMapUrl(@Nullable String mapUrl) {
        this.mapUrl = mapUrl;
    }

    /**
     * @see #photoReference
     */
    @Column(name = "photo_reference")
    @Nullable
    public String getPhotoReference() {
        return photoReference;
    }

    /**
     * @see #photoReference
     */
    public void setPhotoReference(@Nullable String photoReference) {
        this.photoReference = photoReference;
    }

    /**
     * @see #photo
     */
    @Column
    @Nullable
    public String getPhoto() {
        return photo;
    }

    /**
     * @see #photo
     */
    public void setPhoto(@Nullable String photo) {
        this.photo = photo;
    }

    /**
     * @see #photoUpdated
     */
    @Column(name = "photo_updated")
    @Nullable
    public Date getPhotoUpdated() {
        return photoUpdated;
    }

    /**
     * @see #photoUpdated
     */
    public void setPhotoUpdated(@Nullable Date photoUpdated) {
        this.photoUpdated = photoUpdated;
    }

    /**
     * @see #updated
     */
    @Column
    @Nullable
    public Date getUpdated() {
        return updated;
    }

    /**
     * @see #updated
     */
    public void setUpdated(@Nullable Date updated) {
        this.updated = updated;
    }

    /**
     * @see #locations
     */
    @ManyToMany(mappedBy = "places")
    @NonNull
    public Set<Location> getLocations() {
        return locations;
    }

    /**
     * @see #locations
     */
    public void setLocations(@NonNull Set<Location> locations) {
        this.locations = locations;
    }

    @NonNull
    @Override
    public String toString() {
        return "[" + getId() + "] " + getName() + " (" + getAddress() + ")";
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if ( !( obj instanceof Place ) )
            return false;
        if ( obj == this )
            return true;

        Place other = (Place) obj;
        return new EqualsBuilder()
                .append(id, other.id)
                .append(placeId, other.placeId)
                .append(name, other.name)
                .append(rating, other.rating)
                .append(address, other.address)
                .append(phone, other.phone)
                .append(website, other.website)
                .append(mapUrl, other.mapUrl)
                .append(photoReference, other.photoReference)
                .append(photo, other.photo)
                .append(updated, other.updated)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(23, 113)
                .append(id)
                .append(placeId)
                .append(name)
                .append(rating)
                .append(address)
                .append(phone)
                .append(website)
                .append(mapUrl)
                .append(photoReference)
                .append(photo)
                .append(updated)
                .toHashCode();
    }

    private static class RatingComparator implements Comparator<Place> {

        @Override
        public int compare(@NonNull Place o1, @NonNull Place o2) {
            int result = Double.compare(o1.getRating(), o2.getRating()) * -1;
            if ( result == 0 )
                result = o1.getName().compareTo(o2.getName());
            return result;
        }

    }

}
