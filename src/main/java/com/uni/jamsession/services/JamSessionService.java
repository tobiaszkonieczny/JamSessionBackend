package com.uni.jamsession.services;

import com.uni.jamsession.exceptions.ResourceNotFoundException;
import com.uni.jamsession.exceptions.UnauthorizedException;
import com.uni.jamsession.mappers.JamSessionMapper;
import com.uni.jamsession.model.*;
import com.uni.jamsession.dtos.jamsession.CreateJamSessionDto;
import com.uni.jamsession.dtos.jamsession.EditJamSessionDto;
import com.uni.jamsession.dtos.jamsession.JamSessionDto;
import com.uni.jamsession.repositories.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.uni.jamsession.security.AuthService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JamSessionService {

    private final UserRepository userRepository;
    private final JamSessionRepository jamSessionRepository;
    private final JamSessionMapper jamSessionMapper;
    private final UserService userService;
    private final MusicGenreRepository musicGenreRepository;
    private final InstrumentAndRatingRepository instrumentAndRatingRepository;
    private final AuthService authService;
    private final InstrumentRepository instrumentRepository;


    @Transactional
    public JamSession createJamSession(CreateJamSessionDto dto) {

        int userId = authService.getUserPrincipal().getId();

        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        MusicGenre genre = musicGenreRepository.findById(dto.getMusicGenreId())
                .orElseThrow(() -> new ResourceNotFoundException("Music genre", dto.getMusicGenreId()));

        JamSession jamSession = new JamSession();
        jamSession.setOwner(owner);
        jamSession.setRequiredInstruments(dto.getRequiredInstruments());
        jamSession.setStartTime(dto.getStartTime());
        jamSession.setLocation(dto.getLocation());
        jamSession.setMusicGenre(genre);

        jamSessionRepository.save(jamSession);
        return jamSession;
    }


    @Transactional
    public JamSession editJamSession(EditJamSessionDto dto, int id) {
        JamSession jamSession = getById(id);

        ensureOwner(jamSession);

        if (dto.requiredInstrumentsIds() != null) {
            List<Instrument> newRequired = new ArrayList<>();
            for (Integer instId : dto.requiredInstrumentsIds()) {
                instrumentRepository.findById(instId).ifPresent(newRequired::add);
            }
            jamSession.setRequiredInstruments(newRequired);
        }

        if (dto.confirmedInstrumentsIds() != null) {
            var userId = userService.getUserPrinciples().getUserId();
            List<InstrumentAndRating> newConfirmed = instrumentAndRatingRepository
                    .findByUserIdAndInstrumentId(dto.confirmedInstrumentsIds(), userId);

            jamSession.getConfirmedInstruments().clear();
            jamSession.getConfirmedInstruments().addAll(newConfirmed);
        }

        if (dto.musicGenreId() != null) {
            var genre = musicGenreRepository.findById(dto.musicGenreId())
                    .orElseThrow(() -> new ResourceNotFoundException("Genre", dto.musicGenreId()));
            jamSession.setMusicGenre(genre);
        }

        return jamSessionRepository.save(jamSession);
    }

    public List<JamSession> getAllJamSessions() {
        return jamSessionRepository.findAll();
    }

    public Set<JamSession> getAllJamSessionsByUser(int userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
        return jamSessionRepository.findByOwner(user);
    }

    public Set<JamSession> getAllSignedUpJamSessionsByUser(int userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
        return jamSessionRepository.findByConfirmedInstruments_User(user);
    }

    @Transactional
    public void deleteInstrumentFromJamSession(int jamSessionId, int instrumentAndRatingId) {
        JamSession jamSession = getById(jamSessionId);
        int userId = authService.getUserPrincipal().getId();

        var instrument = jamSession.getConfirmedInstruments().stream()
                .filter(j -> j.getId() == instrumentAndRatingId)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Instrument", instrumentAndRatingId));

        if (!instrument.getUser().getId().equals(userId)) {
            throw new UnauthorizedException(
                    "User with id: " + userId + " is not owner of this instrument and rating");
        }
        jamSession.getConfirmedInstruments().remove(instrument);
        jamSessionRepository.save(jamSession);
    }


    @Transactional
    public void deleteJamSession(int id) {
        if(authService.isAdmin()) {
            JamSession jamSession = getById(id);
            jamSessionRepository.delete(jamSession);
        }
        else{
            JamSession jamSession = getJamSessionOwnedByCurrentUser(id);
            jamSessionRepository.delete(jamSession);
        }

    }

    public boolean isOwner(int id) {
        var jam = getById(id);
        var currentUserId = userService.getUserPrinciples().getUserId();
        return jam.getOwner().getId().equals(currentUserId);
    }

    public JamSession save(JamSession jamSession) {
        return jamSessionRepository.save(jamSession);
    }

    @Transactional
    public JamSession addUserToJamSession(int jamSessionId, int instrumentAndRatingId) {
        JamSession jamSession = getById(jamSessionId);
        int currentUserId = authService.getUserPrincipal().getId();

        InstrumentAndRating iar = instrumentAndRatingRepository.findById(instrumentAndRatingId)
                .orElseThrow(() -> new ResourceNotFoundException("InstrumentAndRating", instrumentAndRatingId));

        if (!iar.getUser().getId().equals(currentUserId)) {
            throw new UnauthorizedException("This InstrumentAndRating does not belong to the current user.");
        }

        boolean alreadyIn = jamSession.getConfirmedInstruments().stream()
                .anyMatch(ci -> ci.getUser().getId().equals(currentUserId));
        if (alreadyIn) {
            throw new IllegalArgumentException("User is already signed up for this jam session.");
        }

        long requiredCount = jamSession.getRequiredInstruments().stream()
                .filter(i -> i.getId() == iar.getInstrument().getId())
                .count();

        long confirmedCount = jamSession.getConfirmedInstruments().stream()
                .filter(ci -> ci.getInstrument().getId() == iar.getInstrument().getId())
                .count();

        if (confirmedCount >= requiredCount) {
            throw new IllegalArgumentException("No more available spaces for this jamSession" + iar.getInstrument().getName());
        }

        jamSession.getConfirmedInstruments().add(iar);
        return jamSessionRepository.save(jamSession);
    }

    @Transactional
    public void removeUserFromJamSession(int jamSessionId, int userId) {
        JamSession jamSession = getById(jamSessionId);
        int currentUserId = authService.getUserPrincipal().getId();

        boolean isOwner = jamSession.getOwner().getId().equals(currentUserId);
        boolean isRemovingSelf = currentUserId == userId;
        if ((!isOwner && !isRemovingSelf )&& !authService.isAdmin()) {
            throw new UnauthorizedException(
                    "User with id: " + currentUserId + " is not authorized to remove this user");
        }

        boolean removed = jamSession.getConfirmedInstruments()
                .removeIf(ir -> ir.getUser().getId().equals(userId));

        if (!removed) {
            throw new ResourceNotFoundException("User with id " + userId + " is not part of this jam session", userId);
        }

        jamSessionRepository.save(jamSession);
    }

    @Transactional
    public void addMessageToJamSession(JamSession jamSession, Message message) {
        jamSession.getMessages().add(message);
        jamSessionRepository.save(jamSession);
    }

    // -------------------------------
    // HELPERS
    // -------------------------------
    private JamSession getJamSessionOwnedByCurrentUser(int id) {
        JamSession jam = getById(id);
        ensureOwner(jam);
        return jam;
    }

    private void ensureOwner(JamSession jam) {
        var currentUserId = authService.getUserPrincipal().getId();
        if (!jam.getOwner().getId().equals(currentUserId)) {
            throw new UnauthorizedException("User is not owner of this jam session");
        }
    }

    public JamSession getById(int id) {
        return jamSessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jam Session", id));
    }
}
