/*
 * Ipv4LocationDAO.java
 * Create Date: Aug 11, 2019
 * Initial-Author: Janos Aron Kiss
 */
package com.abixe.hangouttips.dao;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.abixe.hangouttips.model.Ipv4Location;

/**
 * A class that implements this interface can be used to get {@link Ipv4Location} instances from a data source.
 * @author kjaron83
 */
public interface Ipv4LocationDAO {

    /**
     * Returns an {@link Ipv4Location} instance by the specified id.
     */
    @Nullable
    public Ipv4Location get(long id);

    /**
     * Returns an {@link Ipv4Location} instance by the specified IP address.
     */
    @Nullable
    public Ipv4Location getLocation(@NonNull String ip);

    /**
     * Returns an {@link Ipv4Location} instance by the specified IP address.
     */
    @Nullable
    public Ipv4Location getLocation(@NonNull Long ip);

}
