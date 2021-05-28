package com.godeltech.mastery.backend.rest;

import static org.springframework.http.ResponseEntity.ok;

import com.godeltech.mastery.backend.domain.dto.responce.BrandDTO;
import com.godeltech.mastery.backend.domain.dto.responce.ExceptionResponseDTO;
import com.godeltech.mastery.backend.domain.dto.responce.ModelDTO;
import com.godeltech.mastery.backend.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Brand Controller", description = "Operations about brand")
@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class BrandController {

  private final BrandService brandService;

  @Operation(
      summary = "Get all brands",
      description = "Endpoint for getting all brands",
      responses = {
        @ApiResponse(responseCode = "200", description = "Ok"),
        @ApiResponse(
            responseCode = "400",
            description = "Bad Request",
            content = @Content(schema = @Schema(implementation = ExceptionResponseDTO.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = @Content(schema = @Schema(implementation = ExceptionResponseDTO.class)))
      })
  @GetMapping
  public ResponseEntity<List<BrandDTO>> getAllBrands() {
    return ok(brandService.getAllBrands());
  }

  @Operation(
      summary = "Get all brand models",
      description = "Endpoint for getting all brand models",
      responses = {
        @ApiResponse(responseCode = "200", description = "Ok"),
        @ApiResponse(
            responseCode = "400",
            description = "Bad Request",
            content = @Content(schema = @Schema(implementation = ExceptionResponseDTO.class))),
        @ApiResponse(
            responseCode = "409",
            description = "Not found",
            content = @Content(schema = @Schema(implementation = ExceptionResponseDTO.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = @Content(schema = @Schema(implementation = ExceptionResponseDTO.class)))
      })
  @GetMapping("/{brand_id}/models")
  public ResponseEntity<List<ModelDTO>> getAllModelById(
      @PathVariable(name = "brand_id") @Min(1) final Long id) {
    return ok(brandService.getModelsByBrandId(id));
  }
}
