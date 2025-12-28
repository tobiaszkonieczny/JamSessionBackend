package com.sap.jamsession.controllers;

import com.sap.jamsession.dtos.UserDto;
import com.sap.jamsession.repositories.UserRepository;
import com.sap.jamsession.services.UserService;
import com.sap.jamsession.validation.UserValidationGroups;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserRepository userRepository;
  private final UserService userService;

  public UserController(UserRepository userRepository, UserService userService) {
    this.userRepository = userRepository;
    this.userService = userService;
  }

  @GetMapping("/all")
  public ResponseEntity<?> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }

  @PostMapping("/register")
  @Validated(UserValidationGroups.OnRegister.class)
  public ResponseEntity<?> addUser(@Valid @RequestBody UserDto user) {
    var resp = userService.registerUser(user);
    return ResponseEntity.created(URI.create("/users/" + resp.getId())).build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getUser(@PathVariable int id) {
    return ResponseEntity.ok(userService.getUserDtoById(id));
  }

  @PatchMapping("/update/{id}")
  public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody UserDto user)
      throws IllegalAccessException {
    return ResponseEntity.ok(userService.update(id, user));
  }

  @DeleteMapping("/delete/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<?> deleteUser(@PathVariable int id) {
    userRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
