package com.musicapp.validation.validator;

import com.musicapp.service.EmailVerificationService;
import com.musicapp.validation.annotation.EmailVerified;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class EmailVerifiedValidator implements ConstraintValidator<EmailVerified, String> {

    private final EmailVerificationService emailVerificationService;

    public boolean isValid(String email, ConstraintValidatorContext context) {
        return emailVerificationService.isVerified(email);
    }
}
