package com.musicapp.service.impl;

import com.musicapp.domain.NotificationMetadata;
import com.musicapp.dto.NotificationMetadataDto;
import com.musicapp.exception.NotFoundException;
import com.musicapp.mapper.NotificationMetadataMapper;
import com.musicapp.repository.NotificationMetadataRepository;
import com.musicapp.service.NotificationMetadataService;
import com.musicapp.util.constants.MessageConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Реализация сервиса для управления метаданными уведомлений.
 *
 * @author i.dubrovin
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class NotificationMetadataServiceImpl implements NotificationMetadataService {

    private final NotificationMetadataRepository notificationMetadataRepository;
    private final NotificationMetadataMapper notificationMetadataMapper;

    @Transactional
    @Override
    public void createNotification(NotificationMetadataDto notificationMetadataDto) {
        NotificationMetadata notificationMetadata = notificationMetadataMapper.map(notificationMetadataDto);
        notificationMetadataRepository.save(notificationMetadata);
        log.info("Notification has been created");
    }

    @Transactional
    @Override
    public void updateNotification(Long id, NotificationMetadataDto notificationMetadataDto) {
        NotificationMetadata notificationMetadata = notificationMetadataRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MessageConstants.USER_ID_NOT_FOUND, "id"));

        notificationMetadataRepository.save(notificationMetadataMapper.updateNotificationMetadata(
                notificationMetadataDto, notificationMetadata));
        log.info("Notification has been updated with id : " + id);
    }

    @Override
    public List<NotificationMetadata> getAllMetadata() {
        log.info("Sending metadata");
        return notificationMetadataRepository.findAll();
    }
}
