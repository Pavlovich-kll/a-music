package com.musicapp.repository;

import com.musicapp.domain.Concert;
import com.musicapp.domain.Ticket;
import com.musicapp.domain.User;
import com.musicapp.repository.initializer.ContainerDB;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

import static org.junit.Assert.*;

@SqlGroup({
        @Sql("/db/sql/countriesInsert.sql"),
        @Sql("/db/sql/citiesInsert.sql"),
        @Sql("/db/sql/userInsert.sql"),
        @Sql("/db/sql/concertsInsert.sql"),
        @Sql("/db/sql/ticketInsert.sql")
})
public class TicketRepositoryTest extends ContainerDB {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    ConcertRepository concertRepository;

    @Test
    public void findByConcert() {
        List<Ticket> tickets = ticketRepository.findByConcert(getConcert());
        assertFalse(tickets.isEmpty());
        Concert concertExpected = tickets.get(0).getConcert();
        Concert concertActual = ConcertRepositoryTest.getConcert();
        assertEquals(concertActual, concertExpected);
    }

    @Test
    public void findByUser() {
        List<Ticket> tickets = ticketRepository.findByUser(getUser());
        assertFalse(tickets.isEmpty());
    }

    @Test
    public void countUnsoldTicketsByConcertId() {
        int countExpected = ticketRepository.countUnsoldTicketsByConcertId(1);
        int countActual = 4;
        assertEquals(countActual, countExpected);
    }

    private Concert getConcert() {
        return concertRepository.getOne(1L);
    }

    private User getUser() {
        return userRepository.getOne(1L);
    }
}