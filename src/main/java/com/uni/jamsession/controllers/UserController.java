package com.uni.jamsession.controllers;

import com.uni.jamsession.dtos.user.UserDto;
import com.uni.jamsession.dtos.user.UserEditDto;
import com.uni.jamsession.dtos.user.UserRegisterDto;
import com.uni.jamsession.facade.UserFacade;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

  private final UserFacade userFacade;


  @GetMapping("/all")
  public ResponseEntity<List<UserDto>> getAllUsers() {
    return ResponseEntity.ok(userFacade.getAllUsers());
  }

  @PostMapping("/register")
  public ResponseEntity<?> addUser(@Valid @RequestBody UserRegisterDto user) {
    UserDto userDto = userFacade.registerUser(user);
    return ResponseEntity.created(URI.create("/users/" + userDto.id())).build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDto> getUser(@PathVariable int id) {
    return ResponseEntity.ok(userFacade.getUserById(id));
  }

  @PatchMapping("/update/{id}")
  public ResponseEntity<?> editUser(@PathVariable int id, @RequestBody UserEditDto user) throws IllegalAccessException {
    return ResponseEntity.ok(userFacade.editUser(id, user));
  }
}
