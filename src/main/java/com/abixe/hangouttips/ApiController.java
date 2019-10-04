package com.abixe.hangouttips;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.ui.Model;
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

    @GetMapping(value = "/{country}/{city}", produces = "application/json")
    public String pullLocationIsExpired(@PathVariable String country, @PathVariable String city) {
        Location location = locationService.get(country + "/" + city);

        return json(
                createResults(location != null && !placeApiService.isExpired(location))
                );
    }
    
    @GetMapping(value = "/test")
    public String pullIpLocationInfo(Model model, HttpServletRequest request) {
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
        model.addAttribute("info", info);

        return json(info);
    }    

    @GetMapping(value = "/{country}/{city}/update", produces = "application/json")
    public String pullLocationUpdate(@PathVariable String country, @PathVariable String city) {
        Location location = locationService.get(country + "/" + city);
        if ( location != null )
            placeApiService.update(location);

        return json(
                createResults(location != null)
                );
    }

    private Map<String, Object> createResults(boolean success) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("success", success);
        return map;
    }

    private static String json(@NonNull Map map) {
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
