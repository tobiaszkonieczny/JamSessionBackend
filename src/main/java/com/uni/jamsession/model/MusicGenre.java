package com.uni.jamsession.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity()
@Table(name = "music_genres")
public class MusicGenre {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String name;

  @ManyToMany(mappedBy = "favoriteGenres")
  @JsonIgnore
  private Set<User> users = new HashSet<>();
  @OneToMany(mappedBy = "musicGenre") @JsonIgnore private Set<JamSession> jamSessions = new HashSet<>();
}
