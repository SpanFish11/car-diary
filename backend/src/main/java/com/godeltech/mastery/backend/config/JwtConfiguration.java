package com.godeltech.mastery.backend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("jwt.token")
public class JwtConfiguration {

  private String secret;
  private Integer expiration;
  private String issuer;
  private String audience;
}