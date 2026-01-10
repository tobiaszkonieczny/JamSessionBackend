package com.uni.jamsession.mappers;

import com.uni.jamsession.model.MusicGenre;
import com.uni.jamsession.model.User;
import com.uni.jamsession.dtos.UserDto;
import com.uni.jamsession.repositories.MusicGenreRepository;
import java.util.Set;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring", uses = JamSessionMapper.class)
public abstract class UserMapper {

  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private MusicGenreRepository musicGenreRepository;

  @Mapping(source = "ownedJamSessions", target = "ownedJamSessions")
  public abstract UserDto userToUserDto(User user);

  @Mapping(source = "ownedJamSessions", target = "ownedJamSessions")
  public abstract User toEntity(UserDto userDto);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "role", ignore = true)
  @Mapping(target = "profilePictureId", ignore = true)
  @Mapping(target = "ownedJamSessions", ignore = true)
  @Mapping(
      target = "favoriteGenres",
      source = "favouriteGenreIds",
      qualifiedByName = "handleFavoriteMusicGenres")
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  public abstract void onEditToUser(UserDto userDto, @MappingTarget User user);

  @Named("handleFavoriteMusicGenres")
  protected Set<MusicGenre> handleFavoriteMusicGenres(Set<Integer> favouriteGenreIds) {
    return musicGenreRepository.findByIdIn(favouriteGenreIds);
  }

  @Named("handlePassword")
  protected String handlePassword(String password) {
    return (password != null) ? passwordEncoder.encode(password) : null;
  }
}
