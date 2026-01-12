package com.uni.jamsession.services;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.uni.jamsession.dtos.user.UserEditDto;
import com.uni.jamsession.dtos.user.UserLoginDto;
import com.uni.jamsession.exceptions.ResourceDuplicatedException;
import com.uni.jamsession.exceptions.ResourceNotFoundException;
import com.uni.jamsession.exceptions.UnauthorizedException;
import com.uni.jamsession.mappers.UserMapper;
import com.uni.jamsession.model.InstrumentAndRating;
import com.uni.jamsession.model.MusicGenre;
import com.uni.jamsession.model.User;
import com.uni.jamsession.dtos.user.UserDto;
import com.uni.jamsession.repositories.JamSessionRepository;
import com.uni.jamsession.repositories.MusicGenreRepository;
import com.uni.jamsession.repositories.UserRepository;
import com.uni.jamsession.security.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtIssuer jwtIssuer;
  private final AuthenticationManager authenticationManager;
  private final AuthService authService;
  private final MusicGenreRepository musicGenreRepository;
  private final JamSessionRepository jamsSessionRepository;


  @Transactional
  public User update(int id, User user) throws IllegalAccessException {
    userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", id));
    if (!isCurrentUser(id)){
      throw new IllegalAccessException("User cannot update other users");
    }
    return userRepository.save(user);
  }

  public String loginUser(UserLoginDto loginRequest) {
    var authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

    List<String> roles = principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

    return jwtIssuer.issuer(
        principal.getUserId(), principal.getUsername(), principal.getEmail(), roles);
  }

  @Transactional
  public User registerUser(User user, String rawPassword) {
    if (userRepository.existsByEmail(user.getEmail())) {
      throw new ResourceDuplicatedException("User", "email", user.getEmail());
    }

    user.setPassword(passwordEncoder.encode(rawPassword));
    return userRepository.save(user);
  }

  @Transactional
  public User saveUser(User user) {
    if (!isCurrentUser(user.getId())) {
      throw new UnauthorizedException("You can only update your own profile");
    }
    return userRepository.save(user);
  }

  @Transactional
  public void updateGenres(User user, Set<Integer> genreIds) {
    if (genreIds == null) return;

    List<MusicGenre> genres = musicGenreRepository.findAllById(genreIds);
    user.setFavoriteGenres(new HashSet<>(genres));
  }

  public User getUserById(int id) {
    return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", id));
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public User getCurrentUser(){
     return userRepository.findById(
             authService.getUserPrincipal().getId()
     ).orElseThrow(
             () -> new ResourceNotFoundException("User", authService.getUserPrincipal().getId())
     );
  }

  public boolean isCurrentUser(int userId) {
    return authService.getUserPrincipal().getUserId()==userId;
  }
}
