package com.musicapp.security.oauth2;

import com.musicapp.domain.Social;
import com.musicapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

/**
 * Реализация сервиса для получения авторизованного пользователя через соц. сети., предоставляющих данные по OIDC
 *
 * @author a.nagovicyn
 */
@Component
@RequiredArgsConstructor
public class OidcUserServiceImpl extends OidcUserService {

    private final UserRepository repository;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) {
        String clientName = userRequest.getClientRegistration().getClientName();
        OAuth2User user = super.loadUser(userRequest);
        return (OidcUser) Social.processOAuth2User(user, clientName, repository);
    }
}
