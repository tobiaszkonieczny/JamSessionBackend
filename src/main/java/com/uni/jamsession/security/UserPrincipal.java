package com.uni.jamsession.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Collection;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Builder
public class UserPrincipal implements UserDetails {

  private final int userId;
  private final String email;
  private final Collection<? extends GrantedAuthority> authorities;
  @JsonIgnore private final String password;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public Integer getId() {
    return userId;
  }

  @Override
  public String getUsername() {
    return email;
  }
}
