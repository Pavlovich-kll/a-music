package com.musicapp.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Параметры конфигурации сервиса для работы с jwt.
 *
 * @author evgeniycheban
 */
@ConfigurationProperties("token")
@Getter
@Setter
public class TokenServiceProperties {

    private String secret;
    private Long expiration;
}
