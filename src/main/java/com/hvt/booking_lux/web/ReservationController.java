package com.hvt.booking_lux.web;

import com.hvt.booking_lux.model.Reservation;
import com.hvt.booking_lux.model.Unit;
import com.hvt.booking_lux.model.User;
import com.hvt.booking_lux.service.ReservationService;
import com.hvt.booking_lux.service.UnitService;
import org.h2.api.Interval;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/myReservations")
    public String myReservations(HttpServletRequest request,Authentication authentication, Model model)
    {
        List<Reservation> reservationList = reservationService.findAllReservationsForUser((User) authentication.getPrincipal());
        model.addAttribute("reservations",reservationList);
        model.addAttribute("bodyContent","myReservations");
        return "master-template";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{unitId}")
    public String reserveUnit(@PathVariable long unitId, HttpServletRequest request,Authentication authentication,Model model,@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkInDate, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkOutDate)
    {
        if(checkInDate!=null && checkOutDate!=null)
        {
            ZonedDateTime checkIn = ZonedDateTime.of(checkInDate, LocalTime.parse("00:00"), ZoneId.systemDefault());
            ZonedDateTime checkOut = ZonedDateTime.of(checkOutDate, LocalTime.parse("00:00"), ZoneId.systemDefault());
            request.getSession().setAttribute("checkIn",checkIn);
            request.getSession().setAttribute("checkOut",checkOut);
        }
        ZonedDateTime checkIn = (ZonedDateTime) request.getSession().getAttribute("checkIn");
        ZonedDateTime checkOut = (ZonedDateTime) request.getSession().getAttribute("checkOut");
        Unit unit = unitService.findById(unitId);
        if(checkIn==null || checkOut==null)
        {
            return "redirect:/accommodation/" + unit.getResObject().getId() + "/unit/" + unitId;
        }

        model.addAttribute("unit",unit);
        model.addAttribute("user",(User)authentication.getPrincipal());
        /*if(checkInDate!=null)
        ZonedDateTime checkIn = ZonedDateTime.of(checkInDate, LocalTime.parse("00:00"), ZoneId.systemDefault());
        ZonedDateTime checkOut = ZonedDateTime.of(checkOutDate, LocalTime.parse("00:00"), ZoneId.systemDefault());
        model.addAttribute("checkIn",checkIn);
         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkInDate, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkOutDate
        model.addAttribute("checkOut",checkOut);*/
        model.addAttribute("bodyContent", "confirmReservation");
        return "master-template";
    }
    @PostMapping("/{unitId}")
    @PreAuthorize("isAuthenticated()")
    public String confirmReservation(HttpServletRequest request,Authentication authentication,@PathVariable long unitId)
    {
        ZonedDateTime checkIn = (ZonedDateTime) request.getSession().getAttribute("checkIn");
        ZonedDateTime checkOut = (ZonedDateTime) request.getSession().getAttribute("checkOut");
        reservationService.reserve((User) authentication.getPrincipal(),unitId, Period.between(checkIn.toLocalDate(),checkOut.toLocalDate()).getDays(),checkIn,checkOut);
        return "redirect:/reserve/myReservations";
    }

    @PostMapping("/cancel/{resId}")
    public String cancelReservation(@PathVariable long resId)
    {
        reservationService.cancel(resId);
        return "redirect:/reserve/myReservations";
    }

}
