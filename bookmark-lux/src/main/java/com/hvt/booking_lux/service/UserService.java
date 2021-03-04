package com.hvt.booking_lux.service;

import com.hvt.booking_lux.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends UserDetailsService {

    User register(String username, String password, String repeatPassword, String firstName, String lastName);
    User edit(String username,String firstName,String lastName,String email);
    User changePassword(String username,String password,String repeatPassword);
}
