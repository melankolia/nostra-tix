package com.tix.nostra.nostra_tix.security.model;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private UserDetails userDetails;

    private RawAccessJwtToken rawAccessJwtToken;

    public JwtAuthenticationToken(RawAccessJwtToken rawAccessJwtToken) {
        super(null);
        this.rawAccessJwtToken = rawAccessJwtToken;
        setAuthenticated(false);
    }

    // sudah terauthentikasi

    public JwtAuthenticationToken(UserDetails userDetails,
            Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        super.setAuthenticated(false);
        this.userDetails = userDetails;
        this.setAuthenticated(true);

    }

    @Override
    public Object getCredentials() {
        return this.rawAccessJwtToken;
    }

    @Override
    public Object getPrincipal() {
        return this.userDetails;
    }
}