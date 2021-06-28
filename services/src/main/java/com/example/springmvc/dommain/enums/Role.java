package com.example.springmvc.dommain.enums;

import org.springframework.security.core.GrantedAuthority;

/**
 * Enum user roles
 */
public enum Role implements GrantedAuthority {
    USER,ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
