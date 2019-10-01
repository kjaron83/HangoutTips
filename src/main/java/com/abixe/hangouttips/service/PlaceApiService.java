package com.abixe.hangouttips.service;

import org.springframework.lang.NonNull;

import com.abixe.hangouttips.model.Location;
import com.abixe.hangouttips.model.Place;

public interface PlaceApiService {

    public void update(@NonNull Location location);

    public boolean isExpired(@NonNull Place place);

    public boolean isExpired(@NonNull Location location);

}
