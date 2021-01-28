package com.musicapp.repository;

import com.musicapp.domain.Notification;
import com.musicapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Репозиторий для работы с таблицей уведомлений.
 *
 * @author i.dubrovin
 */
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    /**
     * Возвращает все уведомления пользователя
     *
     * @param user пользователь
     * @return лист уведомлений пользователя
     */
    List<Notification> findAllByUser(User user);
}
