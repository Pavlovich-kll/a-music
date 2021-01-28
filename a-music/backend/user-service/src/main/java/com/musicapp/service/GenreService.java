package com.musicapp.service;

import com.musicapp.domain.Genre;
import com.musicapp.dto.GenreCreateDto;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс сервиса для управления жанрами.
 *
 * @author lizavetashpinkova
 */
public interface GenreService {

    Optional<Genre> getGenre(long id);

    Genre createGenre(GenreCreateDto genreCreateDto);

    /**
     * Возвращает список все жанров.
     *
     * @return список всех жанров
     */
    List<Genre> getAllGenres();

    Optional<Genre> getGenreByTitle(String genreTitle);
}
