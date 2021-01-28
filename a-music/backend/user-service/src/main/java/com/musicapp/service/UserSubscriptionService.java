package com.musicapp.service;

import com.musicapp.dto.UserDto;
import com.musicapp.dto.UserSubscriptionDto;

import java.security.Principal;
import java.util.List;

public interface UserSubscriptionService {

    UserSubscriptionDto buySubscription(UserSubscriptionDto temporalUserSubscription);

    void isValidSubscription();

    List<UserDto> getAllUsersByHostUserId(Long hostUserId);

    void deleteUserFromSubscription(Principal principal, Long id);

    UserSubscriptionDto addUsersToSubscription(Principal principal, List<Long> usersId);
}
