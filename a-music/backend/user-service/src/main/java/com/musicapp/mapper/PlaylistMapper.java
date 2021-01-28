package com.musicapp.mapper;

import com.musicapp.domain.Playlist;
import com.musicapp.dto.AudioViewDto;
import com.musicapp.dto.PlaylistCreateDto;
import com.musicapp.dto.PlaylistDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

/**
 * Маппер сущности плейлиста.
 */
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PlaylistMapper {

    @Mapping(target = "cover", ignore = true)
    Playlist map(PlaylistCreateDto playlistCreateDto);

    @Mapping(source = "like", target = "likes")
    @Mapping(source = "count", target = "track_count")
    @Mapping(source = "audios", target = "tracks")
    PlaylistDto map(Playlist playlist, Integer like, Integer count, Set<AudioViewDto> audios);

    List<PlaylistDto> map(List<Playlist> playlists);
}
