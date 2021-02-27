package com.hvt.booking_lux.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class AccountController {

    @GetMapping("/manage")
    public String accountPage(Model model){
        model.addAttribute("bodyContent", "manage-account");
        return "master-template";
    }

    @PostMapping("/manage")
    public void save(){

    }

}
