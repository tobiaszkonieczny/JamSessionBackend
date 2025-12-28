package com.sap.jamsession.controllers;

import com.sap.jamsession.model.InstrumentAndRating;
import com.sap.jamsession.dtos.CreateInstrumentAndRatingDto;
import com.sap.jamsession.repositories.InstrumentAndRatingRepository;
import com.sap.jamsession.services.InstrumentAndRatingService;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rating")
public class InstrumentAndRatingController {

  private final InstrumentAndRatingService instrumentAndRatingService;
  private final InstrumentAndRatingRepository instrumentAndRatingRepository;

  @Autowired
  public InstrumentAndRatingController(
      InstrumentAndRatingService instrumentAndRatingService,
      InstrumentAndRatingRepository instrumentAndRatingRepository) {
    this.instrumentAndRatingService = instrumentAndRatingService;
    this.instrumentAndRatingRepository = instrumentAndRatingRepository;
  }

  @PostMapping("/new")
  public ResponseEntity<Set<InstrumentAndRating>> addInstrumentAndRating(
      @RequestBody Set<CreateInstrumentAndRatingDto> instrumentAndRating) {

    var newInstrumentAndRating =
        instrumentAndRatingService.addNewInstrumentAndRating(instrumentAndRating);
    return new ResponseEntity<>(newInstrumentAndRating, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<InstrumentAndRating>> getUserRatings(
      @RequestParam("userId") Integer userId) {
    return new ResponseEntity<>(instrumentAndRatingRepository.findByUserId(userId), HttpStatus.OK);
  }
}
