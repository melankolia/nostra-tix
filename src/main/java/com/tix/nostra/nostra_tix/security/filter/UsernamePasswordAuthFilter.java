package com.tix.nostra.nostra_tix.security.filter;

import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tix.nostra.nostra_tix.security.LoginRequestDTO;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UsernamePasswordAuthFilter extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failedHandler;
    private final ObjectMapper objectMapper;

    public UsernamePasswordAuthFilter(String defaultFilterProcessesUrl, AuthenticationSuccessHandler successHandler,
            AuthenticationFailureHandler failedHandler, ObjectMapper objectMapper) {
        super(defaultFilterProcessesUrl);
        this.successHandler = successHandler;
        this.failedHandler = failedHandler;
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        LoginRequestDTO loginDto = objectMapper.readValue(request.getReader(), LoginRequestDTO.class);

        if (loginDto.email() == null || loginDto.password() == null) {
            throw new BadRequestException("Email or password is missing");
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDto.email(),
                loginDto.password());

        return this.getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        this.successHandler.onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        this.failedHandler.onAuthenticationFailure(request, response, failed);
    }
}