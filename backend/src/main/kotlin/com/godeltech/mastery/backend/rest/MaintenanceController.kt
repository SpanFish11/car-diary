package com.godeltech.mastery.backend.rest

import com.godeltech.mastery.backend.domain.dto.responce.MaintenanceDto
import com.godeltech.mastery.backend.service.MaintenanceService
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/maintenances")
class MaintenanceController(private val maintenanceService: MaintenanceService) {

    @GetMapping
    fun getAllMaintenances(): ResponseEntity<List<MaintenanceDto>> = ok(maintenanceService.getAllMaintenance())
}