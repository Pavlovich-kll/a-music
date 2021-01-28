package com.musicapp.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.musicapp.service.UserService;
import com.musicapp.validation.annotation.Uniqueness;
import com.musicapp.validation.group.EmailFormatGroup;
import com.musicapp.validation.group.EmailNotBlankGroup;
import com.musicapp.validation.group.EmailUniqueGroup;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * DTO представление e-mail
 *
 * @author a.nagovicyn
 */

@Value
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = EmailDto.EmailDtoBuilder.class)
public class EmailDto {

    @NotBlank(message = "{user.email.empty}", groups = EmailNotBlankGroup.class)
    @Email(groups = EmailFormatGroup.class)
    @Uniqueness(checkerClass = UserService.class, message = "{user.email.duplicate}", groups = EmailUniqueGroup.class)
    String email;
}
