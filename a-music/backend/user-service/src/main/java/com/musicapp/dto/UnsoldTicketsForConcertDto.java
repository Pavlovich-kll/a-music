package com.musicapp.dto;

import java.time.LocalDate;

/**
 * Проекция для получения количества непроданных билетов на концерт
 * используется в ConcertRepository
 */
public interface UnsoldTicketsForConcertDto {

    long getId();

    String getTitle();

    LocalDate getConcertDate();

    int getNumberOfUnsoldTickets();
}
