package com.hvt.booking_lux.web;

import com.hvt.booking_lux.model.Reservation;
import com.hvt.booking_lux.model.Unit;
import com.hvt.booking_lux.model.User;
import com.hvt.booking_lux.service.ReservationService;
import com.hvt.booking_lux.service.UnitService;
import org.h2.api.Interval;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.*;
import java.util.List;

@Controller
@RequestMapping("/reserve")
public class ReservationController {
    private final ReservationService reservationService;
    private final UnitService unitService;

    public ReservationController(ReservationService reservationService, UnitService unitService) {
        this.reservationService = reservationService;
        this.unitService = unitService;
    }

    @GetMapping("/review")
    public String reserveReview(Model model){
        model.addAttribute("bodyContent", "confirmReservation");
        return "master-template";
    }

    @GetMapping
    public String myReservations(HttpServletRequest request,Authentication authentication, Model model)
    {
        List<Reservation> reservationList = reservationService.findAllReservationsForUser((User) authentication.getPrincipal());
        model.addAttribute("reservations",reservationList);
        return "";
    }
    @GetMapping("/{unitId}")
    public String reserveUnit(@PathVariable long unitId,HttpServletRequest request,Authentication authentication,Model model)
    {
        Unit unit = unitService.findById(unitId);
        model.addAttribute("unit",unit);
        model.addAttribute("city",request.getSession().getAttribute("city"));
        model.addAttribute("checkIn",request.getSession().getAttribute("checkIn"));
        model.addAttribute("checkOut",request.getSession().getAttribute("checkOut"));
        return "";
    }
    @PostMapping("/{unitId}")
    public String confirmReservation(Authentication authentication,@PathVariable long unitId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkInDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkOutDate)
    {
        ZonedDateTime checkIn = ZonedDateTime.of(checkInDate, LocalTime.parse("00:00"), ZoneId.systemDefault());
        ZonedDateTime checkOut = ZonedDateTime.of(checkOutDate, LocalTime.parse("00:00"), ZoneId.systemDefault());
        reservationService.reserve((User) authentication.getPrincipal(),unitId, Period.between(checkInDate,checkOutDate).getDays(),checkIn,checkOut);
        return "";
    }

    @PostMapping("/cancel/{resId}")
    public String cancelReservation(@PathVariable long resId)
    {
        reservationService.cancel(resId);
        return "redirect:/reserve";
    }

}
