package com.godeltech.mastery.backend.domain.dto.request

data class Filter(
    val modelId: Long?,
    val vin: String?,
    val lastname: String?,
    val specificYear: Int?,
    val until: Int?,
    val from: Int?
)
