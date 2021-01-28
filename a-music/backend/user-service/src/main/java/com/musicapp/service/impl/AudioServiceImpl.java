package com.musicapp.service.impl;

import com.musicapp.domain.Audio;
import com.musicapp.domain.AudioType;
import com.musicapp.dto.AudioStubCreationDto;
import com.musicapp.dto.AudioViewDto;
import com.musicapp.exception.NotFoundException;
import com.musicapp.mapper.AudioMapper;
import com.musicapp.repository.AudioRepository;
import com.musicapp.service.*;
import com.musicapp.util.constants.MessageConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Реализация сервиса для работы с аудио записями
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AudioServiceImpl implements AudioService {

    private final AudioMapper audioMapper;
    private final AlbumService albumService;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final AudioRepository audioRepository;
    private final UserService userService;
    private final DatabaseFileService databaseFileService;

    @Override
    public Optional<Audio> getAudio(long id) {
        return audioRepository.findById(id);
    }

    @Override
    public Optional<AudioViewDto> getAudioViewDto(long id) {
        return audioRepository.findById(id)
                .map(audio -> audioMapper.map(audio, audioRepository.countLikeById(audio.getId())));
    }

    @Override
    public List<AudioViewDto> getAllAudios() {
        return getAudioWithLike(audioRepository.findAll());
    }

    @Override
    public List<AudioViewDto> getAudiosByAudioType(AudioType audioType) {
        return getAudioWithLike(audioRepository.findAllByType(audioType));
    }

    @Transactional
    @Override
    public Optional<AudioViewDto> createAudio(AudioStubCreationDto audioCreationDto) {
        return authorService.getAuthorByName(audioCreationDto.getAuthorName())
                .flatMap(author -> genreService.getGenreByTitle(audioCreationDto.getGenreName().get(0))
                        .flatMap(genre -> albumService.getAlbumByTitle(audioCreationDto.getAlbumName())
                                .flatMap(album -> databaseFileService.saveMultipartFile(audioCreationDto.getTrack())
                                        .map(file -> audioMapper.map(audioCreationDto)
                                                .setTitle(audioCreationDto.getTitle())
                                                .setAuthor(author)
                                                .setGenre(genre)
                                                .setAlbum(album)
                                                .setFile(file))
                                        .map(audio -> databaseFileService.saveMultipartFile(audioCreationDto.getCover())
                                                .map(audio::setCover)
                                                .orElse(audio)))))
                .map(audioRepository::save)
                .map(audio -> audioMapper.map(audio, audioRepository.countLikeById(audio.getId())));
    }

    @Transactional
    @Override
    public Optional<AudioViewDto> setAudioLike(Long idAudio, Long idAuthUser) {
        Audio audio = getAudio(idAudio)
                .orElseThrow(() -> {
                            log.error("Audio with id " + idAudio + " has not been found");
                            return new NotFoundException(MessageConstants.AUDIO_ID_NOT_FOUND, "id");
                        }
                );
        audio.getUserLikes().add(userService.getById(idAuthUser));
        return Optional.of(audioMapper.map(audioRepository.save(audio), audioRepository.countLikeById(idAudio)));
    }

    @Override
    public List<AudioViewDto> getFavoritesByAuthUser(Long idAuthUser) {
        return getAudioWithLike(userService.getById(idAuthUser).getAudioLikes());
    }

    @Override
    public List<AudioViewDto> getAudioWithLike(Collection<Audio> audios) {
        return audios.stream()
                .map(audio -> audioMapper.map(audio, audioRepository.countLikeById(audio.getId())))
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
