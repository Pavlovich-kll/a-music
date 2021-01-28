package com.musicapp.validation.annotation;

import com.musicapp.validation.validator.SamePasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Аннотация для проверки совпадения пароля.
 *
 * @author evgeniycheban
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = SamePasswordValidator.class)
public @interface SamePassword {

    /**
     * Возвращает валидационное сообщение.
     *
     * @return валидационное сообщение
     */
    String message() default "{user.repeatPassword.ne}";

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
