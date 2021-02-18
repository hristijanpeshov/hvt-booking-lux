package com.hvt.booking_lux.web;

import com.hvt.booking_lux.model.exceptions.InvalidCreatorException;
import com.hvt.booking_lux.model.ResObject;
import com.hvt.booking_lux.model.Unit;
import com.hvt.booking_lux.service.ReservationObjectService;
import com.hvt.booking_lux.service.UnitService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/accommodation/{resObjectId}/unit")
public class UnitController {
    private final UnitService unitService;
    private final ReservationObjectService reservationObjectService;

    public UnitController(UnitService unitService, ReservationObjectService reservationObjectService) {
        this.unitService = unitService;
        this.reservationObjectService = reservationObjectService;
    }

    @GetMapping("/{unitId}")
    public String getUnit(@PathVariable long resObjectId,@PathVariable long unitId, Model model)
    {
        Unit unit = unitService.findById(unitId);
        model.addAttribute("unit",unit);
        return "";
    }

    @GetMapping("/add")
    public String addForm(Authentication authentication,@PathVariable long resObjectId, Model model)
    {
        ResObject resObject = reservationObjectService.findResObjectById(resObjectId);
        if(!resObject.getCreator().equals(authentication.getPrincipal()))
        {
            throw new InvalidCreatorException();
        }
        model.addAttribute("resObjectId",resObjectId);
        return "";
    }

    @PostMapping("/add")
    public String addUnit(Authentication authentication,@PathVariable long resObjectId, @RequestParam double size,@RequestParam int numberPeople,@RequestParam String description,@RequestParam double price)
    {
        ResObject resObject = reservationObjectService.findResObjectById(resObjectId);
        if(!resObject.getCreator().equals(authentication.getPrincipal()))
        {
            throw new InvalidCreatorException();
        }
        unitService.save(resObjectId, size, numberPeople, price, description);
        return "redirect:/accommodation/"+resObjectId;
    }

    @PostMapping("/delete/{unitId}")
    public String delete(Authentication authentication,@PathVariable long resObjectId,@PathVariable long unitId)
    {
        ResObject resObject = reservationObjectService.findResObjectById(resObjectId);
        if(!resObject.getCreator().equals(authentication.getPrincipal()))
        {
            throw new InvalidCreatorException();
        }
        unitService.delete(unitId);
        return "redirect:/accommodation/"+resObjectId;
    }

    @GetMapping("/edit/{unitId}")
    public String editForm(Authentication authentication,@PathVariable long resObjectId,@PathVariable long unitId, Model model)
    {
        ResObject resObject = reservationObjectService.findResObjectById(resObjectId);
        if(!resObject.getCreator().equals(authentication.getPrincipal()))
        {
            throw new InvalidCreatorException();
        }
        Unit unit = unitService.findById(unitId);
        model.addAttribute("unit",unit);
        return "";
    }

    @PostMapping("/edit/{unitId}")
    public String editUnit(Authentication authentication,@PathVariable long resObjectId,@PathVariable long unitId, @RequestParam double size,@RequestParam int numberPeople,@RequestParam String description,@RequestParam double price)
    {
        ResObject resObject = reservationObjectService.findResObjectById(resObjectId);
        if(!resObject.getCreator().equals(authentication.getPrincipal()))
        {
            throw new InvalidCreatorException();
        }
        unitService.edit(unitId,size,numberPeople,price,description);
        return "redirect:/accommodation/"+resObjectId;
    }
}
