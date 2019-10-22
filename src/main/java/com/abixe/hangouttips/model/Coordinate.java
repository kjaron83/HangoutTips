/*
 * Coordinate.java
 * Create Date: Aug 24, 2019
 * Initial-Author: Janos Aron Kiss
 */
package com.abixe.hangouttips.model;

import org.springframework.lang.Nullable;

/**
 * A class that implements this interface can be used as a GPS coordinate.
 * @author kjaron83
 */
public interface Coordinate {

    /**
     * Returns the geocoded latitude value for this coordinate.
     */
    @Nullable
    public Double getLatitude();

    /**
     * Returns the geocoded longitude value for this coordinate.
     */
    @Nullable
    public Double getLongitude();

    public default boolean equals(@Nullable Coordinate coordinate) {
        if ( coordinate == null )
            return false;
        if ( coordinate == this )
            return true;

        Double latitude = getLatitude();
        Double longitude = getLongitude();

        return latitude != null
                && longitude != null
                && latitude.equals(coordinate.getLatitude())
                && longitude.equals(coordinate.getLongitude());
    }

}
