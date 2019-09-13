package com.abixe.hangouttips.dao;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.abixe.hangouttips.model.Place;

public interface PlaceDAO {

	@Nullable
	public Place get(long id);
		
	@Nullable
	public Place get(@NonNull String placeId);

	public void add(@NonNull Place place);
	
	public void update(@NonNull Place place);
	
	public void remove(@NonNull Place place);	
	
}
