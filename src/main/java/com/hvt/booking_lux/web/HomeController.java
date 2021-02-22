package com.hvt.booking_lux.web;

import com.hvt.booking_lux.service.ReservationObjectService;
import com.hvt.booking_lux.service.ReservationService;
import com.hvt.booking_lux.service.UnitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = {"/","/home"})
public class HomeController {

//    private final LocationService locationService;
    private final ReservationObjectService reservationObjectService;
    private final ReservationService reservationService;
    private final UnitService unitService;

    public HomeController(ReservationObjectService reservationObjectService, ReservationService reservationService, UnitService unitService) {
        this.reservationObjectService = reservationObjectService;
        this.reservationService = reservationService;
        this.unitService = unitService;
    }

    @GetMapping
    public String homePage(Model model){
//        List<Country> countryList = locationService.listAllCountries();
//        model.addAttribute("countries",countryList);
        model.addAttribute("bodyContent", "index");
        model.addAttribute("leastExpensive", unitService.findTheLeastExpensive());
        model.addAttribute("mostExpensive", unitService.findTheMostExpensive());
        model.addAttribute("largest", unitService.findTheLargest());
        model.addAttribute("smallest", unitService.findTheSmallest());
        return "master-template";
    }
}
