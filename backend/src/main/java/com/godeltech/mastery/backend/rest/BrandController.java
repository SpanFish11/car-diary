package com.godeltech.mastery.backend.rest;

import com.godeltech.mastery.backend.domain.entity.Brand;
import com.godeltech.mastery.backend.domain.entity.Model;
import com.godeltech.mastery.backend.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@Tag(name = "Brand Controller", description = "Operations about brand")
@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class BrandController {

  private final BrandService brandService;

  @Operation(summary = "Get all brands", description = "Endpoint for getting all brands")
  @GetMapping
  public ResponseEntity<List<Brand>> getAllBrands() {
    return ok(brandService.getAllBrands());
  }

  @Operation(
      summary = "Get all brand models",
      description = "Endpoint for getting all brand models")
  @GetMapping("/{id}/models")
  public ResponseEntity<List<Model>> getAllModelById(@PathVariable Long id) {
    return ok(brandService.getModelsByBrandId(id));
  }
}
