package com.musicapp.service.impl;

import com.musicapp.domain.User;
import com.musicapp.dto.UserPasswordPatchDto;
import com.musicapp.exception.NotFoundException;
import com.musicapp.repository.UserRepository;
import com.musicapp.service.TokenService;
import com.musicapp.util.constants.ClaimConstants;
import io.jsonwebtoken.Jwts;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ResetPasswordServiceImplTest {

    @Mock
    UserRepository userRepositoryMock;
    @Mock
    TokenService tokenServiceMock;
    @Mock
    PasswordEncoder encoderMock;
    @InjectMocks
    private ResetPasswordServiceImpl resetPasswordService;

    @Test(expected = NotFoundException.class)
    public void givenUserDoNotExist_whenChangePassword_thenThrowNotFoundException() {
        when(tokenServiceMock.getClaims(anyString()))
                .thenReturn(Jwts.claims(Collections.singletonMap(ClaimConstants.ID, 1L)));

        resetPasswordService.changePassword("", getUserPasswordPatchDto());
    }

    @Test
    public void givenUserExists_whenChangePassword_thenRepositoryInvoked() {
        when(tokenServiceMock.getClaims(anyString()))
                .thenReturn(Jwts.claims(Collections.singletonMap(ClaimConstants.ID, 1L)));
        when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.of(new User()));

        resetPasswordService.changePassword("", getUserPasswordPatchDto());

        verify(userRepositoryMock).save(any());
    }

    private UserPasswordPatchDto getUserPasswordPatchDto() {
        return UserPasswordPatchDto.builder()
                .withOldPassword("password")
                .withNewPassword("password")
                .build();
    }
}