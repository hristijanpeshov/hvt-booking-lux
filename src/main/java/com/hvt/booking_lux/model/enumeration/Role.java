package com.hvt.booking_lux.model.enumeration;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    ROLE_ADMIN,
    ROLE_MODERATOR,
    ROLE_USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
