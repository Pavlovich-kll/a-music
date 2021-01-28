package com.musicapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * DTO представление сущности жанра.
 *
 * @author lizavetashpinkova
 */
@Value
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class GenreDto {

    long id;
    String title;
}
