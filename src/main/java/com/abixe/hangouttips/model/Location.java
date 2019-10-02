package com.abixe.hangouttips.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Entity
@Table(name = "location")
public class Location implements Coordinate {

    private long id;
    private Double latitude;
    private Double longitude;
    private String countryName;
    private String cityName;
    private String path;
    private Date updated;

    private Set<Place> places = new HashSet<>();

    @Id
    @Column(name = "TABLE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column
    @Nullable
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(@Nullable Double latitude) {
        this.latitude = latitude;
    }

    @Column
    @Nullable
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(@Nullable Double longitude) {
        this.longitude = longitude;
    }

    @Column(name = "country_name")
    @Nullable
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(@Nullable String countryName) {
        this.countryName = countryName;
    }

    @Column(name = "city_name")
    @Nullable
    public String getCityName() {
        return cityName;
    }

    public void setCityName(@Nullable String cityName) {
        this.cityName = cityName;
    }

    @Column(name = "path")
    @Nullable
    public String getPath() {
        return path;
    }

    public void setPath(@Nullable String path) {
        this.path = path;
    }

    @Column
    @Nullable
    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(@Nullable Date updated) {
        this.updated = updated;
    }

    @JoinTable(name = "location_place_connection", joinColumns = {
            @JoinColumn(name = "locationID") }, inverseJoinColumns = { @JoinColumn(name = "placeID") })
    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @NonNull
    public Set<Place> getPlaces() {
        return places;
    }

    public void setPlaces(@NonNull Set<Place> places) {
        this.places = places;
    }

    @NonNull
    @Override
    public String toString() {
        return "[" + getId() + "] " + getLatitude() + ":" + getLongitude();
    }
    
    @Override
    public boolean equals(@Nullable Object obj) {
        if ( !( obj instanceof Location ) )
            return false;
        if ( obj == this )
            return true;        
        
        Location other = (Location) obj;
        return new EqualsBuilder()
                .append(id, other.id)
                .append(latitude, other.latitude)
                .append(longitude, other.longitude)
                .append(countryName, other.countryName)
                .append(cityName, other.cityName)
                .append(path, other.path)
                .append(updated, other.updated)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(19, 109)
                .appendSuper(super.hashCode())
                .append(id)
                .append(latitude)
                .append(longitude)
                .append(countryName)
                .append(cityName)
                .append(path)
                .append(updated)
                .toHashCode();
    }

}
