package com.musicapp.validation.annotation;

import com.musicapp.validation.validator.EmailVerifiedValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Аннотация для проверки верифицированности e-mail.
 *
 * @author evgeniycheban
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EmailVerifiedValidator.class)
public @interface EmailVerified {

    /**
     * Возвращает валидационное сообщение.
     *
     * @return валидационное сообщение
     */
    String message() default "{user.email.unverified}";

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
