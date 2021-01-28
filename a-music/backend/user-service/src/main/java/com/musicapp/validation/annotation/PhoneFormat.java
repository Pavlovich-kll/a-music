package com.musicapp.validation.annotation;

import com.musicapp.validation.validator.PhoneFormatValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Аннотация для проверки формата номера телефона.
 *
 * @author evgeniycheban
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PhoneFormatValidator.class)
public @interface PhoneFormat {

    /**
     * Возвращает валидационное сообщение.
     *
     * @return валидационное сообщение
     */
    String message() default "{user.phone.format}";

    /**
     * Возвращает группы валидации.
     *
     * @return группы валидации
     */
    Class<?>[] groups() default {};

    /**
     * Возвращает payload.
     *
     * @return payload
     */
    Class<? extends Payload>[] payload() default {};
}
