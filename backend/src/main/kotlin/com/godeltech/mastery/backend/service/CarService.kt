package com.godeltech.mastery.backend.service

import com.godeltech.mastery.backend.domain.dto.request.Filter
import com.godeltech.mastery.backend.domain.entity.Car
import org.springframework.data.domain.Page

interface CarService {

    // TODO на дто сделать
    fun getAllCarsOrFindByFilter(filter: Filter, page: Int, pageSize: Int): Page<Car>

    fun findCarById(id: Long): Car

    fun getCarById(id: Long): Car
}