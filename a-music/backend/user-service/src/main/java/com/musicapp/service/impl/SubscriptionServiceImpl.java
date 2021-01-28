package com.musicapp.service.impl;

import com.musicapp.domain.Subscription;
import com.musicapp.repository.SubscriptionRepository;
import com.musicapp.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    /**
     * Метод позволяет получить все подписки из БД.
     *
     * @return - список существующих подписок
     */
    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    /**
     * Метод позволяет получить подписку по id.
     *
     * @param id - id подписки
     * @return - подписку
     */
    public Optional<Subscription> getSubscriptionById(Long id) {
        return subscriptionRepository.findById(id);
    }
}