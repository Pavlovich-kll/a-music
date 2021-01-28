package com.musicapp.web.controller;

import com.musicapp.dto.*;
import com.musicapp.security.AuthorizedUser;
import com.musicapp.dto.PlaylistCreateDto;
import com.musicapp.dto.PlaylistDto;
import com.musicapp.dto.PlaylistLikeCreateDto;
import com.musicapp.dto.PlaylistPutDto;
import com.musicapp.service.PlaylistService;
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
 * Методы для работы с плейлистами.
 *
 * @author lizavetashpinkova
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/playlist")
public class PlaylistController {

    private final PlaylistService playlistService;

    /**
     * Метод для получения плейлиста по id.
     *
     * @param id id плейлиста
     * @return dto плейлиста
     */
    @GetMapping("/{id}")
    public ResponseEntity<PlaylistDto> getPlaylist(@PathVariable String id) {
        return playlistService.getPlaylist(id)
                .map(playlist -> ResponseEntity.ok().body(playlist))
                .orElse(ResponseEntity.badRequest().build());
    }

    /**
     * Метод для получения всех плейлистов.
     *
     * @return список dto всех существующих плейлистов
     */
    @GetMapping
    public ResponseEntity<List<PlaylistDto>> getAllPlaylists() {
        return ResponseEntity.ok().body(playlistService.getAllPlaylists());
    }

    /**
     * Метод для создания плейлиста.
     *
     * @param playlistCreateDto dto для создания плейлиста
     * @return dto созданного плейлиста
     */
    @PostMapping
    public ResponseEntity<PlaylistDto> createPlaylist(@ModelAttribute @Valid PlaylistCreateDto playlistCreateDto) {

        return playlistService.createPlaylist(playlistCreateDto)
                .map(playlistDto -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(playlistDto))
                .orElse(ResponseEntity.badRequest().build());
    }

    /**
     * Метод для добавления аудио в плейлист.
     *
     * @param playlistPutDto dto для добавления аудио в плейлист
     * @return dto плейлиста
     */
    @PutMapping("/new")
    public ResponseEntity<PlaylistDto> addNewAudios(@ModelAttribute @Valid PlaylistPutDto playlistPutDto) {
        return playlistService.addNewAudio(playlistPutDto.getId(), playlistPutDto.getTracks())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    /**
     * @param likeDTO  DTO для добавления лайка к плейлисту
     * @param authUser авторизованный пользователь
     * @return DTO представление плейлиста
     */
    @ApiOperation("Метод для добавления лайка к плейлисту")
    @PutMapping
    public ResponseEntity<PlaylistDto> setPlaylistLike(@RequestBody PlaylistLikeCreateDto likeDTO, @AuthenticationPrincipal AuthorizedUser authUser) {

        return playlistService.setLikeToPlaylist(likeDTO.getId(), authUser.getId())
                .map(playlistDto -> ResponseEntity.status(HttpStatus.OK)
                        .body(playlistDto))
                .orElse(ResponseEntity.badRequest().build());
    }

    /**
     * Метод для удаления конкретного плейлиста по его ID
     *
     * @param id ID плейлиста, подлежащего удаления
     */
    @ApiOperation("Метод удаляет сущность плейлиста по ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable long id) {
        playlistService.deletePlaylist(id);

        return ResponseEntity.ok().build();
    }
}