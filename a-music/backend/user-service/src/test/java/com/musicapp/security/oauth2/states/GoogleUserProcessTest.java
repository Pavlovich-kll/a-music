package com.musicapp.security.oauth2.states;

import com.musicapp.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class GoogleUserProcessTest {

    @Mock
    private UserRepository userRepositoryMock;
    private OAuth2User user;
    @InjectMocks
    private GoogleUserProcess googleUserProcess;

    @Before
    public void setUp() {
        Map<String, Object> emailAttributes = new HashMap<>();
        emailAttributes.put("id", "1");
        emailAttributes.put("name", "Test Bot");
        emailAttributes.put("email", "example@mail.ru");

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        HashSet<SimpleGrantedAuthority> setOfAuthorities = new HashSet<>();
        setOfAuthorities.add(authority);

        Collection<? extends GrantedAuthority> authorities = Collections.unmodifiableSet(setOfAuthorities);

        user = new DefaultOAuth2User(authorities, emailAttributes, "id");
    }

    @Test
    public void createNewUser() {
        assertThat(googleUserProcess.createNewUser(user))
                .isEqualTo(user);
    }
}