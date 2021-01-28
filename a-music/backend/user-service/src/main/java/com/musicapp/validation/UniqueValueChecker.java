package com.musicapp.validation;

/**
 * Интерфейс для классов-валидаторов уникальности поля
 */
public interface UniqueValueChecker {

    /**
     * @param value поле для проверки
     * @return true, если поле уникально, то есть не существует записей с ним в базе
     */
    boolean isUnique(Object value);
}
