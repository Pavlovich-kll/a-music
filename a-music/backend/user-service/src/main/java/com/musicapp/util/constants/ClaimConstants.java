package com.musicapp.util.constants;

import lombok.experimental.UtilityClass;

/**
 * Константы параметров jwt токена.
 *
 * @author evgeniycheban
 */
@UtilityClass
public class ClaimConstants {

    /**
     * Идентификатор пользователя.
     */
    public final String ID = "id";

    /**
     * Имя пользователя.
     */
    public final String USERNAME = "username";

    /**
     * Роли пользователя.
     */
    public final String ROLES = "roles";

    /**
     * Верифицированный номер телефона пользователя.
     */
    public final String VERIFIED_PHONE = "verifiedPhone";

    /**
     * Верифицироанный e-mail
     */
    public final String VERIFIED_EMAIL = "verifiedEmail";
}
