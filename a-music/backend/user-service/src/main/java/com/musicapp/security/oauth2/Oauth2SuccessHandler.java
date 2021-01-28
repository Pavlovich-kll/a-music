package com.musicapp.security.oauth2;

import com.musicapp.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Обработчик успешной аутентификации
 */
@Component
@RequiredArgsConstructor
public class Oauth2SuccessHandler implements AuthenticationSuccessHandler {

    private final TokenService tokenService;
    @Value("${spring.security.oauth2.successUri}")
    private String successUri;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2User principal = (OAuth2User) authentication.getPrincipal();
        String token = tokenService.generate(principal);
        response.sendRedirect(successUri + token);
    }
}
