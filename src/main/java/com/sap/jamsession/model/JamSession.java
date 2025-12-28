package com.sap.jamsession.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

@Getter
@Setter
@Entity
@Table(name = "JamSessions")
public class JamSession {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne private User owner;

  @ManyToMany
  @JoinTable(
          name = "jam_session_instruments_and_ratings",
          joinColumns = @JoinColumn(name = "jam_session_id"),
          inverseJoinColumns = @JoinColumn(name = "instruments_and_ratings_id"))
  private List<InstrumentAndRating> confirmedInstruments; 

  private LocalDateTime startTime;
  @Embedded private Location location;

  @ManyToMany
  @JoinTable(
          name = "jam_session_instruments",
          joinColumns = @JoinColumn(name = "jam_session_id"),
          inverseJoinColumns = @JoinColumn(name = "instruments_id"))
  @OrderColumn(name = "instrument_order")
  private List<Instrument> requiredInstruments;

  @ManyToOne private MusicGenre musicGenre;

  @OneToMany(mappedBy = "jamSession", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Message> messages = new HashSet<>();
}