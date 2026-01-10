package com.sap.jamsession.dtos;

import jakarta.validation.constraints.NotNull;

public record AddUserToJamSessionDto(
    @NotNull(message = "Instrument and rating ID cannot be null") Integer instrumentAndRatingId
) {}

