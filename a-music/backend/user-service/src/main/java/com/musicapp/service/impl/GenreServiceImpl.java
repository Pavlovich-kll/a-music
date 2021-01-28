package com.musicapp.service.impl;

import com.musicapp.domain.Genre;
import com.musicapp.dto.GenreCreateDto;
import com.musicapp.mapper.GenreMapper;
import com.musicapp.repository.GenreRepository;
import com.musicapp.service.GenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Реализация сервиса для управления жанрами.
 *
 * @author lizavetashpinkova
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Override
    public Optional<Genre> getGenre(long id) {
        return genreRepository.findById(id);
    }

    @Transactional
    @Override
    public Genre createGenre(GenreCreateDto genreCreateDto) {
        return genreRepository.save(genreMapper.map(genreCreateDto));
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public Optional<Genre> getGenreByTitle(String genreTitle) {
        return genreRepository.findByTitle(genreTitle);
    }
}
