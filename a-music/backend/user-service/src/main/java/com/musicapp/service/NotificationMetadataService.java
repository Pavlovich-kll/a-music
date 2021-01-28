package com.musicapp.service;

import com.musicapp.domain.NotificationMetadata;
import com.musicapp.dto.NotificationMetadataDto;

import java.util.List;

/**
 * Интерфейс сервиса для управления метаданными уведомлений.
 *
 * @author i.dubrovin
 */
public interface NotificationMetadataService {

    void createNotification(NotificationMetadataDto notificationMetadata);

    void updateNotification(Long id, NotificationMetadataDto notificationMetadata);

    List<NotificationMetadata> getAllMetadata();
}