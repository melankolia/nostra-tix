package com.tix.nostra.nostra_tix.security.provider;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.tix.nostra.nostra_tix.domain.User;
import com.tix.nostra.nostra_tix.repository.UserRepository;
import com.tix.nostra.nostra_tix.security.model.EmailOtpToken;

@Component
public class EmailOtpAuthProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        EmailOtpToken token = (EmailOtpToken) authentication;

        User account = userRepository.findByEmailAndVerificationCode(token.getEmail(),
                token.getCredentials().toString());

        if (account == null)
            throw new RuntimeException("Invalid OTP");

        if (account.getExpiredTime().isBefore(LocalDateTime.now()))
            throw new RuntimeException("OTP Expired");

        if (!account.getVerificationCode().equals(authentication.getCredentials().toString()))
            throw new RuntimeException("Invalid OTP");

        List<String> role = List.of("USER");

        UserDetails userDetails = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return role.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
            }

            @Override
            public String getPassword() {
                return account.getPassword();
            }

            @Override
            public String getUsername() {
                return account.getEmail();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return EmailOtpToken.class.isAssignableFrom(authentication);
    }
}