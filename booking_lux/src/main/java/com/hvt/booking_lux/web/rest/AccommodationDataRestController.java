package com.hvt.booking_lux.web.rest;

import com.hvt.booking_lux.model.User;
import com.hvt.booking_lux.service.ReservationService;
import com.hvt.booking_lux.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/data")
public class AccommodationDataRestController {
    private final ReservationService reservationService;
    private final UserService userService;

    public AccommodationDataRestController(ReservationService reservationService, UserService userService) {
        this.reservationService = reservationService;
        this.userService = userService;
    }

   @GetMapping("income/{year}")
   @PreAuthorize("isAuthenticated()")
   public List<Map<String,String>> myListingsLastYearIncomeData(Authentication authentication, @PathVariable Integer year, @RequestParam(required = false) String username){
        if(username != null){
            User user = (User) userService.loadUserByUsername(username);
            return reservationService.lastYearIncomeForCreatorsAccommodations(user, year);
        }
        return reservationService.lastYearIncomeForCreatorsAccommodations((User) authentication.getPrincipal(),year);
   }
   @GetMapping("visitors/{year}")
   @PreAuthorize("isAuthenticated()")
   public List<Map<String,String>> yearlyVisitorCount(Authentication authentication,@PathVariable Integer year, @RequestParam(required = false) String username)
   {
       if(username != null){
           User user = (User) userService.loadUserByUsername(username);
           return reservationService.yearlyVisitorsStatistic(user, year);
       }
       return reservationService.yearlyVisitorsStatistic((User)authentication.getPrincipal(),year);
   }
}
