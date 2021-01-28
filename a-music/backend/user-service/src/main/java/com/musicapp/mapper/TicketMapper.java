package com.musicapp.mapper;

import com.musicapp.domain.Ticket;
import com.musicapp.dto.TicketCreateDto;
import com.musicapp.dto.TicketDto;
import com.musicapp.dto.TicketPatchDto;
import org.mapstruct.*;

import java.util.List;

/**
 * Маппер сущности билета.
 */
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {ConcertMapper.class, UserMapper.class})
public interface TicketMapper {

    TicketDto map(Ticket ticket);

    List<TicketDto> map(List<Ticket> tickets);

    Ticket map(TicketCreateDto ticketCreateDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Ticket updateTicket(TicketPatchDto ticketPatchDto, @MappingTarget Ticket ticket);
}
