package com.tix.nostra.nostra_tix.security.provider;

import java.util.Collection;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UsernamePasswordAuthProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // Proses Login
        // Service buat Login
        UserDetails user = new UserDetails() {
            @Override
            public String getUsername() {
                // TODO Auto-generated method stub
                return "Ageng";
            }

            @Override
            public String getPassword() {
                return "123456";
            }

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }
        };
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // TODO Auto-generated method stub
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
