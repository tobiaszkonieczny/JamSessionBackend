package com.sap.jamsession.services;

import com.sap.jamsession.mappers.InstrumentAndRatingMapper;
import com.sap.jamsession.model.Instrument;
import com.sap.jamsession.model.InstrumentAndRating;
import com.sap.jamsession.model.User;
import com.sap.jamsession.dtos.CreateInstrumentAndRatingDto;
import com.sap.jamsession.repositories.InstrumentAndRatingRepository;
import com.sap.jamsession.security.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstrumentAndRatingService {

    private final InstrumentAndRatingRepository instrumentAndRatingRepository;
    private final AuthService authService;

    @Transactional
    public InstrumentAndRating addOrUpdateInstrumentAndRating(User user, Instrument instrument, int rating) {

        if(instrumentAndRatingRepository.existsByInstrumentIdAndUserId(instrument.getId(), user.getId())){
            InstrumentAndRating existing = instrumentAndRatingRepository.findByInstrumentIdAndUserId(instrument.getId(), user.getId());
            existing.setRating(rating);
            return instrumentAndRatingRepository.save(existing);
        } else {
            InstrumentAndRating newInstrumentAndRating = new InstrumentAndRating();
            newInstrumentAndRating.setInstrument(instrument);
            newInstrumentAndRating.setUser(user);
            newInstrumentAndRating.setRating(rating);
            return instrumentAndRatingRepository.save(newInstrumentAndRating);
        }
    }

    public void removeInstrumentAndRating(int instrumentId){
        int userId = authService.getUserPrincipal().getUserId();
        if(!instrumentAndRatingRepository.existsByInstrumentIdAndUserId(instrumentId, userId)){
            throw new IllegalArgumentException("InstrumentAndRating does not exist for the given instrumentId and userId");
        }
        instrumentAndRatingRepository.removeByInstrumentIdAndUserId(instrumentId, userId);
    }
}
