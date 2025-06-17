package com.tix.nostra.nostra_tix.security.model;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class EmailAuthToken extends AbstractAuthenticationToken {
    private final String email;
    private final String password;

    public EmailAuthToken(String email, String password) {
        super(null);
        this.email = email;
        this.password = password;
        this.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return password;
    }

    @Override
    public Object getPrincipal() {
        return email;
    }

    public String getEmail() {
        return email;
    }
}