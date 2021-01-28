package com.musicapp.repository;

import com.musicapp.domain.Concert;
import com.musicapp.dto.UnsoldTicketsForConcertDto;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.musicapp.config.CachingConfig.CONCERTS;

/**
 * Репозиторий для работы с таблицей концертов.
 *
 * @author lizashpinkova
 */
public interface ConcertRepository extends JpaRepository<Concert, Long> {

    /**
     * @param concertDate date used to filter concerts
     * @return list of concerts with concert date after specified
     */
    @Cacheable(value = CONCERTS)
    List<Concert> findAllConcertByConcertDateIsAfter(LocalDate concertDate);

    /**
     * Возвращает список концертов и количество непроданных билетов на каждый концерт с фильтрацией по дате концерта.
     *
     * @param concertDate дата концерта
     * @return список концертов и количество непроданных билетов
     */

    @Query("SELECT c.id as id, c.title as title, c.concertDate as concertDate, c.totalNumberOfTickets - " +
            "(SELECT COUNT(*) FROM Ticket as t WHERE t.concert.id=c.id) as numberOfUnsoldTickets FROM Concert as c " +
            "WHERE c.concertDate >= ?1")
    @Cacheable(value = CONCERTS)
    List<UnsoldTicketsForConcertDto> countUnsoldTicketsForEachConcertByConcertDateIsAfter(LocalDate concertDate);

    /**
     * @param id of concert
     */
    @Override
    @CacheEvict(value = CONCERTS, key = "#id")
    void deleteById(Long id);

    /**
     * Метод возвращает концерт по его id.
     *
     * @param id концерта
     * @return концерт по его id
     */
    @Override
    @NonNull
    @Cacheable(value = CONCERTS, key = "#id")
    Optional<Concert> findById(Long id);

    /**
     * Метод сохраняет концерт
     *
     * @param concert
     * @return concert
     */
    @Override
    @NonNull
    @CachePut(value = CONCERTS, key = "#concert")
    <S extends Concert> S save(S concert);
}
