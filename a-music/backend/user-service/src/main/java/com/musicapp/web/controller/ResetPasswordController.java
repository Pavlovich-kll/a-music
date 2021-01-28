package com.musicapp.web.controller;

import com.musicapp.dto.EmailDto;
import com.musicapp.dto.UserPasswordPatchDto;
import com.musicapp.security.context.TokenContextHolder;
import com.musicapp.service.EmailVerificationService;
import com.musicapp.service.ResetPasswordService;
import com.musicapp.validation.sequence.EmailFormatSequence;
import com.musicapp.validation.sequence.SamePasswordWithNewPasswordNotBlankSequence;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Методы для восстановления пароля.
 *
 * @author a.nagovicyn
 */
@Controller
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/reset-password")
@Slf4j
public class ResetPasswordController {

    private final ResetPasswordService resetPasswordService;
    private final EmailVerificationService emailService;

    /**
     * Метод для отправки ссылки с кодом подтверждения на почту для уже существующего пользователя
     *
     * @param emailDto dto представление e-mail
     */
    @PostMapping("/send-code")
    public ResponseEntity<Void> sendCode(@Validated(EmailFormatSequence.class) @RequestBody EmailDto emailDto) {
        emailService.sendCode(emailDto.getEmail());

        return ResponseEntity.ok().build();
    }

    /**
     * Метод для изменения пароля при восстановлении
     *
     * @param newPassword dto сущности пароля пользователя с новыми данными
     */
    @PostMapping("/change-password")
    public ResponseEntity<Void> changeResettingPassword(@Validated(SamePasswordWithNewPasswordNotBlankSequence.class)
                                                        @RequestBody UserPasswordPatchDto newPassword) {

        resetPasswordService.changePassword(TokenContextHolder.getAuthenticationToken(), newPassword);

        return ResponseEntity.ok().build();
    }
}
