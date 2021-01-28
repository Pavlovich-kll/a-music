package com.musicapp.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * DTO для создания нового музыкального альбома
 */
@Getter
@Setter
@Accessors(chain = true)
public class AlbumCreationDto {

    @NotNull
    @Size(min = 1)
    private String title;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate releaseDate;
    private MultipartFile cover;
}
