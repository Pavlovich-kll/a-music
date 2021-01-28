package com.musicapp.service.impl;

import com.musicapp.domain.User;
import com.musicapp.dto.*;
import com.musicapp.exception.NotFoundException;
import com.musicapp.mapper.UserMapperImpl;
import com.musicapp.repository.UserRepository;
import com.musicapp.service.CityService;
import com.musicapp.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private CityService cityServiceMock;
    private UserService userService;
    private User testUser;

    @Before
    public void setUp() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        userService = new UserServiceImpl(userRepositoryMock, new UserMapperImpl(), passwordEncoder,
                cityServiceMock);

        testUser = new User()
                .setId(1L)
                .setUsername("username")
                .setPassword(passwordEncoder.encode("password"))
                .setEmail("email");
    }

    @Test
    public void whenCreateUser_thenReturnUserDto() {
        UserCreateDto userCreateDto = getUserCreateDto();

        UserDto userDto = userService.createUser(userCreateDto, user -> {
            //empty
        });

        assertEquals(userCreateDto.getUsername(), userDto.getUsername());
    }

    @Test
    public void whenCheckPhone_thenRepositoryInvoked() {
        userService.checkPhone("");

        verify(userRepositoryMock).existsByPhone(anyString());
    }

    @Test
    public void whenCheckUser_thenRepositoryInvoked() {
        userService.checkUser(1L);

        verify(userRepositoryMock).existsById(anyLong());
    }

    @Test
    public void givenUserExists_whenCheckPasswords_thenReturnTrue() {
        when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.of(testUser));

        assertTrue(userService.checkPasswords(testUser.getId(), getUserPasswordPatchDto()));
    }

    @Test
    public void givenUserDoNotExist_whenCheckPasswords_thenReturnFalse() {
        assertFalse(userService.checkPasswords(testUser.getId(), getUserPasswordPatchDto()));
    }

    @Test
    public void givenPasswordsDoNotMatch_whenCheckPasswords_thenReturnFalse() {
        when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.of(testUser));

        UserPasswordPatchDto invalidPasswordPatchDto = UserPasswordPatchDto.builder()
                .withOldPassword("")
                .build();

        assertFalse(userService.checkPasswords(testUser.getId(), invalidPasswordPatchDto));
    }

    @Test
    public void givenUserExists_whenGetProfile_thenReturnUserDto() {
        when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.of(testUser));

        assertNotNull(userService.getProfile(testUser.getId()));
    }

    @Test(expected = NotFoundException.class)
    public void givenUserDoNotExist_whenGetProfile_thenThrowNotFoundException() {
        userService.getProfile(testUser.getId());
    }

    @Test(expected = NotFoundException.class)
    public void givenUserDoNotExist_whenUpdateUserPatchDto_thenThrowNotFoundException() {
        userService.updateUser(testUser.getId(), UserPatchDto.builder().build());
    }

    @Test(expected = NotFoundException.class)
    public void givenUserDoNotExist_whenUpdateUserPhoneDto_thenThrowNotFoundException() {
        userService.updateUser(testUser.getId(), PhoneDto.builder().build());
    }

    @Test(expected = NotFoundException.class)
    public void givenUserDoNotExist_whenUpdateUserUserPasswordPatchDto_thenThrowNotFoundException() {
        userService.updateUser(testUser.getId(), UserPasswordPatchDto.builder().build());
    }

    @Test
    public void givenUserPatchDto_whenUpdateUser_thenRepositoryInvoked() {
        when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.of(testUser));

        userService.updateUser(testUser.getId(), UserPatchDto.builder().build());

        verify(userRepositoryMock).save(any());
    }

    @Test
    public void givenPhoneDto_whenUpdateUser_thenRepositoryInvoked() {
        when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.of(testUser));

        userService.updateUser(testUser.getId(), PhoneDto.builder().build());

        verify(userRepositoryMock).save(any());
    }

    @Test
    public void givenUserPasswordPatchDto_whenUpdateUser_thenRepositoryInvoked() {
        when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.of(testUser));

        userService.updateUser(testUser.getId(), getUserPasswordPatchDto());

        verify(userRepositoryMock).save(any());
    }

    @Test
    public void givenUserExists_whenGetById_thenReturnUser() {
        when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.of(testUser));

        assertNotNull(userService.getById(testUser.getId()));
    }

    @Test(expected = NotFoundException.class)
    public void givenUserDoNotExist_whenGetById_thenThrowNotFoundException() {
        userService.getById(testUser.getId());
    }

    @Test
    public void whenCheckEmail_thenRepositoryInvoked() {
        userService.checkEmail("");

        verify(userRepositoryMock).existsByEmail(anyString());
    }

    @Test
    public void whenBlockUser_thenRepositoryInvoked() {
        when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.of(testUser));

        userService.blockUser(testUser.getId());

        assertFalse(testUser.isEnabled());
        verify(userRepositoryMock).save(any());
    }

    @Test
    public void whenUnblockUser_thenRepositoryInvoked() {
        when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.of(testUser));

        userService.unblockUser(testUser.getId());

        assertTrue(testUser.isEnabled());
        verify(userRepositoryMock).save(any());
    }

    @Test(expected = NotFoundException.class)
    public void givenUserExists_whenDeleteUser_thenRepositoryInvoked() {
        userService.deleteUser(testUser.getId());
    }

    @Test
    public void givenUserDoNotExist_whenDeleteUser_thenThrowNotFoundException() {
        when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.of(testUser));

        userService.deleteUser(testUser.getId());

        verify(userRepositoryMock).deleteById(anyLong());
    }

    @Test
    public void givenUserWithEmailExists_whenIsUnique_thenReturnFalse() {
        when(userRepositoryMock.count(any(Specification.class))).thenReturn(1L);

        assertFalse(userService.isUnique(testUser.getEmail()));
    }

    @Test
    public void givenObjectPassedIsNotString_whenIsUnique_thenReturnFalse() {
        assertFalse(userService.isUnique(new Object()));
    }

    private UserCreateDto getUserCreateDto() {
        return UserCreateDto.builder()
                .withUsername("new username")
                .withPassword("password")
                .build();
    }

    private UserPasswordPatchDto getUserPasswordPatchDto() {
        return UserPasswordPatchDto.builder()
                .withOldPassword("password")
                .withNewPassword("password")
                .build();
    }
}
