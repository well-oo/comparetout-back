package com.pepit.compareTout.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN, ROLE_SUPPLIER;

    public String getAuthority() {
        return name();
    }

}
