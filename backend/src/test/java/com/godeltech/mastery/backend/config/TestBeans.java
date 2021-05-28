package com.godeltech.mastery.backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.godeltech.mastery.backend.util.TestUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

@Configuration
public class TestBeans {

  @Bean
  public TestUtils templateDoc(
      final ResourceLoader resourceLoader, final ObjectMapper objectMapper) {
    return new TestUtils(resourceLoader, objectMapper);
  }
}
