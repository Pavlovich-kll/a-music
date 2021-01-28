package com.musicapp.validation.annotation;

import com.musicapp.validation.validator.SamePasswordPatchValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Аннотация для проверки совпадения пароля при смене пароля пользователем
 *
 * @author r.zamoiski
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = SamePasswordPatchValidator.class)
public @interface SamePasswordPatch {

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
