package com.musicapp.exception;

/**
 * Генерируется если сущность не найдена.
 *
 * @author evgeniycheban
 */
public class NotFoundException extends AbstractRuntimeExceptionWithFieldName {

    public NotFoundException(String message, String fieldName) {
        super(message, fieldName);
    }
}