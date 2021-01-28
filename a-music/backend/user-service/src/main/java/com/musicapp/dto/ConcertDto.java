package com.musicapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

/**
 * DTO представление сущности концерта
 */
@Value
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class ConcertDto {

    long id;
    String title;
    String performer;
    LocalDate concertDate;
    int ageRestriction;
    long cityId;
}
