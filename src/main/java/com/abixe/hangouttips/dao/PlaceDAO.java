/*
 * PlaceDAO.java
 * Create Date: Aug 24, 2019
 * Initial-Author: Janos Aron Kiss
 */
package com.abixe.hangouttips.dao;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.abixe.hangouttips.model.Place;

/**
 * A class that implements this interface can be used to get {@link Place} instances from a data source.
 * @author kjaron83
 */
public interface PlaceDAO {

    /**
     * Returns an {@link Place} instance by the specified id.
     */
    @Nullable
    public Place get(long id);

    /**
     * Returns an {@link Place} instance by the specified placeId value.
     * @see Place#placeId
     */
    @Nullable
    public Place get(@NonNull String placeId);

    /**
     * Adds the specified {@link Place} to the data source.
     */
    public void add(@NonNull Place place);

    /**
     * Updates the specified {@link Place} in the data source.
     */
    public void update(@NonNull Place place);

    /**
     * Removes the specified {@link Place} from the data source.
     */
    public void remove(@NonNull Place place);

}
