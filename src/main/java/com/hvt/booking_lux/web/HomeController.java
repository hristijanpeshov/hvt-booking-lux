package com.hvt.booking_lux.web;

import com.hvt.booking_lux.service.ReservationObjectService;
import com.hvt.booking_lux.service.ReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.ZonedDateTime;

@Controller
@RequestMapping(value = {"/","/home"})
public class HomeController {

//    private final LocationService locationService;
    private final ReservationObjectService reservationObjectService;
    private final ReservationService reservationService;

    public HomeController(ReservationObjectService reservationObjectService, ReservationService reservationService) {
        this.reservationObjectService = reservationObjectService;
        this.reservationService = reservationService;
    }

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
