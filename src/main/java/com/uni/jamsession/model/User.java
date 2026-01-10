package com.sap.jamsession.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
@AllArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;
  private String email;
  @JsonIgnore private String password;
  private int profilePictureId;
  private String bio;
  private String role;

  @ManyToMany
  @JoinTable(
      name = "user_ganres",
      joinColumns = {@JoinColumn(name = "user_id")},
      inverseJoinColumns = @JoinColumn(name = "genre_id"))
  private Set<MusicGenre> favoriteGenres = new HashSet<>();

  @OneToMany private Set<JamSession> ownedJamSessions = new HashSet<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private Set<InstrumentAndRating> instrumentsAndRatings = new HashSet<>();

  public User() {}

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (User) obj;
    return Objects.equals(this.id, that.id)
        && Objects.equals(this.name, that.name)
        && Objects.equals(this.email, that.email)
        && Objects.equals(this.password, that.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, email, password);
  }

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
        + ", "
        + "password="
        + password
        + ']';
  }
}
