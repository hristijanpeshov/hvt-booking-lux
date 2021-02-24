package com.hvt.booking_lux.web;

import com.hvt.booking_lux.model.enumeration.Category;
import com.hvt.booking_lux.model.City;
import com.hvt.booking_lux.model.ResObject;
import com.hvt.booking_lux.model.User;
import com.hvt.booking_lux.security.CreatorCheck;
import com.hvt.booking_lux.service.ReservationObjectService;
import com.hvt.booking_lux.service.ReservationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Controller
@RequestMapping("/accommodation")
public class AccommodationController {

    private final ReservationObjectService reservationObjectService;
    private final CreatorCheck creatorCheck;
    private final ReservationService reservationService;

    public AccommodationController(ReservationObjectService reservationObjectService, CreatorCheck creatorCheck,ReservationService reservationService) {
        this.reservationObjectService = reservationObjectService;
        this.creatorCheck = creatorCheck;
        this.reservationService = reservationService;
    }


    @GetMapping
    public String listBySearchParams(HttpServletRequest request, @RequestParam(required = false) String city, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkInDate, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkOutDate, @RequestParam(required = false) Integer numPeople , Model model){
        List<ResObject> resObjectList = null;
        if(city!=null && checkInDate != null && checkOutDate!=null && numPeople!=null)
        {
            ZonedDateTime checkIn = ZonedDateTime.of(checkInDate, LocalTime.parse("00:00"), ZoneId.systemDefault());
            ZonedDateTime checkOut = ZonedDateTime.of(checkOutDate, LocalTime.parse("00:00"), ZoneId.systemDefault());
            request.getSession().setAttribute("cityName",city);
            request.getSession().setAttribute("checkIn",checkIn);
            request.getSession().setAttribute("checkOut",checkOut);
            request.getSession().setAttribute("numPeople",numPeople);
//            resObjectList = reservationObjectService.listByCityName(city);
            if(checkOut.isBefore(checkIn)){
                return "redirect:/home?error=Check in date should be before check out date!";
            }
            reservationObjectService.listAllAvailableUnitsForResObject(2, checkIn, checkOut, numPeople);
            resObjectList = reservationObjectService.findAllAvailable(checkIn, checkOut, numPeople, city);
        }
        else{
            resObjectList = reservationObjectService.listAll();
        }
        model.addAttribute("searchForm","searchForm");
        model.addAttribute("reservationObjects", resObjectList);
        model.addAttribute("bodyContent", "rooms");
        return "master-template";
    }

    @GetMapping("/myListings")
    public String getMyListings(Authentication authentication,Model model)
    {
        List<ResObject>  resObjects = reservationObjectService.listUserAccommodationListings((User)authentication.getPrincipal());
        model.addAttribute("resObjects",resObjects);
        model.addAttribute("bodyContent","myListings");
        return "master-template";
    }

    @GetMapping("/{resObjectId}")
    public String getSpecificAccommodation(@PathVariable long resObjectId, Model model, HttpServletRequest request)
    {
        ResObject resObject = reservationObjectService.findResObjectById(resObjectId);
        model.addAttribute("resObject", resObject);
        ZonedDateTime fromDate = (ZonedDateTime) request.getSession().getAttribute("checkIn");
        ZonedDateTime toDate = (ZonedDateTime) request.getSession().getAttribute("checkOut");
        int numPeople = (int) request.getSession().getAttribute("numPeople");
        model.addAttribute("units", reservationObjectService.listAllAvailableUnitsForResObject(resObjectId, fromDate, toDate, numPeople));
        model.addAttribute("bodyContent", "AccommodationDetails");
        return "master-template";
    }

    @GetMapping("/add")
    public String addForm(Model model){
        model.addAttribute("bodyContent", "add-accommodation");
        model.addAttribute("edit",false);
        return "master-template";
    }

    @PostMapping("/add")
    public String save(Model model,Authentication authentication, @RequestParam String name, @RequestParam String address, @RequestParam String description, @RequestParam Category category, @RequestParam long cityId)
    {
        reservationObjectService.save(name,address,description,category, (User) authentication.getPrincipal(),cityId);
        return "redirect:/accommodation";
    }

    @GetMapping("/edit/{resObjectId}")
    @PreAuthorize("@creatorCheck.check(#resObjectId,authentication)")
    public String edit(Authentication authentication,@PathVariable long resObjectId, Model model)
    {
        ResObject resObject = reservationObjectService.findResObjectById(resObjectId);
        model.addAttribute("reservationObject",resObject);
        model.addAttribute("edit",true);
        model.addAttribute("bodyContent","add-accommodation");
        return "master-template";
    }

    @PostMapping("/edit/{resObjectId}")
    @PreAuthorize("@creatorCheck.check(#resObjectId,authentication)")
    public String edit(Model model,Authentication authentication,@PathVariable long resObjectId, @RequestParam String name, @RequestParam String address, @RequestParam String description, @RequestParam Category category)
    {
        ResObject resObject = reservationObjectService.findResObjectById(resObjectId);
        reservationObjectService.edit(resObjectId,name,address,description,category);
        return "redirect:/accommodation";
    }

    @PostMapping("/delete/{resObjectId}")
    @PreAuthorize("@creatorCheck.check(#resObjectId,authentication)")
    public String delete(@PathVariable long resObjectId)
    {
        reservationObjectService.delete(resObjectId);
        return "redirect:/accommodation";
    }
}
