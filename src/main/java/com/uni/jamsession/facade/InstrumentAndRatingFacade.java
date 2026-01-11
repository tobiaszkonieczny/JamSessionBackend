package com.uni.jamsession.facade;

import com.uni.jamsession.dtos.instrumentandrating.CreateInstrumentAndRatingDto;
import com.uni.jamsession.dtos.instrumentandrating.InstrumentAndRatingDto;
import com.uni.jamsession.mappers.InstrumentAndRatingMapper;
import com.uni.jamsession.model.Instrument;
import com.uni.jamsession.model.User;
import com.uni.jamsession.repositories.InstrumentAndRatingRepository;
import com.uni.jamsession.repositories.InstrumentRepository;
import com.uni.jamsession.services.InstrumentAndRatingService;
import com.uni.jamsession.services.UserService;
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
        return instrumentAndRatingMapper.toDto(
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
                .map(instrumentAndRatingMapper::toDto)
                .collect(Collectors.toSet());
    }

    public void removeInstrumentAndRating(int instrumentId){
        instrumentAndRatingService.removeInstrumentAndRating(instrumentId);
    }
}
