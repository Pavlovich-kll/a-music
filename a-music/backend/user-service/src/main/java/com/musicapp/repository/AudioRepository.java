package com.musicapp.repository;

import com.musicapp.domain.Audio;
import com.musicapp.domain.AudioType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Репозиторий для работы с таблицей аудио записей
 */
public interface AudioRepository extends JpaRepository<Audio, Long> {

    List<Audio> findAllByType(AudioType audioType);

    /**
     * Метод позволяет узнать количество лайков у аудиозаписи
     *
     * @param idAudio ID аудиозаписи
     * @return число лайков аудиозаписи
     */
    @Query(value = "select count(*) from audios_likes where  audio_id = ?1", nativeQuery = true)
    Long countLikeById(Long idAudio);
}
