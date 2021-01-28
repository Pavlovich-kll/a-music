package com.musicapp.web.controller;

import com.musicapp.domain.AudioType;
import com.musicapp.dto.AudioLikeCreateDto;
import com.musicapp.dto.AudioStubCreationDto;
import com.musicapp.dto.AudioViewDto;
import com.musicapp.security.AuthorizedUser;
import com.musicapp.service.AudioService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Контроллер для работы с аудио записями
 */
@Controller
@RequiredArgsConstructor
public class AudioController {

    private final AudioService audioService;

    /**
     * @return лист DTO представлений аудио записей
     */
    @ApiOperation("Метод для получения всех аудио записей")
    @GetMapping("/file")
    public ResponseEntity<List<AudioViewDto>> getAllAudios() {
        return ResponseEntity.ok(audioService.getAllAudios());
    }

    /**
     * @param audioType тип аудио записи
     * @return лист DTO представлений аудио записей
     */
    @ApiOperation("Метод для получения всех аудио записей определенного типа")
    @GetMapping("/{audioType}/file")
    public ResponseEntity<List<AudioViewDto>> getAudiosByAudioType(@PathVariable AudioType audioType) {

        return ResponseEntity.ok(audioService.getAudiosByAudioType(audioType));
    }

    /**
     * @param audioStubCreationDto DTO для создания новой аудио записи
     * @return DTO представление аудио записи
     */
    @ApiOperation("Метод для создания новой аудио записи")
    @PostMapping("/file")
    public ResponseEntity<AudioViewDto> createAudio(@ModelAttribute @Valid AudioStubCreationDto audioStubCreationDto) {

        return audioService.createAudio(audioStubCreationDto)
                .map(audioViewDto -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(audioViewDto))
                .orElse(ResponseEntity.badRequest().build());
    }

    /**
     * @param likeDTO  DTO для добавления лайка к аудиозаписи
     * @param authUser авторизованный пользователь
     * @return DTO представление аудио записи
     */
    @ApiOperation("Метод для добавления лайка к аудио записи")
    @PutMapping("/file")
    public ResponseEntity<AudioViewDto> setAudioLike(@RequestBody AudioLikeCreateDto likeDTO, @AuthenticationPrincipal AuthorizedUser authUser) {

        return audioService.setAudioLike(likeDTO.getTrack_id(), authUser.getId())
                .map(audioViewDto -> ResponseEntity.status(HttpStatus.OK)
                        .body(audioViewDto))
                .orElse(ResponseEntity.badRequest().build());
    }

    /**
     * @param authUser авторизованный пользователь
     * @return писок DTO представлений аудио записей
     */
    @ApiOperation("Метод для получения избранных аудио записей пользователя")
    @GetMapping("/favorites")
    public ResponseEntity<List<AudioViewDto>> getFavorites(@AuthenticationPrincipal AuthorizedUser authUser) {
        return ResponseEntity.ok(audioService.getFavoritesByAuthUser(authUser.getId()));
    }
}
