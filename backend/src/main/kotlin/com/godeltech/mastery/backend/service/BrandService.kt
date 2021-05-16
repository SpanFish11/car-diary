package com.godeltech.mastery.backend.service

import com.godeltech.mastery.backend.domain.dto.responce.BrandDto
import com.godeltech.mastery.backend.domain.dto.responce.ModelDto
import com.godeltech.mastery.backend.domain.entity.Brand

interface BrandService {

    fun getById(id: Long): Brand

    fun getAllBrands(): MutableList<BrandDto>

    fun getModelsByBrandId(id: Long): MutableList<ModelDto>
}