package com.musicapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * DTO для представления существующей аудио записи
 */
@Value
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class AudioViewDto {

    String id;
    String title;
    String type;
    String author;
    String genre;
    String album;
    String track_id;
    String cover_id;
    Long likes;
}
