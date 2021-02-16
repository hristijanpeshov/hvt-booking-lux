package com.hvt.booking_lux.service;

import com.hvt.booking_lux.enumeration.Role;
import com.hvt.booking_lux.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User register(String username, String password, String repeatPassword, String firstName, String lastName);
}