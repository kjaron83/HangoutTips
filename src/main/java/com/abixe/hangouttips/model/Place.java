package com.abixe.hangouttips.model;

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

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Entity
@Table(name="place")
public class Place {
	
	private long id;
	private String placeId;
	private String name;
	private Double rating;
	private String address;
	private String phone;
	private String website;
	private String mapUrl;
	private String photoReference;
	private String photo;
	private Date updated;
	
    private Set<Location> locations = new HashSet<>();		
	    
	@Id
	@Column(name = "TABLE_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "place_id")
	@Nullable
	public String getPlaceId() {
		return placeId;
	}
	
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
	
	@Column(name = "photo_reference")
	@Nullable
	public String getPhotoReference() {
		return photoReference;
	}
	
	public void setPhotoReference(@Nullable String photoReference) {
		this.photoReference = photoReference;
	}
	
	@Column
	@Nullable
	public String getPhoto() {
		return photo;
	}
	
	public void setPhoto(@Nullable String photo) {
		this.photo = photo;
	}
	
	@Column
	@Nullable
	public Date getUpdated() {
		return updated;
	}
	
	public void setUpdated(@Nullable Date updated) {
		this.updated = updated;
	}

    @ManyToMany(mappedBy = "places")
	@NonNull
	public Set<Location> getLocations() {
		return locations;
	}

	public void setLocations(@NonNull Set<Location> locations) {
		this.locations = locations;
	}	
		
	@NonNull
	@Override
	public String toString() {
		return "[" + getId() + "] " + getName() + "(" + getAddress() + ")";		
	}
	
	@Override
	public boolean equals(@Nullable Object obj) {
		if ( obj == null || !( obj instanceof Place) )
			return false;
		
		Place other = (Place) obj;
		if ( id != 0 && other.getId() == id )
			return true;
		
		return other.placeId != null && placeId!= null && other.placeId.equals(placeId);
	}
	
	@Override
	public int hashCode() {
		if ( placeId != null )
			return placeId.hashCode() * 31;
		
		return super.hashCode();
	}
	
}
