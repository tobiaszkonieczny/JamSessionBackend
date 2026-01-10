package com.uni.jamsession.controllers;

import com.uni.jamsession.dtos.UserDto;
import com.uni.jamsession.services.UserService;
import com.uni.jamsession.validation.UserValidationGroups;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
  private final UserService userService;

  @PostMapping("/login")
  @Validated(UserValidationGroups.OnLogin.class)
  public ResponseEntity<?> login(@Valid @RequestBody UserDto loginRequest) {
    var token = userService.loginUser(loginRequest);
    return ResponseEntity.ok(token);
  }
}
