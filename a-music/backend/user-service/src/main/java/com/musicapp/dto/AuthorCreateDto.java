package com.musicapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class AuthorCreateDto {

    @NotNull
    String name;
    @NotNull
    String description;
}

