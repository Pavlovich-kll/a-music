package com.musicapp.security.oauth2.states;

import com.musicapp.domain.Role;
import com.musicapp.domain.Social;
import com.musicapp.domain.User;
import com.musicapp.exception.SocialRegistrationFailedException;
import com.musicapp.exception.SocialRegistrationLoginException;
import com.musicapp.repository.UserRepository;
import com.musicapp.util.constants.MessageConstants;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Класс для аутентификации и регситрации пользователей, авторизованных через соц.сети.
 * Имеет разлинчые стратегии, в завимости от конкретной соц. сети
 *
 * @author a.nagovicyn
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public abstract class SocialUserProcess {

    private final UserRepository repository;
    private final Social socialClientName;

    /**
     * Инициирует полную обработку пользователя, зарегистрированного через сторонний сервис,
     * включая обработку нижеобъявленными методами.
     * В результат возвращает авторизованного в текущем приложении пользователя. В случае, если
     * пользователь не был зарегистрирован, метод регистрирует пользователя.
     *
     * @param user авторизованный через протокол OAuth2 пользователь
     * @return авторизованный в текущем приложении пользователь
     */
    public OAuth2User startProcess(OAuth2User user) {
        Map<String, Object> attributes = user.getAttributes();
        String login = (String) attributes.get("email");

        if (!StringUtils.isEmpty(login)) {
            return checkAuthByEmail(user);
        }

        login = (String) attributes.get("login");

        if (StringUtils.isEmpty(login)) {
            throw new SocialRegistrationFailedException(MessageConstants.USER_LOGIN_EMPTY);
        }

        return checkAuthByLogin(user);
    }

    /**
     * Метод создает нового пользователя, если его не существует.
     * Пользователь отмечается, как зарегистрированный через соотвествующую соц. сеть.
     *
     * @param user авторизованный через протокол OAuth2 пользователь
     * @return авторизованный в текущем приложении пользователь
     */
    public abstract OAuth2User createNewUser(OAuth2User user);

    /**
     * Метод для проверки аутентификации пользователя через email
     *
     * @param user авторизованный через протокол OAuth2 пользователь
     * @return авторизованный в текущем приложении пользователь
     */
    public OAuth2User checkAuthByEmail(OAuth2User user) {
        Map<String, Object> attributes = user.getAttributes();
        String email = (String) attributes.get("email");

        if (StringUtils.isEmpty(email)) {
            throw new SocialRegistrationLoginException(MessageConstants.USER_LOGIN_EMPTY);
        }

        if (repository.existsByEmailAndSocial(email, socialClientName)) {
            return user;
        }

        if (repository.existsByEmail(email)) {
            throw new SocialRegistrationFailedException(MessageConstants.USER_LOGIN_EXISTS);
        }

        return this.createNewUser(user);
    }

    /**
     * Метод для проверки аутентификации пользователя через login сервиса авторизации
     *
     * @param user авторизованный через протокол OAuth2 пользователь
     * @return авторизованный в текущем приложении пользователь
     */
    public OAuth2User checkAuthByLogin(OAuth2User user) {
        Map<String, Object> attributes = user.getAttributes();
        String login = (String) attributes.get("login");

        if (StringUtils.isEmpty(login)) {
            throw new SocialRegistrationLoginException(MessageConstants.USER_LOGIN_EMPTY);
        }

        if (repository.existsByUsernameAndSocial(login, socialClientName)) {
            return user;
        }

        if (repository.existsByUsername(login)) {
            throw new SocialRegistrationFailedException(MessageConstants.USER_LOGIN_EXISTS);
        }

        return this.createNewUser(user);
    }

    protected User createUserWithDefaultSettings() {
        User newUser = new User();
        newUser.setRole(Role.ROLE_USER);
        newUser.setEnabled(true);
        return newUser;
    }
}
