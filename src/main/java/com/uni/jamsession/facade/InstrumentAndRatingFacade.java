package com.sap.jamsession.facade;

import com.sap.jamsession.dtos.CreateInstrumentAndRatingDto;
import com.sap.jamsession.dtos.InstrumentAndRatingDto;
import com.sap.jamsession.mappers.InstrumentAndRatingMapper;
import com.sap.jamsession.model.Instrument;
import com.sap.jamsession.model.InstrumentAndRating;
import com.sap.jamsession.model.User;
import com.sap.jamsession.repositories.InstrumentAndRatingRepository;
import com.sap.jamsession.repositories.InstrumentRepository;
import com.sap.jamsession.security.AuthService;
import com.sap.jamsession.services.InstrumentAndRatingService;
import com.sap.jamsession.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InstrumentAndRatingFacade {

    private final InstrumentAndRatingService instrumentAndRatingService;
    private final InstrumentAndRatingMapper instrumentAndRatingMapper;
    private final UserService userService;
    private final InstrumentRepository instrumentRepository;
    private final InstrumentAndRatingRepository instrumentAndRatingRepository;

    private InstrumentAndRatingDto addNewInstrumentAndRating(CreateInstrumentAndRatingDto instrumentAndRatingDto, User user) {
        Instrument instrument = instrumentRepository.getInstrumentById(
                instrumentAndRatingDto.instrumentId()
        );
        return instrumentAndRatingMapper.instrumentAndRatingtoInstrumentAndRatingDto(
                instrumentAndRatingService.addOrUpdateInstrumentAndRating(user, instrument, instrumentAndRatingDto.rating())
        );
    }
    public Set<InstrumentAndRatingDto> addNewInstrumentAndRatingBatch(Set<CreateInstrumentAndRatingDto> instrumentAndRatingDtos) {
        User user = userService.getCurrentUser();
        Set<InstrumentAndRatingDto> instrumentAndRatings = new HashSet<>();
        for (CreateInstrumentAndRatingDto dto : instrumentAndRatingDtos) {
            instrumentAndRatings.add(
                    addNewInstrumentAndRating(dto, user)
            );
        }
        return instrumentAndRatings;
    }

    public Set<InstrumentAndRatingDto> getByUserId(int userId) {
        return instrumentAndRatingRepository.findByUserId(userId).stream()
                .map(instrumentAndRatingMapper::instrumentAndRatingtoInstrumentAndRatingDto)
                .collect(Collectors.toSet());
    }

    public void removeInstrumentAndRating(int instrumentId){
        instrumentAndRatingService.removeInstrumentAndRating(instrumentId);
    }
}
