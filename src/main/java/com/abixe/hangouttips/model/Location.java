package com.abixe.hangouttips.model;

import org.springframework.lang.Nullable;

public interface Location {

	@Nullable
	public String getCountryCode();

	@Nullable
	public String getCountryName();

	@Nullable
	public String getRegionName();

	@Nullable
	public String getCityName();

	@Nullable
	public Double getLatitude(); 

	@Nullable
	public Double getLongitude();

	@Nullable
	public String getZipCode();

	@Nullable
	public String getTimeZone();
	
}
