package com.musicapp.domain;

import com.musicapp.exception.SocialRegistrationFailedException;
import com.musicapp.repository.UserRepository;
import com.musicapp.security.oauth2.states.FacebookUserProcess;
import com.musicapp.security.oauth2.states.GithubUserProcess;
import com.musicapp.security.oauth2.states.GoogleUserProcess;
import com.musicapp.security.oauth2.states.SocialUserProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.function.Function;

/**
 * Перечисление возможностей авторизации через сторонние сервисы
 */
@RequiredArgsConstructor
public enum Social {

    GOOGLE(GoogleUserProcess::new),

    GITHUB(GithubUserProcess::new),

    FACEBOOK(FacebookUserProcess::new);

    private final Function<UserRepository, SocialUserProcess> strategy;

    /**
     * Метод возвращает нужную стратегию для обработки авторизованного через сторонний сервис пользователя
     *
     * @param repository репозиторий, через который проходит обработка
     * @return стратегия обработки
     */
    public SocialUserProcess getStrategy(UserRepository repository) {
        return strategy.apply(repository);
    }

    /**
     * Метод определяет сторонний сервис авторизации пользователя, устанавливает соответсвующую стратегию обработки
     * и запускает процесс обработки
     *
     * @param oAuth2User авторизованный через сторонние сервисы пользователь
     * @param clientName наименование сервиса авторизации
     * @param repository репозиторий, в который необходимо сохранить пользователя
     * @return авторизованный в текущем приложении пользователь
     */
    public static OAuth2User processOAuth2User(OAuth2User oAuth2User, String clientName, UserRepository repository) {

        Social social;
        try {
            social = Social.valueOf(clientName.toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new SocialRegistrationFailedException("oauth2.social.invalid");
        }

        SocialUserProcess strategy = social.getStrategy(repository);

        return strategy.startProcess(oAuth2User);
    }
}
