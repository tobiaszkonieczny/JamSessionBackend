package com.sap.jamsession.controllers;

import com.sap.jamsession.dtos.UserDto;
import com.sap.jamsession.services.UserService;
import com.sap.jamsession.validation.UserValidationGroups;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final UserService userService;

  public AuthController(UserService userService) {

    this.userService = userService;
  }

  @PostMapping("/login")
  @Validated(UserValidationGroups.OnLogin.class)
  public ResponseEntity<?> login(@Valid @RequestBody UserDto loginRequest) {
    var token = userService.loginUser(loginRequest);
    return ResponseEntity.ok(token);
  }
}
