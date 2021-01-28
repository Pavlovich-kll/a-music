package com.musicapp.mapper;

import com.musicapp.domain.Album;
import com.musicapp.domain.Audio;
import com.musicapp.domain.Author;
import com.musicapp.domain.Genre;
import com.musicapp.dto.AudioStubCreationDto;
import com.musicapp.dto.AudioViewDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Маппер сущности аудио записи
 */
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AudioMapper {

    @Mapping(target = "author", source = "audio.author", qualifiedByName = "authorToAuthorName")
    @Mapping(target = "genre", source = "audio.genre", qualifiedByName = "genreToGenreTitle")
    @Mapping(target = "album", source = "audio.album", qualifiedByName = "albumToAlbumTitle")
    @Mapping(target = "likes", source = "like")
    AudioViewDto map(Audio audio, Long like);

    @Mapping(target = "file", ignore = true)
    @Mapping(target = "cover", ignore = true)
    Audio map(AudioStubCreationDto audioCreationDto);

    @Named("authorToAuthorName")
    static String authorToAuthorName(Author author) {
        return author.getName();
    }

    @Named("genreToGenreTitle")
    static String genreToGenreTitle(Genre genre) {
        return genre.getTitle();
    }

    @Named("albumToAlbumTitle")
    static String albumToAlbumTitle(Album album) {
        return album.getTitle();
    }
}
