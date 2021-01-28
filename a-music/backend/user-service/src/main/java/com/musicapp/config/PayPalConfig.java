package com.musicapp.config;

import com.musicapp.config.properties.PayPalConfigProperties;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Конфигурация apiContext PayPal.
 */
@Configuration
@EnableConfigurationProperties(PayPalConfigProperties.class)
@RequiredArgsConstructor
public class PayPalConfig {

    private final PayPalConfigProperties payPalConfigProperties;

    @Bean
    public Map<String, String> payPalSdkConfig() {
        Map<String, String> sdkConfig = new HashMap<>();
        sdkConfig.put("mode", payPalConfigProperties.getMode());
        return sdkConfig;
    }

    @Bean
    public OAuthTokenCredential authTokenCredential() {
        return new OAuthTokenCredential(payPalConfigProperties.getClientId(), payPalConfigProperties.getClientSecret(), payPalSdkConfig());
    }

    @Bean
    public APIContext apiContext() throws PayPalRESTException {
        APIContext apiContext = new APIContext(authTokenCredential().getAccessToken());
        apiContext.addConfigurations(payPalSdkConfig());
        return apiContext;
    }
}
