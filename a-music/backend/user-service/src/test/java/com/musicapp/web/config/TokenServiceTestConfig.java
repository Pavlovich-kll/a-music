package com.musicapp.web.config;

import com.musicapp.service.TokenService;
import com.musicapp.service.impl.TokenServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class TokenServiceTestConfig {

    @Bean
    public TokenService tokenService(){
        return new TokenServiceImpl("secret",800000L);
    }
}
