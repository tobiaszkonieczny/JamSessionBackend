package com.uni.jamsession.dtos.jamsession;

import com.uni.jamsession.dtos.MusicGenreDto;
import com.uni.jamsession.dtos.instrument.InstrumentDto;
import com.uni.jamsession.dtos.instrumentandrating.InstrumentAndRatingDto;
import com.uni.jamsession.dtos.user.UserShortDto;
import com.uni.jamsession.model.Location;

import java.time.LocalDateTime;
import java.util.List;

public record JamSessionDto(
        Integer id,
        UserShortDto owner,
        List<InstrumentAndRatingDto> confirmedInstruments,
        LocalDateTime startTime,
        Location location,
        List<InstrumentDto> requiredInstruments,
        MusicGenreDto musicGenre) {}
