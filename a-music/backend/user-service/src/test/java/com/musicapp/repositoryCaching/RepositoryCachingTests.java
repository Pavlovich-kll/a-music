package com.musicapp.repositoryCaching;

import com.musicapp.domain.Concert;
import com.musicapp.domain.Ticket;
import com.musicapp.domain.User;
import com.musicapp.dto.UnsoldTicketsForConcertDto;
import com.musicapp.repository.ConcertRepository;
import com.musicapp.repository.TicketRepository;
import com.musicapp.repository.UserRepository;
import com.musicapp.repository.initializer.ContainerDB;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.musicapp.config.CachingConfig.CONCERTS;
import static com.musicapp.config.CachingConfig.TICKETS;
import static org.junit.Assert.*;

@EnableCaching
@SqlGroup({
        @Sql("/db/sql/countriesInsert.sql"),
        @Sql("/db/sql/citiesInsert.sql"),
        @Sql("/db/sql/userInsert.sql"),
        @Sql("/db/sql/concertsInsert.sql"),
        @Sql("/db/sql/ticketInsert.sql")
})
public class RepositoryCachingTests extends ContainerDB {

    @Autowired
    ConcertRepository concertRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CacheManager cacheManager;

    @TestConfiguration
    static class CachingConfiguration {

        @Bean
        public CacheManager cacheManager() {
            SimpleCacheManager cacheManager = new SimpleCacheManager();
            cacheManager.setCaches(Arrays.asList(
                    new ConcurrentMapCache(CONCERTS),
                    new ConcurrentMapCache(TICKETS)));
            return cacheManager;
        }
    }

    /**
     * Проверяем, кладется ли концерт в кэш и
     * сравниваем список, что достается из БД и из кэша
     */
    @Test
    public void findAllConcertByConcertDateIsAfter() {
        LocalDate date = LocalDate.of(2020, 7, 28);
        List<Concert> expectedList = concertRepository.findAllConcertByConcertDateIsAfter(date);
        Object actualList = Objects.requireNonNull(Objects.requireNonNull(cacheManager.getCache(CONCERTS)).get(date)).get();
        assertEquals(expectedList, actualList);
    }

    @Test
    public void countUnsoldTicketsForEachConcertByConcertDateIsAfter() throws NoSuchFieldException {
        LocalDate dateOfPayment = LocalDate.of(2020, 7, 28);
        List<UnsoldTicketsForConcertDto> expectedUnsoldTickets = concertRepository.countUnsoldTicketsForEachConcertByConcertDateIsAfter(dateOfPayment);//возвращает один концерт с остатком непроданных билетов
        Object actualList = Objects.requireNonNull(Objects.requireNonNull(cacheManager.getCache(CONCERTS)).get(dateOfPayment)).get();
        assertEquals(expectedUnsoldTickets, actualList);
    }

    @Test
    public void deleteByConcertId() {
        LocalDate date = LocalDate.of(2020, 7, 28);
        concertRepository.findAllConcertByConcertDateIsAfter(date);
        concertRepository.deleteById(1L);
        Object actualConcert = cacheManager.getCache(CONCERTS).get(1L);
        assertNull(actualConcert);
    }

    @Test
    public void findByUser() {
        List<Ticket> expectedTickets = ticketRepository.findByUser(getUser());
        Object actualTickets = cacheManager.getCache(TICKETS).get(getUser()).get();
        assertEquals(expectedTickets, actualTickets);
    }

    @Test
    public void findByConcert() {
        Concert concert = getConcert();
        List<Ticket> expectedList = ticketRepository.findByConcert(concert);
        Object actualList = cacheManager.getCache(TICKETS).get(concert).get();
        assertTrue(Optional.ofNullable(actualList).isPresent());
        assertEquals(Optional.of(expectedList), Optional.of(actualList));
    }

    @Test
    public void countUnsoldTicketsByConcertId() {
        int countExpected = ticketRepository.countUnsoldTicketsByConcertId(1L);
        Object tickets = cacheManager.getCache(TICKETS).get(1L).get();
        assertEquals(countExpected, tickets);
    }

    @Test
    public void deleteByTicketId() {
        ticketRepository.findByUser(getUser());
        Object actualTicket = cacheManager.getCache(TICKETS).get(1L);
        assertNull(actualTicket);
    }

    private User getUser() {
        return userRepository.getOne(1L);
    }

    private Concert getConcert() {
        return concertRepository.getOne(1L);
    }
}