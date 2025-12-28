package com.sap.jamsession.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtToPrincipalConverter {

  public UserPrincipal convert(DecodedJWT decodedJWT) {
    return UserPrincipal.builder()
        .userId(Integer.parseInt(decodedJWT.getSubject()))
        .email(decodedJWT.getClaim("email").asString())
        .authorities(extractAuthoritiesFromClaim(decodedJWT))
        .build();
  }

  private List<SimpleGrantedAuthority> extractAuthoritiesFromClaim(DecodedJWT jwt) {
    var claim = jwt.getClaim("roles");
    if (claim.isNull() || claim.isMissing()) return List.of();
    List<String> roles = claim.asList(String.class);
    if (roles == null) return List.of();
    return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
  }
}
