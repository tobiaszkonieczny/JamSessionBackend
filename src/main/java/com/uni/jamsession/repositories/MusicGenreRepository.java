package com.uni.jamsession.repositories;

import com.uni.jamsession.model.MusicGenre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface MusicGenreRepository extends JpaRepository<MusicGenre, Integer> {
  Optional<MusicGenre> findByName(String name);
  Set<MusicGenre> findByIdIn(Set<Integer> ids);
}
