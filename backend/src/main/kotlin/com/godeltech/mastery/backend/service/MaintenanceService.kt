package com.godeltech.mastery.backend.service

import com.godeltech.mastery.backend.domain.dto.responce.MaintenanceDto

interface MaintenanceService {

    fun getAllMaintenance(): List<MaintenanceDto>
}