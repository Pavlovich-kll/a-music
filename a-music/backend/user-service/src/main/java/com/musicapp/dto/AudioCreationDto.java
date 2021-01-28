package com.musicapp.dto;

import com.musicapp.domain.AudioType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * DTO для создания новой аудио записи
 */
@Getter
@Setter
@Accessors(chain = true)
public class AudioCreationDto {

    @NotBlank
    private String title;
    @NotNull
    private AudioType type;
    @Min(1)
    private long authorId;
    @Min(1)
    private long genreId;
    @Min(1)
    private long albumId;
    @NotNull
    private MultipartFile file;
    private MultipartFile cover;
}
