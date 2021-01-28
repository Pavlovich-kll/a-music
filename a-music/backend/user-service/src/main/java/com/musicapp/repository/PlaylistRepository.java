package com.musicapp.repository;

import com.musicapp.domain.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Репозиторий для работы с таблицей плейлистов.
 *
 * @author lizashpinkova
 */
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    @Modifying
    @Query(value = "insert into playlists_audios(playlist_id, audio_id) values (:playlistId, :audioId)", nativeQuery = true)
    void addNewTrackInPlaylist(@Param("playlistId") Long playlistId, @Param("audioId") Long audioId);

    @Query(value = "select count(*) from playlists_likes where playlist_id = ?1", nativeQuery = true)
    Integer findCountLike(Long idPlaylist);

    @Query(value = "select count(audio_id) from playlists_audios where  playlist_id = ?1", nativeQuery = true)
    Integer findTrackCount(Long id);

    @Override
    void deleteById(Long id);
}
