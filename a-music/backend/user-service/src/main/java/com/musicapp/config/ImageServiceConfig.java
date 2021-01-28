package com.musicapp.config;

import com.musicapp.config.properties.ImageServiceProperties;
import com.musicapp.service.ImageService;
import com.musicapp.service.ImageVerificationService;
import com.musicapp.service.impl.ImageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация ImageService
 */
@Configuration
@EnableConfigurationProperties(ImageServiceProperties.class)
@RequiredArgsConstructor
public class ImageServiceConfig {

    private final ImageServiceProperties imageServiceProperties;

    @Bean
    public ImageService imageService(ImageVerificationService imageVerificationService) {
        return new ImageServiceImpl(imageVerificationService, imageServiceProperties.getDirectoryPath());
    }
}
