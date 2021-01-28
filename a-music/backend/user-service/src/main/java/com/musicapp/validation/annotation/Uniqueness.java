package com.musicapp.validation.annotation;

import com.musicapp.validation.UniqueValueChecker;
import com.musicapp.validation.validator.UniquenessValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для валидации уникальности поля
 */
@Constraint(validatedBy = UniquenessValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Uniqueness {

    /**
     * @return класс, бин которого валидатор ищет в контексте и передает ему обязанность проверки уникальности
     */
    Class<? extends UniqueValueChecker> checkerClass();

    /**
     * @return true, если поле должно быть уникально. Если проверяется существование поля (его неуникальность) - false
     */
    boolean expected() default true;

    /**
     * @return валидационное сообщение
     */
    String message();

    /**
     * @return группы валидации
     */
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
