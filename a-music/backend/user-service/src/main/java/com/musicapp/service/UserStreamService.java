package com.musicapp.service;

import org.springframework.messaging.MessageChannel;

/**
 * Интерфейс сервиса для взаимодействия с message broker.
 *
 * @author a.nagovicyn
 */
public interface UserStreamService {

    /**
     * Метод для получения исходящего канала сообщений
     *
     * @return исходящий канал сообщений
     */
    MessageChannel output();
}
