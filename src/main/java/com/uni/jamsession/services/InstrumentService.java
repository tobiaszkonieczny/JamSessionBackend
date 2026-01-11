package com.uni.jamsession.services;

import com.uni.jamsession.exceptions.ResourceDuplicatedException;
import com.uni.jamsession.model.Instrument;
import com.uni.jamsession.repositories.InstrumentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // Zastępuje ręczny konstruktor
public class InstrumentService {
  private final InstrumentRepository instrumentRepository;

  @Transactional
  public Instrument createInstrument(String instrumentName) {
    if (instrumentRepository.findByName(instrumentName).isPresent()) {
      throw new ResourceDuplicatedException("Instrument", "name", instrumentName);
    }
    Instrument newInstrument = new Instrument();
    newInstrument.setName(instrumentName);
    return instrumentRepository.save(newInstrument);
  }

  @Transactional
  public void deleteInstrument(int id) {
    if (!instrumentRepository.existsById(id)) {
      throw new ResourceNotFoundException("Instrument:"+ id);
    }
    instrumentRepository.deleteById(id);
  }

    public List<Instrument> findAll() {
        return instrumentRepository.findAll();
    }
}
