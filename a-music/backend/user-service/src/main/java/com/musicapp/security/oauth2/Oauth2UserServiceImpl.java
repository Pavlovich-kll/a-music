package com.musicapp.security.oauth2;

import com.musicapp.domain.Social;
import com.musicapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

/**
 * Реализация сервиса для получения авторизованного пользователя через соц. сети.
 *
 * @author a.nagovicyn
 */
@Component
@RequiredArgsConstructor
public class Oauth2UserServiceImpl extends DefaultOAuth2UserService {

    private final UserRepository repository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        String clientName = userRequest.getClientRegistration().getClientName();
        OAuth2User user = super.loadUser(userRequest);
        return Social.processOAuth2User(user, clientName, repository);
    }
}
