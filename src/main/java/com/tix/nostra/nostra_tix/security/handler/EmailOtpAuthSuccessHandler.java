package com.tix.nostra.nostra_tix.security.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tix.nostra.nostra_tix.security.model.AccessJwtToken;
import com.tix.nostra.nostra_tix.security.util.JwtTokenFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EmailOtpAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;

    private final JwtTokenFactory jwtTokenFactory;

    public EmailOtpAuthSuccessHandler(ObjectMapper objectMapper, JwtTokenFactory jwtTokenFactory) {
        super();
        this.objectMapper = objectMapper;
        this.jwtTokenFactory = jwtTokenFactory;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        AccessJwtToken token = jwtTokenFactory.createAccessJwtToken(userDetails.getUsername(),
                userDetails.getAuthorities());

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("result", "success");
        responseBody.put("token", token.getToken());
        responseBody.put("email", userDetails.getUsername());
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        objectMapper.writeValue(response.getWriter(), responseBody);
    }
}
