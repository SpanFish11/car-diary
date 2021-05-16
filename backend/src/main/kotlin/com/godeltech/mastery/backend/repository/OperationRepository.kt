package com.godeltech.mastery.backend.repository

import com.godeltech.mastery.backend.domain.entity.Car
import com.godeltech.mastery.backend.domain.entity.ServiceOperationRecord
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface OperationRepository : JpaRepository<ServiceOperationRecord, Long> {

    fun getAllByCar(car: Car): Optional<List<ServiceOperationRecord>>
}