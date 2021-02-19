package com.hvt.booking_lux.web;

import com.hvt.booking_lux.model.Country;
import com.hvt.booking_lux.model.User;
import com.hvt.booking_lux.service.LocationService;
import com.hvt.booking_lux.service.UserStatisticsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = {"/","/home"})
public class HomeController {

//    private final LocationService locationService;
    private final UserStatisticsService userStatisticsService;

    public HomeController(UserStatisticsService userStatisticsService) {
        this.userStatisticsService = userStatisticsService;
    }

    @GetMapping
    public String homePage(Model model){
//        List<Country> countryList = locationService.listAllCountries();
        userStatisticsService.findAnnualPropertyReservationCount(new User(), 2021);
//        model.addAttribute("countries",countryList);
        return "";
    }
}
