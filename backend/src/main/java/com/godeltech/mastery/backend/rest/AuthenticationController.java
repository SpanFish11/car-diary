package com.godeltech.mastery.backend.rest;

import com.godeltech.mastery.backend.domain.dto.request.AuthRequest;
import com.godeltech.mastery.backend.domain.dto.responce.AuthResponseDTO;
import com.godeltech.mastery.backend.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

  private final AuthenticationManager authenticationManager;
  private final UserDetailsService clientDetailServiceImpl;
  private final JwtUtils jwtUtils;

  @PostMapping
  public ResponseEntity<AuthResponseDTO> authentication(
      @RequestBody final AuthRequest authRequest) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              authRequest.getEmail(), authRequest.getPassword()));
    } catch (final BadCredentialsException e) {
      throw new BadCredentialsException("Incorrect email or password", e);
    }
    final var customer = clientDetailServiceImpl.loadUserByUsername(authRequest.getEmail());
    final var jwtToken = jwtUtils.generateToken(customer);
    return ok(AuthResponseDTO.builder().token(jwtToken).build());
  }
}
