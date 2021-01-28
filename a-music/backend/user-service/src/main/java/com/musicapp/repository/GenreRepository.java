package com.musicapp.repository;

import com.musicapp.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Репозиторий для работы с таблицей жанров.
 *
 * @author lizavetashpinkova
 */
public interface GenreRepository extends JpaRepository<Genre, Long> {

    Optional<Genre> findByTitle(String title);
}
