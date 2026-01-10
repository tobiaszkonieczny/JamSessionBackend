package com.uni.jamsession.dtos;

public record InstrumentAndRatingDto(
        int id,
        int rating,
        int instrumentId,
        String instrumentName,
        int userId,
        String name
) {}