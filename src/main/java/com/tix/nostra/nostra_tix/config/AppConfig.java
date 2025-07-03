package com.tix.nostra.nostra_tix.config;

import java.security.Key;
import java.util.Properties;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tix.nostra.nostra_tix.security.util.JwtTokenFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.minio.MinioClient;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;

@ComponentScan(basePackages = "com.tix.nostra.nostra_tix")
@Configuration
@OpenAPIDefinition(info = @Info(title = "Nostra Tix API", version = "1.0.0"))
@SecurityScheme(name = "Bearer Authentication", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class AppConfig {

    @Bean
    public SecretKey key() {
        byte[] keyBytes = Decoders.BASE64.decode("QUdFTkcgR0FOVEVORw==QUdFTkcgR0FOVEVORw==QUdFTkcgR0FOVEVORw==");
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Bean
    public MinioClient minioClient(MinioProperties properties) {
        MinioClient minioClient = MinioClient.builder()
                .endpoint(properties.getUrl())
                .credentials(properties.getAccessKey(), properties.getSecretKey())
                .build();
        return minioClient;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public JwtTokenFactory jwtTokenFactory(Key secret) {
        return new JwtTokenFactory(secret);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Properties mailProperties() {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "sandbox.smtp.mailtrap.io");
        prop.put("mail.smtp.port", "587");

        return prop;
    }

    @Bean
    public PasswordAuthentication passwordAuthentication() {
        return new PasswordAuthentication("8ccb79ba485335", "e4c31e44a04480");
    }

    // Dari sini bagaimana dia tau ini yg diinject ke EmailService
    @Bean
    public Session mailSession(
            @Qualifier("mailProperties") Properties mailProperties,
            PasswordAuthentication authentication) {

        Session session = Session.getInstance(mailProperties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return authentication;
            }
        });
        return session;
    }
}
