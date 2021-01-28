package com.musicapp.service;

import com.musicapp.domain.Album;
import com.musicapp.dto.AlbumCreationDto;
import com.musicapp.dto.AlbumViewDto;

import java.io.IOException;
import java.util.Optional;

/**
 * Сервис для работы с музыкальными альбомами
 */
public interface AlbumService {

    Optional<Album> getAlbum(long id);

    /**
     * @param albumCreationDto DTO для создания нового музыкального альбома
     * @return DTO представление существующего музыкального альбома
     * @throws IOException если при получении байтов из загруженной обложки альбома выбросится исключение
     */
    AlbumViewDto createAlbum(AlbumCreationDto albumCreationDto) throws IOException;

    Optional<Album> getAlbumByTitle(String albumTitle);
}
