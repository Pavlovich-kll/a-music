package com.musicapp.service.impl;

import com.musicapp.domain.Notification;
import com.musicapp.domain.User;
import com.musicapp.dto.NotificationDto;
import com.musicapp.exception.NotFoundException;
import com.musicapp.mapper.NotificationMapper;
import com.musicapp.repository.NotificationRepository;
import com.musicapp.service.NotificationService;
import com.musicapp.service.UserService;
import com.musicapp.util.constants.MessageConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Реализация сервиса для управления уведомлениями пользователя.
 *
 * @author i.dubrovin
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserService userService;
    private final NotificationMapper notificationMapper;

    @Transactional
    @Override
    public void setUserNotifications(Long id, NotificationDto notificationDto) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User has not been found.");
                    return new NotFoundException(MessageConstants.USER_ID_NOT_FOUND, "id");
                });
        log.info("User notifications has been set.");
        notificationRepository.save(notificationMapper.updateNotification(notificationDto, notification));
    }

    @Override
    public List<Notification> getUserNotifications(Long userId) {
        User user = userService.getById(userId);
        log.info("Sending list of user notifications with id: " + userId);
        return notificationRepository.findAllByUser(user);
    }
}