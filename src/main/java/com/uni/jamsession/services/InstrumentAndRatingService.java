package com.uni.jamsession.services;

import com.uni.jamsession.exceptions.UnauthorizedException;
import com.uni.jamsession.model.Instrument;
import com.uni.jamsession.model.InstrumentAndRating;
import com.uni.jamsession.model.User;
import com.uni.jamsession.repositories.InstrumentAndRatingRepository;
import com.uni.jamsession.repositories.JamSessionRepository;
import com.uni.jamsession.security.AuthService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InstrumentAndRatingService {

    private final InstrumentAndRatingRepository instrumentAndRatingRepository;
    private final JamSessionRepository jamSessionRepository;
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

    @Transactional
    public void deleteInstrumentAndRating(int instrumentAndRatingId) throws IllegalAccessException{
        int userId = authService.getUserPrincipal().getUserId();
        InstrumentAndRating existing = instrumentAndRatingRepository.findById(instrumentAndRatingId).orElseThrow(
                () -> new IllegalStateException("InstrumentAndRating with id " + instrumentAndRatingId + " does not exist"));
        if(jamSessionRepository.existsByConfirmedInstrumentsContains(existing)){
            throw new IllegalAccessException("Cannot delete InstrumentAndRating that is part of confirmed JamSessions");
        }
        instrumentAndRatingRepository.removeById(instrumentAndRatingId);
    }
}
