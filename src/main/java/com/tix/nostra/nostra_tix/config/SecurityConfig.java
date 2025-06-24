package com.tix.nostra.nostra_tix.config;

import java.util.Arrays;
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
import com.tix.nostra.nostra_tix.security.filter.EmailAuthFilter;
import com.tix.nostra.nostra_tix.security.filter.EmailOtpAuthFilter;
import com.tix.nostra.nostra_tix.security.filter.JwtAuthFilter;
import com.tix.nostra.nostra_tix.security.filter.UsernamePasswordAuthFilter;
import com.tix.nostra.nostra_tix.security.handler.EmailSuccessHandler;
import com.tix.nostra.nostra_tix.security.handler.EmailOtpAuthSuccessHandler;
import com.tix.nostra.nostra_tix.security.handler.UsernamePasswordAuthFailureHandler;
import com.tix.nostra.nostra_tix.security.handler.UsernamePasswordAuthSuccessHandler;
import com.tix.nostra.nostra_tix.security.provider.EmailAuthProvider;
import com.tix.nostra.nostra_tix.security.provider.EmailOtpAuthProvider;
import com.tix.nostra.nostra_tix.security.provider.JwtAuthProvider;
import com.tix.nostra.nostra_tix.security.provider.UsernamePasswordAuthProvider;
import com.tix.nostra.nostra_tix.security.util.JwtTokenFactory;
import com.tix.nostra.nostra_tix.security.util.SkipPathRequestMatcher;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {
    // TODO: Desain Database (buat table baru OTP, Password dihapus, enkrip password
    // dan enkrip otp)
    // TODO: Validasi OTP tidak menggunakan email tapi pakai session id, ditambahin
    // retry OTP jika gagal dicoba besok lagi
    // TODO: Response email valid dengan engga ketika request OTP harusnya sama

    private final static String AUTH_REGISTER = "/api/auth/register";
    private final static String AUTH_URL = "/api/auth/login";
    private final static String AUTH_EMAIL = "/api/auth/email";
    private final static String AUTH_OTP = "/api/auth/otp";

    private final static List<String> PERMIT_ENDPOINT_URL = List.of(
            "/swagger-ui.html",
            "/swagger-ui/index.html",
            "/swagger-ui/index.css",
            "/favicon.ico",
            "/swagger-ui/swagger-ui.css",
            "/swagger-ui/swagger-ui.css.map",
            "/swagger-ui/swagger-ui-standalone-preset.js",
            "/swagger-ui/swagger-ui-standalone-preset.js.map",
            "/swagger-ui/swagger-ui-bundle.js",
            "/swagger-ui/swagger-ui-bundle.js.map",
            "/swagger-ui/favicon-32x32.png",
            "/swagger-ui/swagger-initializer.js",
            "/v3/api-docs/swagger-config",
            "/v3/api-docs",
            AUTH_URL,
            AUTH_EMAIL,
            AUTH_OTP,
            AUTH_REGISTER);

    private final static String API = "/api/**";
    private final static List<String> AUTH = List.of(API);

    @Autowired
    private JwtAuthProvider jwtAuthProvider;

    @Autowired
    private EmailAuthProvider emailAuthProvider;

    @Autowired
    private UsernamePasswordAuthProvider usernamePasswordAuthProvider;

    @Autowired
    private EmailOtpAuthProvider emailOtpAuthProvider;

    @Autowired
    void registerProvider(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(emailAuthProvider)
                .authenticationProvider(emailOtpAuthProvider)
                .authenticationProvider(usernamePasswordAuthProvider)
                .authenticationProvider(jwtAuthProvider);
    }

    @Bean
    public UsernamePasswordAuthFailureHandler usernamePasswordAuthFailureHandler(ObjectMapper objectMapper) {
        return new UsernamePasswordAuthFailureHandler(objectMapper);
    }

    @Bean
    public UsernamePasswordAuthSuccessHandler usernamePasswordAuthSuccessHandler(ObjectMapper objectMapper,
            JwtTokenFactory jwtTokenFactory) {
        return new UsernamePasswordAuthSuccessHandler(objectMapper, jwtTokenFactory);
    }

    @Bean
    public EmailSuccessHandler emailSuccessHandler(ObjectMapper objectMapper) {
        return new EmailSuccessHandler(objectMapper);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public UsernamePasswordAuthFilter usernamePasswordAuthFilter(ObjectMapper objectMapper,
            UsernamePasswordAuthSuccessHandler successHandler,
            UsernamePasswordAuthFailureHandler failedHandler,
            AuthenticationManager manager) {

        UsernamePasswordAuthFilter filter = new UsernamePasswordAuthFilter(
                AUTH_URL, successHandler, failedHandler, objectMapper);
        filter.setAuthenticationManager(manager);

        return filter;
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter(AuthenticationFailureHandler failureHandler, AuthenticationManager authManager) {
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(PERMIT_ENDPOINT_URL, AUTH);
        JwtAuthFilter filter = new JwtAuthFilter(matcher, failureHandler);
        filter.setAuthenticationManager(authManager);
        return filter;
    }

    @Bean
    public EmailAuthFilter emailAuthFilter(
            ObjectMapper objectMapper,
            EmailSuccessHandler successHandler,
            AuthenticationFailureHandler failureHandler,
            AuthenticationManager manager) {
        EmailAuthFilter filter = new EmailAuthFilter(
                AUTH_EMAIL, successHandler, failureHandler, objectMapper);
        filter.setAuthenticationManager(manager);
        return filter;
    }

    @Bean
    public EmailOtpAuthFilter emailOtpAuthFilter(
            ObjectMapper objectMapper,
            EmailOtpAuthSuccessHandler successHandler,
            AuthenticationFailureHandler failureHandler,
            AuthenticationManager manager) {
        EmailOtpAuthFilter filter = new EmailOtpAuthFilter(
                AUTH_OTP, successHandler, failureHandler, objectMapper);
        filter.setAuthenticationManager(manager);
        return filter;
    }

    @Bean
    public EmailOtpAuthSuccessHandler emailOtpAuthSuccessHandler(ObjectMapper objectMapper,
            JwtTokenFactory jwtTokenFactory) {
        return new EmailOtpAuthSuccessHandler(objectMapper, jwtTokenFactory);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
            UsernamePasswordAuthFilter usernamePasswordAuthFilter,
            JwtAuthFilter jwtAuthFilter,
            EmailAuthFilter emailAuthFilter)
            throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(PERMIT_ENDPOINT_URL.toArray(new String[0])).permitAll()
                .requestMatchers(API).authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((sessionManagement) -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http
                .addFilterBefore(usernamePasswordAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
