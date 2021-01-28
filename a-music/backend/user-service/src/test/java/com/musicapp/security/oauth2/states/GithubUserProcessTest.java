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
public class GithubUserProcessTest {

    @Mock
    private UserRepository userRepositoryMock;
    private OAuth2User user;
    @InjectMocks
    private GithubUserProcess githubUserProcess;

    @Before
    public void setUp() {
        Map<String, Object> loginAttributes = new HashMap<>();
        loginAttributes.put("id", "1");
        loginAttributes.put("name", "Test Bot");
        loginAttributes.put("login", "user");

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        HashSet<SimpleGrantedAuthority> setOfAuthorities = new HashSet<>();
        setOfAuthorities.add(authority);

        Collection<? extends GrantedAuthority> authorities = Collections.unmodifiableSet(setOfAuthorities);

        user = new DefaultOAuth2User(authorities, loginAttributes, "id");
    }

    @Test
    public void createNewUser() {
        assertThat(githubUserProcess.createNewUser(user))
                .isEqualTo(user);
    }
}