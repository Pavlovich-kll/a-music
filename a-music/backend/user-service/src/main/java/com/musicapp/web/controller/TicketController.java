package com.musicapp.web.controller;

import com.musicapp.domain.Ticket;
import com.musicapp.dto.TicketCreateDto;
import com.musicapp.dto.TicketDto;
import com.musicapp.dto.TicketPatchDto;
import com.musicapp.mapper.TicketMapper;
import com.musicapp.service.TicketService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Методы для работы с билетами.
 *
 * @author lizavetashpinkova
 */
@Controller
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
    private final TicketMapper ticketMapper;

    /**
     * Метод возвращает перечень всех существующих билетов.
     *
     * @return список DTO всех существующих билетов
     */
    @ApiOperation("Метод возвращает перечень всех существующих билетов")
    @GetMapping("/tickets")
    public ResponseEntity<List<TicketDto>> getAllTickets() {
        return ResponseEntity.ok(ticketMapper.map(ticketService.getAllTickets()));
    }

    /**
     * Метод возвращает билет по id.
     *
     * @param id билета
     * @return dto билета
     */
    @ApiOperation("Метод возвращает билет по id")
    @GetMapping("/tickets/{id}")
    public ResponseEntity<TicketDto> getTicket(@PathVariable Long id) {
        return ResponseEntity.ok(ticketMapper.map(ticketService.getTicket(id)));
    }

    /**
     * Метод возвращает билеты по id концерта.
     *
     * @param concertId id концерта
     * @return список dto билетов
     */
    @ApiOperation("Метод возвращает билеты по id концерта")
    @Secured({"ROLE_ADMIN"})
    @GetMapping("/concerts/{concertId}/tickets")
    public ResponseEntity<List<TicketDto>> getTicketListForConcert(@PathVariable Long concertId) {
        return ResponseEntity.ok(ticketMapper.map(ticketService.getTicketListByConcertId(concertId)));
    }

    /**
     * Метод возвращает билеты по id пользователя.
     *
     * @param userId id пользователя
     * @return список dto билетов
     */
    @ApiOperation("Метод возвращает билеты по id пользователя")
    @GetMapping("/users/{userId}/tickets")
    public ResponseEntity<List<TicketDto>> getTicketListForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(ticketMapper.map(ticketService.getTicketListByUserId(userId)));
    }

    /**
     * Метод для создания билета.
     *
     * @param ticketCreateDto новый билет
     * @return dto созданного билета
     */
    @ApiOperation("Метод для создания билета")
    @PostMapping("/tickets")
    public ResponseEntity<TicketDto> createTicket(@RequestBody TicketCreateDto ticketCreateDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ticketMapper.map(ticketService.buyTicket(ticketCreateDto)));
    }

    /**
     * Метод для удаления билета по id.
     *
     * @param id билета
     */
    @ApiOperation("Метод для удаления билета по id")
    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/tickets/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);

        return ResponseEntity.ok().build();
    }

    /**
     * Метод для обновления данных билета по id.
     *
     * @param id             идентификатор билета
     * @param ticketPatchDto dto билета
     * @return dto билета
     */
    @ApiOperation("Метод для обновления данных билета по id")
    @Secured({"ROLE_ADMIN"})
    @PutMapping("/tickets/{id}")
    public ResponseEntity<TicketDto> updateTicket(@PathVariable Long id, @RequestBody TicketPatchDto ticketPatchDto) {
        Ticket ticket = ticketService.updateTicket(id, ticketPatchDto);
        return ResponseEntity.ok(ticketMapper.map(ticket));
    }
}

