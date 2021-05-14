package com.godeltech.mastery.backend;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.springframework.boot.SpringApplication.run;

// @SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@SpringBootApplication
public class BackendApplication {

  public static void main(String[] args) {
    run(BackendApplication.class, args);
  }
}
