package com.musicapp.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.musicapp.validation.annotation.SamePasswordPatch;
import com.musicapp.validation.group.NewPasswordNotBlankGroup;
import com.musicapp.validation.group.PasswordNotBlankGroup;
import com.musicapp.validation.group.PasswordSizeGroup;
import com.musicapp.validation.group.SamePasswordGroup;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * DTO представление для смены пароля пользователем
 *
 * @author r.zamoiski
 */
@SamePasswordPatch(groups = SamePasswordGroup.class)
@Value
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = UserPasswordPatchDto.UserPasswordPatchDtoBuilder.class)
public class UserPasswordPatchDto {

    @NotBlank(message = "{user.password.empty}", groups = PasswordNotBlankGroup.class)
    String oldPassword;
    @NotBlank(message = "{user.password.empty}", groups = NewPasswordNotBlankGroup.class)
    @Size(min = 8, max = 32, message = "{user.password.size}", groups = PasswordSizeGroup.class)
    String newPassword;
    String repeatNewPassword;
}
