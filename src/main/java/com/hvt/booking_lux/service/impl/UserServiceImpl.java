package com.hvt.booking_lux.service.impl;

import com.hvt.booking_lux.model.enumeration.Role;
import com.hvt.booking_lux.model.exceptions.InvalidUsernameOrPasswordException;
import com.hvt.booking_lux.model.exceptions.PasswordNotMatchException;
import com.hvt.booking_lux.model.exceptions.UsernameAlreadyExistsException;
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

        User user = new User(username, passwordEncoder.encode(password), firstName, lastName, Role.ROLE_USER, "Gjaurska 69", "076 123 456");
        return userRepository.save(user);
    }

    @Override
    public User edit(String username, String firstName, String lastName, String email) {
        User user = (User) loadUserByUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        userRepository.save(user);
        return user;
    }

    @Override
    public User changePassword(String username, String password, String repeatPassword) {
        if(password!=repeatPassword)
        {
            throw new PasswordNotMatchException();
        }
        User user = (User) loadUserByUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findById(s).orElseThrow(() -> new UsernameNotFoundException(s));
        return user;
    }
}
