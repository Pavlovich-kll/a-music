package com.musicapp.security.oauth2.states;

import com.musicapp.exception.SocialRegistrationFailedException;
import com.musicapp.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SocialUserProcessTest {

    @Mock
    private UserRepository userRepositoryMock;
    private SocialUserProcess socialUserProcess;
    private OAuth2User emailUser;
    private OAuth2User loginUser;

    @Before
    public void setUp() {
        socialUserProcess = new FacebookUserProcess(userRepositoryMock);

        Map<String, Object> emailAttributes = new HashMap<>();
        emailAttributes.put("id", "1");
        emailAttributes.put("name", "Test Bot");
        emailAttributes.put("email", "example@mail.ru");

        Map<String, Object> loginAttributes = new HashMap<>();
        loginAttributes.put("id", "1");
        loginAttributes.put("name", "Test Bot");
        loginAttributes.put("login", "user");

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        HashSet<SimpleGrantedAuthority> setOfAuthorities = new HashSet<>();
        setOfAuthorities.add(authority);

        Collection<? extends GrantedAuthority> authorities = Collections.unmodifiableSet(setOfAuthorities);

        emailUser = new DefaultOAuth2User(authorities, emailAttributes, "id");
        loginUser = new DefaultOAuth2User(authorities, loginAttributes, "id");
    }

    @Test
    public void processWhenEmailAuthenticated() {
        when(userRepositoryMock.existsByEmailAndSocial(any(), any())).thenReturn(true);

        assertThat(socialUserProcess.startProcess(emailUser))
                .isEqualTo(emailUser);
    }

    @Test
    public void processWhenLoginAuthenticated() {
        when(userRepositoryMock.existsByUsernameAndSocial(any(), any())).thenReturn(true);

        assertThat(socialUserProcess.startProcess(loginUser))
                .isEqualTo(loginUser);
    }

    @Test
    public void checkAuthByEmailWhenAuthenticated() {
        when(userRepositoryMock.existsByEmailAndSocial(any(), any())).thenReturn(true);

        assertThat(socialUserProcess.checkAuthByEmail(emailUser))
                .isEqualTo(emailUser);
    }

    @Test
    public void checkAuthByEmailWhenNotAuthenticatedAndAlreadyExists() {
        when(userRepositoryMock.existsByEmailAndSocial(any(), any())).thenReturn(false);
        when(userRepositoryMock.existsByEmail(any())).thenReturn(true);

        assertThatThrownBy(() -> socialUserProcess.checkAuthByEmail(emailUser))
                .isInstanceOf(SocialRegistrationFailedException.class);
    }

    @Test
    public void checkAuthByLoginAuthenticated() {
        when(userRepositoryMock.existsByUsernameAndSocial(any(), any())).thenReturn(true);

        assertThat(socialUserProcess.checkAuthByLogin(loginUser))
                .isEqualTo(loginUser);
    }

    @Test
    public void checkAuthByLoginAuthenticatedAndAlreadyExists() {
        when(userRepositoryMock.existsByUsernameAndSocial(any(), any())).thenReturn(false);
        when(userRepositoryMock.existsByUsername(any())).thenReturn(true);

        assertThatThrownBy(() -> socialUserProcess.checkAuthByLogin(loginUser))
                .isInstanceOf(SocialRegistrationFailedException.class);
    }
}