package com.godeltech.mastery.backend.rest;

import com.godeltech.mastery.backend.domain.dto.BrandDTO;
import com.godeltech.mastery.backend.domain.dto.ModelDTO;
import com.godeltech.mastery.backend.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class BrandController {

  private final BrandService brandService;

  @GetMapping
  public ResponseEntity<List<BrandDTO>> getAllBrands() {
    return ok(brandService.getAllBrands());
  }

  @GetMapping("/{brand_id}/models")
  public ResponseEntity<List<ModelDTO>> getAllModelById(
      @PathVariable(name = "brand_id") @Min(1) Long id) {
    return ok(brandService.getModelsByBrandId(id));
  }
}
