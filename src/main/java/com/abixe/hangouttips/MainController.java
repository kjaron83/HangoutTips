/*
 * MainController.java
 * Create Date: Aug 11, 2019
 * Initial-Author: Janos Aron Kiss
 */
package com.abixe.hangouttips;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.abixe.hangouttips.model.IpLocation;
import com.abixe.hangouttips.model.Location;
import com.abixe.hangouttips.model.Place;
import com.abixe.hangouttips.service.IpLocationService;
import com.abixe.hangouttips.service.LocationService;
import com.abixe.hangouttips.service.PlaceApiService;

/**
 * This controller handles the visitors' requests, sets the attributes of the model, and returns a template filename.
 * @author kjaron83
 */
@Controller
public class MainController extends DefaultController {

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
     * Ensure the content of the home page.
     */
    @GetMapping(value = "/")
    public String getHomeContent(Model model, HttpServletRequest request) {
        model.addAttribute("content", "@home");

        String ip = request.getRemoteAddr();
        IpLocation ipLocation = ipLocationService.getLocation(ip);

        HashMap<String, Object> info = new HashMap<>();
        info.put("ip", ip);
        if ( ipLocation != null ) {
            Location location = locationService.get(ipLocation);
            info.put("location", location.getPath());

            if ( placeApiService.isExpired(location) )
                placeApiService.update(location);
        }
        model.addAttribute("info", info);

        return "index";
    }

    /**
     * Ensure the content of the specified location.
     */
    @GetMapping(value = "/{country}/{city}")
    public String getHomeContent(Model model, @PathVariable String country, @PathVariable String city) {
        model.addAttribute("content", "@places");

        Location location = locationService.get(country + "/" + city);
        if ( location == null )
            return redirect(null);

        model.addAttribute("title", "Hangout Tips nearby " + location.getCityName() + " (" + location.getCountryName() + ")");

        HashMap<String, Object> info = new HashMap<>();

        info.put("location", location.getPath());

        ArrayList<Place> places = new ArrayList<>(location.getPlaces());
        Collections.sort(places, Place.RATING_COMPARATOR);
        info.put("places", places);

        if ( placeApiService.isExpired(location) )
            placeApiService.update(location);

        model.addAttribute("info", info);

        return "index";
    }

    /**
     * Ensure the content of the privacy policy page.
     */
    @GetMapping(value = "/privacy-policy")
    public String getPolicyContent(Model model) {
        model.addAttribute("content", "@policy");
        model.addAttribute("title", "Hangout Tips - Privacy policy");
        return "index";
    }

}
