package com.tix.nostra.nostra_tix.security.filter;

import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;

import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tix.nostra.nostra_tix.security.OTPRequestDTO;
import com.tix.nostra.nostra_tix.security.handler.EmailOtpAuthSuccessHandler;
import com.tix.nostra.nostra_tix.security.model.EmailOtpToken;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class EmailOtpAuthFilter extends AbstractAuthenticationProcessingFilter {

    private final EmailOtpAuthSuccessHandler successHandler;
    private final AuthenticationFailureHandler failedHandler;
    private final ObjectMapper objectMapper;

    public EmailOtpAuthFilter(String defaultFilterProcessesUrl, EmailOtpAuthSuccessHandler successHandler,
            AuthenticationFailureHandler failedHandler, ObjectMapper objectMapper) {
        super(defaultFilterProcessesUrl);
        this.successHandler = successHandler;
        this.failedHandler = failedHandler;
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        OTPRequestDTO otpRequestDto = objectMapper.readValue(request.getReader(), OTPRequestDTO.class);

        if (otpRequestDto.email() == null || otpRequestDto.otp() == null) {
            throw new BadRequestException("Email or OTP is missing");
        }
        EmailOtpToken token = new EmailOtpToken(otpRequestDto.email(),
                otpRequestDto.otp());

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