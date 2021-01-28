package com.musicapp.web.controller;

import com.musicapp.dto.EmailDto;
import com.musicapp.dto.SimpleResponse;
import com.musicapp.service.EmailVerificationService;
import com.musicapp.validation.sequence.EmailSequence;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Методы для верификации e-mail.
 *
 * @author a.nagovicyn
 */
@Controller
@RequestMapping("/verification/email")
@RequiredArgsConstructor
@CrossOrigin
public class EmailVerificationController {

    private final EmailVerificationService emailService;

    /**
     * Метод для отправки ссылки с кодом подтверждения на почту для еще не существующего пользователя
     *
     * @param emailDto dto представление e-mail
     */
    @PostMapping("/send-code")
    public ResponseEntity<Void> sendCode(@Validated(EmailSequence.class) @RequestBody EmailDto emailDto) {
        emailService.sendCode(emailDto.getEmail());

        return ResponseEntity.ok().build();
    }

    /**
     * Метод для проверки кода подтверждения.
     *
     * @param code, полученный по e-mail
     * @return ответ с jwt токеном
     */
    @GetMapping("/check-email-code/{code}")
    public ResponseEntity<SimpleResponse> checkEmailCode(@PathVariable String code) {
        return ResponseEntity.ok(SimpleResponse.builder()
                .withPropertyName("token")
                .withPropertyValue(emailService.verify(code))
                .build());
    }
}
