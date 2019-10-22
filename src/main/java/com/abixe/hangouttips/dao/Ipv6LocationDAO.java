/*
 * Ipv6LocationDAO.java
 * Create Date: Aug 11, 2019
 * Initial-Author: Janos Aron Kiss
 */
package com.abixe.hangouttips.dao;

import java.math.BigDecimal;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.abixe.hangouttips.model.Ipv6Location;

/**
 * A class that implements this interface can be used to get {@link Ipv6Location} instances from a data source.
 * @author kjaron83
 */
public interface Ipv6LocationDAO {

    /**
     * Returns an {@link Ipv6Location} instance by the specified id.
     */
    @Nullable
    public Ipv6Location get(long id);

    /**
     * Returns an {@link Ipv6Location} instance by the specified IP address.
     */
    @Nullable
    public Ipv6Location getLocation(@NonNull String ip);

    /**
     * Returns an {@link Ipv6Location} instance by the specified IP address.
     */
    @Nullable
    public Ipv6Location getLocation(@NonNull BigDecimal ip);

}
