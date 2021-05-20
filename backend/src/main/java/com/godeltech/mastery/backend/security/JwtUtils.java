package com.godeltech.mastery.backend.security;

import static io.jsonwebtoken.Claims.AUDIENCE;
import static io.jsonwebtoken.Claims.EXPIRATION;
import static io.jsonwebtoken.Claims.ID;
import static io.jsonwebtoken.Claims.ISSUED_AT;
import static io.jsonwebtoken.Claims.ISSUER;
import static io.jsonwebtoken.Claims.SUBJECT;
import static io.jsonwebtoken.Jwts.builder;
import static io.jsonwebtoken.Jwts.parserBuilder;
import static io.jsonwebtoken.SignatureAlgorithm.HS512;
import static io.jsonwebtoken.io.Decoders.BASE64;
import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.getInstance;
import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.toList;

import com.godeltech.mastery.backend.config.JwtConfiguration;
import com.godeltech.mastery.backend.domain.entity.Client;
import io.jsonwebtoken.Claims;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtUtils {

  private static final String TOKEN_TYPE = "JWT";
  private static final String ROLES = "roles";
  private static final String SUBJECT_ID = "sub_id";

  private final JwtConfiguration jwtConfiguration;

  public String generateToken(final Client client) {
    final var claims = new HashMap<String, Object>();
    claims.put(ISSUER, jwtConfiguration.getIssuer());
    claims.put(SUBJECT, client.getEmail());
    claims.put(SUBJECT_ID, client.getId());
    claims.put(ROLES, getEncryptedRoles(client));
    claims.put(AUDIENCE, jwtConfiguration.getAudience());
    claims.put(EXPIRATION, generateExpirationDate());
    claims.put(ISSUED_AT, generateCurrentDate());
    claims.put(ID, randomUUID());
    return generateToken(claims);
  }

  private String generateToken(final Map<String, Object> claims) {
    return builder()
        .signWith(hmacShaKeyFor(BASE64.decode(jwtConfiguration.getSecret())), HS512)
        .setHeaderParam("typ", TOKEN_TYPE)
        .setClaims(claims)
        .compact();
  }

  private Claims getAllClaimsFromToken(final String token) {
    return parserBuilder()
        .setSigningKey(hmacShaKeyFor(BASE64.decode(jwtConfiguration.getSecret())))
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private <T> T getClaimFromToken(final String token, final Function<Claims, T> claimsResolver) {
    final var claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  public String getUsernameFromToken(final String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  public Date getCreatedDateFromToken(final String token) {
    return (Date) getAllClaimsFromToken(token).get(ISSUED_AT);
  }

  public Date getExpirationDateFromToken(final String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  private Date generateCurrentDate() {
    return new Date();
  }

  private Date generateExpirationDate() {
    final var calendar = getInstance();
    calendar.add(MILLISECOND, jwtConfiguration.getExpiration());
    return calendar.getTime();
  }

  private Boolean isTokenExpired(final String token) {
    final var expiration = this.getExpirationDateFromToken(token);
    return expiration.before(this.generateCurrentDate());
  }

  private List<String> getEncryptedRoles(final Client client) {
    return client.getRoles().stream()
        .map(role -> role.getRoleName().replace("ROLE_", ""))
        .map(String::toLowerCase)
        .collect(toList());
  }

  public Boolean validateToken(final String token, final UserDetails userDetails) {
    final var username = getUsernameFromToken(token);
    return username.equals(userDetails.getUsername());
  }
}
