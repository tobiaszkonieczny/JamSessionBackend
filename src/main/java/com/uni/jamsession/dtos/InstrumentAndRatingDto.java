package com.sap.jamsession.dtos;

import com.sap.jamsession.model.Instrument;
import com.sap.jamsession.model.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record InstrumentAndRatingDto(
        int id,
        int rating,
        int instrumentId,
        String instrumentName,
        int userId,
        String username
) {}