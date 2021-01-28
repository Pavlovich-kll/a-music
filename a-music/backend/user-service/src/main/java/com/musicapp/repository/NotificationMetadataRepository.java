package com.musicapp.repository;

import com.musicapp.domain.NotificationMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с таблицей метаданных уведомлений.
 *
 * @author i.dubrovin
 */
public interface NotificationMetadataRepository extends JpaRepository<NotificationMetadata, Long> {
}
