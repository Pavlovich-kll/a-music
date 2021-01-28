package com.musicapp.web.controller;

import com.musicapp.domain.AudioType;
import com.musicapp.dto.GenreDto;
import com.musicapp.mapper.GenreMapper;
import com.musicapp.service.DatabaseFileService;
import com.musicapp.service.GenreService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Методы для работы с контентом.
 */
@Controller
@RequestMapping("/content")
@RequiredArgsConstructor
public class ContentController {

    private final DatabaseFileService databaseFileService;
    private final GenreService genreService;
    private final GenreMapper genreMapper;

    /**
     * @param id ID файла
     * @return искомый файл
     */
    @ApiOperation("Метод позволяет получить файл из базы данных")
    @GetMapping("/file/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable long id) {
        return databaseFileService.getFile(id)
                .map(persistentFile -> ResponseEntity.ok()
                        .contentType(persistentFile.getMediaType())
                        .body(persistentFile.getBytes()))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Возвращает перечень всех существующих жанров.
     *
     * @return список dto всех существующих жанров
     */
    @ApiOperation("Метод возвращает перечень всех существующих жанров")
    @GetMapping("/genres")
    public ResponseEntity<List<GenreDto>> getAllGenres() {
        return ResponseEntity.ok(genreMapper.map(genreService.getAllGenres()));
    }

    /**
     * @return список всех возможных типов аудио записей
     */
    @ApiOperation("Метод возвращает список всех возможных типов аудио записей")
    @GetMapping("/types")
    public ResponseEntity<List<String>> getAllAudioTypes() {
        return ResponseEntity.ok(Arrays.stream(AudioType.values())
                .map(AudioType::name)
                .collect(Collectors.toList()));
    }
}
