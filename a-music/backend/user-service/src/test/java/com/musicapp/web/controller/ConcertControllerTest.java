package com.musicapp.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicapp.domain.Concert;
import com.musicapp.dto.ConcertCreateDto;
import com.musicapp.dto.ConcertDto;
import com.musicapp.dto.ConcertUpdateDto;
import com.musicapp.exception.NotFoundException;
import com.musicapp.mapper.ConcertMapper;
import com.musicapp.mapper.ConcertMapperImpl;
import com.musicapp.service.ConcertService;
import com.musicapp.service.TicketService;
import com.musicapp.web.config.MockSpringSecurityTestConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ConcertController.class)
@Import(MockSpringSecurityTestConfiguration.class)
@WithMockUser
public class ConcertControllerTest {

    @TestConfiguration
    static class Configuration {

        @Bean
        public ConcertMapper concertMapper() {
            return new ConcertMapperImpl();
        }

        @Bean
        public MessageSource messageSource() {
            MessageSource messageSourceMock = Mockito.mock(MessageSource.class);
            when(messageSourceMock.getMessage(anyString(), any(), any())).thenReturn("");

            return messageSourceMock;
        }
    }

    private static final String CONCERTS_ENDPOINT = "/concerts";
    private static final String UPCOMING_CONCERTS_ENDPOINT = "/concerts/upcoming";
    private static final String CONCERT_BY_ID_ENDPOINT = "/concerts/1";
    private static final String COUNT_UNSOLD_TICKETS_ENDPOINT = CONCERTS_ENDPOINT + "/count-unsold-tickets";
    private static final String COUNT_UNSOLD_TICKETS_BY_ID_ENDPOINT = CONCERT_BY_ID_ENDPOINT + "/count-unsold-tickets";

    @MockBean
    private ConcertService concertServiceMock;
    @MockBean
    private TicketService ticketServiceMock;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private List<Concert> testConcerts;
    private Concert firstTestConcert;

    private int ageRestriction;
    private int totalNumberOfTickets;
    private int basePrice;
    private long cityId;
    private String title;
    private String performer;
    private LocalDate concertDate;
    private String concertJsonDto;
    private ConcertDto concertDto;
    private ConcertCreateDto concertCreateDto;

    @Before
    public void setUp() throws JsonProcessingException {

        testConcerts = LongStream.rangeClosed(1, 10)
                .mapToObj(id -> new Concert().setId(id))
                .collect(Collectors.toList());

        firstTestConcert = testConcerts.iterator().next();

        title = "Rock";
        performer = "Linkin";
        ageRestriction = 21;
        concertDate = LocalDate.of(2020, 11, 21);
        totalNumberOfTickets = 180;
        basePrice = 120;
        cityId = 1L;


        concertDto = ConcertDto.builder()
                .withId(100L)
                .withTitle(title)
                .withPerformer(performer)
                .withConcertDate(concertDate)
                .withAgeRestriction(ageRestriction)
                .withCityId(cityId)
                .build();

        concertCreateDto = ConcertCreateDto.builder()
                .withTitle(title)
                .withPerformer(performer)
                .withConcertDate(concertDate)
                .withAgeRestriction(ageRestriction)
                .withTotalNumberOfTickets(totalNumberOfTickets)
                .withBasePrice(basePrice)
                .withCityId(cityId)
                .build();

        concertJsonDto = objectMapper.writeValueAsString(concertDto);
    }

