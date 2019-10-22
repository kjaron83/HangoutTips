/*
 * IpLocationImpl.java
 * Create Date: Aug 11, 2019
 * Initial-Author: Janos Aron Kiss
 */
package com.abixe.hangouttips.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.lang.Nullable;

/**
 * This abstract class implements the {@link IpLocation} interface, and provides default methods to the offsprings.
 * @author kjaron83
 */
public abstract class IpLocationImpl implements IpLocation {

    private long id;

    private String countryCode;
    private String countryName;
    private String regionName;
    private String cityName;
    private Double latitude;
    private Double longitude;
    private String zipCode;
    private String timeZone;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Nullable
    @Override
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(@Nullable String countryCode) {
        this.countryCode = countryCode;
    }

    @Nullable
    @Override
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(@Nullable String countryName) {
        this.countryName = countryName;
    }

    @Nullable
    @Override
    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(@Nullable String regionName) {
        this.regionName = regionName;
    }

    @Nullable
    @Override
    public String getCityName() {
        return cityName;
    }

    public void setCityName(@Nullable String cityName) {
        this.cityName = cityName;
    }

    @Nullable
    @Override
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(@Nullable Double latitude) {
        this.latitude = latitude;
    }

    @Nullable
    @Override
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(@Nullable Double longitude) {
        this.longitude = longitude;
    }

    @Nullable
    @Override
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(@Nullable String zipCode) {
        this.zipCode = zipCode;
    }

    @Nullable
    @Override
    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(@Nullable String timeZone) {
        this.timeZone = timeZone;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if ( !( obj instanceof IpLocationImpl ) )
            return false;
        if ( obj == this )
            return true;

        IpLocationImpl other = (IpLocationImpl) obj;
        return new EqualsBuilder()
                .append(id, other.id)
                .append(countryCode, other.countryCode)
                .append(countryName, other.countryName)
                .append(regionName, other.regionName)
                .append(cityName, other.cityName)
                .append(latitude, other.latitude)
                .append(longitude, other.longitude)
                .append(zipCode, other.zipCode)
                .append(timeZone, other.timeZone)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(11, 101)
                .append(id)
                .append(countryCode)
                .append(countryName)
                .append(regionName)
                .append(cityName)
                .append(latitude)
                .append(longitude)
                .append(zipCode)
                .append(timeZone)
                .toHashCode();
    }

}
