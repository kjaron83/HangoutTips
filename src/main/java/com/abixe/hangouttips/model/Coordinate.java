package com.abixe.hangouttips.model;

import org.springframework.lang.Nullable;

public interface Coordinate {

    @Nullable
    public Double getLatitude();

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
