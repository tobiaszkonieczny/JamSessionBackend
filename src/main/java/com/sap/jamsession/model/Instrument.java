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
    name = "instruments",
    uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
public class Instrument {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String name;

  @ManyToMany(mappedBy = "requiredInstruments")
  @JsonIgnore
  private Set<JamSession> jamSessions;
}
