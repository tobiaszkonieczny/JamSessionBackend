package com.uni.jamsession.services;

import com.uni.jamsession.exceptions.ResourceDuplicatedException;
import com.uni.jamsession.model.Instrument;
import com.uni.jamsession.repositories.InstrumentRepository;
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
