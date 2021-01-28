package com.musicapp.repository;

import com.musicapp.domain.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для работы с таблицей музыкальных альбомов
 */
@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    Optional<Album> findByTitle(String title);
}
