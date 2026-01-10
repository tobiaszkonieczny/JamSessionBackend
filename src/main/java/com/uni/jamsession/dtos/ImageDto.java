package com.uni.jamsession.dtos;

import com.uni.jamsession.validation.ImageValidationGroups;
import jakarta.validation.constraints.NotNull;

public record ImageDto(
    @NotNull(groups = ImageValidationGroups.Response.class)
    Long id,

    @NotNull(groups = ImageValidationGroups.Retrieve.class)
    String name,

    @NotNull(groups = ImageValidationGroups.Retrieve.class)
    String type,

    @NotNull(groups = ImageValidationGroups.Retrieve.class)
    byte[] data,

    @NotNull(groups = ImageValidationGroups.Response.class)
    String message
) {}

