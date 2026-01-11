package com.uni.jamsession.controllers;

import com.uni.jamsession.dtos.instrument.InstrumentDto;
import com.uni.jamsession.facade.InstrumentFacade;
import com.uni.jamsession.model.Instrument;
import com.uni.jamsession.dtos.instrument.CreateInstrumentDto;
import com.uni.jamsession.repositories.InstrumentRepository;
import com.uni.jamsession.services.InstrumentService;
import jakarta.validation.Valid;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/instruments")
public class InstrumentController {
  private final InstrumentFacade instrumentFacade;

  @PostMapping("/new")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<InstrumentDto> addInstrument(
      @Valid @RequestBody CreateInstrumentDto createInstrumentDto) {
    InstrumentDto instrumentDto = instrumentFacade.createInstrument(createInstrumentDto);
    return ResponseEntity.ok(instrumentDto);
  }

  @GetMapping("/all")
  public ResponseEntity<List<InstrumentDto>> getAllInstruments() {
    List<InstrumentDto> instruments = instrumentFacade.getAllInstruments();
    return ResponseEntity.ok(instruments);
  }

  @DeleteMapping("/delete")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<String> deleteInstrument(@RequestParam int id) {
    instrumentFacade.deleteInstrument(id);
    return ResponseEntity.ok("Deleted instrument with id: " + id);
  }
}
