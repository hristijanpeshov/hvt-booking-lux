package com.hvt.booking_lux.security;

import com.hvt.booking_lux.model.User;
import com.hvt.booking_lux.model.enumeration.Role;
import com.hvt.booking_lux.service.ReservationObjectService;
import com.hvt.booking_lux.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CreatorCheck {
    private final ReservationObjectService reservationObjectService;
    private final UserService userService;

    public CreatorCheck(ReservationObjectService reservationObjectService, UserService userService) {
        this.reservationObjectService = reservationObjectService;
        this.userService = userService;
    }

    public boolean check(long resObjectId, Authentication authentication)
    {
        if(authentication == null){
            return false;
        }
        User user = null;
        try{
            user = (User) userService.loadUserByUsername(authentication.getName());
        }
        catch(UsernameNotFoundException ex)
        {
            return false;
        }
        return reservationObjectService.findResObjectById(resObjectId).getCreator().equals(user) || user.getAuthorities().contains(Role.ROLE_ADMIN);
    }
}
