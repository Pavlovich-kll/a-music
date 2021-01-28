package com.musicapp.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = SubscriptionDto.SubscriptionDtoBuilder.class)
public class SubscriptionDto {

    Long id;
    String name;
    Long price;
    String description;
    int trialPeriod;
    int userCapacity;
}
