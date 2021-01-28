package com.musicapp.repository;

import com.musicapp.domain.Concert;
import com.musicapp.domain.Ticket;
import com.musicapp.domain.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import static com.musicapp.config.CachingConfig.TICKETS;

/**
 * Репозиторий для работы с таблицей билетов.
 *
 * @author lizashpinkova
 */
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    /**
     * Возвращает билет по концерту.
     *
     * @param concert концерт
     * @return билет
     */
    @Cacheable(value = TICKETS, key = "#concert")
    List<Ticket> findByConcert(Concert concert);

    /**
     * Возвращает билет по пользователю.
     *
     * @param user пользователь
     * @return билет
     */
    @Cacheable(value = TICKETS, key = "#user")
    List<Ticket> findByUser(User user);

    /**
     * @param concertId ID of concert
     * @return number of unsold tickets for concert
     */
    @Query("SELECT c.totalNumberOfTickets - (SELECT COUNT(*) FROM Ticket as t WHERE t.concert.id=c.id) " +
            "FROM Concert as c WHERE c.id = ?1")
    @Cacheable(value = TICKETS, key = "#concertId")
    int countUnsoldTicketsByConcertId(long concertId);

    /**
     * @param id of ticket
     */
    @Override
    @CacheEvict(value = TICKETS, key = "#id")
    void deleteById(Long id);

    @Override
    @Cacheable(value = TICKETS)
    List<Ticket> findAll();
}
