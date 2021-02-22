package com.hvt.booking_lux.web;

import com.hvt.booking_lux.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/user")
public class AccountController {

    private final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/manage")
    public String accountPage(Model model){
        model.addAttribute("bodyContent", "manage-account");
        return "master-template";
    }

    @PostMapping("/manage")
    public String editAccount(Authentication authentication, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String email){
        userService.edit(authentication.getName(),firstName,lastName,email);
        return "";
    }
}
