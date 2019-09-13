package com.abixe.hangouttips.dao;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.abixe.hangouttips.model.Location;
import com.abixe.hangouttips.model.Coordinate;

public interface LocationDAO {

	@Nullable
	public Location get(long id);
	
	@Nullable
	public Location get(@NonNull Coordinate coordinate);
	
	public void add(@NonNull Location location);
	
	public void update(@NonNull Location location);
	
	public void remove(@NonNull Location location);
	
}
