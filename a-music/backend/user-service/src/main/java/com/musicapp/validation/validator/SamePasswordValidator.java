package com.musicapp.validation.validator;

import com.musicapp.dto.UserCreateDto;
import com.musicapp.validation.annotation.SamePassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Валидатор для проверки совпадения пароля.
 *
 * @author evgeniycheban
 */
public class SamePasswordValidator implements ConstraintValidator<SamePassword, UserCreateDto> {

    private String message;

    @Override
    public void initialize(SamePassword constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(UserCreateDto userDto, ConstraintValidatorContext context) {
        boolean valid = userDto.getPassword().equals(userDto.getRepeatPassword());
        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(this.message)
                    .addPropertyNode("repeatPassword")
                    .addConstraintViolation();
        }

        return valid;
    }
}
