/*
 * LocationService.java
 * Create Date: Aug 24, 2019
 * Initial-Author: Janos Aron Kiss
 */
package com.abixe.hangouttips.service;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.abixe.hangouttips.model.IpLocation;
import com.abixe.hangouttips.model.Location;

/**
 * A class that implements this interface can be used to get {@link Location} instances from a data source.
 * @author kjaron83
 */
public interface LocationService {

    /**
     * Returns a {@link Location} instance by the specified id.
     */
    @Nullable
    public Location get(long id);

    /**
     * Returns a {@link Location} instance by the {@link IpLocation}.
     */
    @NonNull
    public Location get(@NonNull IpLocation ipLocation);

    /**
     * Returns a {@link Location} instance by the specified path value.
     */
    @Nullable
    public Location get(@NonNull String path);

    /**
     * Adds the specified {@link Location} to the data source.
     */
    public void add(@NonNull Location location);

    /**
     * Updates the specified {@link Location} in the data source.
     */
    public void update(@NonNull Location location);

    /**
     * Removes the specified {@link Location} from the data source.
     */
    public void remove(@NonNull Location location);

}
