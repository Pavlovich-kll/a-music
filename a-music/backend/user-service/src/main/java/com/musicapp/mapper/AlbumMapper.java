package com.musicapp.mapper;

import com.musicapp.domain.Album;
import com.musicapp.dto.AlbumCreationDto;
import com.musicapp.dto.AlbumViewDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Маппер сущности музыкального альбома
 */
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AlbumMapper {

    @Mapping(target = "coverId", source = "cover.id")
    AlbumViewDto map(Album album);

    @Mapping(target = "cover", ignore = true)
    Album map(AlbumCreationDto albumCreationDto);
}
