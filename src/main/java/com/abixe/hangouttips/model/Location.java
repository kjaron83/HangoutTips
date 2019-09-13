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

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Entity
@Table(name="location")
public class Location implements Coordinate {

	private long id;
	private Double latitude; 
	private Double longitude;
	private Date updated;
	
	private Set<Place> places = new HashSet<>();
    	
	@Id
	@Column(name = "TABLE_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
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
	
	@Column
	@Nullable
	public Date getUpdated() {
		return updated;
	}
	
	public void setUpdated(@Nullable Date updated) {
		this.updated = updated;
	}

    @JoinTable(
            name = "location_place_connection", 
            joinColumns = { @JoinColumn(name = "locationID") }, 
            inverseJoinColumns = { @JoinColumn(name = "placeID") }
        )
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
		if ( obj == null || !( obj instanceof Location) )
			return false;
		
		Location other = (Location) obj;
		if ( id != 0 && other.getId() == id )
			return true;
		
		return other.latitude != null && other.longitude != null
				&& latitude != null && longitude != null
				&& other.latitude.equals(latitude) && other.longitude.equals(longitude);
	}
	
	@Override
	public int hashCode() {
		if ( latitude != null && longitude != null )
			return latitude.hashCode() * longitude.hashCode() * 31;
		
		return super.hashCode();		
	}
	
}
