package com.musicapp.mapper;

import com.musicapp.domain.User;
import com.musicapp.domain.UserSubscription;
import com.musicapp.dto.UserCreateDto;
import com.musicapp.dto.UserDto;
import com.musicapp.dto.UserPatchDto;
import com.musicapp.dto.UserSubscriptionDto;
import org.mapstruct.*;

import java.util.List;

/**
 * Маппер сущности пользователя.
 *
 * @author evgeniycheban
 */
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    User map(UserCreateDto userDto);

    User map(UserDto userDto);

    UserDto map(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateUser(UserPatchDto userPatchDto, @MappingTarget User user);

    List<UserDto> map(List<User> users);

    UserSubscriptionDto map(UserSubscription userSubscription);

    UserSubscription map(UserSubscriptionDto userSubscriptionDto);
}