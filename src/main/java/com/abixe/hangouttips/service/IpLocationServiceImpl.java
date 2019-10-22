/*
 * IpLocationServiceImpl.java
 * Create Date: Aug 11, 2019
 * Initial-Author: Janos Aron Kiss
 */
package com.abixe.hangouttips.service;

import java.math.BigDecimal;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import com.abixe.hangouttips.dao.Ipv4LocationDAO;
import com.abixe.hangouttips.dao.Ipv6LocationDAO;
import com.abixe.hangouttips.model.IpLocation;

/**
 * This class implements the {@link IpLocationService} interface and uses {@link Ipv4LocationDAO},
 * or {@link Ipv6LocationDAO}, to get {@link IpLocation} instances from the database.
 * @author kjaron83
 */
public class IpLocationServiceImpl implements IpLocationService {

    private Ipv4LocationDAO ipv4LocationDAO;
    private Ipv6LocationDAO ipv6LocationDAO;

    public void setIpv4LocationDAO(Ipv4LocationDAO ipv4LocationDAO) {
        this.ipv4LocationDAO = ipv4LocationDAO;
    }

    public void setIpv6LocationDAO(Ipv6LocationDAO ipv6LocationDAO) {
        this.ipv6LocationDAO = ipv6LocationDAO;
    }

    @Nullable
    @Override
    @Transactional
    public IpLocation get(@NonNull Generation generation, long id) {
        if ( generation == Generation.IPV4 )
            return ipv4LocationDAO.get(id);
        if ( generation == Generation.IPV6 )
            return ipv4LocationDAO.get(id);

        return null;
    }

    @Nullable
    @Override
    @Transactional
    public IpLocation getLocation(@NonNull String ip) {
        if ( ip.contains(Generation.IPV4.separator) )
            return ipv4LocationDAO.getLocation(ip);
        if ( ip.contains(Generation.IPV6.separator) )
            return ipv6LocationDAO.getLocation(ip);

        return null;
    }

    @Nullable
    @Override
    @Transactional
    public IpLocation getLocation(@NonNull Long ip) {
        return ipv4LocationDAO.getLocation(ip);
    }

    @Nullable
    @Override
    @Transactional
    public IpLocation getLocation(@NonNull BigDecimal ip) {
        return ipv6LocationDAO.getLocation(ip);
    }

}
