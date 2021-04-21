package com.godeltech.mastery.backend.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

  @GetMapping
  public String greeting(@RequestParam(name = "name", defaultValue = "World") String name) {
    return "Hello " + name;
  }
}
