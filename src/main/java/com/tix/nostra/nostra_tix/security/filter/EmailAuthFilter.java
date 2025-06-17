package com.tix.nostra.nostra_tix.security.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tix.nostra.nostra_tix.security.LoginRequestDTO;
import com.tix.nostra.nostra_tix.security.handler.EmailSuccessHandler;
import com.tix.nostra.nostra_tix.security.model.EmailAuthToken;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EmailAuthFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper;
    private final EmailSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;

    public EmailAuthFilter(
            String defaultFilterProcessesUrl,
            EmailSuccessHandler successHandler,
            AuthenticationFailureHandler failureHandler,
            ObjectMapper objectMapper) {
        super(defaultFilterProcessesUrl);
        this.objectMapper = objectMapper;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {

            BufferedReader reader = request.getReader();
            String requestBody = reader.lines().collect(Collectors.joining());

            LoginRequestDTO loginRequest = objectMapper.readValue(requestBody, LoginRequestDTO.class);

            if (loginRequest.email() == null || loginRequest.email().trim().isEmpty()) {
                throw new BadCredentialsException("Email is required");
            }

            if (loginRequest.password() == null || loginRequest.password().trim().isEmpty()) {
                throw new BadCredentialsException("Password is required");
            }

            EmailAuthToken authRequest = new EmailAuthToken(loginRequest.email(), loginRequest.password());
            return this.getAuthenticationManager().authenticate(authRequest);
        } catch (IOException e) {
            throw new BadCredentialsException("Invalid request format");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        successHandler.onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        failureHandler.onAuthenticationFailure(request, response, failed);
    }
}