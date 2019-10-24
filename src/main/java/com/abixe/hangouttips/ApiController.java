/*
 * PlaceApiServiceImpl.java
 * Create Date: Sep 14, 2019
 * Initial-Author: Janos Aron Kiss
 */
package com.abixe.hangouttips;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abixe.hangouttips.model.IpLocation;
import com.abixe.hangouttips.model.Location;
import com.abixe.hangouttips.service.IpLocationService;
import com.abixe.hangouttips.service.LocationService;
import com.abixe.hangouttips.service.PlaceApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This controller handles the API requests and returns JSON encoded answers.
 * @author kjaron83
 */
@RestController
@RequestMapping("api")
public class ApiController {

    private IpLocationService ipLocationService;
    private LocationService locationService;
    private PlaceApiService placeApiService;

    @Autowired()
    @Qualifier(value = "ipLocationService")
    public void setIpLocationService(IpLocationService ipLocationService) {
        this.ipLocationService = ipLocationService;
    }

    @Autowired()
    @Qualifier(value = "locationService")
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    @Autowired()
    @Qualifier(value = "placeApiService")
    public void setPlaceApiService(PlaceApiService placeApiService) {
        this.placeApiService = placeApiService;
    }

    /**
     * Returns whether the details of the specified location are expired.
     */
    @GetMapping(value = "/{country}/{city}", produces = "application/json")
    public String pullLocationIsExpired(@PathVariable String country, @PathVariable String city) {
        Location location = locationService.get(country + "/" + city);

        return json(
                createResults(location != null && !placeApiService.isExpired(location))
                );
    }

    /**
     * Tests if the location can be found by the current IP address, and returns some info about the location.
     */
    @GetMapping(value = "/test")
    public String pullIpLocationInfo(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        IpLocation ipLocation = ipLocationService.getLocation(ip);

        Map<String, Object> info = createResults(ipLocation != null);
        info.put("ip", ip);
        if ( ipLocation != null ) {
            Location location = locationService.get(ipLocation);
            info.put("location", location.getPath());
            info.put("places", location.getPlaces().size());
            info.put("expired", placeApiService.isExpired(location));
        }

        return json(info);
    }

    /**
     * Tries to find a location by the specified path value, and executes an update if the location was found.
     */
    @GetMapping(value = "/{country}/{city}/update", produces = "application/json")
    public String pullLocationUpdate(@PathVariable String country, @PathVariable String city) {
        Location location = locationService.get(country + "/" + city);
        if ( location != null )
            placeApiService.update(location);

        return json(
                createResults(location != null)
                );
    }

    /**
     * Creates a map for further use, which contains the value of the specified success parameter.
     */
    private Map<String, Object> createResults(boolean success) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("success", success);
        return map;
    }

    /**
     * Encodes the specified map to a JSON string.
     */
    private static String json(@NonNull Map<String, Object> map) {
        String json = "";

        ObjectMapper mapper = new ObjectMapper();
        try {
            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
        }
        catch ( JsonProcessingException e ) {
            throw new RuntimeException(e);
        }

        return json;
    }

}
