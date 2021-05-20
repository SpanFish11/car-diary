package com.godeltech.mastery.backend.config;

import static io.swagger.v3.oas.models.security.SecurityScheme.In.HEADER;
import static io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP;
import static java.util.Arrays.asList;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

  @Bean
  public OpenAPI customOpenAPI(
      final Info info, final SecurityRequirement securityRequirement, final Components components) {
    return new OpenAPI().components(components).info(info).addSecurityItem(securityRequirement);
  }

  @Bean
  public Components components(final SecurityScheme securityScheme) {
    return new Components().addSecuritySchemes("bearer-jwt", securityScheme);
  }

  @Bean
  public SecurityScheme bearerSecurityScheme() {
    return new SecurityScheme()
        .type(HTTP)
        .scheme("bearer")
        .bearerFormat("JWT")
        .in(HEADER)
        .name("Authorization");
  }

  @Bean
  public SecurityRequirement securityRequirement() {
    return new SecurityRequirement().addList("bearer-jwt", asList("read", "write"));
  }

  @Bean
  public Info info() {
    return new Info()
        .title("Car Diary Application API")
        .version("v1")
        .description("Some description");
  }
}
