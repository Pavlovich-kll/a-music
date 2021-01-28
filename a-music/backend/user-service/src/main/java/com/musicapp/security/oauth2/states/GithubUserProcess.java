package com.musicapp.security.oauth2.states;

import com.musicapp.domain.Social;
import com.musicapp.domain.User;
import com.musicapp.repository.UserRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

/**
 * Стратегия для регистрации пользователей, авторизованных через github
 *
 * @author a.nagovicyn
 */
public class GithubUserProcess extends SocialUserProcess {

    public GithubUserProcess(UserRepository repository) {
        super(repository, Social.GITHUB);
    }

    public OAuth2User createNewUser(OAuth2User user) {
        UserRepository repository = getRepository();

        Map<String, Object> attributes = user.getAttributes();

        User newUser = createUserWithDefaultSettings();
        newUser.setUsername((String) attributes.get("login"));
        newUser.setSocial(getSocialClientName());

        repository.save(newUser);

        return user;
    }
}
