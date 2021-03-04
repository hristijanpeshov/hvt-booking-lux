package com.hvt.booking_lux.web;

import com.hvt.booking_lux.model.User;
import com.hvt.booking_lux.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class AccountController {

    private final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/manage")
    @PreAuthorize("isAuthenticated()")
    public String accountPage(Authentication authentication,Model model, @RequestParam(required = false) String message){
        model.addAttribute("user",(User)authentication.getPrincipal());
        model.addAttribute("message",message);
        model.addAttribute("bodyContent", "manage-account");
        return "master-template";
    }

    @PostMapping("/manage")
    @PreAuthorize("isAuthenticated()")
    public String editAccount(Authentication authentication, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String email){
        User user = userService.edit(authentication.getName(),firstName,lastName,email);
        Authentication auth = new PreAuthenticatedAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        return "redirect:/user/manage";
    }
    @GetMapping("/login")
    public String getLoginPage(Model model,@RequestParam(required = false) String badLogin){
        model.addAttribute("bodyContent", "login");
        model.addAttribute("message",badLogin);
        return "master-template";
    }

}
