package com.musicapp.service.impl;

import com.musicapp.domain.EmailCode;
import com.musicapp.exception.NotFoundException;
import com.musicapp.repository.EmailCodeRepository;
import com.musicapp.security.context.TokenContextHolder;
import com.musicapp.service.EmailVerificationService;
import com.musicapp.service.TokenService;
import com.musicapp.util.constants.ClaimConstants;
import com.musicapp.util.constants.MessageConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Реализация сервиса для отправки токена подтверждения на e-mail
 *
 * @author a.nagovicyn
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EmailVerificationServiceImpl implements EmailVerificationService {

    private final JavaMailSender mailSender;
    private final EmailCodeRepository emailCodeRepository;
    private final TokenService tokenService;
    @Value("${activating.uri}")
    private String activatingUri;
    @Value("${spring.mail.username}")
    private String from;

    @Transactional
    @Override
    public void sendCode(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        String code = UUID.randomUUID().toString();
        String link = activatingUri + code;
        create(email, code);

        message.setTo(email);
        message.setSubject("A-music activating code");
        message.setText("Greetings! Please, follow this link to confirm your e-mail:" + "\n" + link);
        message.setFrom(from);

        mailSender.send(message);
        log.info("Message has been sent for email : " + email);
    }

    @Transactional
    @Override
    public String verify(String code) {
        EmailCode emailCode = emailCodeRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException(MessageConstants.EMAIL_CODE_INCORRECT, "code"));
        String email = emailCode.getEmail();
        Claims claims = Jwts.claims();
        claims.put(ClaimConstants.VERIFIED_EMAIL, email);

        String token = tokenService.generate(claims);
        TokenContextHolder.putVerificationToken(email, token);
        emailCode.setCode(null);
        emailCodeRepository.save(emailCode);

        return token;
    }

    @Override
    public boolean isVerified(String email) {
        String token = TokenContextHolder.getVerificationToken(email);

        if (StringUtils.isBlank(token)) {
            return false;
        }

        Claims claims = tokenService.getClaims(token);
        String verifiedEmail = claims.get(ClaimConstants.VERIFIED_EMAIL, String.class);
        boolean isSameEmails = email.equals(verifiedEmail);

        if (isSameEmails) {
            TokenContextHolder.removeVerificationToken(email);
        }

        return isSameEmails;
    }


    @Override
    public EmailCode create(String email, String code) {
        EmailCode emailCode = new EmailCode();
        emailCode.setEmail(email);
        emailCode.setCode(code);
        log.info("Email code has been created for email : " + email);
        return emailCodeRepository.save(emailCode);
    }
}