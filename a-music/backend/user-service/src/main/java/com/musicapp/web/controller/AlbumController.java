package com.musicapp.web.controller;

import com.musicapp.dto.AlbumCreationDto;
import com.musicapp.dto.AlbumViewDto;
import com.musicapp.mapper.AlbumMapper;
import com.musicapp.service.AlbumService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

/**
 * Котроллер для работы с музыкальными альбомами
 */
@Controller
@RequestMapping("/albums")
@Secured("ROLE_ADMIN")
@RequiredArgsConstructor
@Slf4j
public class AlbumController {

    private final AlbumService albumService;
    private final AlbumMapper albumMapper;

    /**
     * @param id ID музыкального альбома
     * @return представление существующего музыкального альбома
     */
    @ApiOperation("Метод позволяет админу получить представление существующего музыкального альбома")
    @GetMapping("/{id}")
    public ResponseEntity<AlbumViewDto> getAlbum(@PathVariable long id) {

        return albumService.getAlbum(id)
                .map(album -> ResponseEntity.ok(albumMapper.map(album)))
                .orElse(ResponseEntity.badRequest().build());
    }

    /**
     * @param albumCreationDto DTO для создания нового музыкального альбома
     * @return представление существующего музыкального альбома
     */
    @ApiOperation("Метод позволяет админу создать новый музыкальный альбом")
    @PostMapping
    public ResponseEntity<AlbumViewDto> createAlbum(@ModelAttribute @Valid AlbumCreationDto albumCreationDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(albumService.createAlbum(albumCreationDto));
        } catch (IOException e) {
            log.warn("Could not get bytes from file", e);

            return ResponseEntity.badRequest().build();
        }
    }
}
