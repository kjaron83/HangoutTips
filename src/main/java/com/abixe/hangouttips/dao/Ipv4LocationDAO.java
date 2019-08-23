package com.abixe.hangouttips.dao;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.abixe.hangouttips.model.Ipv4Location;

public interface Ipv4LocationDAO {

	@Nullable
	public Ipv4Location get(long id);
	
	@Nullable
	public Ipv4Location getLocation(@NonNull String ip);
	
	@Nullable
	public Ipv4Location getLocation(@NonNull Long ip);
	
}
