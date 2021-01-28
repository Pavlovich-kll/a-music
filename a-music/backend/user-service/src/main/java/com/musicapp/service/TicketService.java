package com.musicapp.service;

import com.musicapp.domain.Ticket;
import com.musicapp.dto.TicketCreateDto;
import com.musicapp.dto.TicketPatchDto;

import java.util.List;

/**
 * Интерфейс сервиса для управления билетами.
 *
 * @author lizavetashpinkova
 */
public interface TicketService {

    /**
     * Возвращает список всех билетов.
     *
     * @return список билетов
     */
    List<Ticket> getAllTickets();

    /**
     * Возвращает билет по id.
     *
     * @param id билета
     * @return билет
     */
    Ticket getTicket(Long id);

    /**
     * Метод для получения всех билетов по id концерта.
     *
     * @param concertId id концерта
     * @return список билетов
     */
    List<Ticket> getTicketListByConcertId(Long concertId);

    /**
     * Метод для получения всех билетов по id пользователя.
     *
     * @param userId пользователь
     * @return список билетов
     */
    List<Ticket> getTicketListByUserId(Long userId);

    /**
     * Метод для удаления билета по id.
     *
     * @param id id билета
     */
    void deleteTicket(Long id);

    /**
     * Метод для покупки билета.
     *
     * @param ticketCreateDto новый билет
     * @return билет
     */
    Ticket buyTicket(TicketCreateDto ticketCreateDto);

    /**
     * Метод для изменения билета.
     *
     * @param id             идентификатор билета
     * @param ticketPatchDto dto нового билета
     * @return обновленный билет
     */
    Ticket updateTicket(Long id, TicketPatchDto ticketPatchDto);

    /**
     * Возвращает количество непроданных билетов по ID концерта.
     *
     * @param concertId идентификатор концерта
     * @return количество непроданных билетов
     */
    int countUnsoldTicketsByConcertId(long concertId);
}
