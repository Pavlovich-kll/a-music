package com.musicapp.service.impl;

import com.musicapp.domain.Author;
import com.musicapp.dto.AuthorCreateDto;
import com.musicapp.mapper.AuthorMapper;
import com.musicapp.repository.AuthorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepositoryMock;
    @Mock
    private AuthorMapper authorMapper;
    @InjectMocks
    private AuthorServiceImpl authorServiceMock;

    private Author testAuthor;

    @Before
    public void setUp() {

        testAuthor = new Author().setId(1L);
    }

    @Test
    public void whenGetAuthor_thenReturnTestAuthor() {

        when(authorRepositoryMock.findById(anyLong())).thenReturn(Optional.of(testAuthor));

        authorServiceMock.getAuthor(anyLong()).ifPresent(author -> assertEquals(testAuthor.getId(), author.getId()));
    }

    @Test
    public void whenCreatedAuthor_thenReturnTestAuthor() {

        when(authorRepositoryMock.save(any())).thenReturn(testAuthor);

        assertEquals(testAuthor.getId(), authorServiceMock.createAuthor(new AuthorCreateDto("name", "description")).getId());
    }
}
