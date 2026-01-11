package com.uni.jamsession.dtos.jamsession;

import com.uni.jamsession.dtos.MusicGenreDto;
import com.uni.jamsession.dtos.UserDto;
import com.uni.jamsession.dtos.instrument.InstrumentDto;
import com.uni.jamsession.dtos.instrumentandrating.InstrumentAndRatingDto;
import com.uni.jamsession.model.Instrument;
import com.uni.jamsession.model.InstrumentAndRating;
import com.uni.jamsession.model.Location;
import com.uni.jamsession.model.MusicGenre;

import java.time.LocalDateTime;
import java.util.List;

public record JamSessionDto(
        Integer id,
        UserDto ownerDto,
        List<InstrumentAndRatingDto> confirmedInstruments,
        LocalDateTime startTime,
        Location location,
        List<InstrumentDto> requiredInstruments,
        MusicGenreDto musicGenre) {}
