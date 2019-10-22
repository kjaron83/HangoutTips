/*
 * PlaceApiService.java
 * Create Date: Aug 24, 2019
 * Initial-Author: Janos Aron Kiss
 */
package com.abixe.hangouttips.service;

import org.springframework.lang.NonNull;

import com.abixe.hangouttips.model.Location;
import com.abixe.hangouttips.model.Place;

/**
 * A class that implements this interface can be used to update the set of the nearby {@link Place}s of a
 * {@link Location}, and the details of the {@link Place}s as well.
 * @author kjaron83
 */
public interface PlaceApiService {

    /**
     * Updates the set of the nearby {@link Place}s of the specified {@link Location}, and the details of the
     * {@link Place}s as well.
     */
    public void update(@NonNull Location location);

    /**
     * Returns whether the details of the specified place were expired.
     */
    public boolean isExpired(@NonNull Place place);

    /**
     * Returns whether the set of the nearby places of the specified location is expired.
     */
    public boolean isExpired(@NonNull Location location);

}
