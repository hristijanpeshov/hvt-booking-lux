package com.hvt.booking_lux.web;

import com.hvt.booking_lux.model.exceptions.InvalidArgumentException;
import com.hvt.booking_lux.model.exceptions.PasswordNotMatchException;
import com.hvt.booking_lux.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;


    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model){
        if(error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        return "register";
    }

    @PostMapping
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String repeatedPassword,
                           @RequestParam String name,
                           @RequestParam String surname){
        try {
            userService.register(username, password, repeatedPassword, name, surname);
            return "redirect:/login";
        }
        catch (InvalidArgumentException | PasswordNotMatchException ex){
            return "redirect:/register?error=" + ex.getMessage();
        }
    }

}
