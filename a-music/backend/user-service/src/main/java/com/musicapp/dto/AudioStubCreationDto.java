package com.musicapp.dto;

import com.musicapp.domain.AudioType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * DTO заглушка для создания новой аудио записи
 */
@Getter
@Setter
@Accessors(chain = true)
public class AudioStubCreationDto {

    @NotBlank
    private String title;
    private AudioType type;
    @NotBlank
    private String authorName;
    private List<String> genreName;
    @NotBlank
    private String albumName;
    @NotNull
    private MultipartFile track;
    private MultipartFile cover;
}
