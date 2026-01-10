package com.sap.jamsession.mappers;

import com.sap.jamsession.exceptions.ResourceNotFoundException;
import com.sap.jamsession.model.Instrument;
import com.sap.jamsession.repositories.InstrumentRepository;
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
