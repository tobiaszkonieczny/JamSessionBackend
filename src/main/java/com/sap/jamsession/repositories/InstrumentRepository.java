package com.sap.jamsession.repositories;

import com.sap.jamsession.model.Instrument;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstrumentRepository extends JpaRepository<Instrument, Integer> {
  public Instrument getInstrumentById(int instrumentId);

  public Instrument save(Instrument instrument);

  public List<Instrument> findByIdIn(List<Integer> instrumentIds);

  Optional<Instrument> findByName(String name);
}
