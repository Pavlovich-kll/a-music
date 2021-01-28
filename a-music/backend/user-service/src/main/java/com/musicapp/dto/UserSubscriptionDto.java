package com.musicapp.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
@AllArgsConstructor
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = UserSubscriptionDto.UserSubscriptionDtoBuilder.class)
public class UserSubscriptionDto {

    Long actualPrice;
    LocalDate purchaseDate;
    boolean isValid;
    Long hostUser;
    SubscriptionDto subscription;
    List<Long> invitedUsersId;
}