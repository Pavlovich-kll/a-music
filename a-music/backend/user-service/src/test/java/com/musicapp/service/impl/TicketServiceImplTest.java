package com.musicapp.service.impl;

import com.musicapp.domain.Concert;
import com.musicapp.domain.Ticket;
import com.musicapp.domain.User;
import com.musicapp.dto.TicketPatchDto;
import com.musicapp.exception.NotFoundException;
import com.musicapp.repository.TicketRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class TicketServiceImplTest {

    @Mock
    private TicketRepository ticketRepositoryMock;
    @InjectMocks
    private TicketServiceImpl ticketService;

    private List<Ticket> testTickets;
    private Ticket firstTestTicket;
    private Concert testConcert;
    private User testUser;

    @Before
    public void setUp() {

        testConcert = new Concert().setId(1L);
        testUser = new User().setId(1L);

        testTickets = LongStream.rangeClosed(1, 10)
                .mapToObj(id -> new Ticket().setId(id))
                .map(ticket -> ticket.setConcert(testConcert))
                .map(ticket -> ticket.setUser(testUser))
                .collect(Collectors.toList());


        firstTestTicket = testTickets.get(0);
    }

    @Test
    public void whenGetAllTickets_thenGetTestTickets() {
        when(ticketRepositoryMock.findAll()).thenReturn(testTickets);

        assertEquals(testTickets, ticketService.getAllTickets());
    }

    @Test
    public void whenGetTicket_thenReturnTestTicket() {
        when(ticketRepositoryMock.findById(1L)).thenReturn(java.util.Optional.ofNullable(firstTestTicket));

        Ticket ticket = ticketService.getTicket(firstTestTicket.getId());

        assertEquals(firstTestTicket, ticket);
    }

    @Test(expected = NotFoundException.class)
    public void whenGetNotExistTicket_thenReturnException() {
        ticketService.getTicket(11L);
    }

    @Test
    public void whenGetTicketListByConcertId_thenReturnListTickets() {
        when(ticketRepositoryMock.findByConcert(testConcert)).thenReturn(testTickets);
    }

    @Test
    public void whenGetTicketListByUserId_thenReturnListTickets() {
        when(ticketRepositoryMock.findByUser(testUser)).thenReturn(testTickets);
    }

    @Test(expected = NotFoundException.class)
    public void whenDeleteTicket_thenTicketRepositoryInvoked() {
        ticketService.deleteTicket(1L);

        verify(ticketRepositoryMock).deleteById(1L);
    }

    @Test
    public void whenBuyTicker_thenCreatedTicket() {
        when(ticketRepositoryMock.save(any())).thenReturn(firstTestTicket);
    }

    @Test(expected = NotFoundException.class)
    public void wheUpdateTicket_thenReturnUpdatedTicket() {
        ticketService.updateTicket(1L, TicketPatchDto.builder().build());

        verify(ticketRepositoryMock).save(firstTestTicket);
    }

    @Test
    public void whenCountUnsoldTicketsByConcertId_thenTicketsRepositoryInvoked() {
        long concertId = 1;

        ticketService.countUnsoldTicketsByConcertId(concertId);

        verify(ticketRepositoryMock).countUnsoldTicketsByConcertId(concertId);
    }
}