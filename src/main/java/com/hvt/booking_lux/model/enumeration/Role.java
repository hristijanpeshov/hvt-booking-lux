package com.hvt.booking_lux.model.enumeration;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    ADMIN,
    MODERATOR,
    USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
