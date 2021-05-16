package com.godeltech.mastery.backend.domain.dto.responce

import java.time.LocalDate

data class GuaranteeDto(val start: LocalDate?, val end: LocalDate?, val extended: Boolean?)
