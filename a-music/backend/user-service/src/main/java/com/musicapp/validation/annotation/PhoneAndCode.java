package com.musicapp.validation.annotation;

import com.musicapp.validation.validator.PhoneAndCodeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Аннотация для проверки кода подтверждения номера телефона.
 *
 * @author evgeniycheban
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PhoneAndCodeValidator.class)
public @interface PhoneAndCode {

    /**
     * Возвращает валидационное сообщение.
     *
     * @return валидационное сообщение
     */
    String message() default "{user.phoneCode.incorrect}";

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
