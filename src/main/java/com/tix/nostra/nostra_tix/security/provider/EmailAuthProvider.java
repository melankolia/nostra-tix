package com.tix.nostra.nostra_tix.security.provider;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.tix.nostra.nostra_tix.domain.User;
import com.tix.nostra.nostra_tix.security.model.EmailAuthToken;
import com.tix.nostra.nostra_tix.service.UserService;
import com.tix.nostra.nostra_tix.util.EmailService;

@Component
public class EmailAuthProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    private final EmailService emailService;

    // public EmailAuthProvider(AccountService accountService) {
    // this.accountService = accountService;
    // }
    public EmailAuthProvider(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        EmailAuthToken token = (EmailAuthToken) authentication;
        String email = token.getPrincipal().toString();
        String password = token.getCredentials().toString();

        try {
            User user = userService.sendVerificationCode(email, password);

            if (user.getVerificationCode() == null) {
                throw new RuntimeException("Verification code is null");
            }

            if (user.getExpiredTime().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("Verification code is expired");
            }

            emailService.sendMail(email, "Verification Code",
                    "Your verification code is: " + user.getVerificationCode());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new EmailAuthToken(email, password);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return EmailAuthToken.class.isAssignableFrom(authentication);
    }
}