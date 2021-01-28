package com.musicapp.mapper;

import com.musicapp.domain.Genre;
import com.musicapp.dto.GenreCreateDto;
import com.musicapp.dto.GenreDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Маппер сущности жанра.
 */
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface GenreMapper {

    GenreDto map(Genre genre);

    List<GenreDto> map(List<Genre> genres);

    Genre map(GenreCreateDto genreCreateDto);
}
