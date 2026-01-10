package com.sap.jamsession.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreateMusicGenreDto(
    int id, @NotBlank(message = "Music genre name cannot be empty.") String name) {}
