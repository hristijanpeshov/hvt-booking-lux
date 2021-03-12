package com.hvt.booking_lux.web;

import com.hvt.booking_lux.model.BedTypes;
import com.hvt.booking_lux.model.ResObject;
import com.hvt.booking_lux.model.Unit;
import com.hvt.booking_lux.model.enumeration.BedType;
import com.hvt.booking_lux.security.CreatorCheck;
import com.hvt.booking_lux.service.ReservationObjectService;
import com.hvt.booking_lux.service.UnitService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/accommodation/{resObjectId}/unit")
public class UnitController {
    private final UnitService unitService;
    private final ReservationObjectService reservationObjectService;
    private final CreatorCheck creatorCheck;

    public UnitController(UnitService unitService, ReservationObjectService reservationObjectService, CreatorCheck creatorCheck) {
        this.unitService = unitService;
        this.reservationObjectService = reservationObjectService;
        this.creatorCheck = creatorCheck;
    }

    @GetMapping("/{unitId}")
    public String getUnit(@PathVariable long resObjectId,@PathVariable long unitId, Model model)
    {
        Unit unit = unitService.findById(unitId);
        model.addAttribute("unit",unit);
        model.addAttribute("resObjectId",resObjectId);
        model.addAttribute("bodyContent","unitDetails");
        return "master-template";
    }

    @GetMapping("/add")
    @PreAuthorize("@creatorCheck.check(#resObjectId,authentication)")
    public String addForm(Authentication authentication,@PathVariable long resObjectId, Model model)
    {
        ResObject resObject = reservationObjectService.findResObjectById(resObjectId);
        model.addAttribute("edit",false);
        model.addAttribute("resObjectId",resObjectId);
        model.addAttribute("bodyContent","add-unit");
        return "master-template";
    }

    @PostMapping("/add")
    @PreAuthorize("@creatorCheck.check(#resObjectId,authentication)")
    public String addUnit(Authentication authentication, @PathVariable long resObjectId, @RequestParam double size, @RequestParam int numberPeople, @RequestParam String description, @RequestParam double price, @RequestParam String title,@RequestParam  List<BedType> bedType, @RequestParam List<Integer> count)
    {
        ResObject resObject = reservationObjectService.findResObjectById(resObjectId);
        unitService.save(resObjectId,title, size, numberPeople, price, description,bedType,count);
        return "redirect:/accommodation/"+resObjectId;
    }

    @PostMapping("/delete/{unitId}")
    @PreAuthorize("@creatorCheck.check(#resObjectId,authentication)")
    public String delete(Authentication authentication,@PathVariable long resObjectId,@PathVariable long unitId)
    {
        ResObject resObject = reservationObjectService.findResObjectById(resObjectId);
        unitService.delete(unitId);
        return "redirect:/accommodation/"+resObjectId;
    }

    @GetMapping("/edit/{unitId}")
    @PreAuthorize("@creatorCheck.check(#resObjectId,authentication)")
    public String editForm(Authentication authentication,@PathVariable long resObjectId,@PathVariable long unitId, Model model)
    {
        ResObject resObject = reservationObjectService.findResObjectById(resObjectId);
        Unit unit = unitService.findById(unitId);
        model.addAttribute("edit",true);
        model.addAttribute("unit",unit);
        model.addAttribute("resObjectId",resObjectId);
        model.addAttribute("bodyContent","add-unit");
        return "master-template";
    }

    @PostMapping("/edit/{unitId}")
    @PreAuthorize("@creatorCheck.check(#resObjectId,authentication)")
    public String editUnit(Authentication authentication,@PathVariable long resObjectId, @RequestParam String title,@PathVariable long unitId, @RequestParam double size,@RequestParam int numberPeople,@RequestParam String description,@RequestParam double price,@RequestParam  List<BedType> bedType, @RequestParam List<Integer> count)
    {
        ResObject resObject = reservationObjectService.findResObjectById(resObjectId);
        /*if(!resObject.getCreator().equals(authentication.getPrincipal()))
        {
            throw new InvalidCreatorException();
        }*/
        unitService.edit(unitId,title,size,numberPeople,price,description,bedType,count);
        return "redirect:/accommodation/"+resObjectId;
    }
}
