package com.tix.nostra.nostra_tix.config;

import java.security.Key;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tix.nostra.nostra_tix.security.util.JwtTokenFactory;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.minio.MinioClient;

@ComponentScan(basePackages = "com.tix.nostra.nostra_tix")
@Configuration
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
}
