package com.musicapp.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.musicapp.validation.annotation.PhoneAndCode;
import com.musicapp.validation.annotation.PhoneFormat;
import com.musicapp.validation.group.PhoneAndCodeGroup;
import com.musicapp.validation.group.PhoneFormatGroup;
import com.musicapp.validation.group.PhoneNotBlankGroup;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;

/**
 * DTO представление номера телефона и кода подтверждения
 *
 * @author evgeniycheban
 */
@PhoneAndCode(groups = PhoneAndCodeGroup.class)
@Value
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = PhoneCodeDto.PhoneCodeDtoBuilder.class)
public class PhoneCodeDto {

    @NotBlank(groups = PhoneNotBlankGroup.class)
    @PhoneFormat(groups = PhoneFormatGroup.class)
    String phone;
    String code;
}
