package com.godeltech.mastery.backend.service

import com.godeltech.mastery.backend.domain.dto.responce.EquipmentDto
import com.godeltech.mastery.backend.domain.entity.Equipment

interface EquipmentService {

    fun getEquipmentById(id: Long) : Equipment

    fun getEquipments() : List<EquipmentDto>
}