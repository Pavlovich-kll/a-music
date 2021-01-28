package com.musicapp.repository;

import com.musicapp.domain.UserSubscriptionEmailCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSubscriptionEmailCodeRepository extends JpaRepository<UserSubscriptionEmailCode, Long> {

    /**
     * Ищет объект UserSubscriptionEmailCode по уникальному коду.
     * @param code - уникальному код.
     * @return - объект UserSubscriptionEmailCode.
     */
    Optional<UserSubscriptionEmailCode> findByCode(String code);

    /**
     * Ищет объект UserSubscriptionEmailCode по id.
     * @param id - id UserSubscriptionEmailCode.
     * @return - объект UserSubscriptionEmailCode.
     */
    Optional<UserSubscriptionEmailCode> findByInvitedUserId(Long id);
}
