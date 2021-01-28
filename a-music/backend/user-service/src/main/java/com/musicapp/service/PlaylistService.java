package com.musicapp.service;

import com.musicapp.dto.PlaylistCreateDto;
import com.musicapp.dto.PlaylistDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Интерфейс сервиса для управления плейлистами.
 *
 * @author lizavetashpinkova
 */
public interface PlaylistService {

    /**
     * Возвращает плейлист по id.
     *
     * @param id плейлиста
     * @return dto плейлиста
     */
    Optional<PlaylistDto> getPlaylist(String id);

    /**
     * Возвращает dto всех плейлистов.
     *
     * @return список плейлистов
     */
    List<PlaylistDto> getAllPlaylists();

    /**
     * Метод для создания плейлиста.
     *
     * @param playlistCreateDto dto плейлиста
     * @return dto плейлиста
     */
    Optional<PlaylistDto> createPlaylist(PlaylistCreateDto playlistCreateDto);

    /**
     * Метод для добавления аудио в плейлист.
     *
     * @param id     id плейлиста
     * @param tracks список id аудио
     * @return dto плейлиста
     */
    Optional<PlaylistDto> addNewAudio(String id, Set<String> tracks);

    /**
     * Метод для добавления аудио в плейлист.
     *
     * @param idPlaylist id плейлиста
     * @param idAuthUser id авторизованного пользователя
     * @return dto плейлиста
     */
    Optional<PlaylistDto> setLikeToPlaylist(Long idPlaylist, Long idAuthUser);

    /**
     * Метод удаления плейлиста по id
     *
     * @param id Playlist
     */
    void deletePlaylist(Long id);
}
