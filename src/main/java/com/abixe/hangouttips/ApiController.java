package com.abixe.hangouttips;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abixe.hangouttips.model.Location;
import com.abixe.hangouttips.service.LocationService;
import com.abixe.hangouttips.service.PlaceApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("api")
public class ApiController {

	private LocationService locationService;
	private PlaceApiService placeApiService;
	
	@Autowired()
	@Qualifier(value = "locationService")
	public void setLocationService(LocationService locationService){
		this.locationService = locationService;
	}		

	@Autowired()
	@Qualifier(value = "placeApiService")
	public void setPlaceApiService(PlaceApiService placeApiService){
		this.placeApiService = placeApiService;
	}			
	
	@GetMapping(value = "/{country}/{city}", produces = "application/json")
    public String pullPerson(@PathVariable String country, @PathVariable String city) {
		Location location = locationService.get(country + "/" + city);
    	    	
    	return json(
    			createResults(location != null && !placeApiService.isExpired(location))
    			);
    }
	
    private Map createResults(boolean success) {
    	LinkedHashMap<String, Object> map = new LinkedHashMap<>();
    	map.put("success", success);    		
    	return map;
    }	

    private static String json(@NonNull Map map) {
    	String json = "";
    	
    	ObjectMapper mapper = new ObjectMapper();
    	try {
			json = mapper.writerWithDefaultPrettyPrinter()
			  .writeValueAsString(map);
		}
    	catch (JsonProcessingException e) {
    		throw new RuntimeException(e);
		}    	
    	
    	return json;
    }	
	
}
