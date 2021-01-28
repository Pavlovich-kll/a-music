package com.musicapp.service.impl;

import com.musicapp.domain.Audio;
import com.musicapp.domain.DatabaseFile;
import com.musicapp.domain.Playlist;
import com.musicapp.dto.AudioViewDto;
import com.musicapp.dto.PlaylistCreateDto;
import com.musicapp.dto.PlaylistDto;
import com.musicapp.exception.NotFoundException;
import com.musicapp.mapper.AudioMapper;
import com.musicapp.mapper.PlaylistMapper;
import com.musicapp.repository.AudioRepository;
import com.musicapp.repository.PlaylistRepository;
import com.musicapp.service.AudioService;
import com.musicapp.service.DatabaseFileService;
import com.musicapp.service.PlaylistService;
import com.musicapp.service.UserService;
import com.musicapp.util.constants.MessageConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Реализация сервиса для управления плейлистами.
 *
 * @author lizavetashpinkova
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final DatabaseFileService databaseFileService;
    private final PlaylistMapper playlistMapper;
    private final AudioService audioService;
    private final UserService userService;
    private final AudioMapper audioMapper;
    private final AudioRepository audioRepository;


    @Override
    public Optional<PlaylistDto> getPlaylist(String id) {
        return playlistRepository.findById(Long.valueOf(id))
                .map(playlist -> playlistMapper
                        .map(playlist, playlistRepository.findCountLike(playlist.getId()),
                                playlistRepository.findTrackCount(playlist.getId()),
                                getAudioViewDtoSet(playlist.getAudios())));
    }

    @Override
    public List<PlaylistDto> getAllPlaylists() {
        return getPlaylistsWithLikeAndCount(playlistRepository.findAll());
    }

    @Transactional
    @Override
    public Optional<PlaylistDto> createPlaylist(PlaylistCreateDto playlistCreateDto) {

        return Optional.of(playlistMapper.map(playlistCreateDto))
                .map(playlist -> Optional.ofNullable(playlistCreateDto.getPic())
                        .flatMap(this::saveMultipartFile)
                        .map(playlist::setCover)
                        .orElse(playlist))
                .flatMap(playlist -> getAudios(playlistCreateDto.getTracks().stream()
                        .map(Long::valueOf).collect(Collectors.toSet()))
                        .map(playlist::setAudios))
                .map(playlistRepository::save)
                .map(playlist -> playlistMapper
                        .map(playlist, playlistRepository.findCountLike(playlist.getId()),
                                playlistRepository.findTrackCount(playlist.getId()),
                                getAudioViewDtoSet(playlist.getAudios())));
    }

    @Transactional
    @Override
    public Optional<PlaylistDto> addNewAudio(String id, Set<String> tracks) {

        getAudios(tracks.stream()
                .map(Long::valueOf)
                .collect(Collectors.toSet()))
                .ifPresent(audioSet -> audioSet.forEach(audio -> playlistRepository
                        .addNewTrackInPlaylist(Long.valueOf(id), audio.getId())));

        return playlistRepository.findById(Long.valueOf(id))
                .flatMap(playlist -> getAudios(tracks.stream().map(Long::valueOf).collect(Collectors.toSet()))
                        .map(playlist::setAudios))
                .map(playlistRepository::save)
                .map(playlist -> playlistMapper
                        .map(playlist, playlistRepository.findCountLike(playlist.getId()),
                                playlistRepository.findTrackCount(playlist.getId()),
                                getAudioViewDtoSet(playlist.getAudios())));
    }

    private Optional<Set<Audio>> getAudios(Set<Long> audioIdsList) {

        Set<Audio> audios = new HashSet<>();

        for (long id : audioIdsList) {
            Optional<Audio> audio = audioService.getAudio(id);
            if (!audio.isPresent()) {
                return Optional.empty();
            }
            audios.add(audio.get());
        }
        return Optional.of(audios);
    }

    private Optional<DatabaseFile> saveMultipartFile(MultipartFile file) {
        try {
            return Optional.of(databaseFileService.saveFile(file));
        } catch (IOException e) {
            log.warn("Cannot save file to database " + e);
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Optional<PlaylistDto> setLikeToPlaylist(Long idPlaylist, Long idAuthUser) {
        Playlist playlist = playlistRepository.findById(idPlaylist)
                .orElseThrow(() -> {
                            log.error("Playlist with id " + idPlaylist + " has not been found");
                            return new NotFoundException(MessageConstants.PLAYLIST_ID_NOT_FOUND, "id");
                        }
                );
        playlist.getLikeUsers().add(userService.getById(idAuthUser));
        return Optional.of(playlistMapper.map(
                playlistRepository.save(playlist), playlistRepository.findCountLike(idPlaylist),
                playlistRepository.findTrackCount(idPlaylist),
                getAudioViewDtoSet(playlist.getAudios())));
    }

    /**
     * Метод для получения плейлистов с количеством треков и лайками
     *
     * @param playlists список плейлистов (без лайков и количества треков)
     * @return Список представлений плейлистов с лайками и количеством треков
     */
    private List<PlaylistDto> getPlaylistsWithLikeAndCount(List<Playlist> playlists) {

        return playlists.stream()
                .map(playlist -> playlistMapper
                        .map(playlist, playlistRepository.findCountLike(playlist.getId()),
                                playlistRepository.findTrackCount(playlist.getId()),
                                getAudioViewDtoSet(playlist.getAudios())))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Метод для получения аудиозаписей плейлиста
     *
     * @param audios список аудиозаписей
     * @return Список представлений аудиозаписей с лайками
     */
    private Set<AudioViewDto> getAudioViewDtoSet(Set<Audio> audios) {
        return audios.stream()
                .map(audio -> audioMapper.map(audio, audioRepository.countLikeById(audio.getId())))
                .collect(Collectors.toCollection(HashSet::new));
    }

    @Transactional
    @Override
    public void deletePlaylist(Long id) {
        playlistRepository.deleteById(id);
        log.info("Playlist with id " + id + " has been deleted");
    }
}

