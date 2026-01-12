package com.uni.jamsession.mappers;

import com.uni.jamsession.dtos.user.UserEditDto;
import com.uni.jamsession.dtos.user.UserRegisterDto;
import com.uni.jamsession.dtos.user.UserShortDto;
import com.uni.jamsession.model.MusicGenre;
import com.uni.jamsession.model.User;
import com.uni.jamsession.dtos.user.UserDto;
import com.uni.jamsession.repositories.MusicGenreRepository;
import java.util.Set;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(
        componentModel = "spring",
        uses = {MusicGenreMapper.class, JamSessionMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {

  UserDto toDto(User user);
  UserShortDto toShortDto(User user);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "password", ignore = true)
  @Mapping(target = "role", constant = "ROLE_USER")
  @Mapping(target = "favoriteGenres", ignore = true)
  @Mapping(target = "ownedJamSessions", ignore = true)
  @Mapping(target = "instrumentsAndRatings", ignore = true)
  User toEntity(UserRegisterDto dto);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "password", ignore = true)
  @Mapping(target = "email", ignore = true)
  @Mapping(target = "favoriteGenres", ignore = true)
  @Mapping(target = "ownedJamSessions", ignore = true)
  @Mapping(target = "instrumentsAndRatings", ignore = true)
  void updateUserFromDto(UserEditDto dto, @MappingTarget User user);
}
