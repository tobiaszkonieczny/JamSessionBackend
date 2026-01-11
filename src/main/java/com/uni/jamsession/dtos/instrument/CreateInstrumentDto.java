package com.uni.jamsession.dtos.instrument;

import jakarta.validation.constraints.NotBlank;

public record CreateInstrumentDto(
   @NotBlank(message = "Instrument name cannot be empty.") String name) {
}
