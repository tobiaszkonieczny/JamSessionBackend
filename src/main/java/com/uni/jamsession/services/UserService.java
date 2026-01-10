package com.sap.jamsession.services;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sap.jamsession.exceptions.ResourceDuplicatedException;
import com.sap.jamsession.exceptions.ResourceNotFoundException;
import com.sap.jamsession.mappers.UserMapper;
import com.sap.jamsession.model.User;
import com.sap.jamsession.dtos.UserDto;
import com.sap.jamsession.repositories.UserRepository;
import com.sap.jamsession.security.*;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@AllArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;
  private final HttpServletRequest request;
  private final JwtDecoder jwtDecoder;
  private final JwtToPrincipalConverter jwtToPrincipalConverter;
  private final JwtIssuer jwtIssuer;
  private final AuthenticationManager authenticationManager;
  private final AuthService authService;


  public UserDto update(int id, UserDto editUserDto) throws IllegalAccessException {

    userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", id));

    var userPrinciple = getUserPrinciples();
    if (userPrinciple.getUserId() != id)
      throw new IllegalAccessException("User cannot update other users");
    var user = getUser(userPrinciple.getUserId());
    userMapper.onEditToUser(editUserDto, user);
    userRepository.save(user);

    return userMapper.userToUserDto(user);
  }

  public String loginUser(UserDto loginRequest) {
    var authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    var principal = (UserPrincipal) authentication.getPrincipal();

    var roles = principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

    return jwtIssuer.issuer(
        principal.getUserId(), principal.getUsername(), principal.getEmail(), roles);
  }

  public User registerUser(UserDto registerUserDto) {
    if (userRepository.existsByEmail(registerUserDto.email())) {
      throw new ResourceDuplicatedException("User", "email", registerUserDto.email());
    }
    User user = new User();
    user.setName(registerUserDto.name());
    user.setEmail(registerUserDto.email());
    var password = passwordEncoder.encode(registerUserDto.password());
    user.setPassword(password);
    user.setRole("ROLE_USER");
    userRepository.save(user);
    return user;
  }

  public UserDto getUserDtoById(int id) {
    var user =
        userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", id));
    return userMapper.userToUserDto(user);
  }

  public User getUserById(int id) {
    return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", id));
  }

  public List<UserDto> getAllUsers() {
    return userRepository.findAll().stream()
        .map(userMapper::userToUserDto)
        .collect(Collectors.toList());
  }

  public User getUser(int id) {
    return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", id));
  }

  public UserPrincipal getUserPrinciples() {
    var tokenHeader = request.getHeader("Authorization");

    if (!StringUtils.hasText(tokenHeader) || !tokenHeader.startsWith("Bearer ")) {
      throw new JWTVerificationException("");
    }
    var token = tokenHeader.substring(7);
    DecodedJWT decodedJwt = jwtDecoder.decode(token);
    return jwtToPrincipalConverter.convert(decodedJwt);
  }

  public User getCurrentUser(){
     return userRepository.findById(
             authService.getUserPrincipal().getId()
     ).orElseThrow(
             () -> new ResourceNotFoundException("User", authService.getUserPrincipal().getId())
     );
  }

  public void saveUser(User user) {
    userRepository.save(user);
  }

  public boolean isCurrentUser(int userId) {
    return authService.getUserPrincipal().getUserId()==userId;
  }
}
