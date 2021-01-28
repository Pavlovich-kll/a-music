package com.musicapp.service;

import com.musicapp.domain.EmailCode;
import com.musicapp.exception.NotFoundException;

/**
 * Интерфейс сервиса для верификации e-mail.
 *
 * @author a.nagovicyn
 */
public interface EmailVerificationService {

    /**
     * Отправляет ссылку с кодом для подтверждения на почту.
     *
     * @param email e-mail адрес
     */
    void sendCode(String email);

    /**
     * Верифицирует e-mail.
     *
     * @param code код подтверждения, отправленный на почту
     * @return токен
     * @throws NotFoundException в случае, если невеврно указан код подтверждения
     */
    String verify(String code);

    /**
     * Проверяет, что e-mail верифицирован.
     *
     * @param email адрес почты
     * @return true, если e-mail верифицирован
     */
    boolean isVerified(String email);

    /**
     * Сохраняет запись e-mail - код подтверждения
     *
     * @param email адрес пользователя
     * @param code  отправленный на почту код
     * @return сущность созданной записи
     */
    EmailCode create(String email, String code);
}
