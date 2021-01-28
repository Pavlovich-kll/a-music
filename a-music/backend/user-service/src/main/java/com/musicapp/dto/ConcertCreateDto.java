package com.musicapp.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

/**
 * DTO представление сущности созданного концерта
 */
@Value
@AllArgsConstructor
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = ConcertCreateDto.ConcertCreateDtoBuilder.class)
public class ConcertCreateDto {

    String title;
    String performer;
    LocalDate concertDate;
    int ageRestriction;
    int totalNumberOfTickets;
    int basePrice;
    long cityId;
}
