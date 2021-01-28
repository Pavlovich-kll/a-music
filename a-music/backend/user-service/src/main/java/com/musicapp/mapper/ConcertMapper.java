package com.musicapp.mapper;

import com.musicapp.domain.Concert;
import com.musicapp.dto.ConcertCreateDto;
import com.musicapp.dto.ConcertDto;
import com.musicapp.dto.ConcertUpdateDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * Маппер сущности концерта.
 */
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ConcertMapper {

    ConcertDto map(Concert concert);

    List<ConcertDto> map(List<Concert> concerts);

    Concert update(ConcertUpdateDto concertDto, @MappingTarget Concert concert);

    Concert map(ConcertCreateDto concertCreateDto);
}
