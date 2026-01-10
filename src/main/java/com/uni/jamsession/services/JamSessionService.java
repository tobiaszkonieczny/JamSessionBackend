package com.uni.jamsession.services;

import com.uni.jamsession.exceptions.ResourceNotFoundException;
import com.uni.jamsession.exceptions.UnauthorizedException;
import com.uni.jamsession.mappers.EditJamSessionMapper;
import com.uni.jamsession.mappers.JamSessionMapper;
import com.uni.jamsession.model.Instrument;
import com.uni.jamsession.model.InstrumentAndRating;
import com.uni.jamsession.model.JamSession;
import com.uni.jamsession.dtos.CreateJamSessionDto;
import com.uni.jamsession.dtos.EditJamSessionDto;
import com.uni.jamsession.dtos.JamSessionDto;
import com.uni.jamsession.repositories.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.uni.jamsession.repositories.*;
import com.uni.jamsession.security.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JamSessionService {

    private final UserRepository userRepository;
    private final JamSessionRepository jamSessionRepository;
    private final JamSessionMapper jamSessionMapper;
    private final EditJamSessionMapper editJamSessionMapper;
    private final UserService userService;
    private final MusicGenreRepository musicGenreRepository;
    private final InstrumentAndRatingRepository instrumentAndRatingRepository;
    private final AuthService authService;
    private final InstrumentRepository instrumentRepository;


    public int createJamSession(CreateJamSessionDto dto) {

        var userId = userService.getUserPrinciples().getUserId();

        var owner = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        var genre = musicGenreRepository.findById(dto.getMusicGenreId())
                .orElseThrow(() -> new ResourceNotFoundException("Music genre", dto.getMusicGenreId()));

        var jamSession = new JamSession();
        jamSession.setOwner(owner);
        jamSession.setRequiredInstruments(dto.getRequiredInstruments());
        jamSession.setStartTime(dto.getStartTime());
        jamSession.setLocation(dto.getLocation());
        jamSession.setMusicGenre(genre);

        jamSessionRepository.save(jamSession);
        return jamSession.getId();
    }


    public JamSession editJamSession(EditJamSessionDto dto, int id) {
        var jamSession = getById(id);

        ensureOwner(jamSession);

        editJamSessionMapper.updateJamSessionFromDto(dto, jamSession);

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

            // Tutaj decydujesz czy nadpisujesz (set) czy dodajesz (addAll)
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

    public JamSessionDto getJamSessionById(int id) {
        return jamSessionMapper.jamSessionToJamSessionDto(getById(id));
    }

    public List<JamSessionDto> getAllJamSessions() {
        return jamSessionRepository.findAll().stream()
                .map(jamSessionMapper::jamSessionToJamSessionDto)
                .collect(Collectors.toList());
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

    public void deleteInstrumentFromJamSession(int jamSessionId, int instrumentAndRatingId) {

        var jamSession = getById(jamSessionId);
        var currentUser = userService.getUserDtoById(userService.getUserPrinciples().getUserId());

        var instrument = jamSession.getConfirmedInstruments().stream()
                .filter(j -> j.getId() == instrumentAndRatingId)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Instrument", instrumentAndRatingId));

        if (!instrument.getUser().getId().equals(currentUser.id())) {
            throw new UnauthorizedException(
                    "User with id: " + currentUser.id() + " is not owner of this instrument and rating");
        }

        jamSession.getConfirmedInstruments().remove(instrument);
        jamSessionRepository.save(jamSession);
    }


    public void deleteJamSession(int id) {
        var jamSession = getJamSessionOwnedByCurrentUser(id);
        jamSessionRepository.delete(jamSession);
    }

    public boolean isOwner(int id) {
        var jam = getById(id);
        var currentUserId = userService.getUserPrinciples().getUserId();
        return jam.getOwner().getId().equals(currentUserId);
    }

    public JamSession save(JamSession jamSession) {
        return jamSessionRepository.save(jamSession);
    }

    public JamSession addUserToJamSession(int jamSessionId, int instrumentAndRatingId) {
        var jamSession = getById(jamSessionId);
        var currentUserId = userService.getUserPrinciples().getUserId();

        var iar = instrumentAndRatingRepository.findById(instrumentAndRatingId)
                .orElseThrow(() -> new ResourceNotFoundException("InstrumentAndRating", instrumentAndRatingId));

        if (!iar.getUser().getId().equals(currentUserId)) {
            throw new UnauthorizedException("Nie posiadasz uprawnień do tego instrumentu.");
        }

        boolean alreadyIn = jamSession.getConfirmedInstruments().stream()
                .anyMatch(ci -> ci.getUser().getId().equals(currentUserId));
        if (alreadyIn) {
            throw new IllegalArgumentException("Już bierzesz udział w tej sesji.");
        }

        long requiredCount = jamSession.getRequiredInstruments().stream()
                .filter(i -> i.getId() == iar.getInstrument().getId())
                .count();

        long confirmedCount = jamSession.getConfirmedInstruments().stream()
                .filter(ci -> ci.getInstrument().getId() == iar.getInstrument().getId())
                .count();

        if (confirmedCount >= requiredCount) {
            throw new IllegalArgumentException("Brak wolnych miejsc na instrument: " + iar.getInstrument().getName());
        }

        jamSession.getConfirmedInstruments().add(iar);
        return jamSessionRepository.save(jamSession);
    }

    public void removeUserFromJamSession(int jamSessionId, int userId) {
        System.out.println("Attempting to remove user with ID: " + userId + " from jam session with ID: " + jamSessionId);
        var jamSession = getById(jamSessionId);
        var currentUserId = userService.getUserPrinciples().getUserId();

        boolean isOwner = jamSession.getOwner().getId().equals(currentUserId);
        boolean isRemovingSelf = currentUserId == userId;
        System.out.println("IsAdmin:" + authService.isAdmin());
        if ((!isOwner && !isRemovingSelf )&& !authService.isAdmin()) {
            throw new UnauthorizedException(
                    "User with id: " + currentUserId + " is not authorized to remove this user");
        }

        for( InstrumentAndRating ir : jamSession.getConfirmedInstruments()) {
            System.out.println("JamSession ID: " + jamSessionId + ", InstrumentAndRating ID: " + ir.getId() + ", User ID: " + ir.getUser().getId());
        }
        boolean removed = jamSession.getConfirmedInstruments()
                .removeIf(ir -> ir.getUser().getId().equals(userId));

        if (!removed) {
            throw new ResourceNotFoundException("User with id " + userId + " is not part of this jam session", userId);
        }

        jamSessionRepository.save(jamSession);
    }

    // -------------------------------
    // HELPERS
    // -------------------------------
    private JamSession getJamSessionOwnedByCurrentUser(int id) {
        var jam = getById(id);
        ensureOwner(jam);
        return jam;
    }

    private void ensureOwner(JamSession jam) {
        var currentUserId = userService.getUserPrinciples().getUserId();
        if (!jam.getOwner().getId().equals(currentUserId)) {
            throw new UnauthorizedException("User is not owner of this jam session");
        }
    }

    public JamSession getById(int id) {
        return jamSessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jam Session", id));
    }
}
