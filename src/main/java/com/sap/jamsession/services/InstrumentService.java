package com.sap.jamsession.services;

import com.sap.jamsession.exceptions.ResourceDuplicatedException;
import com.sap.jamsession.model.Instrument;
import com.sap.jamsession.repositories.InstrumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstrumentService {
  private final InstrumentRepository instrumentRepository;

  @Autowired
  public InstrumentService(InstrumentRepository instrumentRepository) {
    this.instrumentRepository = instrumentRepository;
  }

  public Instrument createInstrument(String instrumentName) {
    if (instrumentRepository.findByName(instrumentName).isPresent()) {
      throw new ResourceDuplicatedException("Instrument", "name", instrumentName);
    }
    var newInstrument = new Instrument();
    newInstrument.setName(instrumentName);
    instrumentRepository.save(newInstrument);
    return newInstrument;
  }
}
