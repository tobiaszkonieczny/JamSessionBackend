package com.uni.jamsession.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Integer id;

  private String name;
  private String email;
  @JsonIgnore private String password;
  private int profilePictureId;
  private String bio;
  private String role;

  @ManyToMany
  @JoinTable(
      name = "user_genres",
      joinColumns = {@JoinColumn(name = "user_id")},
      inverseJoinColumns = @JoinColumn(name = "genre_id"))
  private Set<MusicGenre> favoriteGenres = new HashSet<>();

  @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
  private Set<JamSession> ownedJamSessions = new HashSet<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private Set<InstrumentAndRating> instrumentsAndRatings = new HashSet<>();

  public User() {}


  @Override
  public String toString() {
    return "User["
        + "id="
        + id
        + ", "
        + "name="
        + name
        + ", "
        + "email="
        + email
        + ']';
  }
}
