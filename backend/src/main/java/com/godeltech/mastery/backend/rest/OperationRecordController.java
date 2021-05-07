package com.godeltech.mastery.backend.rest;

import com.godeltech.mastery.backend.domain.dto.request.OperationCreateRequest;
import com.godeltech.mastery.backend.domain.dto.responce.ExceptionResponseDTO;
import com.godeltech.mastery.backend.domain.dto.responce.ServiceOperationRecordDTO;
import com.godeltech.mastery.backend.service.OperationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.ok;

@Tag(
    name = "Service operation record Controller",
    description = "Operations about service operation record")
@RestController
@RequestMapping("/api/v1/operations")
@RequiredArgsConstructor
public class OperationRecordController {

  private final OperationService operationService;

  @Operation(
      summary = "Add new service operation record",
      description = "Endpoint for added new service operation record for car",
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
  public ResponseEntity<Long> saveOperation(
      @PathVariable("car_id") @Min(1) final Long id,
      @RequestBody @Valid final OperationCreateRequest operationCreateRequest) {
    return new ResponseEntity<>(
        operationService.createOperation(id, operationCreateRequest), CREATED);
  }

  @Operation(
      summary = "Get service operation records by car id",
      description = "Endpoint for getting full history of service operations for current car",
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
  public ResponseEntity<List<ServiceOperationRecordDTO>> getCarOperations(
      @PathVariable("car_id") @Min(1) final Long id) {
    return ok(operationService.getAllRecordsByCarId(id));
  }

  @Operation(
      summary = "Generate and print full history of service operations",
      description = "Endpoint for generation and printing full history of service operations",
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
  @PostMapping("/{car_id}/print")
  public ResponseEntity<HttpStatus> printCarOperation(
      @PathVariable("car_id") @Min(1) final Long id,
      @RequestParam final String path,
      @RequestParam(required = false) final String fileFormat) {
    operationService.createReport(id, path, fileFormat);
    return ok().build();
  }
}
