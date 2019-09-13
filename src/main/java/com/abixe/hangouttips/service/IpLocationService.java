package com.abixe.hangouttips.service;

import java.math.BigDecimal;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.abixe.hangouttips.model.IpLocation;

public interface IpLocationService {

	@Nullable
	public IpLocation get(@NonNull Generation generation, long id);
	
	@Nullable
	public IpLocation getLocation(@NonNull String ip);
	
	@Nullable
	public IpLocation getLocation(@NonNull Long ip);
	
	@Nullable
	public IpLocation getLocation(@NonNull BigDecimal ip);
	
	public enum Generation {
		IPV4("."),
		IPV6(":");
		
		public final String separator;
		
		private Generation(@NonNull String separator) {
			this.separator = separator;
		}		
		
	}	
	
}
