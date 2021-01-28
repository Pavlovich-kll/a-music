package com.musicapp.service.impl;

import com.musicapp.domain.Author;
import com.musicapp.dto.AuthorCreateDto;
import com.musicapp.mapper.AuthorMapper;
import com.musicapp.repository.AuthorRepository;
import com.musicapp.service.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    public Optional<Author> getAuthor(long id) {
        return authorRepository.findById(id);
    }

    @Transactional
    @Override
    public Author createAuthor(AuthorCreateDto author) {
        return authorRepository.save(authorMapper.map(author));
    }

    @Override
    public Optional<Author> getAuthorByName(String authorName) {
        return authorRepository.findByName(authorName);
    }
}
