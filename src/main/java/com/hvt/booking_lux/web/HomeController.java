package com.hvt.booking_lux.web;

import com.hvt.booking_lux.service.LocationService;
import com.hvt.booking_lux.service.ReservationObjectService;
import com.hvt.booking_lux.service.ReservationService;
import com.hvt.booking_lux.service.ReviewService;
import com.hvt.booking_lux.service.UnitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = {"/","/home"})
public class HomeController {

//    private final LocationService locationService;
    private final ReservationObjectService reservationObjectService;
    private final ReservationService reservationService;
    private final UnitService unitService;
    private final LocationService locationService;
    private final ReviewService reviewService;

    public HomeController(ReservationObjectService reservationObjectService, ReservationService reservationService, UnitService unitService, LocationService locationService) {
    public HomeController(ReservationObjectService reservationObjectService, ReservationService reservationService, UnitService unitService, ReviewService reviewService) {
        this.reservationObjectService = reservationObjectService;
        this.reservationService = reservationService;
        this.unitService = unitService;
        this.locationService = locationService;
        this.reviewService = reviewService;
    }

    @GetMapping
    public String homePage(Model model, HttpServletRequest request){
//        List<Country> countryList = locationService.listAllCountries();
//        model.addAttribute("countries",countryList);
        model.addAttribute("cities",locationService.listAllCities());
        model.addAttribute("searchForm","searchForm");
        model.addAttribute("bodyContent", "index");
        model.addAttribute("leastExpensive", unitService.findTheLeastExpensive());
        model.addAttribute("mostExpensive", unitService.findTheMostExpensive());
        model.addAttribute("largest", unitService.findTheLargest());
        model.addAttribute("smallest", unitService.findTheSmallest());
        if(request.getParameter("error") != null){
            model.addAttribute("hasError", true);
            model.addAttribute("error", "Check in date should be before check out date!");
        }
//        reviewService.canUserWriteReview(1, "user@user.com");
        model.addAttribute("bodyContent", "index");
        return "master-template";
    }

    @GetMapping("/account/manage")
    public String accountPage(Model model){
        model.addAttribute("bodyContent", "manage-account");
        return "master-template";
    }
}
