package com.hvt.booking_lux.web;

import com.hvt.booking_lux.model.enumeration.Category;
import com.hvt.booking_lux.model.exceptions.InvalidCreatorException;
import com.hvt.booking_lux.model.City;
import com.hvt.booking_lux.model.ResObject;
import com.hvt.booking_lux.model.User;
import com.hvt.booking_lux.service.ReservationObjectService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/accommodation")
public class AccommodationController {

    private final ReservationObjectService reservationObjectService;

    public AccommodationController(ReservationObjectService reservationObjectService) {
        this.reservationObjectService = reservationObjectService;
    }


    @GetMapping
    public String listBySearchParams(@RequestParam(required = false) Long countryId,@RequestParam(required = false) Long cityId, Model model){
        List<ResObject> resObjectList = new ArrayList<>();
        if(countryId!=null && cityId!=null)
        {
            resObjectList = reservationObjectService.listByCity(cityId);

        }else if(countryId!=null)
        {
            resObjectList = reservationObjectService.listByCountry(countryId);
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
