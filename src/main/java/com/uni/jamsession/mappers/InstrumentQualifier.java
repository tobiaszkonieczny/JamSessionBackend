package com.uni.jamsession.mappers;

import com.uni.jamsession.exceptions.ResourceNotFoundException;
import com.uni.jamsession.model.Instrument;
import com.uni.jamsession.repositories.InstrumentRepository;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class InstrumentQualifier {
  private final InstrumentRepository instrumentRepository;

  public InstrumentQualifier(InstrumentRepository instrumentRepository) {
    this.instrumentRepository = instrumentRepository;
  }

  @Named("getInstrument")
  public Instrument getInstruments(Integer instrumentId) {
    return instrumentRepository
        .findById(instrumentId)
        .orElseThrow(() -> new ResourceNotFoundException("Instrument", instrumentId));
  }
}
