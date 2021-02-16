package com.hvt.booking_lux.service.impl;

import com.hvt.booking_lux.enumeration.Role;
import com.hvt.booking_lux.exceptions.InvalidUsernameOrPasswordException;
import com.hvt.booking_lux.exceptions.PasswordNotMatchException;
import com.hvt.booking_lux.exceptions.UsernameAlreadyExistsException;
import com.hvt.booking_lux.model.User;
import com.hvt.booking_lux.repository.UserRepository;
import com.hvt.booking_lux.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User register(String username, String password, String repeatPassword, String firstName, String lastName) {
        if(username == null || username.isEmpty() || password==null || password.isEmpty()){
            throw new InvalidUsernameOrPasswordException();
        }
        if(!password.equals(repeatPassword))
            throw new PasswordNotMatchException();

        if(this.userRepository.findById(username).isPresent())
            throw new UsernameAlreadyExistsException(username);

        User user = new User(username, passwordEncoder.encode(password), firstName, lastName, Role.USER);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findById(s).orElseThrow(() -> new UsernameNotFoundException(s));
        return user;
    }
}