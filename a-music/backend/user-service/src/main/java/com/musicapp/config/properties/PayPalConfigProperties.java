package com.musicapp.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Параметры конфигурации PayPal клиента.
 *
 * @author lizavetashpinkova
 */
@ConfigurationProperties("paypal")
@Data
public class PayPalConfigProperties {

    private String clientId;
    private String clientSecret;
    private String mode;
}
