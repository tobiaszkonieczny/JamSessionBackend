package com.sap.jamsession.controllers;

import com.sap.jamsession.dtos.InstrumentAndRatingDto;
import com.sap.jamsession.facade.InstrumentAndRatingFacade;
import com.sap.jamsession.model.InstrumentAndRating;
import com.sap.jamsession.dtos.CreateInstrumentAndRatingDto;
import com.sap.jamsession.repositories.InstrumentAndRatingRepository;
import com.sap.jamsession.services.InstrumentAndRatingService;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rating")
@AllArgsConstructor
public class InstrumentAndRatingController {

  private final InstrumentAndRatingFacade instrumentAndRatingFacade;


  @PostMapping("/batch")
  public ResponseEntity<Set<InstrumentAndRatingDto>> addInstrumentAndRating(
      @RequestBody Set<CreateInstrumentAndRatingDto> instrumentAndRatings) {

   Set<InstrumentAndRatingDto> instrumentAndRatingDtos = instrumentAndRatingFacade.addNewInstrumentAndRatingBatch(instrumentAndRatings);
    return ResponseEntity.ok(instrumentAndRatingDtos);
  }

  @GetMapping
  public ResponseEntity<Set<InstrumentAndRatingDto>> getUserRatings(
      @RequestParam("userId") Integer userId) {
    return ResponseEntity.ok(instrumentAndRatingFacade.getByUserId(userId));
  }
}
