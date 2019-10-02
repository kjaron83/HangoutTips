package com.abixe.hangouttips.model;

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

@Entity
@Table(name = "ip2location_db11")
public class Ipv4Location extends IpLocationImpl {

    private Long ipFrom;
    private Long ipTo;

    @Id
    @Column(name = "TABLE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Override
    public long getId() {
        return super.getId();
    }

    @Column(name = "ip_from", columnDefinition = "int(10) UNSIGNED")
    @Nullable
    public Long getIpFrom() {
        return ipFrom;
    }

    public void setIpFrom(@Nullable Long ipFrom) {
        this.ipFrom = ipFrom;
    }

    @Column(name = "ip_to", columnDefinition = "int(10) UNSIGNED")
    @Nullable
    public Long getIpTo() {
        return ipTo;
    }

    @Nullable
    public void setIpTo(@Nullable Long ipTo) {
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

    public static long convert(@NonNull String ip) {
        String[] parts = ip.split("\\.");

        long result = 0;
        for ( int i = 0; i < parts.length; i++ )
            result = result << 8 | ( Integer.parseInt(parts[i]) & 0xFF );

        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "[" + getId() + "] " + getZipCode() + ". " + getCountryName() + ", " + getCityName();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if ( !( obj instanceof Ipv4Location ) )
            return false;
        if ( obj == this )
            return true;        

        Ipv4Location other = (Ipv4Location) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(ipFrom, other.ipFrom)
                .append(ipTo, other.ipTo)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(13, 103)
                .appendSuper(super.hashCode())
                .append(ipFrom)
                .append(ipTo)
                .toHashCode();
    }
    
    
}
