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

        Double thisLatitude = getLatitude();
        Double thislongitude = getLongitude();

        Double coordinateLatitude = coordinate.getLatitude();
        Double coordinatelongitude = coordinate.getLongitude();

        if ( thisLatitude == null &&  coordinateLatitude == null &&
                thislongitude == null && coordinatelongitude == null )
            return true;

        return thisLatitude != null && thisLatitude.equals(coordinateLatitude)
                && thislongitude != null && thislongitude.equals(coordinatelongitude);
    }

}
