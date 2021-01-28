package com.musicapp.service.impl;

import com.musicapp.domain.Concert;
import com.musicapp.dto.ConcertCreateDto;
import com.musicapp.dto.ConcertUpdateDto;
import com.musicapp.exception.NotFoundException;
import com.musicapp.mapper.ConcertMapperImpl;
import com.musicapp.repository.ConcertRepository;
import com.musicapp.service.CityService;
import com.musicapp.service.ConcertService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ConcertServiceImplTest {

    @Mock
    private ConcertRepository concertRepositoryMock;
    @Mock
    private CityService cityService;
    private ConcertService concertService;
    private List<Concert> testConcerts;
    private Concert firstTestConcert;

    @Before
    public void setUp() {
        concertService = new ConcertServiceImpl(concertRepositoryMock, new ConcertMapperImpl(), cityService);

        testConcerts = LongStream.rangeClosed(1, 10)
                .mapToObj(id -> {
                    Concert concert = new Concert();
                    concert.setId(id);
                    return concert;
                })
                .collect(Collectors.toList());

        firstTestConcert = testConcerts.iterator().next();
    }

    @Test
    public void whenDeleteConcert_thenConcertRepositoryInvoked() {
        concertService.deleteConcert(Long.MAX_VALUE);

        verify(concertRepositoryMock, times(1)).deleteById(Long.MAX_VALUE);
    }

    @Test
    public void whenGetAllConcerts_thenGetTestConcerts() {
        when(concertRepositoryMock.findAll()).thenReturn(testConcerts);

        assertEquals(testConcerts, concertService.getAllConcerts());
    }

    @Test
    public void whenGetAllConcertByConcertDateIsAfter_thenGetTestConcerts() {
        when(concertRepositoryMock.findAllConcertByConcertDateIsAfter(any())).thenReturn(testConcerts);

        assertEquals(testConcerts, concertService.getAllConcertByConcertDateIsAfter(LocalDate.MIN));
    }

    @Test
    public void whenCountUnsoldTicketsForEachConcertByConcertDateIsAfter_thenConcertRepositoryInvoked() {
        concertService.countUnsoldTicketsForEachConcertByConcertDateIsAfter(LocalDate.MIN);

        verify(concertRepositoryMock, times(1)).countUnsoldTicketsForEachConcertByConcertDateIsAfter(LocalDate.MIN);
    }

    @Test
    public void whenGetConcert_thenReturnTestConcert() {
        when(concertRepositoryMock.findById(firstTestConcert.getId())).thenReturn(Optional.of(firstTestConcert));

        Concert concert = concertService.getConcert(firstTestConcert.getId());

        assertEquals(firstTestConcert, concert);
    }

    @Test(expected = NotFoundException.class)
    public void givenEntityDoNotExists_whenUpdateConcert_thenNotFoundException() {
        concertService.updateConcert(1, ConcertUpdateDto.builder().build());
    }

    @Test
    public void givenEntityExists_whenUpdateConcert_thenConcertUpdated() {
        when(concertRepositoryMock.findById(firstTestConcert.getId())).thenReturn(Optional.of(firstTestConcert));

        concertService.updateConcert(firstTestConcert.getId(), ConcertUpdateDto.builder().build());

        verify(concertRepositoryMock, times(1)).save(firstTestConcert);
    }

    @Test
    public void whenCreate_thenRepositoryInvoked() {
        when(concertRepositoryMock.save(any())).thenReturn(firstTestConcert);

        concertService.create(ConcertCreateDto.builder().build());

        verify(cityService, times(1)).getById(0L);

        verify(concertRepositoryMock, times(1)).save(firstTestConcert);
    }
}