package com.musicapp.repository;

import com.musicapp.domain.UserSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {

    /**
     * Метод поиска userSubscription по id подписки.
     *
     * @param id - id подписки.
     * @return - userSubscription
     */
    Optional<UserSubscription> findBySubscriptionId(Long id);

    /**
     * Метод поиска userSubscription по id главного пользователя.
     *
     * @param hostUserId - id главного пользователя
     * @return - userSubscription
     */
    @Query(value = "select u from UserSubscription u where u.hostUser = ?1")
    Optional<UserSubscription> findByHostUserId(Long hostUserId);
}