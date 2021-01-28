package com.musicapp.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

/**
 * DTO представление сущности для изменения билета
 */
@Value
@AllArgsConstructor
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = TicketPatchDto.TicketPatchDtoBuilder.class)
public class TicketPatchDto {

    int price;
    LocalDate dateOfPayment;
    long concertId;
    long userId;
}
