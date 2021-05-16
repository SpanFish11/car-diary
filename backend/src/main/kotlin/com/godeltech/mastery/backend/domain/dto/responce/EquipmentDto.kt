package com.godeltech.mastery.backend.domain.dto.responce

data class EquipmentDto(
    val id: Long?,
    val name: String?,
    val engineType: String?,
    val transmissionType: String?,
    val engineSize: Double?,
    val horsePower: Int?
)
