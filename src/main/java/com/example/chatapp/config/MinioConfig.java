package com.example.chatapp.config;

import io.minio.MinioClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {
    private final Logger log = LoggerFactory.getLogger(MinioConfig.class);

    @Value("${minio.url}")
    private String url;

    @Value("${minio.accessKey}")
    private String accessKey;

    @Value("${minio.secretKey}")
    private String secretKey;

    @Bean
    public MinioClient minioClient() {
        try {
            return MinioClient.builder().endpoint(url)
                    .credentials(accessKey, secretKey).build();
        } catch (Exception e) {
            log.error("Error connect MinIO");
            throw e;
        }
    }
}
