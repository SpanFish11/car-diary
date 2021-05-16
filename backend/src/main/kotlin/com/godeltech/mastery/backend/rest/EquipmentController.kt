package com.godeltech.mastery.backend.rest

import com.godeltech.mastery.backend.domain.dto.responce.EquipmentDto
import com.godeltech.mastery.backend.service.EquipmentService
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/equipments")
class EquipmentController(private val equipmentService: EquipmentService) {

    @GetMapping
    fun getAllEqu(): ResponseEntity<List<EquipmentDto>> = ok(equipmentService.getEquipments())
}