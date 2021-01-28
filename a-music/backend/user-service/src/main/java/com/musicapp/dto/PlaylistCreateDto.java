package com.musicapp.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * DTO представление сущности для создания плейлиста.
 */
@Getter
@Setter
@Accessors(chain = true)
public class PlaylistCreateDto {

    @NotBlank
    private String title;
    @NotBlank
    private String description;
    private MultipartFile pic;
    Set<String> tracks;
}

