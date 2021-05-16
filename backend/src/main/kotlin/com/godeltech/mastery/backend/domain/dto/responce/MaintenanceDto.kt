package com.godeltech.mastery.backend.domain.dto.responce

data class MaintenanceDto(
    val id: Long?,
    val operationNumber: String?,
    val mileage: Int?,
    val timeInterval: Int?,
    val operations: List<MaintenanceOperationDto>?,
    val details: List<DetailDto>?
)
