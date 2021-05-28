package com.godeltech.mastery.backend.security;

import static java.lang.Boolean.TRUE;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import com.godeltech.mastery.backend.exception.CustomAuthenticationException;
import io.jsonwebtoken.JwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {

  private static final String BEARER = "Bearer ";

  private final UserDetailsService clientDetailServiceImpl;
  private final JwtUtils jwtUtils;
  private final JwtAuthEntryPoint entryPoint;

  @Override
  protected void doFilterInternal(
      final HttpServletRequest request,
      final HttpServletResponse response,
      final FilterChain filterChain)
      throws ServletException, IOException {

    final var authorizationHeader = request.getHeader(AUTHORIZATION);
    String email = null;
    String jwtToken = null;

    try {
      if (nonNull(authorizationHeader) && authorizationHeader.startsWith(BEARER)) {
        jwtToken = authorizationHeader.substring(BEARER.length());
        email = jwtUtils.getUsernameFromToken(jwtToken);
      }
      if (nonNull(email) && isNull(getContext().getAuthentication())) {
        final var userDetails = clientDetailServiceImpl.loadUserByUsername(email);
        final var bool = jwtUtils.validateToken(jwtToken, userDetails);
        if (TRUE.equals(bool)) {
          final var authentication =
              new UsernamePasswordAuthenticationToken(
                  userDetails, null, userDetails.getAuthorities());
          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          getContext().setAuthentication(authentication);
        }
      }
      filterChain.doFilter(request, response);
    } catch (final AuthenticationException | JwtException ex) {
      entryPoint.commence(
          request, response, new CustomAuthenticationException(ex.getMessage(), ex));
    }
  }
}
