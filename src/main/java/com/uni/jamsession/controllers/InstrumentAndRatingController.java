package com.uni.jamsession.controllers;

import com.uni.jamsession.dtos.instrumentandrating.InstrumentAndRatingDto;
import com.uni.jamsession.facade.InstrumentAndRatingFacade;
import com.uni.jamsession.dtos.instrumentandrating.CreateInstrumentAndRatingDto;

import java.util.Set;

import lombok.AllArgsConstructor;
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

  @DeleteMapping
    public ResponseEntity<Void> deleteInstrumentAndRating(
        @RequestParam("id") Integer id) throws IllegalAccessException {
        instrumentAndRatingFacade.deleteInstrumentAndRating(id);
        return ResponseEntity.noContent().build();
    }

  @GetMapping
  public ResponseEntity<Set<InstrumentAndRatingDto>> getUserRatings(
      @RequestParam("userId") Integer userId) {
    return ResponseEntity.ok(instrumentAndRatingFacade.getByUserId(userId));
  }
}
