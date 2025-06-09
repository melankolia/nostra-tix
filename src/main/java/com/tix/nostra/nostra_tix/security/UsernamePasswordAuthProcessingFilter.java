package com.tix.nostra.nostra_tix.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tix.nostra.nostra_tix.dto.LoginRequestDTO;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UsernamePasswordAuthProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private final UsernamePasswordAuthSuccessHandler successHandler;
    private final UsernamePasswordAuthFailureHandler failureHandler;
    private final ObjectMapper objectMapper;

    protected UsernamePasswordAuthProcessingFilter(String defaultFilterProcessesUrl, ObjectMapper objectMapper,
            UsernamePasswordAuthSuccessHandler successHandler, UsernamePasswordAuthFailureHandler failureHandler) {
        super(defaultFilterProcessesUrl);
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        LoginRequestDTO dto = objectMapper.readValue(request.getReader(), LoginRequestDTO.class);

        // Validasi
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dto.username(),
                dto.password());
        return this.getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        // TODO Auto-generated method stub
        super.successfulAuthentication(request, response, chain, authResult);

        // SecurityContext ctx = SecurityContextHolder.createEmptyContext();
        // ctx.setAuthentication(authResult);
        // SecurityContextHolder.setContext(ctx);

        // chain.doFilter(request, response);

        this.successHandler.onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        // TODO Auto-generated method stub
        super.unsuccessfulAuthentication(request, response, failed);

        this.failureHandler.onAuthenticationFailure(request, response, failed);
    }

}
