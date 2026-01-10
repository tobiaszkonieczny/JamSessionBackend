package com.uni.jamsession.dtos;

import com.uni.jamsession.model.Location;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

public record EditJamSessionDto(
    List<Integer> confirmedInstrumentsIds,
    @Size(min = 1, message = "If provided, at least one required instrument must be selected.")
    List<Integer> requiredInstrumentsIds,
    @Future(message = "Start time must be in the future.") LocalDateTime startTime,
    @Valid Location location,
    Integer musicGenreId) {}
