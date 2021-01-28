package com.musicapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * DTO представление сущности для добавления нового трека в плейлист.
 */
@Accessors(chain = true)
@Value
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class PlaylistPutDto {

    String id;
    Set<String> tracks;
}
