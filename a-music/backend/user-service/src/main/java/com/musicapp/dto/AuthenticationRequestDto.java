package com.musicapp.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;

/**
 * DTO представление запроса аутентификации
 *
 * @author evgeniycheban
 */
@Value
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = AuthenticationRequestDto.AuthenticationRequestDtoBuilder.class)
public class AuthenticationRequestDto {

    @NotBlank(message = "{user.username.empty}")
    String username;
    @NotBlank(message = "{user.password.empty}")
    String password;
}
