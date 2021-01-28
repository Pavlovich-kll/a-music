package com.musicapp.repository;

import com.musicapp.domain.DatabaseFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для работы с файлами, хранящимися в базе
 */
@Repository
public interface DatabaseFileRepository extends JpaRepository<DatabaseFile, Long> {

    Optional<DatabaseFile> findByName(String fileName);
}
