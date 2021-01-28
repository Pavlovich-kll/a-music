package com.musicapp.service;

import com.musicapp.domain.Subscription;

import java.util.List;
import java.util.Optional;

public interface SubscriptionService {

    List<Subscription> getAllSubscriptions();

    Optional<Subscription> getSubscriptionById(Long id);
}
