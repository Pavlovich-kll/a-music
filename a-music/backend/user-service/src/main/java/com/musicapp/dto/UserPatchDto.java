package com.musicapp.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

/**
 * DTO представление сущности для обновления пользователя
 */
@Value
@AllArgsConstructor
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = UserPatchDto.UserPatchDtoBuilder.class)
public class UserPatchDto {

    String firstName;
    String lastName;
    String username;
    LocalDate dateOfBirth;
    String avatar;
}
