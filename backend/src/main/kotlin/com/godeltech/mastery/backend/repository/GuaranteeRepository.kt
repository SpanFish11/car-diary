package com.godeltech.mastery.backend.repository

import com.godeltech.mastery.backend.domain.entity.Car
import com.godeltech.mastery.backend.domain.entity.Guarantee
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface GuaranteeRepository : JpaRepository<Guarantee, Long> {

    fun findByCar(car: Car): Optional<Guarantee>
}