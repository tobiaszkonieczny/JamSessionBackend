package com.sap.jamsession.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class JwtIssuer {

  private final JwtProperties jwtProperties;

  public JwtIssuer(JwtProperties jwtProperties) {
    this.jwtProperties = jwtProperties;
  }

  public String issuer(int userId, String name, String email, List<String> roles) {
    var chronoUnit = ChronoUnit.valueOf(jwtProperties.getUnit().toUpperCase());
    var expiration = jwtProperties.getValue();
    return JWT.create()
        .withSubject(Integer.toString(userId))
        .withExpiresAt(Instant.now().plus(Duration.of(expiration, chronoUnit)))
        .withClaim("email", email)
        .withClaim("name", name)
        .withClaim("roles", roles)
        .sign(Algorithm.HMAC256(jwtProperties.getSecretKey()));
  }
}
