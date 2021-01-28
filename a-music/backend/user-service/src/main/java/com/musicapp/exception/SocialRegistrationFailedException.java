package com.musicapp.exception;

/**
 * Генерируется в случае ошибок при попытке добавления нового пользователя, который уже зарегистрирован.
 *
 * @author a.nagovicyn
 */
public class SocialRegistrationFailedException extends RuntimeException {

    public SocialRegistrationFailedException(String message) {
        super(message);
    }
}
