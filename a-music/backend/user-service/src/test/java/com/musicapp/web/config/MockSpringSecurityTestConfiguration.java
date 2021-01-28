package com.musicapp.web.config;

import com.musicapp.domain.Role;
import com.musicapp.security.AuthorizedUser;
import com.musicapp.security.oauth2.Oauth2SuccessHandler;
import com.musicapp.security.oauth2.Oauth2UserServiceImpl;
import com.musicapp.security.oauth2.OidcUserServiceImpl;
import com.musicapp.service.TokenService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestConfiguration
public class MockSpringSecurityTestConfiguration {

    @MockBean
    private TokenService tokenService;
    @MockBean
    private Oauth2SuccessHandler oauth2SuccessHandler;
    @MockBean
    private OidcUserServiceImpl oidcUserService;
    @MockBean
    private Oauth2UserServiceImpl oauth2UserService;
    @MockBean
    private ClientRegistrationRepository clientRegistrationRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetailsService userDetailsService = mock(UserDetailsService.class);
        when(userDetailsService.loadUserByUsername(anyString()))
                .thenReturn(new AuthorizedUser(1L, "username", Collections.singleton(Role.ROLE_USER), "phoneNumber", "someEmail"));

        return userDetailsService;
    }
}
