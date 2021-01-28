package com.musicapp.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.musicapp.validation.annotation.EmailVerified;
import com.musicapp.validation.annotation.PhoneFormat;
import com.musicapp.validation.annotation.PhoneVerified;
import com.musicapp.validation.annotation.SamePassword;
import com.musicapp.validation.group.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * DTO представление сущности созданного пользователя
 *
 * @author evgeniycheban
 */
@SamePassword(groups = SamePasswordGroup.class)
@Value
@AllArgsConstructor
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = UserCreateDto.UserCreateDtoBuilder.class)
public class UserCreateDto {

    String firstName;
    String lastName;
    @Email(groups = EmailFormatGroup.class)
    @NotBlank(message = "${user.email.empty}")
    @EmailVerified(groups = EmailVerifiedGroup.class)
    String email;
    LocalDate dateOfBirth;
    String avatar;
    @NotBlank(message = "{user.phone.empty}", groups = PhoneNotBlankGroup.class)
    @PhoneFormat(groups = PhoneFormatGroup.class)
    @PhoneVerified(groups = PhoneVerifiedGroup.class)
    String phone;
    @NotBlank(message = "{user.username.empty}", groups = UsernameNotBlankGroup.class)
    String username;
    @NotBlank(message = "{user.password.empty}", groups = PasswordNotBlankGroup.class)
    @Size(min = 8, max = 32, message = "{user.password.size}", groups = PasswordSizeGroup.class)
    String password;
    String repeatPassword;
    long cityId;
}
