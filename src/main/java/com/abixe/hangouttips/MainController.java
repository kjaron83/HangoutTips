package com.abixe.hangouttips;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.abixe.hangouttips.model.Location;
import com.abixe.hangouttips.service.LocationService;

@Controller
public class MainController extends DefaultController {
	
	private LocationService locationService;
	
	@Autowired()
	@Qualifier(value = "locationService")
	public void setLocationService(LocationService locationService){
		this.locationService = locationService;
	}	

	@GetMapping(value = "/")
	public String getHomeContent(Model model, HttpServletRequest request) {
		model.addAttribute("content", "@home");
		
		String ip = request.getRemoteAddr();		
		Location location = locationService.getLocation(ip);
		
		HashMap<String, Object> info = new HashMap<>();
		info.put("ip", ip);
		if ( location != null ) {
			info.put("country", location.getCountryName());			
			info.put("city", location.getCityName());			
			info.put("longitude", location.getLongitude());			
			info.put("latitude", location.getLatitude());			
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
