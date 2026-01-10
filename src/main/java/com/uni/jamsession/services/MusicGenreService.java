package com.uni.jamsession.services;

import com.uni.jamsession.exceptions.ResourceDuplicatedException;
import com.uni.jamsession.exceptions.ResourceNotFoundException;
import com.uni.jamsession.model.MusicGenre;
import com.uni.jamsession.dtos.CreateMusicGenreDto;
import com.uni.jamsession.repositories.MusicGenreRepository;
import org.springframework.stereotype.Service;

@Service
public class MusicGenreService {
  private final MusicGenreRepository musicGenreRepository;

  public MusicGenreService(MusicGenreRepository musicGenreRepository) {
    this.musicGenreRepository = musicGenreRepository;
  }

  public CreateMusicGenreDto createMusicGenre(CreateMusicGenreDto createMusicGenreDto) {

    musicGenreRepository
        .findByName(createMusicGenreDto.name())
        .ifPresent(
            (genre) -> {
              throw new ResourceDuplicatedException("Music genre", "name", genre.getName());
            });
    var newGenre = new MusicGenre();
    newGenre.setName(createMusicGenreDto.name());
    musicGenreRepository.save(newGenre);
    return new CreateMusicGenreDto(newGenre.getId(), newGenre.getName());
  }

  public MusicGenre getMusicGenreById(int id) {
    return musicGenreRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("MusicGenre", id));
  }
}
