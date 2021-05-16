package com.godeltech.mastery.backend.service.impl

import com.godeltech.mastery.backend.domain.dto.responce.MaintenanceDto
import com.godeltech.mastery.backend.domain.entity.Maintenance
import com.godeltech.mastery.backend.repository.MaintenanceRepository
import com.godeltech.mastery.backend.service.MaintenanceService
import org.springframework.stereotype.Service

@Service
class MaintenanceServiceImpl(private val maintenanceRepository: MaintenanceRepository) : MaintenanceService {

    override fun getAllMaintenance(): List<MaintenanceDto> = maintenanceRepository.findAll().map(Maintenance::toDto)
}