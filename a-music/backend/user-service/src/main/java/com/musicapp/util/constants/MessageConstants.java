package com.musicapp.util.constants;

import lombok.experimental.UtilityClass;

/**
 * Константы сообщений об ошибках.
 *
 * @author a.nagovicyn
 */
@UtilityClass
public class MessageConstants {

    /**
     * Сообщение об отсутствии логина пользователя при регистрации
     */
    public static final String USER_LOGIN_EMPTY = "oauth2.user.login.empty";

    /**
     * Сообщение о занятом логине пользователя при регистрации
     */
    public static final String USER_LOGIN_EXISTS = "oauth2.user.alreadyExists";

    /**
     * Сообщение о том, что введенный и повторенный пароль не совпадают
     */
    public static final String USER_REPEAT_PASSWORD_NOT_MATCH = "user.repeatPassword.ne";

    /**
     * Сообщение об отсутствующем в базе email коде
     */
    public static final String EMAIL_CODE_INCORRECT = "emailCode.code.incorrect";

    /**
     * Сообщение об отсутствии заявки с таким статусом
     */
    public static final String INVITE_STATUS_NOT_FOUND = "friendInvite.status.notFound";

    /**
     * Сообщение об отсутствии заявки в друзья пользователю
     */
    public static final String INVITE_ACCEPTOR_NOT_FOUND = "friendInvite.acceptor.notFound";

    /**
     * Сообщение об отсутствии заявки в друзья от пользователю
     */
    public static final String INVITE_INITIATOR_NOT_FOUND = "friendInvite.initiator.notFound";

    /**
     * Сообщение об отсуствии пользователя с таким id
     */
    public static final String USER_ID_NOT_FOUND = "user.id.notFound";

    /**
     * Сообщение об отсуствии концерта с таким id
     */
    public static final String CONCERT_ID_NOT_FOUND = "concert.id.notFound";

    /**
     * Сообщение об отсутствии билета с таким id
     */
    public static final String TICKET_ID_NOT_FOUND = "ticket.id.notFound";

    /**
     * Сообщение о неправильном формате загружаемого файла
     */
    public static final String FILE_TYPE_NOT_SUPPORTED = "image.format.notSupported";

    /**
     * Сообщение об отсуствии города с таким id
     */
    public static final String CITY_ID_NOT_FOUND = "city.id.notFound";

    /**
     * Сообщение об отсуствии страны с таким id
     */
    public static final String COUNTRY_ID_NOT_FOUND = "country.id.notFound";

    /**
     * Сообщение об отсуствии файла с таким именем
     */
    public static final String FILE_NAME_NOT_FOUND = "file.name.notFound";
    /**
     * Сообщение об отсуствии плейлиста с таким id
     */
    public static final String PLAYLIST_ID_NOT_FOUND = "playlist.id.notFound";

    /**
     * Сообщение об отсуствии подписки с таким id
     */
    public static final String SUBSCRIPTION_ID_NOT_FOUND = "subscription.id.notFound";

    /**
     * Сообщение указывает на отсутствие значения в объекте UserSubscriptionEmailCode
     */
    public static final String USER_SUBSCRIPTION_EMAIL_CODE_NOT_FOUND = "userSubscriptionEmailCode.notFound";

    /**
     * Сообщение об отсуствии аудио с таким id
     */
    public static final String AUDIO_ID_NOT_FOUND = "audio.id.notFound";
}
