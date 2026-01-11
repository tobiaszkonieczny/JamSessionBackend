package com.uni.jamsession.services;

import com.uni.jamsession.dtos.MusicGenreDto;
import com.uni.jamsession.exceptions.ResourceDuplicatedException;
import com.uni.jamsession.exceptions.ResourceNotFoundException;
import com.uni.jamsession.model.MusicGenre;
import com.uni.jamsession.dtos.CreateMusicGenreDto;
import com.uni.jamsession.repositories.MusicGenreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class MusicGenreService {
  private final MusicGenreRepository musicGenreRepository;

  public List<MusicGenre> getAllMusicGenres() {
    return musicGenreRepository.findAll();
  }

  @Transactional
  public MusicGenre createMusicGenre(MusicGenre musicGenre) {
    musicGenreRepository
        .findByName(musicGenre.getName())
        .ifPresent(
            (genre) -> {
              throw new ResourceDuplicatedException("Music genre", "name", genre.getName());
            });
      return musicGenreRepository.save(musicGenre);
  }

  @Transactional
  public void deleteById(int id) {
    if (!musicGenreRepository.existsById(id)) {
      throw new ResourceNotFoundException("Music genre", id);
    }
    musicGenreRepository.deleteById(id);
  }

}
