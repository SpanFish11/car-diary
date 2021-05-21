package com.godeltech.mastery.backend.config;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Profile("test")
@Order(HIGHEST_PRECEDENCE)
public class ApplicationNoSecurity extends WebSecurityConfigurerAdapter {

  @Override
  public void configure(final WebSecurity web) {
    web.ignoring().antMatchers("/**");
  }
}
