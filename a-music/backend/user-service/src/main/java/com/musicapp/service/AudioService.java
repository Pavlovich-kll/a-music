package com.musicapp.service;

import com.musicapp.domain.Audio;
import com.musicapp.domain.AudioType;
import com.musicapp.dto.AudioStubCreationDto;
import com.musicapp.dto.AudioViewDto;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с аудио записями
 */
public interface AudioService {

    /**
     * @param id ID аудио записи
     * @return сущность музыкальной записи
     */
    Optional<Audio> getAudio(long id);

    /**
     * @param id ID аудио записи
     * @return DTO представление аудио записи
     */
    Optional<AudioViewDto> getAudioViewDto(long id);

    /**
     * @return лист DTO представлений аудио записей
     */
    List<AudioViewDto> getAllAudios();

    /**
     * @param audioType тип аудио записи
     * @return лист DTO представлений аудио записей
     */
    List<AudioViewDto> getAudiosByAudioType(AudioType audioType);

    /**
     * @param audioCreationDto DTO для создания новой аудио записи
     * @return DTO представление аудио записи
     */
    Optional<AudioViewDto> createAudio(AudioStubCreationDto audioCreationDto);

    /**
     * @param idAudio    id аудиозаписи
     * @param idAuthUser id авторизованного пользователя
     * @return DTO представление аудио записи
     */
    Optional<AudioViewDto> setAudioLike(Long idAudio, Long idAuthUser);

    /**
     * Метод для получения избранных аудио записей
     *
     * @param idAuthUser id авторизованного пользователя
     * @return список DTO представлений аудио записей
     */
    List<AudioViewDto> getFavoritesByAuthUser(Long idAuthUser);

    /**
     * Метод для получения лайков каждой аудио записи
     *
     * @param audios список аудиозаписей(без лайков)
     * @return Список представлений аудиозаписей с лайками
     */
    List<AudioViewDto> getAudioWithLike(Collection<Audio> audios);
}
