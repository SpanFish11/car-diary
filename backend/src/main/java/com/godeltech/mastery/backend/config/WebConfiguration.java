package com.godeltech.mastery.backend.config;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;

@Component
public class WebConfiguration
    implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

  @Override
  public void customize(final ConfigurableServletWebServerFactory factory) {
    factory.addErrorPages(new ErrorPage(NOT_FOUND, "/index.html"));
  }
}
