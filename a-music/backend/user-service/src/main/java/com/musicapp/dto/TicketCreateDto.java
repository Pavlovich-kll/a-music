package com.musicapp.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

/**
 * DTO представление сущности созданного билета
 */
@Value
@AllArgsConstructor
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = TicketCreateDto.TicketCreateDtoBuilder.class)
public class TicketCreateDto {

    int price;
    LocalDate dateOfPayment;
    long concertId;
    long userId;
}
