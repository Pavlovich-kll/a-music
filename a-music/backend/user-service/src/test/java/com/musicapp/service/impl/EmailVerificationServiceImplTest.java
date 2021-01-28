package com.musicapp.service.impl;

import com.musicapp.domain.EmailCode;
import com.musicapp.repository.EmailCodeRepository;
import com.musicapp.security.context.TokenContextHolder;
import com.musicapp.service.TokenService;
import com.musicapp.util.constants.ClaimConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmailVerificationServiceImplTest {

    @Mock
    EmailCodeRepository emailCodeRepositoryMock;
    @Mock
    TokenService tokenServiceMock;
    @InjectMocks
    EmailVerificationServiceImpl emailService;

    private String code;
    private String jwtToken;
    private String email;
    private String unVerifiedEmail;
    private EmailCode emailCode;
    private EmailCode savedInRepoEmailCode;


    @Before
    public void setUp() {
        code = "code";
        email = "email@mail.ru";
        unVerifiedEmail = "wrong@mail.ru";

        emailCode = new EmailCode()
                .setEmail(email)
                .setCode(code);

        savedInRepoEmailCode = new EmailCode()
                .setId(1L)
                .setEmail(email)
                .setCode(code);

        jwtToken = "token";

        Claims claims = Jwts.claims();
        claims.put(ClaimConstants.VERIFIED_EMAIL, email);
        when(tokenServiceMock.generate(claims)).thenReturn(jwtToken);
        when(tokenServiceMock.getClaims(jwtToken)).thenReturn(claims);
    }

    @Test
    public void verify() {
        when(emailCodeRepositoryMock.findByCode(code))
                .thenReturn(Optional.of(savedInRepoEmailCode));

        emailService.verify(code);
        String savedToken = TokenContextHolder.getVerificationToken(email);
        assertEquals(jwtToken, savedToken);
    }

    @Test
    public void isVerified() {
        TokenContextHolder.putVerificationToken(email, jwtToken);

        boolean isVerified = emailService.isVerified(email);
        assertTrue(isVerified);
    }

    @Test
    public void isVerified_when_emailIsNotVerified() {
        boolean isVerified = emailService.isVerified(unVerifiedEmail);
        assertFalse(isVerified);
    }


    @Test
    public void create() {
        when(emailCodeRepositoryMock.save(emailCode)).thenReturn(savedInRepoEmailCode);

        EmailCode createdEmailCode = emailService.create(email, code);
        assertEquals(savedInRepoEmailCode, createdEmailCode);

    }
}