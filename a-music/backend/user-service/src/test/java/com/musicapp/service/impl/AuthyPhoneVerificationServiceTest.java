package com.musicapp.service.impl;

import com.authy.AuthyApiClient;
import com.authy.api.PhoneVerification;
import com.authy.api.Verification;
import com.musicapp.domain.PhoneVerificationType;
import com.musicapp.exception.PhoneParseException;
import com.musicapp.exception.PhoneVerificationException;
import com.musicapp.security.context.TokenContextHolder;
import com.musicapp.service.TokenService;
import com.musicapp.util.constants.ClaimConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthyPhoneVerificationServiceTest {

    @Mock
    private AuthyApiClient authyApiClientMock;
    @Mock
    private TokenService tokenServiceMock;
    @Mock
    private Verification verificationMock;
    @Mock
    private PhoneVerification phoneVerificationMock;
    @InjectMocks
    private AuthyPhoneVerificationService authyPhoneVerificationService;

    @Test(expected = PhoneParseException.class)
    public void givenPhoneIncorrect_whenSendCode_thenThrowPhoneParseException() {
        authyPhoneVerificationService.sendCode("incorrect phone", PhoneVerificationType.SMS);
    }

    @Test
    public void givenVerificationIsOk_whenSendCode_thenOk() {
        when(authyApiClientMock.getPhoneVerification()).thenReturn(phoneVerificationMock);
        when(phoneVerificationMock.start(anyString(), anyString(), anyString(), any())).thenReturn(verificationMock);
        when(verificationMock.isOk()).thenReturn(true);

        authyPhoneVerificationService.sendCode("+375291111111", PhoneVerificationType.SMS);
    }

    @Test(expected = PhoneVerificationException.class)
    public void givenVerificationIsNotOk_whenSendCode_thenThrowPhoneVerificationException() {
        when(authyApiClientMock.getPhoneVerification()).thenReturn(phoneVerificationMock);
        when(phoneVerificationMock.start(anyString(), anyString(), anyString(), any())).thenReturn(verificationMock);

        authyPhoneVerificationService.sendCode("+375291111111", PhoneVerificationType.SMS);
    }

    @Test(expected = PhoneParseException.class)
    public void givenPhoneIncorrect_whenIsValid_thenThrowPhoneParseException() {
        authyPhoneVerificationService.isValid("incorrect phone", "code");
    }

    @Test
    public void givenVerificationIsOk_whenVerify_thenTokenSet() {
        when(tokenServiceMock.generate(any(Claims.class))).thenReturn("");
        when(authyApiClientMock.getPhoneVerification()).thenReturn(phoneVerificationMock);
        when(phoneVerificationMock.check(anyString(), anyString(), anyString())).thenReturn(verificationMock);
        when(verificationMock.isOk()).thenReturn(true);

        assertTrue(authyPhoneVerificationService.verify("+375291111111", ""));
    }

    @Test
    public void givenVerificationIsNotOk_whenVerify_thenReturnFalse() {
        when(authyApiClientMock.getPhoneVerification()).thenReturn(phoneVerificationMock);
        when(phoneVerificationMock.check(anyString(), anyString(), anyString())).thenReturn(verificationMock);
        when(verificationMock.isOk()).thenReturn(false);

        assertFalse(authyPhoneVerificationService.verify("+375291111111", ""));
        verify(tokenServiceMock, times(0)).generate(any(Claims.class));
    }

    @Test
    public void givenVerificationTokenIsBlank_whenIsVerified_thenReturnFalse() {
        TokenContextHolder.putVerificationToken("", "");

        assertFalse(authyPhoneVerificationService.isVerified(""));
    }

    @Test
    public void given_whenIsVerified_thenReturnFalse() {
        String phone = "phone";
        TokenContextHolder.putVerificationToken(phone, "token");
        when(tokenServiceMock.getClaims(anyString()))
                .thenReturn(Jwts.claims(Collections.singletonMap(ClaimConstants.VERIFIED_PHONE, phone)));

        assertTrue(authyPhoneVerificationService.isVerified(phone));
    }
}