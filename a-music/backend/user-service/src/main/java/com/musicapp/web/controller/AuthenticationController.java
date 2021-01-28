package com.musicapp.web.controller;

import com.musicapp.dto.AuthenticationRequestDto;
import com.musicapp.dto.SimpleResponse;
import com.musicapp.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Методы для аутентификации.
 *
 * @author evgeniycheban
 */
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthenticationController {

    private final AuthenticationService service;

    /**
     * Метод аутентификации.
     *
     * @param request dto запрос аутентификации
     * @return ответ с jwt токеном
     */
    @PostMapping
    public ResponseEntity<SimpleResponse> authenticate(@Valid @RequestBody AuthenticationRequestDto request) {
        return ResponseEntity.ok(getResponseWithToken(service.authenticate(request.getUsername(),
                request.getPassword())));
    }

    /**
     * @deprecated заменен на {@link #getToken(String)}
     * Но запрос вида auth/get-token/ пойдет сразу в метод {@link #getToken(String)}!
     */
    @Deprecated
    @GetMapping("/get-token/{token}")
    public ResponseEntity<SimpleResponse> getTokenDeprecated(@PathVariable String token) {
        return getToken(token);
    }

    /**
     * Метод для получения jwt токена после авторизации через протокол OAuth2,
     * см. {@link com.musicapp.security.oauth2.Oauth2SuccessHandler}
     *
     * @param token сгенерированный после авторизации
     * @return dto сущность токена
     */
    @GetMapping("/{token}")
    public ResponseEntity<SimpleResponse> getToken(@PathVariable String token) {
        return ResponseEntity.ok(getResponseWithToken(token));
    }

    private SimpleResponse getResponseWithToken(String token) {
        return SimpleResponse.builder()
                .withPropertyName("token")
                .withPropertyValue(token)
                .build();
    }
}
