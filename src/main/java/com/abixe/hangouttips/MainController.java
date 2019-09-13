package com.abixe.hangouttips;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.abixe.hangouttips.model.IpLocation;
import com.abixe.hangouttips.model.Location;
import com.abixe.hangouttips.service.IpLocationService;
import com.abixe.hangouttips.service.LocationService;
import com.abixe.hangouttips.service.PlaceApiService;

@Controller
public class MainController extends DefaultController {
	
	private IpLocationService ipLocationService;
	private LocationService locationService;
	private PlaceApiService placeApiService;
	
	@Autowired()
	@Qualifier(value = "ipLocationService")
	public void setIpLocationService(IpLocationService ipLocationService){
		this.ipLocationService = ipLocationService;
	}	
	
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

	@GetMapping(value = "/")
	public String getHomeContent(Model model, HttpServletRequest request) {
		model.addAttribute("content", "@home");
		
		String ip = request.getRemoteAddr();		
		IpLocation ipLocation = ipLocationService.getLocation(ip);
		
		HashMap<String, Object> info = new HashMap<>();
		info.put("ip", ip);
		if ( ipLocation != null ) {
			info.put("country", ipLocation.getCountryName());			
			info.put("city", ipLocation.getCityName());			
			info.put("longitude", ipLocation.getLongitude());			
			info.put("latitude", ipLocation.getLatitude());
			
			Location location = locationService.get(ipLocation);
			info.put("location", location.toString());
			
			if ( placeApiService.isExpired(location) )
				placeApiService.update(location);
		}
		model.addAttribute("info", info);
		
		return "index";		
	}	
	
	@GetMapping(value = "/privacy-policy")
	public String getPolicyContent(Model model) {
		model.addAttribute("content", "@policy");		
		return "index";		
	}	

}
