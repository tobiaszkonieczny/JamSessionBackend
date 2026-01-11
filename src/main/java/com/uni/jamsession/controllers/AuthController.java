package com.uni.jamsession.controllers;

import com.uni.jamsession.dtos.user.UserLoginDto;
import com.uni.jamsession.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
  private final UserService userService;

  @PostMapping("/login")
  public ResponseEntity<String> login(@Valid @RequestBody UserLoginDto loginRequest) {
    String token = userService.loginUser(loginRequest);
    return ResponseEntity.ok(token);
  }
}
