package com.tix.nostra.nostra_tix.security.model;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class EmailOtpToken extends AbstractAuthenticationToken {
    private final String email;
    private final String otp;

    public EmailOtpToken(String email, String otp) {
        super(null);
        this.email = email;
        this.otp = otp;
        this.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return otp;
    }

    @Override
    public Object getPrincipal() {
        return email;
    }

    public String getEmail() {
        return email;
    }
}