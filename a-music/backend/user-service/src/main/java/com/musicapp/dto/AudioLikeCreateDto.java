package com.musicapp.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

/**
 * DTO для добавления лайка аудиозаписи
 */
@Value
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = AudioLikeCreateDto.AudioLikeCreateDtoBuilder.class)
public class AudioLikeCreateDto {

    Long track_id;
}

