package com.godeltech.mastery.backend.domain.dto.responce

data class CarDto(
    val id: Long?,
    val brand: BrandDto?,
    val model: ModelDto?,
    val year: Int?,
    val photoUrl: String?,
    val vin: String?,
    val mileage: Int?,
    val ours: Boolean?,
    val client: ClientDto?,
    val equipment: EquipmentDto?,
    val used: Boolean?,
    val price: Double?,
    val guarantee: GuaranteeDto,
    val serviceOperations: List<ServiceOperationRecordDto>?
)
