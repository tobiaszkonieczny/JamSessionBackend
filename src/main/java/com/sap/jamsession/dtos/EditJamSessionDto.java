package com.sap.jamsession.dtos;

import com.sap.jamsession.model.Location;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public record EditJamSessionDto(
    List<Integer> confirmedInstrumentsIds,
    @Size(min = 1, message = "If provided, at least one required instrument must be selected.")
    List<Integer> requiredInstrumentsIds,
    @Future(message = "Start time must be in the future.") LocalDateTime startTime,
    @Valid Location location,
    Integer musicGenreId) {}
