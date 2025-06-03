package com.tix.nostra.nostra_tix.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import io.minio.MinioClient;

@ComponentScan(basePackages = "com.tix.nostra.nostra_tix")
@Configuration
public class AppConfig {
    @Bean
    public MinioClient minioClient(MinioProperties properties) {
        MinioClient minioClient = MinioClient.builder()
                .endpoint(properties.getUrl())
                .credentials(properties.getAccessKey(), properties.getSecretKey())
                .build();
        return minioClient;
    }
}
