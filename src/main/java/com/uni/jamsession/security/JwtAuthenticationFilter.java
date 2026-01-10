package com.uni.jamsession.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtDecoder jwtDecoder;
  private final JwtToPrincipalConverter jwtToPrincipalConverter;
  private final HandlerExceptionResolver handlerExceptionResolver;

  public JwtAuthenticationFilter(
      JwtDecoder jwtDecoder,
      JwtToPrincipalConverter jwtToPrincipalConverter,
      HandlerExceptionResolver handlerExceptionResolver) {
    this.jwtDecoder = jwtDecoder;
    this.jwtToPrincipalConverter = jwtToPrincipalConverter;
    this.handlerExceptionResolver = handlerExceptionResolver;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      extractTokenFromRequest(request)
          .map(jwtDecoder::decode)
          .map(jwtToPrincipalConverter::convert)
          .map(UserPrincipleAuthenticationToken::new)
          .ifPresent(
              authentication ->
                  SecurityContextHolder.getContext().setAuthentication(authentication));
    } catch (Exception ex) {
      handlerExceptionResolver.resolveException(request, response, null, ex);
      return;
    }

    filterChain.doFilter(request, response);
  }

  private Optional<String> extractTokenFromRequest(HttpServletRequest request) {
    var token = request.getHeader("Authorization");
    if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
      return Optional.of(token.substring(7));
    }
    return Optional.empty();
  }
}
