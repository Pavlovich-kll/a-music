package com.musicapp.web.controller;

import com.musicapp.domain.PhoneVerificationType;
import com.musicapp.dto.PhoneCodeDto;
import com.musicapp.dto.PhoneDto;
import com.musicapp.dto.SimpleResponse;
import com.musicapp.security.context.TokenContextHolder;
import com.musicapp.service.PhoneVerificationService;
import com.musicapp.validation.sequence.PhoneCodeSequence;
import com.musicapp.validation.sequence.PhoneSequence;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Методы для верификации номера телефона.
 *
 * @author evgeniycheban
 */
@Controller
@RequestMapping("/verification/phone")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class PhoneVerificationController {

    private final PhoneVerificationService phoneVerificationService;

    /**
     * Метод для отправки кода подтверждения на номер телефона.
     *
     * @param phoneDto dto номера телефона пользователя
     * @param type     тип отправки кода подтверждения
     */
    @PostMapping(value = "/{type}/send-code", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> sendCode(@Validated(PhoneSequence.class) @RequestBody PhoneDto phoneDto,
                                         @PathVariable PhoneVerificationType type) {

        phoneVerificationService.sendCode(phoneDto.getPhone(), type);

        return ResponseEntity.ok().build();
    }

    /**
     * Метод для проверки номера телефона и кода подтверждения.
     *
     * @param phoneCodeDto dto номера телефона и кода подтверждения
     * @return ответ с jwt токеном
     */
    @PostMapping(value = "/check-phone-code")
    public ResponseEntity<SimpleResponse> checkPhoneCode(@Validated(PhoneCodeSequence.class) @RequestBody PhoneCodeDto phoneCodeDto) {
        log.debug("Verified phone: {}", phoneCodeDto.getPhone());

        return ResponseEntity.ok(SimpleResponse.builder()
                .withPropertyName("token")
                .withPropertyValue(TokenContextHolder.getVerificationToken(phoneCodeDto.getPhone()))
                .build());
    }
}
