package com.uni.jamsession.dtos;

import com.uni.jamsession.model.Instrument;
import com.uni.jamsession.model.InstrumentAndRating;
import com.uni.jamsession.model.Location;
import com.uni.jamsession.model.MusicGenre;

import java.time.LocalDateTime;
import java.util.List;

public record JamSessionDto(
        Integer id,
        UserDto ownerDto,
        List<InstrumentAndRating> confirmedInstruments,
        LocalDateTime startTime,
        Location location,
        List<Instrument> requiredInstruments,
        MusicGenre musicGenre) {}
