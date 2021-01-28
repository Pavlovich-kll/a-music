package com.musicapp.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.*;
import java.time.LocalDate;

/**
 * DTO представление сущности концерта с обновленными полями
 */
@Value
@AllArgsConstructor
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = ConcertUpdateDto.ConcertUpdateDtoBuilder.class)
public class ConcertUpdateDto {

    @NotNull
    @Size(min = 1)
    String title;
    @NotNull
    @Size(min = 1)
    String performer;
    @FutureOrPresent
    LocalDate concertDate;
    @Min(1)
    @Max(100)
    int ageRestriction;
    @Min(1)
    int totalNumberOfTickets;
    @Min(1)
    int basePrice;
    @NotNull
    @Min(1)
    long cityId;
}
