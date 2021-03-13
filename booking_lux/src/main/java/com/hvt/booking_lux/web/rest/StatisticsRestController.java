package com.hvt.booking_lux.web.rest;

import com.hvt.booking_lux.model.User;
import com.hvt.booking_lux.service.ReservationService;
import com.hvt.booking_lux.service.UserStatisticsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsRestController {

    private final UserStatisticsService userStatisticsService;
    private final ReservationService reservationService;

    public StatisticsRestController(UserStatisticsService userStatisticsService, ReservationService reservationService) {
        this.userStatisticsService = userStatisticsService;
        this.reservationService = reservationService;
    }

    @GetMapping("/{id}/pie")
    @PreAuthorize("isAuthenticated() && @creatorCheck.check(#id,authentication)")
    public Map<String,Integer> sentimentAnalysis(@PathVariable Long id){
        return userStatisticsService.findSentimentForResObject(id);
//        return reservationService.lastYearIncomeForCreatorsAccommodations((User) authentication.getPrincipal());
    }

}
