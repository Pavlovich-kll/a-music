package com.musicapp.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.musicapp.domain.City;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

/**
 * DTO представление профиля пользователя.
 *
 * @author alexandrkudinov
 */
@Value
@AllArgsConstructor
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = UserDto.UserDtoBuilder.class)
public class UserDto {

    Long id;
    String firstName;
    String lastName;
    String email;
    String phone;
    String username;
    LocalDate dateOfBirth;
    String avatar;
    City city;
    UserSubscriptionDto userSubscription;
}
