package com.godeltech.mastery.backend.rest;

import com.godeltech.mastery.backend.domain.dto.responce.maintenance.MaintenanceDTO;
import com.godeltech.mastery.backend.service.MaintenanceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@Tag(name = "Maintenance Controller", description = "Operations about maintenance")
@RestController
@RequestMapping("/api/v1/maintenances")
@RequiredArgsConstructor
public class MaintenanceController {

  private final MaintenanceService maintenanceService;

  @GetMapping
  public ResponseEntity<List<MaintenanceDTO>> getAllMaintenances() {
    return ok(maintenanceService.getAllMaintenance());
  }
}
