package com.hvt.booking_lux.web;

import com.hvt.booking_lux.model.User;
import com.hvt.booking_lux.service.ReservationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/data")
public class AccommodationDataRestController {
    private final ReservationService reservationService;

    public AccommodationDataRestController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

   @GetMapping("income/{year}")
   @PreAuthorize("isAuthenticated()")
   public List<Map<String,String>> myListingsLastYearIncomeData(Authentication authentication, @PathVariable Integer year){
       return reservationService.lastYearIncomeForCreatorsAccommodations((User) authentication.getPrincipal(),year);
   }
   @GetMapping("visitors/{year}")
   @PreAuthorize("isAuthenticated()")
   public List<Map<String,String>> yearlyVisitorCount(Authentication authentication,@PathVariable Integer year)
   {
       return reservationService.yearlyVisitorsStatistic((User)authentication.getPrincipal(),year);
   }
}
