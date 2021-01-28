package com.musicapp.validation.validator;

import com.musicapp.dto.UserPasswordPatchDto;
import com.musicapp.validation.annotation.SamePasswordPatch;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Валидатор для проверки совпадения паролей при смене пароля пользователем
 *
 * @author r.zamoiski
 */
public class SamePasswordPatchValidator implements ConstraintValidator<SamePasswordPatch, UserPasswordPatchDto> {

    private String message;

    @Override
    public void initialize(SamePasswordPatch constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(UserPasswordPatchDto userPasswordPatchDto,
                           ConstraintValidatorContext constraintValidatorContext) {

        boolean valid = userPasswordPatchDto.getNewPassword().equals(userPasswordPatchDto.getRepeatNewPassword());
        if (!valid) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(this.message)
                    .addPropertyNode("repeatPassword")
                    .addConstraintViolation();
        }

        return valid;
    }
}
