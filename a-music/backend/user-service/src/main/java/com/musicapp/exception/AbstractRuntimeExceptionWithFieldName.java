package com.musicapp.exception;

import lombok.Getter;

/**
 * Базовый класс для исключений содержащих в себе указание поля
 * Используется для интерпритации сообщения и названия поля при обработки исключений-потомков
 */
@Getter
public abstract class AbstractRuntimeExceptionWithFieldName extends RuntimeException {

    private final String fieldName;

    AbstractRuntimeExceptionWithFieldName(String message, String fieldName) {
        super(message);
        this.fieldName = fieldName;
    }
}
