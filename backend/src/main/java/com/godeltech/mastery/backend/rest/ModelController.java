package com.godeltech.mastery.backend.rest;

import com.godeltech.mastery.backend.domain.dto.ModelDTO;
import com.godeltech.mastery.backend.service.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/models")
@RequiredArgsConstructor
public class ModelController {

  private final ModelService modelService;

  @GetMapping
  public ResponseEntity<List<ModelDTO>> getAllModels() {
    return ok(modelService.getAllModels());
  }
}
