package com.musicapp.service;

import com.musicapp.dto.UserPasswordPatchDto;

/**
 * Интерфейс сервиса для восстановления пароля.
 *
 * @author a.nagovicyn
 */
public interface ResetPasswordService {

    /**
     * Метод изменения пароля при восстановлении
     *
     * @param token          jwt токен
     * @param newPasswordDto dto-представление нового пароля
     */
    void changePassword(String token, UserPasswordPatchDto newPasswordDto);
}
