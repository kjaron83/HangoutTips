package com.abixe.hangouttips.model;

import org.springframework.lang.Nullable;

public interface Coordinate {

	@Nullable
	public Double getLatitude();
	
	@Nullable
	public Double getLongitude();	

}
