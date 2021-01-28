package com.musicapp.mapper;

import com.musicapp.domain.Subscription;
import com.musicapp.domain.UserSubscription;
import com.musicapp.dto.SubscriptionDto;
import com.musicapp.dto.UserSubscriptionDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserSubscriptionMapper {

    UserSubscription map(UserSubscriptionDto userSubscription);

    UserSubscriptionDto map(UserSubscription userSubscription);

    List<UserSubscriptionDto> map(List<UserSubscription> userSubscriptionsDto);

    SubscriptionDto map(Subscription subscription);

    Subscription map(SubscriptionDto subscription);
}