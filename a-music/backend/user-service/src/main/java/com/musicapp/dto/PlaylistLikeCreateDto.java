package com.musicapp.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

/**
 * DTO для добавления лайка аудиозаписи
 */
@Value
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = PlaylistLikeCreateDto.PlaylistLikeCreateDtoBuilder.class)
public class PlaylistLikeCreateDto {

    Long id;
}

