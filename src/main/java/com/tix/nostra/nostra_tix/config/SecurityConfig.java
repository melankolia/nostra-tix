package com.tix.nostra.nostra_tix.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tix.nostra.nostra_tix.security.filter.JwtAuthFilter;
import com.tix.nostra.nostra_tix.security.filter.UsernamePasswordAuthFilter;
import com.tix.nostra.nostra_tix.security.handler.UsernamePasswordAuthFailureHandler;
import com.tix.nostra.nostra_tix.security.handler.UsernamePasswordAuthSuccessHandler;
import com.tix.nostra.nostra_tix.security.provider.JwtAuthProvider;
import com.tix.nostra.nostra_tix.security.provider.UsernamePasswordAuthProvider;
import com.tix.nostra.nostra_tix.security.util.JwtTokenFactory;
import com.tix.nostra.nostra_tix.security.util.SkipPathRequestMatcher;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    private final static String AUTH_URL = "/api/auth/login";
    private final static String AUTH_ALL_LOGIN_PATHS = "/api/auth/login/**";
    private final static String API = "/api/**";

    private final static List<String> PERMS = List.of(AUTH_URL, AUTH_ALL_LOGIN_PATHS);
    private final static List<String> AUTH = List.of(API);

    @Autowired
    private JwtAuthProvider jwtAuthProvider;

    @Autowired
    private UsernamePasswordAuthProvider usernamePasswordAuthProvider;

    @Autowired
    void registerProvider(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(usernamePasswordAuthProvider)
                .authenticationProvider(jwtAuthProvider);
    }

    @Bean
    public AuthenticationFailureHandler usernamePasswordAuthFailureHandler(ObjectMapper objectMapper) {
        return new UsernamePasswordAuthFailureHandler(objectMapper);
    }

    @Bean
    public AuthenticationSuccessHandler usernamePasswordAuthSuccessHandler(ObjectMapper objectMapper,
            JwtTokenFactory jwtTokenFactory) {
        return new UsernamePasswordAuthSuccessHandler(objectMapper, jwtTokenFactory);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public UsernamePasswordAuthFilter usernamePasswordAuthFilter(ObjectMapper objectMapper,
            AuthenticationSuccessHandler successHandler,
            AuthenticationFailureHandler failedHandler,
            AuthenticationManager manager) {

        UsernamePasswordAuthFilter filter = new UsernamePasswordAuthFilter(
                AUTH_URL, successHandler, failedHandler, objectMapper);
        filter.setAuthenticationManager(manager);

        return filter;
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter(AuthenticationFailureHandler failureHandler, AuthenticationManager authManager) {
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(PERMS, AUTH);
        JwtAuthFilter filter = new JwtAuthFilter(matcher, failureHandler);
        filter.setAuthenticationManager(authManager);
        return filter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
            UsernamePasswordAuthFilter usernamePasswordAuthFilter, JwtAuthFilter jwtAuthFilter)
            throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(AUTH_ALL_LOGIN_PATHS).permitAll()
                .requestMatchers(AUTH_URL).permitAll()
                .requestMatchers(API).authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((sessionManagement) -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(usernamePasswordAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
