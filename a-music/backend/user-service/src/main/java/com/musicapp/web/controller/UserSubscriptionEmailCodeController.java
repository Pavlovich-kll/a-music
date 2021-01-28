package com.musicapp.web.controller;

import com.musicapp.service.UserSubscriptionEmailCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-subscription-email-code")
@RequiredArgsConstructor
public class UserSubscriptionEmailCodeController {

    private final UserSubscriptionEmailCodeService userSubscriptionEmailCodeService;

    /**
     * Метод для проверки кода подтверждения.
     *
     * @param code, полученный по e-mail
     * @return ответ с jwt токеном
     */
    @GetMapping("/{code}")
    public ResponseEntity checkEmailCode(@PathVariable String code) {
        userSubscriptionEmailCodeService.verifyByCodeAndSave(code);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
