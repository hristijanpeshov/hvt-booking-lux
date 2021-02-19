package com.hvt.booking_lux.web;

import com.hvt.booking_lux.model.enumeration.Category;
import com.hvt.booking_lux.model.exceptions.InvalidCreatorException;
import com.hvt.booking_lux.model.City;
import com.hvt.booking_lux.model.ResObject;
import com.hvt.booking_lux.model.User;
import com.hvt.booking_lux.service.ReservationObjectService;
import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/accommodation")
public class AccommodationController {

    private final ReservationObjectService reservationObjectService;

    public AccommodationController(ReservationObjectService reservationObjectService) {
        this.reservationObjectService = reservationObjectService;
    }


    @GetMapping
    public String listBySearchParams(@RequestParam(required = false) String city, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkInDate, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkOutDate, @RequestParam(required = false) Integer numPeople , Model model){
        List<ResObject> resObjectList = null;
        if(city!=null && checkInDate != null && checkOutDate!=null && numPeople!=null)
        {
            ZonedDateTime checkIn = ZonedDateTime.of(checkInDate, LocalTime.parse("00:00"), ZoneId.systemDefault());
            ZonedDateTime checkOut = ZonedDateTime.of(checkOutDate, LocalTime.parse("00:00"), ZoneId.systemDefault());
            resObjectList = reservationObjectService.listByCityName(city);
        }
        else{
            resObjectList = reservationObjectService.listAll();
        }
        model.addAttribute("reservationObjects",resObjectList);
        model.addAttribute("bodyContent", "rooms");
        return "master-template";
    }

    @GetMapping("/{resObjectId}")
    public String getSpecificAccommodation(@PathVariable long resObjectId,Model model)
    {
        ResObject resObject = reservationObjectService.findResObjectById(resObjectId);
        model.addAttribute("resObject",resObject);
        return "";
    }

    @GetMapping("/add")
    public String addForm(Model model){
        model.addAttribute("bodyContent", "add-accommodation");
        return "master-template";
    }
    @PostMapping("/add")
    public String save(Authentication authentication, @RequestParam String name, @RequestParam String address, @RequestParam String description, @RequestParam Category category, @RequestParam City city)
    {
        reservationObjectService.save(name,address,description,category, (User) authentication.getPrincipal(),city);
        return "redirect:/accommodation";
    }
    @GetMapping("/edit/{resObjectId}")
    public String edit(Authentication authentication,@PathVariable long resObjectId, Model model)
    {
        ResObject resObject = reservationObjectService.findResObjectById(resObjectId);
        if(!resObject.getCreator().equals((User) authentication.getPrincipal()))
        {
            throw new InvalidCreatorException();
        }
        model.addAttribute("reservationObject",resObject);
        return "";
    }
    @PostMapping("/edit/{resObjectId}")
    public String edit(Authentication authentication,@PathVariable long resObjectId, @RequestParam String name, @RequestParam String address, @RequestParam String description, @RequestParam Category category)
    {
        ResObject resObject = reservationObjectService.findResObjectById(resObjectId);
        if(!resObject.getCreator().equals((User) authentication.getPrincipal()))
        {
            throw new InvalidCreatorException();
        }
        reservationObjectService.edit(resObjectId,name,address,description,category,resObject.getCreator(),resObject.getCity());
        return "redirect:/accommodation";
    }
    @PostMapping("/delete/{resObjectId}")
    public String delete(@PathVariable long resObjectId)
    {
        reservationObjectService.delete(resObjectId);
        return "redirect:/accommodation";
    }
}
