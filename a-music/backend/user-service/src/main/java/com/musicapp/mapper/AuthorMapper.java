package com.musicapp.mapper;

import com.musicapp.domain.Author;
import com.musicapp.dto.AuthorCreateDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AuthorMapper {

    Author map(AuthorCreateDto authorCreateDto);
}
