package com.musicapp.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * DTO представление метаданных уведомлений
 *
 * @author i.dubrovin
 */
@Value
@AllArgsConstructor
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = NotificationMetadataDto.NotificationMetadataDtoBuilder.class)
public class NotificationMetadataDto {

    long id;
    String name;
    String description;
    String secondaryDescription;
}
