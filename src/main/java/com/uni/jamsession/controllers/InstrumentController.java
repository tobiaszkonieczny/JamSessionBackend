package com.uni.jamsession.controllers;

import com.uni.jamsession.model.Instrument;
import com.uni.jamsession.dtos.CreateInstrumentDto;
import com.uni.jamsession.repositories.InstrumentRepository;
import com.uni.jamsession.services.InstrumentService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/instruments")
public class InstrumentController {
  private final InstrumentService instrumentService;
  private final InstrumentRepository instrumentRepository;

  @Autowired
  public InstrumentController(
      InstrumentService instrumentService, InstrumentRepository instrumentRepository) {
    this.instrumentService = instrumentService;
    this.instrumentRepository = instrumentRepository;
  }

  @PostMapping("/new")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<CreateInstrumentDto> addInstrument(
      @Valid @RequestBody CreateInstrumentDto createInstrumentDto) {
    var savedInstrument = instrumentService.createInstrument(createInstrumentDto.name());
    var instrumentToReturn =
        new CreateInstrumentDto(savedInstrument.getId(), savedInstrument.getName());
    return new ResponseEntity<>(instrumentToReturn, HttpStatus.CREATED);
  }

  @GetMapping("/all")
  public ResponseEntity<List<Instrument>> getAllInstruments() {
    List<Instrument> instruments = instrumentRepository.findAll();
    return new ResponseEntity<>(instruments, HttpStatus.OK);
  }

  @DeleteMapping("/delete")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<String> deleteInstrument(@RequestParam int id) {
    instrumentRepository.deleteById(id);
    return new ResponseEntity<>("Deleted instrument", HttpStatus.OK);
  }
}
