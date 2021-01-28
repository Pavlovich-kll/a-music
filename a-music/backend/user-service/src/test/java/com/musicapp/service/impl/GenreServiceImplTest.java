package com.musicapp.service.impl;

import com.musicapp.domain.Genre;
import com.musicapp.dto.GenreCreateDto;
import com.musicapp.mapper.GenreMapper;
import com.musicapp.repository.GenreRepository;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GenreServiceImplTest {

    @Mock
    private GenreRepository genreRepositoryMock;

    @Mock
    private GenreMapper genreMapper;

    @InjectMocks
    private GenreServiceImpl genreService;

    private List<Genre> testGenres;
    private Genre testGenre;

    @Before
    public void setUp() {

        testGenres = LongStream.rangeClosed(1, 10)
                .mapToObj(id -> new Genre().setId(id))
                .collect(Collectors.toList());

        testGenre = testGenres.iterator().next();
    }

    @Test
    public void whenGetAllGenres_thenReturnListTestGenres() {

        when(genreRepositoryMock.findAll()).thenReturn(testGenres);

        assertEquals(testGenres, genreService.getAllGenres());
    }

    @Test
    public void whenGetGenre_thenReturnTestGenre() {

        when(genreRepositoryMock.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(testGenre));

        genreService.getGenre(anyLong()).ifPresent(genre -> assertEquals(testGenre.getId(), genre.getId()));
    }

    @Test
    public void whenCreatedGenre_thenReturnTestGenre() {

        when(genreRepositoryMock.save(any())).thenReturn(testGenre);

        assertEquals(testGenre.getId(), genreService.createGenre(new GenreCreateDto("title")).getId());
    }
}
