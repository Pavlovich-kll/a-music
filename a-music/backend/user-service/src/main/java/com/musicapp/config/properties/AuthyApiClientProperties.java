package com.musicapp.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Параметры конфигурации Authy клиента.
 *
 * @author evgeniycheban
 */
@ConfigurationProperties("authy")
@Getter
@Setter
public class AuthyApiClientProperties {

    private String apiKey;
}
