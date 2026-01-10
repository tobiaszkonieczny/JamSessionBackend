package com.uni.jamsession.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreateInstrumentDto(
    int id, @NotBlank(message = "Instrument name cannot be empty.") String name) {}
