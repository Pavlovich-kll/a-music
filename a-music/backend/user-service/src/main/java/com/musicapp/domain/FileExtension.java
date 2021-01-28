package com.musicapp.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * Перечисление поддерживаемых типов файлов
 */
@Getter
@RequiredArgsConstructor
public enum FileExtension {

    MP3("audio/mpeg"),
    PNG("image/png"),
    JPEG("image/jpeg");

    private final String mediaTypeString;

    /**
     * Позволяет преобразоватить content type из MultipartFile в соответствующее расширение
     *
     * @param contentType content type из MultipartFile
     * @return соответствующее расширение
     */
    public static FileExtension parse(String contentType) {
        return Arrays.stream(FileExtension.values())
                .filter(fileExtension -> fileExtension.getMediaTypeString().equals(contentType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("File extension not supported: " + contentType));
    }
}
