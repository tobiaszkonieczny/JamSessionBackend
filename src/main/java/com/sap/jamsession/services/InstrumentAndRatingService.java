package com.sap.jamsession.services;

import com.sap.jamsession.mappers.InstrumentAndRatingMapper;
import com.sap.jamsession.model.InstrumentAndRating;
import com.sap.jamsession.model.User;
import com.sap.jamsession.dtos.CreateInstrumentAndRatingDto;
import com.sap.jamsession.repositories.InstrumentAndRatingRepository;
import jakarta.transaction.Transactional;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstrumentAndRatingService {

  private final InstrumentAndRatingRepository instrumentAndRatingRepository;

  private final UserService userService;
  private final InstrumentAndRatingMapper instrumentAndRatingMapper;

  @Autowired
  public InstrumentAndRatingService(
      InstrumentAndRatingRepository instrumentAndRatingRepository,
      UserService userService,
      InstrumentAndRatingMapper instrumentAndRatingMapper) {
    this.instrumentAndRatingRepository = instrumentAndRatingRepository;

    this.userService = userService;
    this.instrumentAndRatingMapper = instrumentAndRatingMapper;
  }

  @Transactional
  public Set<InstrumentAndRating> addNewInstrumentAndRating(
      Set<CreateInstrumentAndRatingDto> createInstrumentAndRatingDto) {

    var userPrinciple = userService.getUserPrinciples();
    var user = userService.getUser(userPrinciple.getUserId());

    AtomicBoolean isChanged = new AtomicBoolean(false);

    var newInstruments =
        createInstrumentAndRatingDto.stream()
            .map(instrumentAndRatingMapper::instrumentAndRatingDtotoInstrumentAndRating)
            .toList();

    Map<String, Integer> newInstrumentMap =
        newInstruments.stream()
            .collect(
                Collectors.toMap(
                    instrument -> instrument.getInstrument().getName(),
                    InstrumentAndRating::getRating));

    user.getInstrumentsAndRatings()
        .removeIf(
            existingInstrument -> {
              var instrumentName = existingInstrument.getInstrument().getName();

              if (newInstrumentMap.containsKey(instrumentName)) {
                isChanged.set(true);
                if (!(existingInstrument.getRating() == newInstrumentMap.get(instrumentName))) {
                  existingInstrument.setRating(newInstrumentMap.get(instrumentName));
                }
                return false;
              } else {
                return true;
              }
            });

    newInstruments.forEach(
        instrument -> {
          String instrumentName = instrument.getInstrument().getName();

          if (user.getInstrumentsAndRatings().stream()
              .noneMatch(existing -> existing.getInstrument().getName().equals(instrumentName))) {
            isChanged.set(true);
            instrument.setUser(user);
            user.getInstrumentsAndRatings().add(instrument);
          }
        });

    if (isChanged.get()) {
      userService.saveUser(user);
    }

    return user.getInstrumentsAndRatings();
  }

  //  @Transactional
  //  public Set<InstrumentAndRating> addNewInstrumentAndRating(
  //      Set<CreateInstrumentAndRatingDto> createInstrumentAndRatingDto) {
  //
  //    var userPrinciple = userService.getUserPrinciples();
  //    var user = userService.getUser(userPrinciple.getUserId());
  //
  //    var newInstruments =
  //        createInstrumentAndRatingDto.stream()
  //            .map(instrumentAndRatingMapper::instrumentAndRatingDtotoInstrumentAndRating)
  //            .toList();
  //
  //    var existingInstruments = user.getInstrumentsAndRatings();
  //    boolean isChanged = false;
  //
  //    for (var newInstrument : newInstruments) {
  //      Optional<InstrumentAndRating> existing =
  //          existingInstruments.stream().filter(i -> i.getId() ==
  // newInstrument.getId()).findFirst();
  //
  //      if (existing.isPresent()) {
  //
  //        throw new ResourceDuplicatedException(
  //            "Instrument and Rating", "Instrument", existing.get().getInstrument().getName());
  //
  //      } else {
  //        newInstrument.setUser(user);
  //        existingInstruments.add(newInstrument);
  //        isChanged = true;
  //      }
  //    }
  //
  //    if (isChanged) {
  //      userService.saveUser(user);
  //    }
  //
  //    return existingInstruments;
  //  }

  private void createInstrumentAndRating(InstrumentAndRating instrument, User user) {
    user.getInstrumentsAndRatings().stream()
        .filter(i -> i.getId() == instrument.getId())
        .findFirst()
        .ifPresentOrElse(
            i -> {
              i.setRating(instrument.getRating());
            },
            () -> user.getInstrumentsAndRatings().add(instrument));
  }
}
