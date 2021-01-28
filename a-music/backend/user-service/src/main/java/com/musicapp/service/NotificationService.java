package com.musicapp.service;

import com.musicapp.domain.Notification;
import com.musicapp.dto.NotificationDto;

import java.util.List;

/**
 * Интерфейс сервиса для управления уведомлениями пользователя.
 *
 * @author i.dubrovin
 */
public interface NotificationService {

    void setUserNotifications(Long id, NotificationDto notification);

    List<Notification> getUserNotifications(Long userId);
}