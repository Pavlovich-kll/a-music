package com.musicapp.web.controller;

import com.musicapp.dto.*;
import com.musicapp.service.EmailVerificationService;
import com.musicapp.service.PhoneVerificationService;
import com.musicapp.service.UserService;
import com.musicapp.service.UserStreamService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest extends AbstractControllerTest {

    @TestConfiguration
    static class Configuration {

        @Bean
        public MessageSource messageSource() {
            MessageSource messageSourceMock = Mockito.mock(MessageSource.class);
            when(messageSourceMock.getMessage(anyString(), any(), any())).thenReturn("");

            return messageSourceMock;
        }
    }

    private static final String USER_BY_ID_ENDPOINT = "/users/1";
    private static final String UPDATE_USER_BY_ID_ENDPOINT = "/users/update/1";
    private static final String UPDATE_PASSWORD_ENDPOINT = "/users/update-password";
    private static final String UPDATE_PHONE_ENDPOINT = "/users/update-phone";
    private static final String CREATE_BY_PHONE_ENDPOINT = "/users/create-by-phone";
    private static final String CREATE_BY_EMAIL_ENDPOINT = "/users/create-by-email";
    private static final String CHECK_EMAIL_ENDPOINT = "/users/check-email";
    private static final String CHECK_PHONE_ENDPOINT = "/users/check-phone";
    private static final String BLOCK_USER_ENDPOINT = "/users/block-user";
    private static final String UNBLOCK_USER_ENDPOINT = "/users/unblock-user";

    @MockBean
    private UserService userServiceMock;
    @MockBean
    private UserStreamService userStreamServiceMock;
    @MockBean
    private PhoneVerificationService phoneVerificationServiceMock;
    @MockBean
    private EmailVerificationService emailVerificationServiceMock;
    private UserDto testUserDto;

    @Before
    public void createTestUserDto() {
        testUserDto = UserDto.builder()
                .withId(1L)
                .withUsername("username")
                .build();
    }

    @Test
    public void givenPhoneIsNotCorrect_whenCreateByPhone_thenStatusBadRequest() throws Exception {
        performPost(CREATE_BY_PHONE_ENDPOINT,
                UserCreateDto.builder()
                        .withEmail("email")
                        .withPhone("incorrect phone")
                        .withUsername("username")
                        .withPassword("password")
                        .withRepeatPassword("password")
                        .build())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenPhoneIsNotVerified_whenCreateByPhone_thenStatusBadRequest() throws Exception {
        performPost(CREATE_BY_PHONE_ENDPOINT, getValidUserCreateDto())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenUsernameIsBlank_whenCreateByPhone_thenStatusBadRequest() throws Exception {
        when(phoneVerificationServiceMock.isVerified(anyString())).thenReturn(true);

        performPost(CREATE_BY_PHONE_ENDPOINT,
                UserCreateDto.builder()
                        .withEmail("email")
                        .withPhone("+375291111111")
                        .withPassword("password")
                        .withRepeatPassword("password")
                        .build())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenPasswordIsBlank_whenCreateByPhone_thenStatusBadRequest() throws Exception {
        when(phoneVerificationServiceMock.isVerified(anyString())).thenReturn(true);

        performPost(CREATE_BY_PHONE_ENDPOINT,
                UserCreateDto.builder()
                        .withEmail("email")
                        .withPhone("+375291111111")
                        .withUsername("username")
                        .build())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenPasswordIsTooShort_whenCreateByPhone_thenStatusBadRequest() throws Exception {
        when(phoneVerificationServiceMock.isVerified(anyString())).thenReturn(true);

        performPost(CREATE_BY_PHONE_ENDPOINT,
                UserCreateDto.builder()
                        .withEmail("email")
                        .withPhone("+375291111111")
                        .withUsername("username")
                        .withPassword("pass")
                        .withRepeatPassword("pass")
                        .build())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenPasswordsDoNotMatch_whenCreateByPhone_thenStatusBadRequest() throws Exception {
        when(phoneVerificationServiceMock.isVerified(anyString())).thenReturn(true);

        performPost(CREATE_BY_PHONE_ENDPOINT,
                UserCreateDto.builder()
                        .withEmail("email")
                        .withPhone("+375291111111")
                        .withUsername("username")
                        .withPassword("password")
                        .withRepeatPassword("password2")
                        .build())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenValidUserCreateDto_whenCreateByPhone_thenStatusCreated() throws Exception {
        when(phoneVerificationServiceMock.isVerified(anyString())).thenReturn(true);

        performPost(CREATE_BY_PHONE_ENDPOINT, getValidUserCreateDto())
                .andExpect(status().isCreated());
    }

    @Test
    public void givenEmailIsNotCorrect_whenCreateByEmail_thenStatusBadRequest() throws Exception {
        when(phoneVerificationServiceMock.isVerified(anyString())).thenReturn(true);

        performPost(CREATE_BY_EMAIL_ENDPOINT, UserCreateDto.builder()
                .withEmail("email")
                .withPhone("+375291111111")
                .withUsername("username")
                .withPassword("password")
                .withRepeatPassword("password")
                .build())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenEmailIsNotVerified_whenCreateByEmail_thenStatusBadRequest() throws Exception {
        when(phoneVerificationServiceMock.isVerified(anyString())).thenReturn(true);

        performPost(CREATE_BY_EMAIL_ENDPOINT, getValidUserCreateDto())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenValidUserCreateDto_whenCreateByEmail_thenStatusCreated() throws Exception {
        when(phoneVerificationServiceMock.isVerified(anyString())).thenReturn(true);
        when(emailVerificationServiceMock.isVerified(anyString())).thenReturn(true);

        performPost(CREATE_BY_EMAIL_ENDPOINT, getValidUserCreateDto())
                .andExpect(status().isCreated());
    }

    @Test
    public void givenValidEmailDto_whenCheckEmail_thenReturnTrue() throws Exception {
        when(userServiceMock.checkEmail(anyString())).thenReturn(true);

        performPost(CHECK_EMAIL_ENDPOINT, getValidEmailDto())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exists").value(is(true), Boolean.class));
    }

    @Test
    public void givenValidPhoneDto_whenCheckPhone_thenReturnTrue() throws Exception {
        when(userServiceMock.checkPhone(anyString())).thenReturn(true);

        performPost(CHECK_PHONE_ENDPOINT, getValidPhoneDto())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exists").value(is(true), Boolean.class));
    }

    @Test
    public void whenGetProfile_thenReturnTestUserDto() throws Exception {
        when(userServiceMock.getProfile(anyLong())).thenReturn(testUserDto);

        performGet(USER_BY_ID_ENDPOINT)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(is(testUserDto.getId()), Long.class));
    }

    @Test
    public void whenUpdate_thenStatusOk() throws Exception {
        when(userServiceMock.updateUser(anyLong(), any(UserPatchDto.class))).thenReturn(testUserDto);

        performPatch(UPDATE_USER_BY_ID_ENDPOINT, UserPatchDto.builder().build())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(is(testUserDto.getId()), Long.class));
    }

    @Test
    @WithUserDetails
    public void givenPasswordsMatch_whenUpdatePassword_thenStatusOk() throws Exception {
        when(userServiceMock.checkPasswords(anyLong(), any())).thenReturn(true);
        when(userServiceMock.updateUser(anyLong(), any(UserPasswordPatchDto.class))).thenReturn(testUserDto);

        performPatch(UPDATE_PASSWORD_ENDPOINT, getUserPasswordPatchDto())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(is(testUserDto.getId()), Long.class));
    }

    @Test
    @WithUserDetails
    public void givenPasswordsDoNotMatch_whenUpdatePassword_thenStatusNotFound() throws Exception {
        performPatch(UPDATE_PASSWORD_ENDPOINT,
                UserPasswordPatchDto.builder()
                        .withOldPassword("password")
                        .withNewPassword("new password")
                        .withRepeatNewPassword("new password2")
                        .build())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails
    public void whenUpdatePhone_thenStatusOk() throws Exception {
        when(phoneVerificationServiceMock.isVerified(anyString())).thenReturn(true);
        when(userServiceMock.updateUser(anyLong(), any(PhoneDto.class))).thenReturn(testUserDto);

        performPatch(UPDATE_PHONE_ENDPOINT, getValidPhoneDto())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(is(testUserDto.getId()), Long.class));
    }

    @Test
    public void givenRoleUser_whenBlockUser_thenStatusForbidden() throws Exception {
        performPost(BLOCK_USER_ENDPOINT, testUserDto.getId())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenRoleAdmin_whenBlockUser_thenStatusOk() throws Exception {
        performPost(BLOCK_USER_ENDPOINT, testUserDto.getId())
                .andExpect(status().isOk());

        verify(userServiceMock).blockUser(anyLong());
    }

    @Test
    public void givenRoleUser_whenUnblockUser_thenStatusForbidden() throws Exception {
        performPost(UNBLOCK_USER_ENDPOINT, testUserDto.getId())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenRoleAdmin_whenUnblockUser_thenStatusOk() throws Exception {
        performPost(UNBLOCK_USER_ENDPOINT, testUserDto.getId())
                .andExpect(status().isOk());

        verify(userServiceMock).unblockUser(anyLong());
    }

    @Test
    public void whenDeleteUser_thenStatusForbidden() throws Exception {
        performDelete(USER_BY_ID_ENDPOINT)
                .andExpect(status().isOk());

        verify(userServiceMock).deleteUser(anyLong());
    }

    private UserCreateDto getValidUserCreateDto() {
        return UserCreateDto.builder()
                .withEmail("email@gmail.com")
                .withPhone("+375291111111")
                .withUsername("username")
                .withPassword("password")
                .withRepeatPassword("password")
                .build();
    }

    private EmailDto getValidEmailDto() {
        return EmailDto.builder()
                .withEmail("email@gmail.com")
                .build();
    }

    private PhoneDto getValidPhoneDto() {
        return PhoneDto.builder()
                .withPhone("+375291111111")
                .build();
    }

    private UserPasswordPatchDto getUserPasswordPatchDto() {
        return UserPasswordPatchDto.builder()
                .withOldPassword("password")
                .withNewPassword("new password")
                .withRepeatNewPassword("new password")
                .build();
    }
}