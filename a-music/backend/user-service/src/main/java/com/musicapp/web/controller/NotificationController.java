package com.musicapp.web.controller;

import com.musicapp.dto.NotificationDto;
import com.musicapp.dto.NotificationMetadataDto;
import com.musicapp.mapper.NotificationMapper;
import com.musicapp.mapper.NotificationMetadataMapper;
import com.musicapp.service.NotificationMetadataService;
import com.musicapp.service.NotificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Методы для управления уведомлениями пользователя и метаданными уведомлений.
 *
 * @author i.dubrovin
 */
@Controller
@RequiredArgsConstructor
@CrossOrigin
@Api
public class NotificationController {

    private final NotificationMetadataService notificationMetadataService;
    private final NotificationService notificationService;
    private final NotificationMapper notificationMapper;
    private final NotificationMetadataMapper notificationMetadataMapper;

    /**
     * Метод для создания нового раздела уведомлений
     *
     * @param notificationMetadata DTO сущности метеданных уведомления
     */
    @ApiOperation("Метод для создания разделов уведомлений")
    @Secured({"ROLE_ADMIN"})
    @PostMapping("/notifications")
    public ResponseEntity<Void> createNotification(@RequestBody NotificationMetadataDto notificationMetadata) {
        notificationMetadataService.createNotification(notificationMetadata);

        return ResponseEntity.ok().build();
    }

    /**
     * Метод обновляет существующий раздел уведомлений
     *
     * @param id                   идентификатор метаданных уведомления
     * @param notificationMetadata DTO сущности метеданных уведомления с изменениями
     */
    @ApiOperation("Метод для изменения разделов уведомлений")
    @Secured({"ROLE_ADMIN"})
    @PutMapping("/notifications/{id}")
    public ResponseEntity<Void> updateNotification(@PathVariable Long id,
                                                   @RequestBody NotificationMetadataDto notificationMetadata) {

        notificationMetadataService.updateNotification(id, notificationMetadata);

        return ResponseEntity.ok().build();
    }

    /**
     * Метод настраивает способ уведомления у отдельного юзера по отдельному разделу уведомлений
     *
     * @param notification DTO сущности уведомления с изменениями прикрепленного за пользователем
     */
    @ApiOperation("Метод для установки способов уведомлений у юзера по одному за раз")
    @PutMapping("/notifications/user-notifications/{id}")
    public ResponseEntity<Void> setUserNotifications(@PathVariable Long id, @RequestBody NotificationDto notification) {
        notificationService.setUserNotifications(id, notification);

        return ResponseEntity.ok().build();
    }

    /**
     * Метод возвращает список всех существующих разделов уведомлений
     *
     * @return лист с DTO сущностей метаданных уведомлений
     */
    @ApiOperation("Метод для получения списка всех разделов уведомлений")
    @GetMapping("/notifications")
    public ResponseEntity<List<NotificationMetadataDto>> getAllNotifications() {
        return ResponseEntity.ok(notificationMetadataMapper.map(notificationMetadataService.getAllMetadata()));
    }

    /**
     * Метод возвращает список всех разделов и способов уведомлений прикрепленных за пользователем
     *
     * @param id идентификатор пользователя
     * @return лист с DTO сущностей уведомлений
     */
    @ApiOperation("Метод для получения разделов уведомлений юзера с настройками по id юзера.")
    @GetMapping("/notifications/user-notification/{id}")
    public ResponseEntity<List<NotificationDto>> getUserNotifications(@PathVariable Long id) {
        return ResponseEntity.ok(notificationMapper.map(notificationService.getUserNotifications(id)));
    }
}