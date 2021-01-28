package com.musicapp.service.impl;

import com.musicapp.domain.Concert;
import com.musicapp.domain.Ticket;
import com.musicapp.domain.User;
import com.musicapp.dto.TicketCreateDto;
import com.musicapp.dto.TicketPatchDto;
import com.musicapp.exception.NotFoundException;
import com.musicapp.mapper.TicketMapper;
import com.musicapp.repository.TicketRepository;
import com.musicapp.service.ConcertService;
import com.musicapp.service.TicketService;
import com.musicapp.service.UserService;
import com.musicapp.util.constants.MessageConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Реализация сервиса для управления билетами.
 *
 * @author lizavetashpinkova
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final ConcertService concertService;
    private final UserService userService;

    @Override
    public List<Ticket> getAllTickets() {
        log.info("Sending tickets list");
        return ticketRepository.findAll();
    }

    @Override
    public Ticket getTicket(Long id) {
        log.info("Sending ticket with id : " + id);
        return ticketRepository.findById(id)
                .orElseThrow(() -> {
                            log.error("Ticket has not been found.");
                            return new NotFoundException(MessageConstants.TICKET_ID_NOT_FOUND, "id");
                        }
                );
    }

    @Override
    public List<Ticket> getTicketListByConcertId(Long concertId) {
        Concert concert = concertService.getConcert(concertId);
        log.info("Sending tickets list with concert id : " + concertId);
        return ticketRepository.findByConcert(concert);
    }

    @Override
    public List<Ticket> getTicketListByUserId(Long userId) {
        User user = userService.getById(userId);
        log.info("Sending tickets list with user id : " + userId);
        return ticketRepository.findByUser(user);
    }

    @Transactional
    @Override
    public void deleteTicket(Long id) {
        if (ticketRepository.findById(id).isPresent()) {
            ticketRepository.deleteById(id);
            log.info("Ticket has been deleted with id : " + id);
        } else {
            log.error("Ticket has not been found.");
            throw new NotFoundException(MessageConstants.TICKET_ID_NOT_FOUND, "id");
        }
    }

    @Transactional
    @Override
    public Ticket buyTicket(TicketCreateDto ticketCreateDto) {
        User user = userService.getById(ticketCreateDto.getUserId());
        Concert concert = concertService.getConcert(ticketCreateDto.getConcertId());
        Ticket ticket = ticketMapper.map(ticketCreateDto);
        ticket.setConcert(concert);
        ticket.setUser(user);
        ticketRepository.save(ticket);
        log.info("Ticket has been bought.");
        return ticket;
    }

    @Transactional
    @Override
    public Ticket updateTicket(Long id, TicketPatchDto ticketPatchDto) {
        Ticket ticket = ticketRepository.findById(id)
                .map(t -> ticketMapper.updateTicket(ticketPatchDto, t))
                .orElseThrow(() -> {
                    log.error("Ticket has not been found with id : " + id);
                    return new NotFoundException(MessageConstants.TICKET_ID_NOT_FOUND, "id");
                });
        log.info("Updating ticket with id : " + id);
        return ticketRepository.save(ticket);
    }

    @Override
    public int countUnsoldTicketsByConcertId(long concertId) {
        log.info("Sending number of unsold tickets with concert id : " + concertId);
        return ticketRepository.countUnsoldTicketsByConcertId(concertId);
    }
}
