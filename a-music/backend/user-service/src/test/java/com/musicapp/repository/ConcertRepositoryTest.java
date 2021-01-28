package com.musicapp.repository;

import com.musicapp.domain.Concert;
import com.musicapp.dto.UnsoldTicketsForConcertDto;
import com.musicapp.repository.initializer.ContainerDB;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Pavlovich_Kirill
 */

@SqlGroup({
        @Sql("/db/sql/countriesInsert.sql"),
        @Sql("/db/sql/citiesInsert.sql"),
        @Sql("/db/sql/userInsert.sql"),
        @Sql("/db/sql/concertsInsert.sql"),
        @Sql("/db/sql/ticketInsert.sql")
})
public class ConcertRepositoryTest extends ContainerDB {

    @Autowired
    private ConcertRepository concertRepository;

    /**
     * проверка по ID на существование концерта после сохранения в БД контейнера Docker.
     */
    @Test
    public void concertIsExistAfterSaving() {
        Concert concert = getConcert();
        Concert saved = concertRepository.saveAndFlush(concert);
        assertEquals(1, saved.getId());
    }

    /**
     * проверяем, есть ли еще концерты после сохранения.
     */
    @Test
    public void findOtherConcerts() {
        Concert concert = getConcert();
        Concert saved = concertRepository.saveAndFlush(concert);
        assertTrue(concertRepository.count() < 3);
    }

    /**
     * Сравнивается правильность нахождения концерта по дате.
     * Успех в случае нахождения концерта после удтвержденной даты;
     */
    @Test
    public void findAllConcertByConcertDateIsAfter() {
        Concert concert = getConcert();
        List<Concert> actualListConcerts = new ArrayList<>();
        actualListConcerts.add(concert);

        LocalDate date = LocalDate.of(2020, 7, 28);
        List<Concert> expectedListConcertsFromDB = concertRepository.findAllConcertByConcertDateIsAfter(date);
        assertEquals(actualListConcerts, expectedListConcertsFromDB);
    }

    @Test
    public void countUnsoldTicketsForEachConcertByConcertDateIsAfter() {
        LocalDate dateOfPayment = LocalDate.of(2020, 7, 28);
        List<UnsoldTicketsForConcertDto> unsoldTicketsForConcertDtos = concertRepository.countUnsoldTicketsForEachConcertByConcertDateIsAfter(dateOfPayment);//возвращает один концерт с остатком непроданных билетов
        int numberOfUnsoldTickets = unsoldTicketsForConcertDtos.get(0).getNumberOfUnsoldTickets();//получаем кол-во непроданных билетов
        assertTrue(numberOfUnsoldTickets > 3);
    }

    static Concert getConcert() {
        LocalDate date = LocalDate.of(2020, 7, 29);
        Concert concert = new Concert();
        concert.setId(1);
        concert.setTitle("Concert");
        concert.setPerformer("concert");
        concert.setAgeRestriction(18);
        concert.setConcertDate(date);
        concert.setTotalNumberOfTickets(5);
        concert.setBasePrice(1800);
        return concert;
    }
}