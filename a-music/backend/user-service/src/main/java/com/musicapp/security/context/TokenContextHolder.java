package com.musicapp.security.context;

import lombok.experimental.UtilityClass;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Thread local хранилище для jwt токена.
 *
 * @author evgeniycheban
 */
@UtilityClass
public class TokenContextHolder {

    private final ThreadLocal<String> authenticationTokenStorage = new ThreadLocal<>();
    private final ConcurrentHashMap<String, String> verificationTokenStorage = new ConcurrentHashMap<>();

    /**
     * Возвращает jwt токен аутентификации из хранилища.
     *
     * @return jwt токен
     */
    public String getAuthenticationToken() {
        return authenticationTokenStorage.get();
    }

    /**
     * Сохраняет jwt токен аутентификации  в хранилище.
     *
     * @param token jwt токен
     */
    public void setAuthenticationToken(String token) {
        authenticationTokenStorage.set(token);
    }


    /**
     * Получить jwt токен из хранилища
     *
     * @param key номер телефона или email
     * @return jwt токен
     */
    public String getVerificationToken(String key) {
        return verificationTokenStorage.get(key);
    }


    /**
     * Сохранить jwt токен в хранилище
     *
     * @param key номер телефона или email
     * @param token jwt токен
     */
    public void putVerificationToken(String key, String token) {
        verificationTokenStorage.put(key, token);
    }

    /**
     * Удалить jwt токен из хранилища
     *
     * @param key номер телефона или email
     */
    public void removeVerificationToken(String key) {
        verificationTokenStorage.remove(key);
    }
}
