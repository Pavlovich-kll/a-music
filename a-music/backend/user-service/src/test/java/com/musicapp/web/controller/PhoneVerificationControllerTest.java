package com.musicapp.web.controller;

import com.musicapp.dto.PhoneCodeDto;
import com.musicapp.dto.PhoneDto;
import com.musicapp.security.context.TokenContextHolder;
import com.musicapp.service.PhoneVerificationService;
import com.musicapp.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PhoneVerificationController.class)
public class PhoneVerificationControllerTest extends AbstractControllerTest {

    private static final String SEND_CODE_SMS_ENDPOINT = "/verification/phone/SMS/send-code";
    private static final String CHECK_PHONE_CODE_ENDPOINT = "/verification/phone/check-phone-code";

    @MockBean
    private PhoneVerificationService phoneVerificationServiceMock;
    @MockBean
    private UserService userServiceMock;

    @Test
    public void givenPhoneIncorrect_whenSendCode_thenStatusBadRequest() throws Exception {
        when(userServiceMock.isUnique(any())).thenReturn(true);

        performPost(SEND_CODE_SMS_ENDPOINT,
                PhoneDto.builder()
                        .withPhone("incorrect phone")
                        .build())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenPhoneNotUnique_whenSendCode_thenStatusBadRequest() throws Exception {
        performPost(SEND_CODE_SMS_ENDPOINT, getValidPhoneDto())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenValidPhoneDto_whenSendCode_thenStatusOk() throws Exception {
        when(userServiceMock.isUnique(any())).thenReturn(true);

        performPost(SEND_CODE_SMS_ENDPOINT, getValidPhoneDto())
                .andExpect(status().isOk());

        verify(phoneVerificationServiceMock).sendCode(anyString(), any());
    }

    @Test
    public void givenPhoneIncorrect_whenCheckPhoneCode_thenStatusBadRequest() throws Exception {
        when(phoneVerificationServiceMock.verify(anyString(), anyString())).thenReturn(true);

        performPost(CHECK_PHONE_CODE_ENDPOINT,
                PhoneCodeDto.builder()
                        .withPhone("incorrect phone")
                        .withCode("")
                        .build())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenPhoneAndCodeAreNotVerified_whenCheckPhoneCode_thenStatusBadRequest() throws Exception {
        performPost(CHECK_PHONE_CODE_ENDPOINT, getValidPhoneCodeDto())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenValidPhoneCodeDto_whenCheckPhoneCode_thenReturnToken() throws Exception {
        String token = "token";
        PhoneCodeDto validPhoneCodeDto = getValidPhoneCodeDto();
        TokenContextHolder.putVerificationToken(validPhoneCodeDto.getPhone(), token);
        when(phoneVerificationServiceMock.verify(anyString(), anyString())).thenReturn(true);

        performPost(CHECK_PHONE_CODE_ENDPOINT, validPhoneCodeDto)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(is(token)));
    }

    private PhoneDto getValidPhoneDto() {
        return PhoneDto.builder()
                .withPhone("+375291111111")
                .build();
    }

    private PhoneCodeDto getValidPhoneCodeDto() {
        return PhoneCodeDto.builder()
                .withPhone("+375291111111")
                .withCode("")
                .build();
    }
}