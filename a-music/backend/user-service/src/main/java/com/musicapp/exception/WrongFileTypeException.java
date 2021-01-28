package com.musicapp.exception;

/**
 * Выбрасывается при попытке загрузить файл с неподдерживаемым расширением
 */
public class WrongFileTypeException extends AbstractRuntimeExceptionWithFieldName {

    public WrongFileTypeException(String message, String fieldName) {
        super(message, fieldName);
    }
}
