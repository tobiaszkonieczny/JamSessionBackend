package com.uni.jamsession.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreateMusicGenreDto(
        @NotBlank(message = "Music genre name cannot be empty.")
        String name
) {}
