package com.musicapp.service.impl;

import com.musicapp.domain.Role;
import com.musicapp.security.AuthorizedUser;
import com.musicapp.service.TokenService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import java.util.Collections;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManagerMock;
    @Mock
    private TokenService tokenService;
    @Mock
    private Authentication authenticationMock;
    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Test
    public void whenAuthenticate_thenReturnToken() {
        when(authenticationManagerMock.authenticate(any())).thenReturn(authenticationMock);
        when(authenticationMock.getPrincipal())
                .thenReturn(new AuthorizedUser(1L, "username", Collections.singleton(Role.ROLE_USER),"phoneNumber", "someEmail"));
        when(tokenService.generate(any(AuthorizedUser.class))).thenReturn("");

        assertNotNull(authenticationService.authenticate("", ""));
    }
}