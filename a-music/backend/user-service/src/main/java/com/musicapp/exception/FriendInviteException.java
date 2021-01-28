package com.musicapp.exception;

/**
 * Выбрасывается при попытке создать приглашение в друзья, если такое приглашение уже существует
 */
public class FriendInviteException extends AbstractRuntimeExceptionWithFieldName {

    public FriendInviteException(String message, String fieldName) {
        super(message, fieldName);
    }
}
