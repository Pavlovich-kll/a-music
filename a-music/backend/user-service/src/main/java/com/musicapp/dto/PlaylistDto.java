package com.musicapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.Set;

/**
 * DTO представление сущности плейлиста.
 */
@Value
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class PlaylistDto {

    String id;
    String title;
    String description;
    Integer track_count;
    Set<AudioViewDto> tracks;
    String pic;
    Integer likes;
}
