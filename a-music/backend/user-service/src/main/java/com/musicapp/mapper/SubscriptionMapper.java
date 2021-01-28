package com.musicapp.mapper;

import com.musicapp.domain.Subscription;
import com.musicapp.dto.SubscriptionDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface SubscriptionMapper {

    SubscriptionDto map(Subscription subscription);

    List<SubscriptionDto> map(List<Subscription> users);
}
