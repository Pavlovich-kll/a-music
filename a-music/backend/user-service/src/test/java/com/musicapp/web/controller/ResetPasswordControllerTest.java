package com.musicapp.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicapp.dto.UserPasswordPatchDto;
import com.musicapp.security.oauth2.Oauth2SuccessHandler;
import com.musicapp.security.oauth2.Oauth2UserServiceImpl;
import com.musicapp.security.context.TokenContextHolder;
import com.musicapp.security.oauth2.OidcUserServiceImpl;
import com.musicapp.service.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ResetPasswordController.class)
@WithMockUser
@MockBean({UserDetailsService.class, UserService.class, TokenService.class,
        PhoneVerificationService.class, ResetPasswordService.class, Oauth2UserServiceImpl.class,
        OidcUserServiceImpl.class, Oauth2SuccessHandler.class, ClientRegistrationRepository.class, EmailVerificationService.class})
public class ResetPasswordControllerTest {


    @Autowired
    private ResetPasswordService passwordService;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    private String uri;
    private String token;
    private UserPasswordPatchDto newPasswordDto;
    private String newPasswordDtoJson;


    @Before
    public void setUp() throws JsonProcessingException {
        uri = "/reset-password";

        token = "token";
        TokenContextHolder.setAuthenticationToken(token);

        newPasswordDto = UserPasswordPatchDto.builder()
                .withNewPassword("NewTestPassword")
                .withRepeatNewPassword("NewTestPassword")
                .build();

        newPasswordDtoJson = mapper.writeValueAsString(newPasswordDto);

    }

    @Test
    public void changePassword() throws Exception {
        doNothing().when(passwordService).changePassword("token", newPasswordDto);

        mvc.perform(post(uri + "/change-password")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(newPasswordDtoJson))
                .andExpect(status().isOk());

        verify(passwordService).changePassword(token, newPasswordDto);
    }
}