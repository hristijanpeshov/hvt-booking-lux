package com.hvt.booking_lux.web;

import com.hvt.booking_lux.model.Country;
import com.hvt.booking_lux.service.LocationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = {"/","/home"})
public class HomeController {
//
//    private final LocationService locationService;
//
//    public HomeController(LocationService locationService) {
//        this.locationService = locationService;
//    }

    @GetMapping
    public String homePage(Model model){
//        List<Country> countryList = locationService.listAllCountries();
//        model.addAttribute("countries",countryList);
        model.addAttribute("bodyContent", "index");
        return "master-template";
    }

    @GetMapping("/account/manage")
    public String accountPage(Model model){
        model.addAttribute("bodyContent", "manage-account");
        return "master-template";
    }
}
