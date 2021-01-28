package com.musicapp.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicapp.dto.SimpleResponse;
import com.musicapp.exception.NotFoundException;
import com.musicapp.service.EmailVerificationService;
import com.musicapp.web.config.MockSpringSecurityTestConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EmailVerificationController.class)
@Import(MockSpringSecurityTestConfiguration.class)
public class EmailVerificationControllerTest {

    @TestConfiguration
    static class Configuration {

        @Bean
        public MessageSource messageSource() {
            MessageSource messageSourceMock = Mockito.mock(MessageSource.class);
            when(messageSourceMock.getMessage(anyString(), any(), any())).thenReturn("");

            return messageSourceMock;
        }
    }

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmailVerificationService emailVerificationServiceMock;
    @Autowired
    private ObjectMapper objectMapper;

    private String uri;
    private String code;
    private String jwtToken;
    private String tokenDtoProjection;

    @Before
    public void setUp() throws Exception {
        uri = "/verification/email";
        code = "qwerty1234";
        jwtToken = "token";

        tokenDtoProjection = objectMapper.writeValueAsString(SimpleResponse.builder()
                .withPropertyName("token")
                .withPropertyValue(jwtToken)
                .build());
    }

    @Test
    public void checkEmailCode() throws Exception {
        when(emailVerificationServiceMock.verify(anyString())).thenReturn(jwtToken);

        mockMvc.perform(get(uri + "/check-email-code/{code}", code))
                .andExpect(status().isOk())
                .andExpect(content().json(tokenDtoProjection));
    }

    @Test
    public void givenResponse_when_codeIsNotFound() throws Exception {
        doThrow(new NotFoundException("", "")).when(emailVerificationServiceMock).verify(code);

        mockMvc.perform(get(uri + "/check-email-code/{code}", code))
                .andExpect(status().isNotFound());

        verify(emailVerificationServiceMock).verify(code);
    }
}