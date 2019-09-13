package com.abixe.hangouttips.model;

import org.springframework.lang.Nullable;

public interface IpLocation extends Coordinate {

	@Nullable
	public String getCountryCode();

	@Nullable
	public String getCountryName();

	@Nullable
	public String getRegionName();

	@Nullable
	public String getCityName();

	@Nullable
	public String getZipCode();

	@Nullable
	public String getTimeZone();
	
}
