package com.musicapp.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Параметры конфигурации Swagger.
 *
 * @author evgeniycheban
 */
@ConfigurationProperties("swagger")
@Getter
@Setter
public class SwaggerConfigProperties {

    private String host;
}
