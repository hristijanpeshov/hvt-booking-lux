package com.hvt.booking_lux.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contact")
public class ContactController {
    @GetMapping
    public String getContactPage(Model model){
        model.addAttribute("bodyContent", "contact");
        return "master-template";
    }
}
