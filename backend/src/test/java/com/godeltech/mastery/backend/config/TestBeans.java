package com.godeltech.mastery.backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.godeltech.mastery.backend.utils.TemplateDoc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

@Configuration
public class TestBeans {

  @Bean
  public TemplateDoc templateDoc(final ResourceLoader resourceLoader,
      final ObjectMapper objectMapper) {
    return new TemplateDoc(resourceLoader, objectMapper);
  }
}
