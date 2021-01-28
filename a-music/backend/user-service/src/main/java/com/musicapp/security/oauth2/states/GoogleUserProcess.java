package com.musicapp.security.oauth2.states;

import com.musicapp.domain.Social;
import com.musicapp.domain.User;
import com.musicapp.repository.UserRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

/**
 * Стратегия для регистрации пользователей, авторизованных через facebook
 *
 * @author a.nagovicyn
 */
public class GoogleUserProcess extends SocialUserProcess {

    public GoogleUserProcess(UserRepository repository) {
        super(repository, Social.GOOGLE);
    }

    public OAuth2User createNewUser(OAuth2User user) {
        UserRepository repository = getRepository();

        Map<String, Object> attributes = user.getAttributes();

        User newUser = createUserWithDefaultSettings();
        newUser.setEmail((String) attributes.get("email"));
        newUser.setUsername((String) attributes.get("name"));
        newUser.setSocial(getSocialClientName());

        repository.save(newUser);

        return user;
    }
}
