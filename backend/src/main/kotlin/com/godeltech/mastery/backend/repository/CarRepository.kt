package com.godeltech.mastery.backend.repository

import com.godeltech.mastery.backend.domain.entity.Car
import com.godeltech.mastery.backend.domain.entity.Client
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface CarRepository : JpaRepository<Car, Long>, JpaSpecificationExecutor<Car> {

    fun getAllByClient(client: Client): List<Car>
}