package com.musicapp.service;

import com.musicapp.domain.Author;
import com.musicapp.dto.AuthorCreateDto;

import java.util.Optional;

public interface AuthorService {

    Optional<Author> getAuthor(long id);

    Author createAuthor(AuthorCreateDto author);

    Optional<Author> getAuthorByName(String authorName);
}
