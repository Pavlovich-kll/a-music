package com.musicapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

/**
 * DTO представление сущности билета
 */
@Value
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class TicketDto {

    long id;
    int price;
    LocalDate dateOfPayment;
    ConcertDto concert;
    UserDto user;
}
