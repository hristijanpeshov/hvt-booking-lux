package com.hvt.booking_lux.web;

import com.hvt.booking_lux.model.City;
import com.hvt.booking_lux.service.LocationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LocationRestController {
    private final LocationService locationService;

    public LocationRestController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/cities")
    List<String> getCities(){
        return locationService.listAllCityNames();
    }
}
