package com.godeltech.mastery.backend.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class MailSenderConfigurationProperties {

  @Value("${spring.mail.username}")
  private String userName;

  @Value("${spring.mail.password}")
  private String password;

  @Value("${spring.mail.host}")
  private String host;

  @Value("#{T(java.lang.Integer).parseInt('${spring.mail.port}')}")
  private Integer port;
}
