package com.sap.jamsession.repositories;

import com.sap.jamsession.model.JamSession;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.sap.jamsession.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JamSessionRepository extends JpaRepository<JamSession, Integer> {
    public Set<JamSession> findByOwner(User owner);

    public Set<JamSession> findByConfirmedInstruments_User(User user);

  public JamSession save(JamSession session);

  public Optional<JamSession> findById(int id);

  public List<JamSession> findAll();
}
