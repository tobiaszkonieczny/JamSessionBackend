package com.uni.jamsession.services;

import com.uni.jamsession.model.Instrument;
import com.uni.jamsession.model.InstrumentAndRating;
import com.uni.jamsession.model.User;
import com.uni.jamsession.repositories.InstrumentAndRatingRepository;
import com.uni.jamsession.security.AuthService;
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
