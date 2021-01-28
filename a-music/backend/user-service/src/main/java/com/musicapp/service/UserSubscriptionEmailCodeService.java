package com.musicapp.service;

import com.musicapp.domain.User;
import com.musicapp.domain.UserSubscriptionEmailCode;

import java.util.List;
import java.util.Optional;

public interface UserSubscriptionEmailCodeService {

    void addToUserSubscriptionEmailCode(Long hostUserId, Long id);

    Optional<UserSubscriptionEmailCode> findByInvitedUserId(Long id);

    Optional<UserSubscriptionEmailCode> findByCode(String code);

    void sendEmailToInvitedUsers(List<User> invitedUsersNames);

    void verifyByCodeAndSave(String code);

    void sendEmailToUser(User user);
}
