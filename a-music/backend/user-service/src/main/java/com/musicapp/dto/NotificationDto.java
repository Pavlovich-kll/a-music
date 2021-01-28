package com.musicapp.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * DTO представление уведомлений
 *
 * @author i.dubrovin
 */
@Value
@AllArgsConstructor
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = NotificationDto.NotificationDtoBuilder.class)
public class NotificationDto {

    long id;
    boolean mobileNotification;
    boolean mailNotification;
    boolean textNotification;
}
