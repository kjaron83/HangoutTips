/*
 * Ipv6Location.java
 * Create Date: Aug 11, 2019
 * Initial-Author: Janos Aron Kiss
 */
package com.abixe.hangouttips.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * This class extends the {@link IpLocationImpl} class, and represents an IPV6 address, stored in a database.
 * @author kjaron83
 */
@Entity
@Table(name = "ip2location_db11_ipv6")
public class Ipv6Location extends IpLocationImpl {

    private BigDecimal ipFrom;
    private BigDecimal ipTo;

    @Id
    @Column(name = "TABLE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Override
    public long getId() {
        return super.getId();
    }

    @Column(name = "ip_from", columnDefinition = "decimal(39,0) UNSIGNED")
    @Nullable
    public BigDecimal getIpFrom() {
        return ipFrom;
    }

    public void setIpFrom(@Nullable BigDecimal ipFrom) {
        this.ipFrom = ipFrom;
    }

    @Column(name = "ip_to", columnDefinition = "decimal(39,0) UNSIGNED")
    @Nullable
    public BigDecimal getIpTo() {
        return ipTo;
    }

    @Nullable
    public void setIpTo(@Nullable BigDecimal ipTo) {
        this.ipTo = ipTo;
    }

    @Column(name = "country_code")
    @Nullable
    @Override
    public String getCountryCode() {
        return super.getCountryCode();
    }

    @Column(name = "country_name")
    @Nullable
    @Override
    public String getCountryName() {
        return super.getCountryName();
    }

    @Column(name = "region_name")
    @Nullable
    @Override
    public String getRegionName() {
        return super.getRegionName();
    }

    @Column(name = "city_name")
    @Nullable
    @Override
    public String getCityName() {
        return super.getCityName();
    }

    @Column(name = "latitude")
    @Nullable
    @Override
    public Double getLatitude() {
        return super.getLatitude();
    }

    @Column(name = "longitude")
    @Nullable
    @Override
    public Double getLongitude() {
        return super.getLongitude();
    }

    @Column(name = "zip_code")
    @Nullable
    @Override
    public String getZipCode() {
        return super.getZipCode();
    }

    @Column(name = "time_zone")
    @Nullable
    @Override
    public String getTimeZone() {
        return super.getTimeZone();
    }

    /**
     * Converts an IP address to a numeric value.
     */
    @NonNull
    public static BigDecimal convert(@NonNull String ip) {
        try {
            byte[] bytes = InetAddress.getByName(ip).getAddress();
            return new BigDecimal(new BigInteger(1, bytes));
        }
        catch ( UnknownHostException e ) {
            throw new RuntimeException(e);
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "[" + getId() + "] " + getZipCode() + ". " + getCountryName() + ", " + getCityName();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if ( !( obj instanceof Ipv6Location ) )
            return false;
        if ( obj == this )
            return true;

        Ipv6Location other = (Ipv6Location) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(ipFrom, other.ipFrom)
                .append(ipTo, other.ipTo)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 107)
                .appendSuper(super.hashCode())
                .append(ipFrom)
                .append(ipTo)
                .toHashCode();
    }

}
