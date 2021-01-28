package com.musicapp.service;

import com.musicapp.domain.Concert;
import com.musicapp.dto.ConcertCreateDto;
import com.musicapp.dto.ConcertDto;
import com.musicapp.dto.ConcertUpdateDto;
import com.musicapp.dto.UnsoldTicketsForConcertDto;

import java.time.LocalDate;
import java.util.List;

/**
 * Интерфейс сервиса для управления концертами.
 *
 * @author lizavetashpinkova
 */
public interface ConcertService {

    /**
     * @return concert with specified ID
     */
    Concert getConcert(long id);

    /**
     * @param id concert ID to delete
     */
    void deleteConcert(long id);

    /**
     * Возвращает список всех концертов.
     *
     * @return список концертов
     */
    List<Concert> getAllConcerts();

    /**
     * @param concertDate date used to filter concerts
     * @return list of concerts with concert date after specified
     */
    List<Concert> getAllConcertByConcertDateIsAfter(LocalDate concertDate);

    /**
     * @param id               concert ID
     * @param concertUpdateDto concert DTO with new values
     */
    void updateConcert(long id, ConcertUpdateDto concertUpdateDto);

    /**
     * Возвращает количество непроданных билетов для каждого концерта отсортированных по дате концерта.
     *
     * @param concertDate дата концерта
     * @return список непроданных билетов
     */
    List<UnsoldTicketsForConcertDto> countUnsoldTicketsForEachConcertByConcertDateIsAfter(LocalDate concertDate);

    /**
     * Метод для создания нового концерта.
     *
     * @param concertCreateDto новый концерт
     * @return концерт
     */
    ConcertDto create(ConcertCreateDto concertCreateDto);
}
