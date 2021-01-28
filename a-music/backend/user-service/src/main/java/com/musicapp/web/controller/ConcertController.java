package com.musicapp.web.controller;

import com.musicapp.dto.ConcertCreateDto;
import com.musicapp.dto.ConcertDto;
import com.musicapp.dto.ConcertUpdateDto;
import com.musicapp.dto.UnsoldTicketsForConcertDto;
import com.musicapp.mapper.ConcertMapper;
import com.musicapp.service.ConcertService;
import com.musicapp.service.TicketService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

/**
 * Методы для работы с концертами.
 *
 * @author lizavetashpinkova
 */
@Controller
@RequestMapping("/concerts")
@RequiredArgsConstructor
public class ConcertController {

    private final ConcertService concertService;
    private final TicketService ticketService;
    private final ConcertMapper concertMapper;

    /**
     * Метод для удаления конкретного концерта по его ID
     *
     * @param id ID концерта, подлежащего удаления
     */
    @ApiOperation("Метод удаляет сущность концерта по ID")
    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> deleteConcert(@PathVariable long id) {
        concertService.deleteConcert(id);

        return ResponseEntity.ok().build();
    }

    /**
     * Метод для получения конкретного коцерта по его ID
     *
     * @param id ID концерта
     * @return DTO концерта
     */
    @ApiOperation("Метод возвращает концерт по его ID")
    @GetMapping("/{id}")
    public ResponseEntity<ConcertDto> getConcert(@PathVariable long id) {
        return ResponseEntity.ok(concertMapper.map(concertService.getConcert(id)));
    }

    /**
     * Метод для получения списка всех существующих концертов
     *
     * @return список DTO концертов
     */
    @ApiOperation("Метод возвращает перечень всех существующих концертов")
    @GetMapping
    public ResponseEntity<List<ConcertDto>> getAllConcerts() {
        return ResponseEntity.ok(concertMapper.map(concertService.getAllConcerts()));
    }

    /**
     * Метод для получения списка концертов, отфильтрованных по дате проведения
     *
     * @return список DTO концертов
     */
    @ApiOperation("Метод возвращает перечень актуальных концертов концертов")
    @GetMapping(params = "concertDateAfter")
    public ResponseEntity<List<ConcertDto>> getAllConcertsByConcertDateIsAfter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate concertDateAfter) {

        return ResponseEntity.ok(concertMapper.map(concertService.getAllConcertByConcertDateIsAfter(concertDateAfter)));
    }

    /**
     * Метод для получения списка концертов и количества билетов на них, отфильтрованных по дате проведения
     *
     * @return список DTO в формате концерт + число непроданных билетов к нему
     */
    @ApiOperation("Метод возвращает количество непроданных билетов для каждого будущего концерта")
    @GetMapping("/count-unsold-tickets")
    public ResponseEntity<List<UnsoldTicketsForConcertDto>> getUnsoldTicketsForEachConcert() {
        return ResponseEntity.ok(concertService.countUnsoldTicketsForEachConcertByConcertDateIsAfter(LocalDate.now()));
    }

    /**
     * Метод для получения количества непроданных билетов по ID концерта
     *
     * @return число непроданных билетов
     */
    @ApiOperation("Метод возвращает количество непроданных билетов по ID концерта")
    @GetMapping("/{id}/count-unsold-tickets")
    public ResponseEntity<Integer> getUnsoldTicketsCount(@PathVariable long id) {
        return ResponseEntity.ok(ticketService.countUnsoldTicketsByConcertId(id));
    }

    /**
     * Метод для редактирования концерта
     *
     * @param id               ID концерта
     * @param concertUpdateDto DTO концерта с новыми значениями
     */
    @ApiOperation("Метод для обновления сущности концерта")
    @PutMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> updateConcert(@PathVariable long id, @RequestBody @Valid ConcertUpdateDto concertUpdateDto) {
        concertService.updateConcert(id, concertUpdateDto);

        return ResponseEntity.ok().build();
    }

    /**
     * Метод для создания нового концерта.
     *
     * @param concertCreateDto dto нового концерта
     * @return dto созданного концерта
     */
    @ApiOperation("Метод для создания нового концерта")
    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<ConcertDto> createConcert(@RequestBody ConcertCreateDto concertCreateDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(concertService.create(concertCreateDto));
    }
}
