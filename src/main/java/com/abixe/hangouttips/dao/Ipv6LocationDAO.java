package com.abixe.hangouttips.dao;

import java.math.BigDecimal;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.abixe.hangouttips.model.Ipv6Location;

public interface Ipv6LocationDAO {

	@Nullable
	public Ipv6Location get(long id);
	
	@Nullable
	public Ipv6Location getLocation(@NonNull String ip);
	
	@Nullable
	public Ipv6Location getLocation(@NonNull BigDecimal ip);
	
}