    @Test
    public void givenValidConcertId_whenGetConcert_thenGetTestConcertDto() throws Exception {
        when(concertServiceMock.getConcert(firstTestConcert.getId())).thenReturn(firstTestConcert);

        mockMvc.perform(get(CONCERT_BY_ID_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(is(firstTestConcert.getId()), Long.class));
    }

    @Test
    public void givenInvalidConcertId_whenGetConcert_thenStatusNotFound() throws Exception {
        long invalidConcertId = 99;

        doThrow(new NotFoundException("", "")).when(concertServiceMock).getConcert(invalidConcertId);

        mockMvc.perform(get(CONCERTS_ENDPOINT + "/" + invalidConcertId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenRoleUser_whenDeleteConcert_thenStatusForbidden() throws Exception {
        mockMvc.perform(delete(CONCERT_BY_ID_ENDPOINT))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenConcertExists_whenDeleteConcert_thenStatusOk() throws Exception {
        mockMvc.perform(delete(CONCERT_BY_ID_ENDPOINT))
                .andExpect(status().isOk());

        verify(concertServiceMock).deleteConcert(1);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenConcertDoNotExist_whenDeleteConcert_thenStatusNotFound() throws Exception {
        doThrow(new EmptyResultDataAccessException(0)).when(concertServiceMock).deleteConcert(anyLong());

        mockMvc.perform(delete(CONCERT_BY_ID_ENDPOINT))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenGetAllConcerts_thenGetTestConcertsDto() throws Exception {
        when(concertServiceMock.getAllConcerts()).thenReturn(testConcerts);

        mockMvc.perform(get(CONCERTS_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(testConcerts.size())));
    }

    @Test
    public void whenGetAllConcertsByConcertDateIsAfter_thenGetTestConcertsDto() throws Exception {
        when(concertServiceMock.getAllConcertByConcertDateIsAfter(any())).thenReturn(testConcerts);

        mockMvc.perform(get(CONCERTS_ENDPOINT)
                .param("concertDateAfter", LocalDate.MIN.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(testConcerts.size())));
    }

    @Test
    public void whenGetUnsoldTicketsForEachConcert_thenConcertServiceInvoked() throws Exception {
        mockMvc.perform(get(COUNT_UNSOLD_TICKETS_ENDPOINT))
                .andExpect(status().isOk());

        verify(concertServiceMock).countUnsoldTicketsForEachConcertByConcertDateIsAfter(any());
    }

    @Test
    public void whenGetUnsoldTicketsCount_thenReturnNumber() throws Exception {
        int unsoldTicketsCount = 10;

        when(ticketServiceMock.countUnsoldTicketsByConcertId(anyLong())).thenReturn(unsoldTicketsCount);

        MvcResult mvcResult = mockMvc.perform(get(COUNT_UNSOLD_TICKETS_BY_ID_ENDPOINT))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(unsoldTicketsCount, Integer.parseInt(mvcResult.getResponse().getContentAsString()));
    }

    @Test
    public void givenRoleUser_whenUpdateConcert_thenStatusForbidden() throws Exception {
        mockMvc.perform(put(CONCERT_BY_ID_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(objectMapper.writeValueAsString(getValidConcertUpdateDto())))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void givenRoleAdmin_whenUpdateConcert_thenStatusOk() throws Exception {
        mockMvc.perform(put(CONCERT_BY_ID_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(objectMapper.writeValueAsString(getValidConcertUpdateDto())))
                .andExpect(status().isOk());

        verify(concertServiceMock).updateConcert(anyLong(), any());
    }

    @Test
    public void givenInvalidDto_whenUpdateConcert_thenStatusBadRequest() throws Exception {
        mockMvc.perform(put(CONCERT_BY_ID_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(objectMapper.writeValueAsString(ConcertDto.builder().build())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void givenInvalidConcertId_whenUpdateConcert_thenStatusNotFound() throws Exception {
        long invalidConcertId = 99;

        doThrow(new NotFoundException("", "")).when(concertServiceMock).updateConcert(eq(invalidConcertId), any());

        mockMvc.perform(put(CONCERTS_ENDPOINT + "/" + invalidConcertId)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(objectMapper.writeValueAsString(getValidConcertUpdateDto())))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenCreateConcert_thenCreated() throws Exception {
        mockMvc.perform(post(CONCERTS_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(concertJsonDto))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(concertServiceMock).create(any());
    }

    private ConcertUpdateDto getValidConcertUpdateDto() {
        return ConcertUpdateDto.builder()
                .withTitle("title")
                .withPerformer("performer")
                .withConcertDate(LocalDate.MAX)
                .withAgeRestriction(18)
                .withTotalNumberOfTickets(100)
                .withBasePrice(100)
                .withCityId(1)
                .build();
    }
}