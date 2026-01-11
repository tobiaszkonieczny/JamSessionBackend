package com.uni.jamsession.dtos.instrumentandrating;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateInstrumentAndRatingDto(
    @NotNull(message = "Instrument id cannot be null")
    int instrumentId,

    @NotNull(message = "Rating id cannot be null")
        @Min(message = "Rating has to be grater than 0", value = 1)
        @Max(message = "Rating cannot be grater than 5", value = 5)
        int rating)
{}
