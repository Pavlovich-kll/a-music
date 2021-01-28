package com.musicapp.service.impl;

import com.musicapp.domain.User;
import com.musicapp.domain.UserSubscription;
import com.musicapp.domain.UserSubscriptionEmailCode;
import com.musicapp.exception.NotFoundException;
import com.musicapp.repository.UserRepository;
import com.musicapp.repository.UserSubscriptionEmailCodeRepository;
import com.musicapp.repository.UserSubscriptionRepository;
import com.musicapp.service.UserService;
import com.musicapp.service.UserSubscriptionEmailCodeService;
import com.musicapp.util.constants.MessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserSubscriptionEmailCodeServiceImpl implements UserSubscriptionEmailCodeService {

    private final UserSubscriptionEmailCodeRepository userSubscriptionEmailCodeRepository;
    private final JavaMailSender mailSender;
    private final UserSubscriptionRepository userSubscriptionRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    @Value("${subscription.uri}")
    private String subscriptionUri;
    @Value("${spring.mail.username}")
    private String from;

    /**
     * Добавляет информацию в БД для верификации invitedUser'а и и присвоения ему userSubscriptionId.
     *
     * @param subscriptionId - id подписки
     * @param invitedUserId  - invitedUser id
     */
    public void addToUserSubscriptionEmailCode(Long subscriptionId, Long invitedUserId) {
        UserSubscriptionEmailCode userSubscriptionEmailCode = new UserSubscriptionEmailCode()
                .setInvitedUserId(invitedUserId)
                .setCode(generateCode())
                .setUserSubscriptionId(subscriptionId);
        userSubscriptionEmailCodeRepository.save(userSubscriptionEmailCode);
    }

    /**
     * Метод находит объект userSubscriptionEmailCode по id invitedUser'а
     *
     * @param id - invitedUser id
     * @return - объект userSubscriptionEmailCode
     */
    @Override
    public Optional<UserSubscriptionEmailCode> findByInvitedUserId(Long id) {
        return userSubscriptionEmailCodeRepository.findByInvitedUserId(id);
    }

    /**
     * Метод поиска объекта UserSubscriptionEmailCode по уникальному коду.
     *
     * @param code - уникальный код.
     * @return - объект userSubscriptionEmailCode.
     */
    @Override
    public Optional<UserSubscriptionEmailCode> findByCode(String code) {
        return userSubscriptionEmailCodeRepository.findByCode(code);
    }

    /**
     * Метод вызывает метод отправки сообщения при необходимости отправки eMail сообщений разным пользователям
     *
     * @param invitedUsersNames - список пользователей включаетых в подписку.
     */
    public void sendEmailToInvitedUsers(List<User> invitedUsersNames) {
        for (User user : invitedUsersNames) {
            sendEmailToUser(user);
        }
    }

    /**
     * Метод сверяет код полученный по eMail код и сетает ползователю id userSubscription.
     *
     * @param code - уникальный код отсылаемый каждому invitedUser.
     */
    @Transactional
    public void verifyByCodeAndSave(String code) {
        UserSubscriptionEmailCode userSubscriptionEmailCode = findByCode(code).orElseThrow(
                () -> new NotFoundException(MessageConstants.USER_SUBSCRIPTION_EMAIL_CODE_NOT_FOUND, "id"));
        Optional<UserSubscription> bySubscriptionId = findBySubscriptionId(userSubscriptionEmailCode.getUserSubscriptionId());
        User invitedUser = userService.getById(userSubscriptionEmailCode.getInvitedUserId());
        invitedUser.setUserSubscription(bySubscriptionId.orElseThrow(() -> new NotFoundException(MessageConstants.USER_ID_NOT_FOUND, "id")));
        userRepository.save(invitedUser);
    }

    /**
     * Метод отправляет eMail сообщение пользователю, для получения подписки.
     */
    public void sendEmailToUser(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        UserSubscriptionEmailCode userSubscriptionEmailCode = findByInvitedUserId(user.getId()).orElseThrow(
                () -> new NotFoundException(MessageConstants.USER_SUBSCRIPTION_EMAIL_CODE_NOT_FOUND, "id"));
        String code = userSubscriptionEmailCode.getCode();
        String link = subscriptionUri + code;

        message.setTo(user.getEmail());
        message.setSubject("A-music subscription activating code");
        message.setText("Greetings! Please, follow this link to confirm your e-mail:" + "\n" + link);
        message.setFrom(from);

        mailSender.send(message);
    }

    /**
     * Метод поиска подписки по id.
     *
     * @param userSubscription - id userSubscription
     * @return - UserSubscription
     */
    private Optional<UserSubscription> findBySubscriptionId(Long userSubscription) {
        return userSubscriptionRepository.findBySubscriptionId(userSubscription);
    }

    /**
     * Метод генерирует уникальный ключ
     *
     * @return - строковое представление ключа
     */
    private String generateCode() {
        return UUID.randomUUID().toString();
    }
}
