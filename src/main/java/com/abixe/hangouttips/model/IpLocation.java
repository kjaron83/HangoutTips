/*
 * IpLocation.java
 * Create Date: Aug 11, 2019
 * Initial-Author: Janos Aron Kiss
 */
package com.abixe.hangouttips.model;

import org.springframework.lang.Nullable;

/**
 * This interface represents an <a href="https://lite.ip2location.com/ip2location-lite">IP2Location LITE entity</a>,
 * stored in a database.
 * @author kjaron83
 */
public interface IpLocation extends Coordinate {

    @Nullable
    public String getCountryCode();

    @Nullable
    public String getCountryName();

    @Nullable
    public String getRegionName();

    @Nullable
    public String getCityName();

    @Nullable
    public String getZipCode();

    @Nullable
    public String getTimeZone();

}
