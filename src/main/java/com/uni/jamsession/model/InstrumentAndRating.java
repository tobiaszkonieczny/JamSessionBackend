package com.sap.jamsession.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
    name = "instruments_and_ratings",
    indexes = @Index(name = "instruments_and_ratings_idx", columnList = "instrument_id, user_id"))
public class InstrumentAndRating {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "instrument_id", nullable = false)
  private Instrument instrument;

  private int rating;

  @ManyToMany(mappedBy = "confirmedInstruments", cascade = CascadeType.ALL)
  @JsonIgnore
  private Set<JamSession> jamSessions;
}
