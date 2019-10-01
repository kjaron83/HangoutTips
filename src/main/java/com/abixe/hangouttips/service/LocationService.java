package com.abixe.hangouttips.service;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.abixe.hangouttips.model.IpLocation;
import com.abixe.hangouttips.model.Location;

public interface LocationService {

    @Nullable
    public Location get(long id);

    @NonNull
    public Location get(@NonNull IpLocation ipLocation);

    @Nullable
    public Location get(@NonNull String path);

    public void add(@NonNull Location location);

    public void update(@NonNull Location location);

    public void remove(@NonNull Location location);

}
