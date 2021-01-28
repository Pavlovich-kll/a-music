package com.musicapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

/**
 * DTO для представления существующего музыкального альбома
 */
@Value
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class AlbumViewDto {

    long id;
    String title;
    LocalDate releaseDate;
    Long coverId;
}
