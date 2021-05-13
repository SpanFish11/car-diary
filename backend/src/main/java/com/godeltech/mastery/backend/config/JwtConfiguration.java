package com.godeltech.mastery.backend.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class JwtConfiguration {

  @Value("${jwt.token.secret}")
  private String secret;

  @Value("#{T(java.lang.Integer).parseInt('${jwt.token.expiration}')}")
  private Integer expiration;

  @Value("${jwt.token.issuer}")
  private String issuer;

  @Value("${jwt.token.audience}")
  private String audience;
}
