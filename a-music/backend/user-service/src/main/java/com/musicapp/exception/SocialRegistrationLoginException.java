package com.musicapp.exception;

/**
 * Выбрасывается при проверке аутентификации пользователя, если входные данные некорректны
 */
public class SocialRegistrationLoginException extends RuntimeException {

    public SocialRegistrationLoginException(String message) {
        super(message);
    }
}
