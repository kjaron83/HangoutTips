/*
 * IpLocationService.java
 * Create Date: Aug 11, 2019
 * Initial-Author: Janos Aron Kiss
 */
package com.abixe.hangouttips.service;

import java.math.BigDecimal;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.abixe.hangouttips.model.IpLocation;

/**
 * A class that implements this interface can be used to get {@link IpLocation} instances from a data source.
 * @author kjaron83
 */
public interface IpLocationService {

    /**
     * Returns an {@link IpLocation} instance by the specified {@link Generation}, and id.
     */
    @Nullable
    public IpLocation get(@NonNull Generation generation, long id);

    /**
     * Returns an {@link IpLocation} instance by the specified IP address.
     */
    @Nullable
    public IpLocation getLocation(@NonNull String ip);

    /**
     * Returns an {@link IpLocation} instance by the specified IP address.
     */
    @Nullable
    public IpLocation getLocation(@NonNull Long ip);

    /**
     * Returns an {@link IpLocation} instance by the specified IP address.
     */
    @Nullable
    public IpLocation getLocation(@NonNull BigDecimal ip);

    /**
     * This enum represents the generations of the Internet Protocols.
     * @author kjaron83
     */
    public enum Generation {
        IPV4("."),
        IPV6(":");

        public final String separator;

        private Generation(@NonNull String separator) {
            this.separator = separator;
        }

    }

}
