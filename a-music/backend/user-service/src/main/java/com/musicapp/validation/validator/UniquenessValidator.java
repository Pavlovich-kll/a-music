package com.musicapp.validation.validator;

import com.musicapp.validation.UniqueValueChecker;
import com.musicapp.validation.annotation.Uniqueness;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * Класс-валидатор для проверки уникальности поля
 * Ищет бин переданного аннотацией класса в контексте и вызывает у него соответствующий метод
 */
@RequiredArgsConstructor
public class UniquenessValidator implements ConstraintValidator<Uniqueness, Object> {

    private final ApplicationContext applicationContext;
    private Class<? extends UniqueValueChecker> checkerClass;
    private boolean expected;

    @Override
    public void initialize(Uniqueness constraintAnnotation) {
        checkerClass = constraintAnnotation.checkerClass();
        expected = constraintAnnotation.expected();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        return Objects.isNull(value) ||
                Objects.equals(expected, applicationContext.getBean(checkerClass).isUnique(value));
    }
}
