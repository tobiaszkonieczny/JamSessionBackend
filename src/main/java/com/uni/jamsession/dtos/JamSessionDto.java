package com.sap.jamsession.dtos;

import com.sap.jamsession.model.Instrument;
import com.sap.jamsession.model.InstrumentAndRating;
import com.sap.jamsession.model.Location;
import com.sap.jamsession.model.MusicGenre;

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
