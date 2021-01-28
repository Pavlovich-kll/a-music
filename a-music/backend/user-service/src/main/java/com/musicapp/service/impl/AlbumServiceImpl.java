package com.musicapp.service.impl;

import com.musicapp.domain.Album;
import com.musicapp.dto.AlbumCreationDto;
import com.musicapp.dto.AlbumViewDto;
import com.musicapp.mapper.AlbumMapper;
import com.musicapp.repository.AlbumRepository;
import com.musicapp.service.AlbumService;
import com.musicapp.service.DatabaseFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

/**
 * Реализация сервиса для работы с музыкальными альбомами
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;
    private final DatabaseFileService databaseFileService;
    private final AlbumMapper albumMapper;

    @Override
    public Optional<Album> getAlbum(long id) {
        return albumRepository.findById(id);
    }

    @Transactional(rollbackFor = IOException.class)
    @Override
    public AlbumViewDto createAlbum(AlbumCreationDto albumCreationDto) throws IOException {
        Album album = albumMapper.map(albumCreationDto);

        if (Objects.nonNull(albumCreationDto.getCover())) {
            album.setCover(databaseFileService.saveFile(albumCreationDto.getCover()));
        }

        return albumMapper.map(albumRepository.save(album));
    }

    @Override
    public Optional<Album> getAlbumByTitle(String albumTitle) {
        return albumRepository.findByTitle(albumTitle);
    }
}
