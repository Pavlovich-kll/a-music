package com.musicapp.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Параметры конфигурации ImageService.
 */
@ConfigurationProperties("images")
@Getter
@Setter
public class ImageServiceProperties {

    private String directoryPath;
}
