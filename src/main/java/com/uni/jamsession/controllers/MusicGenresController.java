package com.uni.jamsession.controllers;

import com.uni.jamsession.model.MusicGenre;
import com.uni.jamsession.dtos.CreateMusicGenreDto;
import com.uni.jamsession.repositories.MusicGenreRepository;
import com.uni.jamsession.services.MusicGenreService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/genres")
public class MusicGenresController {

  private final MusicGenreRepository musicGenreRepository;
  private final MusicGenreService musicGenreService;

  public MusicGenresController(
      MusicGenreRepository musicGenreRepository, MusicGenreService musicGenreService) {
    this.musicGenreRepository = musicGenreRepository;
    this.musicGenreService = musicGenreService;
  }

  @GetMapping("/all")
  public ResponseEntity<List<MusicGenre>> getAllGenres() {
    return ResponseEntity.ok(musicGenreRepository.findAll());
  }

  @PostMapping("/create")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<CreateMusicGenreDto> addGenre(
      @RequestBody CreateMusicGenreDto musicGenreDto) {
    return new ResponseEntity<>(
        musicGenreService.createMusicGenre(musicGenreDto), HttpStatus.CREATED);
  }
  @DeleteMapping("/delete")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<String> deleteGenre(@RequestParam int id) {
    musicGenreRepository.deleteById(id);
    return new ResponseEntity<>("Deleted genre", HttpStatus.OK);
  }

}
