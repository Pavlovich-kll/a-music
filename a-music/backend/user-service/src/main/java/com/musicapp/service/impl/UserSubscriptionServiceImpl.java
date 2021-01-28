package com.musicapp.service.impl;

import com.musicapp.domain.Subscription;
import com.musicapp.domain.User;
import com.musicapp.domain.UserSubscription;
import com.musicapp.dto.UserDto;
import com.musicapp.dto.UserSubscriptionDto;
import com.musicapp.exception.NotFoundException;
import com.musicapp.exception.UserSubscriptionException;
import com.musicapp.mapper.UserMapper;
import com.musicapp.mapper.UserSubscriptionMapper;
import com.musicapp.repository.UserRepository;
import com.musicapp.repository.UserSubscriptionRepository;
import com.musicapp.service.SubscriptionService;
import com.musicapp.service.UserService;
import com.musicapp.service.UserSubscriptionEmailCodeService;
import com.musicapp.service.UserSubscriptionService;
import com.musicapp.util.constants.MessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSubscriptionServiceImpl implements UserSubscriptionService {

    private static final int SUBSCRIPTION_PERIOD = 30;

    private final UserSubscriptionRepository userSubscriptionRepository;
    private final UserSubscriptionMapper userSubscriptionMapper;
    private final UserSubscriptionEmailCodeService userSubscriptionEmailCodeService;
    private final SubscriptionService subscriptionService;
    private final UserMapper userMapper;
    private final UserService userService;
    private final UserRepository userRepository;

    /**
     * Метод создания новой подписки.
     *
     * @param userSubscriptionDto - объект UserSubscription который приходит с фронта.
     * @return - UserSubscription
     */
    @Transactional
    @Override
    public UserSubscriptionDto buySubscription(UserSubscriptionDto userSubscriptionDto) {
        UserSubscription userSubscription = userSubscriptionMapper.map(userSubscriptionDto);
        if (!isUsersCountAcceptable(userSubscription.getSubscription(), userSubscriptionDto.getInvitedUsersId().size())) {
            throw new UserSubscriptionException("Count of invited users more than it possible.");
        }
        User hostUser = userService.getById(userSubscriptionDto.getHostUser());
        userSubscription.setValid(true);
        userSubscriptionRepository.save(userSubscription);

        hostUser.setUserSubscription(userSubscription);
        userRepository.save(hostUser);

        inviteUsers(userSubscriptionDto);
        return userSubscriptionMapper.map(userSubscription);
    }

    /**
     * Метод проверки действует подписка или срок уже истек.
     * Запускается каждый день в полночь (https://crontab.guru/examples.html).
     */
    @Transactional
    @Scheduled(cron = "0 0 * * * *")
    public void isValidSubscription() {
        for (UserSubscription userSubscription : userSubscriptionRepository.findAll()) {
            Period period = Period.between(userSubscription.getPurchaseDate(), LocalDate.now());
            if (period.getDays() < SUBSCRIPTION_PERIOD) {
                userSubscription.setValid(true);
            } else {
                userSubscription.setValid(false);
            }
            userSubscriptionRepository.save(userSubscription);
        }
    }

    /**
     * Метод получения всех пользователей в подписке.
     *
     * @param hostUserId - id главного пользователя
     */
    @Transactional
    public List<UserDto> getAllUsersByHostUserId(Long hostUserId) {
        UserSubscription userSubscription = userSubscriptionRepository.findByHostUserId(hostUserId).orElseThrow(
                () -> new NotFoundException(MessageConstants.USER_SUBSCRIPTION_EMAIL_CODE_NOT_FOUND, "id"));

        List<User> usersWithSameSubscription = userService.findAllByUserSubscriptionId(userSubscription.getId());

        return userMapper.map(usersWithSameSubscription);
    }

    /**
     * Метод удаляет пользователя из подписки.
     *
     * @param id - id удаляемого пользователя.
     */
    @Transactional
    public void deleteUserFromSubscription(Principal principal, Long id) {
        User invitedUser = userService.getById(id);
        Long invitedUserSubscriptionId = invitedUser.getUserSubscription().getId();

        User hostUser = userService.findByUsername(principal.getName());
        Long subscriptionIdOfHostUser = hostUser.getUserSubscription().getId();
        if (invitedUserSubscriptionId.equals(subscriptionIdOfHostUser)) {
            invitedUser.setUserSubscription(null);
            userRepository.save(invitedUser);
        }
    }

    /**
     * Метод добавления пользователя в существующую подписку (userSubscription)
     *
     * @param principal - зарегестрированный пользователь
     * @param usersId   - список id добавляемых пользователей
     */
    @Transactional
    @Override
    public UserSubscriptionDto addUsersToSubscription(Principal principal, List<Long> usersId) {
        User hostUser = userService.findByUsername(principal.getName());
        UserSubscription userSubscription = hostUser.getUserSubscription();

        if (!isUsersCountAcceptable(userSubscription.getSubscription(), userSubscription.getInvitedUsers().size())) {
            throw new UserSubscriptionException("Count of invited users more than it possible.");
        }
        for (Long id : usersId) {
            final User invitedUser = userService.getById(id);
            userSubscriptionEmailCodeService.addToUserSubscriptionEmailCode(userSubscription.getId(), id);
            userSubscriptionEmailCodeService.sendEmailToUser(invitedUser);
        }
        return userSubscriptionMapper.map(userSubscription);
    }

    /**
     * Метод добавляет пользователей в подписку и отсылает сообщение
     *
     * @param userSubscriptionDto - userSubscriptionDto
     */
    private void inviteUsers(UserSubscriptionDto userSubscriptionDto) {
        Subscription subscription = subscriptionService.getSubscriptionById(
                userSubscriptionDto.getSubscription().getId()).orElseThrow(() ->
                new NotFoundException(MessageConstants.SUBSCRIPTION_ID_NOT_FOUND, ""));

        List<User> invitedUserToAddToSubscription = userService.findByUserIds(userSubscriptionDto.getInvitedUsersId());
        for (User user : invitedUserToAddToSubscription) {
            userSubscriptionEmailCodeService.addToUserSubscriptionEmailCode(subscription.getId(), user.getId());
        }
        userSubscriptionEmailCodeService.sendEmailToInvitedUsers(invitedUserToAddToSubscription);
    }

    /**
     * Метод проверки допустимого числа пользователей в подписке.
     *
     * @param subscription    - подписка к которой привязывается пользователь
     * @param invitedUserSize - число пользователей в подписке
     * @return - true если число добавляемых пользователей меньше допустимого числа пользователей в подписке.
     */
    private boolean isUsersCountAcceptable(Subscription subscription, int invitedUserSize) {
        return (subscription.getUserCapacity() > invitedUserSize);
    }
}