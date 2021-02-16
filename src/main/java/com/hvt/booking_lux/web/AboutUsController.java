package com.hvt.booking_lux.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/about")
public class AboutUsController {
    @GetMapping
    public String getAboutPage(Model model){
        return "";
    }
}