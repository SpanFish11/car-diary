package com.godeltech.mastery.backend.rest;

import com.godeltech.mastery.backend.domain.entity.Brand;
import com.godeltech.mastery.backend.domain.entity.Model;
import com.godeltech.mastery.backend.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class BrandController {

  private final BrandService brandService;

  @GetMapping
  public ResponseEntity<List<Brand>> getAllBrands() {
    return ok(brandService.getAllBrands());
  }

  @GetMapping("/{id}/models")
  public ResponseEntity<List<Model>> getAllModelById(@PathVariable Long id) {
    return ok(brandService.getModelsByBrandId(id));
  }
}
