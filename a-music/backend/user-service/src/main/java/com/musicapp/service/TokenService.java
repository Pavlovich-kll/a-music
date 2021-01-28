package com.musicapp.service;

import com.musicapp.security.AuthorizedUser;
import io.jsonwebtoken.Claims;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * Интерфейс сервиса для работы с jwt токеном.
 *
 * @author evgeniycheban
 */
public interface TokenService {

    /**
     * Возвращает авторизованного пользователя из jwt токена.
     *
     * @param token jwt токен
     * @return сущность пользователя
     */
    AuthorizedUser getAuthorizedUser(String token);

    /**
     * Генерирует jwt токен из авторизованного пользователя.
     *
     * @param authorizedUser авторизованный пользователь
     * @return jwt токен
     */
    String generate(AuthorizedUser authorizedUser);

    /**
     * Генерирует jwt токен из авторизованного через соц. сети пользователя.
     *
     * @param authorizedUser авторизованный пользователь
     * @return jwt токен
     */
    String generate(OAuth2User authorizedUser);

    /**
     * Генерирует jwt токен из параметров.
     *
     * @param claims параметры
     * @return jwt токен
     */
    String generate(Claims claims);

    /**
     * Возвращает параметры из jwt токена.
     *
     * @param token jwt токен
     * @return параметры
     */
    Claims getClaims(String token);
}
