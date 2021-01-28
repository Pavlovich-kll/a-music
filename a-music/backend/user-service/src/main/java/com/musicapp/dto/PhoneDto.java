package com.musicapp.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.musicapp.service.UserService;
import com.musicapp.validation.annotation.PhoneFormat;
import com.musicapp.validation.annotation.PhoneVerified;
import com.musicapp.validation.annotation.Uniqueness;
import com.musicapp.validation.group.PhoneFormatGroup;
import com.musicapp.validation.group.PhoneNotBlankGroup;
import com.musicapp.validation.group.PhoneUniqueGroup;
import com.musicapp.validation.group.PhoneVerifiedGroup;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;

/**
 * DTO представление номера телефона
 *
 * @author evgeniycheban
 */
@Value
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = PhoneDto.PhoneDtoBuilder.class)
public class PhoneDto {

    @NotBlank(message = "{user.phone.empty}", groups = PhoneNotBlankGroup.class)
    @PhoneFormat(groups = PhoneFormatGroup.class)
    @Uniqueness(checkerClass = UserService.class, message = "{user.phone.duplicate}", groups = PhoneUniqueGroup.class)
    @PhoneVerified(groups = PhoneVerifiedGroup.class)
    String phone;
}
