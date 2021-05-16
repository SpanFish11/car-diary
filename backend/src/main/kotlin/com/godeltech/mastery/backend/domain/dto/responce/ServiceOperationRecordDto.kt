package com.godeltech.mastery.backend.domain.dto.responce

import java.time.LocalDate

data class ServiceOperationRecordDto(
    val id: Long?,
    val serviceOperationNumber: String?,
    val date: LocalDate?,
    val mileage: Int?,
    val serviceWorks: List<ServiceWorkDto>?,
    val changableParts: List<ChangeablePartDto>?
)
