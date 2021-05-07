package com.godeltech.mastery.backend.rest;

import com.godeltech.mastery.backend.domain.dto.request.GuaranteeCreateRequest;
import com.godeltech.mastery.backend.domain.dto.responce.ExceptionResponseDTO;
import com.godeltech.mastery.backend.domain.dto.responce.GuaranteeDTO;
import com.godeltech.mastery.backend.service.GuaranteeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.ok;

@Tag(name = "Guarantee Controller", description = "Operations about guarantee")
@RestController
@RequestMapping("/api/v1/guarantee")
@RequiredArgsConstructor
public class GuaranteeController {

  private final GuaranteeService guaranteeService;

  @Operation(
      summary = "Add new guarantee",
      description = "Endpoint for added new guarantee for car",
      responses = {
        @ApiResponse(responseCode = "201", description = "Created"),
        @ApiResponse(
            responseCode = "400",
            description = "Bad Request",
            content = @Content(schema = @Schema(implementation = ExceptionResponseDTO.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Not found",
            content = @Content(schema = @Schema(implementation = ExceptionResponseDTO.class))),
        @ApiResponse(
            responseCode = "409",
            description = "Conflict",
            content = @Content(schema = @Schema(implementation = ExceptionResponseDTO.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = @Content(schema = @Schema(implementation = ExceptionResponseDTO.class)))
      })
  @PostMapping("/{car_id}")
  public ResponseEntity<Long> createGuarantee(
      @PathVariable("car_id") @Min(1) final Long id,
      @RequestBody @Valid final GuaranteeCreateRequest request) {
    return new ResponseEntity<>(guaranteeService.createGuarantee(id, request), CREATED);
  }

  @Operation(
      summary = "Get guarantee by car id",
      description = "Endpoint for getting guarantee by car id",
      responses = {
        @ApiResponse(responseCode = "200", description = "Ok"),
        @ApiResponse(
            responseCode = "400",
            description = "Bad Request",
            content = @Content(schema = @Schema(implementation = ExceptionResponseDTO.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Not found",
            content = @Content(schema = @Schema(implementation = ExceptionResponseDTO.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = @Content(schema = @Schema(implementation = ExceptionResponseDTO.class)))
      })
  @GetMapping("/{car_id}")
  public ResponseEntity<GuaranteeDTO> getGuarantee(@PathVariable("car_id") @Min(1) final Long id) {
    return ok(guaranteeService.getGuarantee(id));
  }
}
