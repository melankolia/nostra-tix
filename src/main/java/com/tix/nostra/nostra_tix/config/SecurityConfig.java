package com.tix.nostra.nostra_tix.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tix.nostra.nostra_tix.security.UsernamePasswordAuthFailureHandler;
import com.tix.nostra.nostra_tix.security.UsernamePasswordAuthProcessingFilter;
import com.tix.nostra.nostra_tix.security.UsernamePasswordAuthSuccessHandler;
import com.tix.nostra.nostra_tix.security.provider.UsernamePasswordAuthProvider;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UsernamePasswordAuthProvider authProvider;

    @Bean
    public AuthenticationFailureHandler usernamePasswordAuthFailureHandler(ObjectMapper objectMapper) {
        return new UsernamePasswordAuthFailureHandler(objectMapper);
    }

    @Bean
    public AuthenticationSuccessHandler usernamePasswordAuthSuccessHandler(ObjectMapper objectMapper) {
        return new UsernamePasswordAuthSuccessHandler(objectMapper);
    }

}
