package com.musicapp.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicapp.domain.Ticket;
import com.musicapp.dto.TicketCreateDto;
import com.musicapp.dto.TicketDto;
import com.musicapp.dto.TicketPatchDto;
import com.musicapp.mapper.*;
import com.musicapp.service.TicketService;
import com.musicapp.service.UserService;
import com.musicapp.web.config.MockSpringSecurityTestConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TicketController.class)
@Import(MockSpringSecurityTestConfiguration.class)
@WithMockUser
public class TicketControllerTest {

    @TestConfiguration
    static class Configuration {
        @Bean
        public TicketMapper ticketMapper() {
            return new TicketMapperImpl(concertMapper(), userMapper());
        }

        @Bean
        public ConcertMapper concertMapper() {
            return new ConcertMapperImpl();
        }

        @Bean
        public UserMapper userMapper() {
            return new UserMapperImpl();
        }
    }

    @MockBean
    private TicketService ticketServiceMock;
    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private int price;
    private int newPrice;

    private String ticketJsonDto;
    private String ticketCreateJsonDto;
    private String ticketPatchJsonDto;

    private LocalDate dateOfPayment;

    private Ticket ticket;
    private TicketDto ticketDto;
    private TicketCreateDto ticketCreateDto;
    private TicketPatchDto ticketPatchDto;

    private List<Ticket> testTicket;

    private static final String TICKETS_ENDPOINT = "/tickets";
    private static final String BUY_TICKET_ENDPOINT = "/tickets/buy-ticket";

    @Before
    public void setUp() throws JsonProcessingException {

        testTicket = LongStream.rangeClosed(1, 10)
                .mapToObj(id -> new Ticket()
                        .setId(id)
                        .setUser(userService.getById(1L)))
                .collect(Collectors.toList());
        price = 20;
        newPrice = 120;
        dateOfPayment = LocalDate.of(2020, 11, 21);

        ticket = new Ticket();
        ticket.setId(100);
        ticket.setPrice(price);
        ticket.setDateOfPayment(dateOfPayment);

        ticketDto = TicketDto.builder()
                .withId(100L)
                .withPrice(price)
                .withDateOfPayment(dateOfPayment)
                .build();

        ticketCreateDto = TicketCreateDto.builder()
                .withPrice(price)
                .withDateOfPayment(dateOfPayment)
                .withUserId(1L)
                .build();

        ticketPatchDto = TicketPatchDto.builder()
                .withPrice(newPrice)
                .withDateOfPayment(dateOfPayment)
                .build();

        ticketJsonDto = objectMapper.writeValueAsString(ticketDto);
        ticketCreateJsonDto = objectMapper.writeValueAsString(ticketDto);
        ticketPatchJsonDto = objectMapper.writeValueAsString(ticketPatchDto);
    }

    @Test
    public void whenGetTicket_thenGetTicket() throws Exception {
        when(ticketServiceMock.getTicket(100L)).thenAnswer(invocation -> new Ticket()
                .setId(100)
                .setPrice(price)
                .setDateOfPayment(dateOfPayment));

        mockMvc.perform(get(TICKETS_ENDPOINT + "/{id}", 100))
                .andExpect(status().isOk())
                .andExpect(content().json(ticketJsonDto));

        verify(ticketServiceMock).getTicket(100L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenDeleteTicket_thenTicketServiceInvoked() throws Exception {
        mockMvc.perform(delete(TICKETS_ENDPOINT + "/{id}", 100))
                .andExpect(status().isOk());

        verify(ticketServiceMock).deleteTicket(100L);
    }

    @Test
    public void whenGetTicketListForUser_thenTicketServiceInvoked() throws Exception {
        mockMvc.perform(get("/users/{userId}/tickets", 1L))
                .andExpect(status().isOk());

        verify(ticketServiceMock).getTicketListByUserId(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenGetTicketsForConcert_thenTicketServiceInvoked() throws Exception {
        mockMvc.perform(get("/concerts/{concertId}/tickets", 100L))
                .andExpect(status().isOk());

        verify(ticketServiceMock).getTicketListByConcertId(100L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenUpdateTicket_thenGetUpdatedTicket() throws Exception {
        when(ticketServiceMock.updateTicket(100L, ticketPatchDto)).thenReturn(ticket);

        mockMvc.perform(put(TICKETS_ENDPOINT + "/{id}", 100L)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(ticketPatchJsonDto))
                .andExpect(status().isOk())
                .andExpect(content().json(ticketJsonDto));
    }

    @Test
    public void whenGetAllTickets_thenGetTestTicketsDto() throws Exception {
        when(ticketServiceMock.getAllTickets()).thenReturn(testTicket);

        mockMvc.perform(get(TICKETS_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(testTicket.size())));
    }

    @Test
    public void whenCreateTicket_thenTicketCreated() throws Exception {
        mockMvc.perform(post(TICKETS_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(ticketCreateJsonDto))
                .andExpect(status().isCreated());
    }
}
