package com.godeltech.mastery.backend.rest;

import static org.springframework.http.ResponseEntity.ok;

import com.godeltech.mastery.backend.domain.dto.responce.ExceptionResponseDTO;
import com.godeltech.mastery.backend.domain.dto.responce.maintenance.MaintenanceDTO;
import com.godeltech.mastery.backend.service.MaintenanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Maintenance Controller", description = "Operations about maintenance")
@RestController
@RequestMapping("/api/v1/maintenances")
@RequiredArgsConstructor
public class MaintenanceController {

  private final MaintenanceService maintenanceService;

  @Operation(
      summary = "Get all maintenance",
      description = "Endpoint for getting all regular technical services",
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
  @GetMapping
  public ResponseEntity<List<MaintenanceDTO>> getAllMaintenances() {
    return ok(maintenanceService.getAllMaintenance());
  }
}
