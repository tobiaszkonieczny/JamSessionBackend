package com.sap.jamsession.security;

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

  @Builder
  public UserPrincipal(
      int userId,
      String email,
      Collection<? extends GrantedAuthority> authorities,
      String password) {
    this.userId = userId;
    this.email = email;
    this.authorities = authorities;
    this.password = password;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  public Integer getId() {
    return userId;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
