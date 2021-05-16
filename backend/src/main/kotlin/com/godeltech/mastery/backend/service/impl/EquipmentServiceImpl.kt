package com.godeltech.mastery.backend.service.impl

import com.godeltech.mastery.backend.domain.dto.responce.EquipmentDto
import com.godeltech.mastery.backend.domain.entity.Equipment
import com.godeltech.mastery.backend.exception.EntityNotFoundException
import com.godeltech.mastery.backend.repository.EquipmentRepository
import com.godeltech.mastery.backend.service.EquipmentService
import org.springframework.stereotype.Service

@Service
class EquipmentServiceImpl(private val equipmentRepository: EquipmentRepository) : EquipmentService {

    override fun getEquipmentById(id: Long): Equipment =
        equipmentRepository.findById(id).orElseThrow { EntityNotFoundException("equipment", id) }

    override fun getEquipments(): List<EquipmentDto> = equipmentRepository.findAll().map(Equipment::toDto)
}